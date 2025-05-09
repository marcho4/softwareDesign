use actix_web::web;
use actix_web::web::ServiceConfig;
use crate::api::get_file_content::get_file_content;
use crate::api::upload::upload;

pub fn config(cfg: &mut ServiceConfig) {
    cfg.service(
        web::scope("/files")
            .service(upload)
            .service(get_file_content)
    );
}