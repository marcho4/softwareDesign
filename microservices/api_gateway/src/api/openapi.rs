use utoipa::OpenApi;

use crate::api::storage::get_file_by_id::__path_get_file_by_id;

use crate::api::analysis::check_by_file_id::__path_check_file_by_id;
use crate::api::analysis::get_word_cloud::__path_get_word_cloud_image;
use crate::api::storage::uplaod_file::__path_upload_file;

#[derive(OpenApi)]
#[openapi(
    info(
        title = "API Gateway",
        description = "Gateway API for the file system application",
        version = "1.0.0",
    ),
    paths(
        get_file_by_id,
        check_file_by_id,
        upload_file, 
        get_word_cloud_image
    )
)]
pub struct OpenApiDocs;