use actix_web::{get, web, HttpResponse, Responder};
use crate::models::error_response::ErrorResponse;
use crate::models::file_content_response::FileContentResponse;
use crate::services::gateway::Gateway;

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
pub async fn get_file_by_id(
    id: web::Path<i32>,
    gateway: web::Data<Gateway>,
) -> impl Responder {
    let id = id.into_inner();
    
    match gateway.get_file_content(id).await {
        Ok(content) => HttpResponse::Ok().json(FileContentResponse {
            content 
        }),
        Err(err) => HttpResponse::InternalServerError().json(ErrorResponse {
            error: err.to_string(),
            message: "Failed to get content".to_string(),
        }),
    }
}