use actix_web::{get, web, HttpResponse, Responder};
use serde_json::json;
use crate::repositories::repo::Repo;
use crate::services::file_service::FileService;

#[utoipa::path(
get,
path="/files/content/{id}",
description="Get file content by ID",
summary="Get file information",
tag="Storage"
)]
#[get("/content/{id}")]
pub async fn get_file_content(
    id: web::Path<i32>,
    repo: web::Data<Repo>,
) -> impl Responder {
    let file_location = id.into_inner();
    
    let file_info = match repo.get_file_info(file_location).await {
        Ok(m) => m,
        Err(e) => return HttpResponse::InternalServerError().json(json!({
            "error": e.to_string(),
            "message": "Error getting file info"
        }))
    };
    
    match FileService::get_file_content(file_info.location) {
        Ok(content) => HttpResponse::Ok().json(json!({
            "content": content
        })),
        Err(err) => HttpResponse::InternalServerError().json(json!({
            "error": err.to_string(),
            "message": "Error getting file content"
        })),
    }
}