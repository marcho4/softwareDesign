use serde::Deserialize;
use utoipa::ToSchema;

#[derive(serde::Serialize, ToSchema, Deserialize)]
pub struct FileContentResponse {
    pub content: String,
}