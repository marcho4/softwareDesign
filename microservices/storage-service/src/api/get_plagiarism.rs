use actix_web::{get, web, HttpResponse, Responder};
use crate::api::dto::ErrorResponse;
use crate::models::plagiarism::Plagiarism;
use crate::services::storage_service::StorageService;


#[utoipa::path(
    get,
    path="/files/plagiarism/{id}",
    description="Ищет есть ли в БД повторяющийся хэш",
    summary="Получение является ли файл дубликатом или нет",
    tag="Storage",
    params(
        ("id" = i32, description = "Идентификатор файла"),
    ),
    responses(
        (status = 200, description = "Файл успешно проверен на плагиат", body = Plagiarism, example = json!({"plagiarism": true})),
        (status = 500, description = "Ошибка при получении содержимого файла или информации о файле", body = ErrorResponse, example = json!({"error": "Ошибка", "message": "Ошибка при получении содержимого файла"}))
    ),
)]
#[get("/plagiarism/{id}")]
pub async fn get_plagiarism(
    id: web::Path<i32>,
    service: web::Data<StorageService>
) -> impl Responder {
    let id = id.into_inner();
    let service = service.into_inner();

    match service.get_plagiarism(id).await {
        Ok(plagiarism) => HttpResponse::Ok().json(Plagiarism {
            plagiarism
        }),
        Err(e) => HttpResponse::InternalServerError().json(ErrorResponse{
            error: e.to_string(),
            message: "Failed to get info about file".to_string()
        })
    }
}