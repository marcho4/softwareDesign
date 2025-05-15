use std::path::Path;
use chrono::Utc;
use sqlx::Error;
use crate::repositories::repo::Repo;
use crate::services::file_service::FileService;

pub struct StorageService {
    repo: Repo
}

impl StorageService {
    pub fn new(repo: Repo) -> Self {
        Self {
            repo
        }
    }

    pub async fn upload(
        &self,
        temp_path: &Path,
        filename: String,
    ) -> Result<i32, Box<dyn std::error::Error>> {
        let hash = FileService::get_file_hash(temp_path)?;
        
        // if let Ok(id) = self.repo.check_if_hash_exists(hash.clone()).await {
        //     return Ok(id);
        // };
        let timestamp = Utc::now().format("%Y%m%d_%H%M%S").to_string();
        let new_filename = format!("{}_{}", timestamp, filename);
        
        FileService::save_file(temp_path, new_filename.clone())?;
        
        let file_id = self.repo.upload_info_about_file(
            hash,
            filename.clone(),
            "content/".to_owned() + new_filename.as_str()
        ).await?;
        
        Ok(file_id)
    }
    
    pub async fn get_file_content(&self, id: i32) -> Result<String, Box<dyn std::error::Error>> {
        let file_info = self.repo.get_file_info(id).await?;
        Ok(FileService::get_file_content(file_info.location)?)
    }
    
    pub async fn get_plagiarism(&self, id: i32) -> Result<bool, Error> {
        let file_hash = self.repo.get_file_info(id).await?.hash;
        let result = self.repo.get_hash_count(file_hash).await?;
        Ok(result)
    }
}