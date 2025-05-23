use actix_web::{HttpResponse, post, web};
use actix_multipart::form::{tempfile::TempFile, MultipartForm};
use crate::api::dto::{ErrorResponse, SuccessResponse, UploadFormSchema};
use crate::services::storage_service::StorageService;

#[derive(Debug, MultipartForm)]
struct UploadForm {
    #[multipart()]
    file: TempFile,
}

#[utoipa::path(
    post,
    path="/files/upload",
    description="Загрузить файл в систему. Принимает файл и возвращает его идентификатор.",
    summary="Загрузка файла в систему",
    tag="Storage",
    request_body(content = UploadFormSchema, content_type = "multipart/form-data", description = "Форма для загрузки файла. Поле file — сам файл."),
    responses(
        (status = 200, description = "Файл успешно загружен", body = SuccessResponse, example = json!({"id": 1})),
        (status = 500, description = "Ошибка при загрузке файла", body = ErrorResponse, example = json!({"error": "Ошибка", "message": "Ошибка при загрузке файла"}))
    )
)]
#[post("/upload")]
pub async fn upload(
    MultipartForm(form): MultipartForm<UploadForm>,
    repo: web::Data<StorageService>
) -> HttpResponse {
    let temp_path = form.file.file.path();
    let filename = form.file.file_name.unwrap();
    let service = repo.into_inner();
    
    match service.upload(temp_path, filename).await {
        Ok(res) => HttpResponse::Ok().json(SuccessResponse{
            id: res,
        }),
        Err(err) => HttpResponse::InternalServerError().json(ErrorResponse {
            error: err.to_string(),
            message: "Failed to upload file".to_string()
        })
    }
}