use serde::{Deserialize, Serialize};
use utoipa::ToSchema;

#[derive(Clone, Debug, Serialize, Deserialize, ToSchema)]
pub struct AnalysisMetadata {
    pub id: i32,
    pub location: String,
    pub paragraphs: i32,
    pub words: i32,
    pub symbols: i32,
    pub plagiarism: bool
}