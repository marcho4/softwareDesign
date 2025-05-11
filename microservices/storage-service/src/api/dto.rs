use serde::Deserialize;
use utoipa::ToSchema;

#[derive(serde::Serialize, ToSchema, Deserialize)]
pub struct FileContentResponse {
    pub content: String,
}

#[derive(serde::Serialize, ToSchema)]
pub struct ErrorResponse {
    pub error: String,
    pub message: String,
}

#[derive(serde::Serialize, ToSchema)]
pub struct SuccessResponse {
    pub id: i32
}

#[derive(ToSchema)]
pub struct UploadFormSchema {
    #[schema(format = Binary)]
    pub file: String,
}