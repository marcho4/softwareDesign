use serde::{Serialize, Deserialize};
use utoipa::ToSchema;

#[derive(Serialize, Deserialize, Debug, ToSchema)]
pub struct SuccessResponse {
    pub(crate) id: i32,
}