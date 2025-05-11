use actix_web::web;
use actix_web::web::ServiceConfig;
use crate::api::analysis::check_by_file_id::check_file_by_id;
use crate::api::analysis::get_word_cloud::get_word_cloud_image;

pub fn file_storage_config(cfg: &mut ServiceConfig) {
    cfg.service(
        web::scope("/analysis")
            .service(get_word_cloud_image)
            .service(check_file_by_id)
    );
}