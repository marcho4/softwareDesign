use actix_web::{get, web, HttpResponse, Responder};
use crate::models::error_response::ErrorResponse;
use crate::services::gateway::Gateway;

#[utoipa::path(
    get,
    path="/analysis/word-cloud/{location}",
    description="Получить картинку облака слов",
    summary="Получить картинку облака слов",
    tag="Analysis",
    params(
        ("location" = String, description = "Расположение файла в системе"),
    ),
    responses(
        (status = 200, description = "Картинка успешно получена. Возвращает байты", body = Vec<u8>, content_type = "image/png"),
        (status = 500, description = "Ошибка получения картинки", body = ErrorResponse, example = json!({"error": "Ошибка", "message": "Ошибка при получении содержимого файла"}))
    ),
)]
#[get("/word-cloud/{location}")]
pub async fn get_word_cloud_image(
    location: web::Path<String>,
    gateway: web::Data<Gateway>
) -> impl Responder {
    let location = location.into_inner();
    match gateway.get_word_cloud(location).await {
        Ok(image) => HttpResponse::Ok().content_type("image/png").body(image),
        Err(err) => {
            HttpResponse::InternalServerError().json(ErrorResponse {
                error: err.to_string(),
                message: "Failed to get image".to_string(),
            })
        }
    }
}