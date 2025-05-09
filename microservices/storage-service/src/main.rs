mod api;
mod services;
mod repositories;
mod models;

use std::sync::Arc;
use actix_web::{web, App, HttpServer};
use actix_web::middleware::Logger;
use env_logger::Env;
use sqlx::postgres::PgPoolOptions;
use sqlx::migrate::Migrator;
use utoipa::OpenApi;
use utoipa_rapidoc::RapiDoc;
use crate::api::config::config;
use crate::api::openapi::OpenApiDocs;
use crate::repositories::repo::Repo;

static MIGRATOR: Migrator = sqlx::migrate!("src/migrations");

#[actix_web::main]
async fn main() -> std::io::Result<()> {
    dotenv::dotenv().ok();
    env_logger::init_from_env(Env::default().default_filter_or("info"));

    let openapi = OpenApiDocs::openapi();

    let db_user = dotenv::var("POSTGRES_USER").expect("POSTGRES_USER must be set");
    let db_password = dotenv::var("POSTGRES_PASSWORD").expect("POSTGRES_PASSWORD must be set");
    let db_name = dotenv::var("DATABASE_NAME").expect("DATABASE_NAME must be set");
    let db_host = dotenv::var("POSTGRES_HOST").expect("POSTGRES_HOST must be set");
    
    let pool = PgPoolOptions::new()
        .max_connections(1)
        .connect(format!("postgres://{db_user}:{db_password}@{db_host}:5432/{db_name}").as_str()).await;

    let pool = match pool {
        Ok(pool) => Arc::new(pool),
        Err(e) => panic!("{}", e.to_string())
    };
    
    let db_repo = web::Data::new(Repo {
        pool: pool.clone()
    });

    MIGRATOR.run(&*pool).await.unwrap();

    HttpServer::new(move || {
        App::new()
            .wrap(Logger::default())
            .app_data(db_repo.clone())
            .configure(config)
            .service(
                RapiDoc::with_openapi("/api-docs/openapi.json", openapi.clone())
                    .path("/rapidoc"),
            )
    })
        .bind("0.0.0.0:8001")?
        .run()
        .await
}