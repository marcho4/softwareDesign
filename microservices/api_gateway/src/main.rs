use api::analysis::config_file_analysis::file_storage_config;
use api::storage::config_file_service::file_analysis_config;
use services::gateway::Gateway;
use crate::api::openapi::OpenApiDocs;
use actix_web::{App, HttpServer, web};
use utoipa::OpenApi;
use utoipa_swagger_ui::SwaggerUi;

mod api;
mod services;
mod models;

#[actix_web::main]
async fn main() -> std::io::Result<()> {
    dotenv::dotenv().ok();

    let storage_url = std::env::var("STORAGE_URL").expect("STORAGE_URL is not set");
    let analysis_url = std::env::var("ANALYSIS_URL").expect("ANALYSIS_URL is not set");

    let openapi = OpenApiDocs::openapi();

    let gateway = web::Data::new(Gateway::new(storage_url, analysis_url));
    
    HttpServer::new(move || {
        App::new()
            .app_data(gateway.clone())
            .configure(file_storage_config)
            .configure(file_analysis_config)
            .service(
                SwaggerUi::new("/swagger-ui/{_:.*}")
                    .url("/api-docs/openapi.json", openapi.clone())
            )
    })
    .bind("0.0.0.0:8000")?
    .run()
    .await
}

