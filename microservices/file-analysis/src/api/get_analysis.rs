use actix_web::{get, web, HttpResponse, Responder};
use crate::models::analysis_metadata::AnalysisMetadata;
use crate::models::error_respose::ErrorResponse;
use crate::services::analysis_service::AnalysisService;

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
pub async fn get_analysis(
    id: web::Path<i32>,
    service: web::Data<AnalysisService>
) -> impl Responder {
    let id = id.into_inner();
    let service = service.into_inner().clone();
    
    match service.analyze_file(id).await {
        Ok(metadata) => HttpResponse::Ok().json(metadata),
        Err(e) => HttpResponse::InternalServerError().json(ErrorResponse{
            error: e.to_string(),
            message: "Error while analyzing file".to_string()
        })
    }
}
