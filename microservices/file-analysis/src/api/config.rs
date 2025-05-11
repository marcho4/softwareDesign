use actix_web::web;
use crate::api::get_analysis::get_analysis;
use crate::api::get_word_cloud::get_word_cloud;

pub fn config(cfg: &mut web::ServiceConfig) {
    cfg.service(
        web::scope("/analysis")
            .service(get_analysis)
            .service(get_word_cloud)
    );
}