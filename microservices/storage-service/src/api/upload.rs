use actix_web::{HttpResponse, post, web};
use actix_multipart::form::{tempfile::TempFile, MultipartForm};
use serde_json::json;

use crate::repositories::repo::Repo;
use crate::services::file_service::FileService;

#[derive(Debug, MultipartForm)]
struct UploadForm {
    #[multipart()]
    file: TempFile,
}

#[utoipa::path(
    post,
    path="/files/upload",
    description="Upload file",
    summary="Upload file into system",
    tag="Storage"
)]
#[post("/upload")]
pub async fn upload(
    MultipartForm(form): MultipartForm<UploadForm>,
    repo: web::Data<Repo>
) -> HttpResponse {
    let temp_path = form.file.file.path();
    let filename = form.file.file_name.unwrap();
    
    let hash = match FileService::get_file_hash(temp_path) {
        Ok(hash) => hash,
        Err(e) => {
            return HttpResponse::InternalServerError().json(json!({
                "error": e.to_string(),
                "message": "Error while hashing file"
            }));
        }
    };
    
    if let Ok(id) = repo.check_if_hash_exists(hash.clone()).await {
        return HttpResponse::Ok().json(json!({
            "id": id
        }))
    };
    
    match FileService::save_file(temp_path, filename.clone()) {
        Ok(_) => (),
        Err(e) => return HttpResponse::InternalServerError().json(json!({
            "error": e.to_string(),
            "message": "Error while saving file"
        }))   
    };
    
    let file_id = match repo.upload_info_about_file(
        hash, 
        filename.clone(), 
        "content/".to_owned() + filename.as_str()
    ).await {
        Ok(id) => id,
        Err(e) => return HttpResponse::InternalServerError().json(json!({
                "error": e.to_string(),
                "message": "Error while uploading file info"
            }))
    };
    
    HttpResponse::Ok().json(json!({
        "id": file_id
    }))
}