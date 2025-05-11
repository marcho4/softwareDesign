use actix_web::{get, web, HttpResponse, Responder};
use crate::api::dto::{ErrorResponse, FileContentResponse};
use crate::services::storage_service::StorageService;

#[utoipa::path(
get,
path="/files/content/{id}",
description="Получить содержимое файла по его идентификатору. Возвращает текстовое содержимое файла, если файл найден и доступен. В случае ошибки возвращает описание проблемы.",
summary="Получение содержимого файла по ID",
tag="Storage",
params(
    ("id" = i32, description = "Идентификатор файла"),
),
responses(
    (status = 200, description = "Содержимое файла успешно получено", body = FileContentResponse, example = json!({"content": "Пример содержимого файла"})),
    (status = 500, description = "Ошибка при получении содержимого файла или информации о файле", body = ErrorResponse, example = json!({"error": "Ошибка", "message": "Ошибка при получении содержимого файла"}))
),
)]
#[get("/content/{id}")]
pub async fn get_file_content(
    id: web::Path<i32>,
    repo: web::Data<StorageService>,
) -> impl Responder {
    let file_location = id.into_inner();
    match repo.get_file_content(file_location).await {
        Ok(content) => HttpResponse::Ok().json(FileContentResponse { content }),
        Err(err) => HttpResponse::InternalServerError().json(ErrorResponse {
            error: err.to_string(),
            message: "Failed to get content".to_string(),
        }),
    }
}