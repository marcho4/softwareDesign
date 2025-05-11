use utoipa::OpenApi;

use crate::api::{
    get_word_cloud::__path_get_word_cloud,
    get_analysis::__path_get_analysis,
};

#[derive(OpenApi)]
#[openapi(
    info(
        title = "Analysis Service",
        description = "Analysis Service API for the file system application",
        version = "1.0.0",
    ),
    paths(
        get_word_cloud,
        get_analysis,
    )
)]
pub struct OpenApiDocs;