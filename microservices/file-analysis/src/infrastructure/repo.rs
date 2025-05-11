use std::sync::Arc;
use sqlx::{Error, Pool, Postgres, Row};
use crate::models::analysis_metadata::AnalysisMetadata;

pub struct Repo {
    pool: Arc<Pool<Postgres>>
}

impl Repo {
    pub fn new(pool: Arc<Pool<Postgres>>) -> Self {
        Self { pool }
    }
    
    pub async fn save_analysis(&self, metadata: AnalysisMetadata) -> Result<(), Error> {
        let _ = sqlx::query("INSERT INTO analysis (id, location, paragraphs, words, symbols, plagiarism) VALUES ($1, $2, $3, $4, $5, $6)")
            .bind(metadata.id)
            .bind(metadata.location)
            .bind(metadata.paragraphs)
            .bind(metadata.words)
            .bind(metadata.symbols)
            .bind(metadata.plagiarism)
            .execute(self.pool.as_ref())
            .await?;
        Ok(())
    }
    
    pub async fn get_analysis_by_file_id(&self, file_id: i32) -> Result<AnalysisMetadata, Error> {
        let row = sqlx::query("SELECT * FROM analysis WHERE id = $1")
            .bind(file_id)
            .fetch_one(self.pool.as_ref())
            .await?;
        
        Ok(AnalysisMetadata {
            id: row.get("id"),
            location: row.get("location"),
            paragraphs: row.get("paragraphs"),
            words: row.get("words"),
            symbols: row.get("symbols"),
            plagiarism: row.get("plagiarism"),
        })
    }
}