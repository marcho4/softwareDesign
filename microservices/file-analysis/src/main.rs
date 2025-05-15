mod api;
mod services;
mod infrastructure;
mod models;

use std::sync::Arc;
use actix_web::{web, App, HttpServer};
use env_logger::Env;
use sqlx::migrate::Migrator;
use sqlx::postgres::PgPoolOptions;
use utoipa::OpenApi;
use utoipa_swagger_ui::SwaggerUi;
use crate::api::config::config;
use crate::api::openapi::OpenApiDocs;
use crate::infrastructure::repo::Repo;
use crate::infrastructure::words_cloud_repo::WordsRepo;
use crate::infrastructure::storage_repo::StorageRepo;
use crate::services::analysis_service::AnalysisService;


static MIGRATOR: Migrator = sqlx::migrate!("src/migrations");

#[actix_web::main]
async fn main() -> std::io::Result<()> {
    dotenv::dotenv().ok();
    
    env_logger::init_from_env(Env::default().default_filter_or("info"));

    let db_user = dotenv::var("POSTGRES_USER").expect("POSTGRES_USER must be set");
    let db_password = dotenv::var("POSTGRES_PASSWORD").expect("POSTGRES_PASSWORD must be set");
    let db_name = dotenv::var("DATABASE_NAME_ANALYSIS").expect("DATABASE_NAME_ANALYSIS must be set");
    let db_host = dotenv::var("POSTGRES_ANALYSIS_HOST").expect("POSTGRES_ANALYSIS_HOST must be set");
    let db_port = dotenv::var("POSTGRES_ANALYSIS_PORT").expect("POSTGRES_ANALYSIS_PORT must be set");
    let storage_service_url = dotenv::var("STORAGE_URL").expect("STORAGE_URL must be set");
    
    let pool = PgPoolOptions::new()
        .max_connections(1)
        .connect(format!("postgres://{db_user}:{db_password}@{db_host}:{db_port}/{db_name}").as_str()).await;

    let pool = match pool {
        Ok(pool) => Arc::new(pool),
        Err(e) => panic!("{}", e.to_string())
    };

    MIGRATOR.run(&*pool).await.unwrap();
    println!("Database migrations completed");
    
    let word_service = WordsRepo::new();
    let storage_repo = StorageRepo::new(storage_service_url);
    let repo = Repo::new(pool);
    
    let service: web::Data<AnalysisService> = web::Data::new(AnalysisService::new(word_service, storage_repo, repo));

    let openapi = OpenApiDocs::openapi();

    HttpServer::new(move || {
        App::new()
            .app_data(service.clone())
            .configure(config)
            .service(
                SwaggerUi::new("/swagger-ui/{_:.*}")
                    .url("/api-docs/openapi.json", openapi.clone()),
            )
    })
        .bind("0.0.0.0:8002")?
        .run()
        .await
}