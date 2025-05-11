use actix_web::{get, web, HttpResponse, Responder};
use crate::models::analysis_metadata::AnalysisMetadata;
use crate::models::error_response::ErrorResponse;
use crate::services::gateway::Gateway;

#[utoipa::path(
    get,
    path="/analysis/{id}",
    description="Получить анализ файла по его id. Анализ включает в себя кол-во слов, символов, абзацев и проверку на плагиат",
    summary="Получить анализ файла",
    tag="Analysis",
    params(
        ("id" = i32, description = "ID файла в системе"),
    ),
    responses(
        (status = 200, description = "Файл успешно проанализирован", body = AnalysisMetadata),
        (status = 500, description = "Ошибка при загрузке файла", body = ErrorResponse, example = json!({"error": "Ошибка", "message": "Ошибка при загрузке файла"}))
    )
)]
#[get("/{id}")]
pub async fn check_file_by_id(
    id: web::Path<i32>,
    gateway: web::Data<Gateway>
) -> impl Responder {
    let id = id.into_inner();
    
    match gateway.analysis_file(id).await {
       Ok(result) => {
           HttpResponse::Ok().json(result)
       },
        Err(err) => {
           HttpResponse::InternalServerError().json(ErrorResponse {
               error: err.to_string(),
               message: "Failed to get analysis".to_string(),
           })
        }
    }
    
}