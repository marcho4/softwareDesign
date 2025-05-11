use actix_web::{web, get, HttpResponse, Responder};
use serde_json::json;
use crate::models::error_respose::ErrorResponse;
use crate::services::analysis_service::AnalysisService;


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
pub async fn get_word_cloud(
    location: web::Path<String>,
    service: web::Data<AnalysisService>
) -> impl Responder {
    let location = location.into_inner();
    
    match service.get_image_by_location(location) {
        Ok(bytes) => HttpResponse::Ok().content_type("image/png").body(bytes),
        Err(e) => {
            HttpResponse::InternalServerError().json(json!({
                "error": e.to_string(),
                "message": "Service could not get word cloud from location"
            }))
        }
    }
}