use crate::infrastructure::repo::Repo;
use crate::infrastructure::storage_repo::StorageRepo;
use crate::infrastructure::words_cloud_repo::WordsRepo;
use crate::models::analysis_metadata::AnalysisMetadata;
use crate::models::analysis_result::AnalysisResult;
use super::file_service::FileService;

pub struct AnalysisService {
    words_service: WordsRepo,
    storage_repo: StorageRepo,
    repo: Repo
}

impl AnalysisService {
    pub fn new(words_service: WordsRepo, storage_repo: StorageRepo, repo: Repo) -> Self {
        Self {
            words_service,
            storage_repo,
            repo
        }
    }

    pub async fn analyze_file(&self, id: i32) -> Result<AnalysisMetadata, Box<dyn std::error::Error>> {
        match self.repo.get_analysis_by_file_id(id).await {
            Ok(analysis) => return Ok(analysis),
            Err(_) => {}
        }
        
        let content = self.storage_repo.get_file_content(id).await?;
        
        let is_plagiarism = self.storage_repo.check_plagiarism(id).await?;

        let image_bytes = self.words_service.get_words_image_bytes(content.clone()).await?;
        
        let _ = FileService::save_image(image_bytes, id.to_string() + ".png")?;
        
        let analysis_result = AnalysisService::analyze_words(content.as_str());
        
        let metadata = AnalysisMetadata {
            id,
            location: id.to_string() + ".png",
            paragraphs: analysis_result.paragraphs,
            words: analysis_result.words,
            symbols: analysis_result.symbols,
            plagiarism: is_plagiarism,
        };
        
        self.repo.save_analysis(metadata.clone()).await?;
        
        Ok(metadata)
    }
    
    pub fn get_image_by_location(&self, location: String) -> Result<Vec<u8>, Box<dyn std::error::Error>> {
        Ok(FileService::get_image(location)?)
    }
    

    fn analyze_words(content: &str) -> AnalysisResult {
        let symbols = content.chars().count() as i32;
        let words   = content.split_whitespace().count() as i32;

        let mut paragraphs = 0;
        let mut in_para     = false;

        for line in content.lines() {
            if line.trim().is_empty() {
                in_para = false;
            } else if !in_para {
                paragraphs += 1;
                in_para     = true;
            }
        }

        AnalysisResult { words, paragraphs, symbols }
    }
}   