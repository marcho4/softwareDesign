use utoipa::OpenApi;
use crate::api::{ 
    get_file_content::__path_get_file_content,
    upload::__path_upload, 
    get_plagiarism::__path_get_plagiarism
};

#[derive(OpenApi)]
#[openapi(
    info(
        title = "Storage Service",
        description = "Storage Service API for the file system application",
        version = "1.0.0",
    ),
    paths(
        get_file_content,
        upload,
        get_plagiarism
    )
)]
pub struct OpenApiDocs;