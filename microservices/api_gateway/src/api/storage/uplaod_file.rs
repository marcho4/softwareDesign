use actix_multipart::form::MultipartForm;
use actix_multipart::form::tempfile::TempFile;
use actix_web::{post, web, HttpResponse, Responder};
use crate::models::error_response::ErrorResponse;
use crate::models::success_response::SuccessResponse;
use crate::services::gateway::Gateway;
use utoipa::ToSchema;

#[derive(ToSchema)]
pub struct UploadFormSchema {
    #[schema(format = Binary)]
    pub file: String,
}

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
pub async fn upload_file(
    MultipartForm(form): MultipartForm<UploadForm>,
    gateway: web::Data<Gateway>
) -> impl Responder {
    let path = form.file.file.path();

    match gateway.upload_file(path).await {
        Ok(res) => HttpResponse::Ok().json(SuccessResponse {
            id: res,
        }),
        Err(err) => HttpResponse::InternalServerError().json(ErrorResponse {
            error: err.to_string(),
            message: "Failed to upload file".to_string()
        })
    }
}