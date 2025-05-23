mod api;
mod services;
mod repositories;
mod models;

use std::sync::Arc;
use actix_web::{web, App, HttpServer};
use actix_web::middleware::Logger;
use dotenv::dotenv;
use env_logger::Env;
use sqlx::postgres::PgPoolOptions;
use sqlx::migrate::Migrator;
use utoipa::OpenApi;
use utoipa_swagger_ui::SwaggerUi;
use crate::api::config::config;
use crate::api::openapi::OpenApiDocs;
use crate::repositories::repo::Repo;
use crate::services::storage_service::StorageService;

static MIGRATOR: Migrator = sqlx::migrate!("src/migrations");

#[actix_web::main]
async fn main() -> std::io::Result<()> {
    dotenv::dotenv().ok();
    env_logger::init_from_env(Env::default().default_filter_or("info"));

    let openapi = OpenApiDocs::openapi();

    let db_user = dotenv::var("POSTGRES_USER").expect("POSTGRES_USER must be set");
    let db_password = dotenv::var("POSTGRES_PASSWORD").expect("POSTGRES_PASSWORD must be set");
    let db_name = dotenv::var("DATABASE_NAME_STORAGE").expect("DATABASE_NAME_STORAGE must be set");
    let db_host = dotenv::var("POSTGRES_STORAGE_HOST").expect("POSTGRES_STORAGE_HOST must be set");
    let db_port = dotenv::var("POSTGRES_STORAGE_PORT").expect("POSTGRES_STORAGE_PORT must be set");

    let pool = PgPoolOptions::new()
        .max_connections(1)
        .connect(format!("postgres://{db_user}:{db_password}@{db_host}:{db_port}/{db_name}").as_str()).await;

    let pool = match pool {
        Ok(pool) => Arc::new(pool),
        Err(e) => panic!("{}", e.to_string())
    };
    
    let db_repo = Repo::new(pool.clone());
    let service = web::Data::new(StorageService::new(db_repo));

    MIGRATOR.run(&*pool).await.unwrap();

    HttpServer::new(move || {
        App::new()
            .wrap(Logger::default())
            .app_data(service.clone())
            .configure(config)
            .service(
                SwaggerUi::new("/swagger-ui/{_:.*}")
                    .url("/api-docs/openapi.json", openapi.clone()),
            )
    })
        .bind("0.0.0.0:8001")?
        .run()
        .await
}