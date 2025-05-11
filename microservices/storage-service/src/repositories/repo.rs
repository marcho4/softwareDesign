use std::sync::Arc;
use sqlx::{Error, Pool, Postgres, Row};
use crate::models::file_metadata::FileMetadata;

pub struct Repo {
    pool: Arc<Pool<Postgres>>
}

impl Repo {
    pub fn new(pool: Arc<Pool<Postgres>>) -> Self {
        Self { pool }
    }
    pub async fn check_if_hash_exists(&self, hash: String) -> Result<i32, Error> {
        let row = sqlx::query("SELECT * FROM files WHERE hash = $1 ")
            .bind(hash)
            .fetch_one(self.pool.as_ref())
            .await?;
        
        if row.is_empty() {
            return Err(Error::RowNotFound)
        };
        
        let id: i32 = row.get("id");
        Ok(id)
    }

    pub async fn upload_info_about_file(&self, hash: String, name: String, location: String) -> Result<i32, Error> {
        let row = sqlx::query("INSERT INTO files (hash, name, location) VALUES ($1, $2, $3) RETURNING id")
            .bind(hash).bind(name).bind(location)
            .fetch_one(self.pool.as_ref())
            .await?;
        let id: i32 = row.get("id");
        Ok(id)
    }
    
    pub async fn get_file_info(&self, id: i32) -> Result<FileMetadata, Error> {
        let row = sqlx::query("SELECT * FROM files WHERE id = $1")
            .bind(id)
            .fetch_one(self.pool.as_ref()).await?;
        
        Ok(FileMetadata {
            id: row.get("id"),
            hash: row.get("hash"),
            name: row.get("name"),
            location: row.get("location")
        })
    }
    
    pub async fn get_hash_count(&self, hash: String) -> Result<bool, Error> {
        let rows = sqlx::query("SELECT * FROM files WHERE hash = $1")
            .bind(hash)
            .fetch_all(self.pool.as_ref())
            .await?;
        
        Ok(rows.len() > 1)
    }
}