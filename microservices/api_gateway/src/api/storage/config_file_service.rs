use actix_web::web;
use actix_web::web::ServiceConfig;
use crate::api::storage::get_file_by_id::get_file_by_id;
use crate::api::storage::uplaod_file::upload_file;

pub fn file_analysis_config(cfg: &mut ServiceConfig) {
    cfg.service(
        web::scope("/files")
            .service(get_file_by_id)
            .service(upload_file)
    );
}