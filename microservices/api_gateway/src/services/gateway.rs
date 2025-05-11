use std::path::Path;
use reqwest::Client;
use reqwest::multipart::{Form};
use serde::de::DeserializeOwned;
use crate::models::{
    error::Error, 
    success_response::SuccessResponse
};
use crate::models::analysis_metadata::AnalysisMetadata;
use crate::models::error_response::ErrorResponse;
use crate::models::file_content_response::FileContentResponse;

pub struct Gateway {
    storage_url: String,
    analysis_url: String,
    client: Client,
}

impl Gateway {
    pub fn new(storage_url: String, analysis_url: String) -> Self {
        Self { 
            storage_url, 
            analysis_url,
            client: Client::new() 
        }
    }

    pub async fn upload_file(&self, tmp_file_path: &Path) -> Result<i32, Error> {
        let form = Form::new()
            .file("file", tmp_file_path)
            .await
            .map_err(|e| Error(e.to_string()))?;
        
        let response = self.client.post(format!("{}/files/upload", self.storage_url))
            .multipart(form)
            .send()
            .await
            .map_err(|e| Error(e.to_string()))?;
        
        let success_response = self.process_response::<SuccessResponse>(response).await?;
        Ok(success_response.id)
    }

    pub async fn analysis_file(&self, id: i32) -> Result<AnalysisMetadata, Error> {
        let response = match self.client
            .get(&format!("{}/analysis/{}", self.analysis_url, id))
            .send()
            .await {
                Ok(resp) => resp,
                Err(err) => {
                    return Err(Error(err.to_string()))
                }
            };
        
        self.process_response::<AnalysisMetadata>(response).await
    }

    pub async fn get_file_content(&self, id: i32) -> Result<String, Error> {
        let response = match self.client
            .get(&format!("{}/files/content/{}", self.storage_url, id))
            .send()
            .await {
                Ok(resp) => resp,
                Err(body) => {
                    return Err(Error(body.to_string()))
                }
            };

        let content_response = self.process_response::<FileContentResponse>(response).await?;
        Ok(content_response.content)
    }

    pub async fn get_word_cloud(&self, location: String) -> Result<Vec<u8>, Error> {
        let response = match self.client
            .get(&format!("{}/analysis/word-cloud/{}", self.analysis_url, location))
            .send()
            .await {
                Ok(resp) => resp,
                Err(body) => {
                    return Err(Error(body.to_string()))
                }
            };

        if response.status().is_success() {
            response.bytes().await
                .map(|b| b.to_vec())
                .map_err(|err| Error(err.to_string()))
        } else {
            let error = self.process_response::<ErrorResponse>(response).await?;
            Err(Error(error.error))
        }
    }

    async fn process_response<T: DeserializeOwned>(&self, response: reqwest::Response) -> Result<T, Error> {
        if response.status().is_success() {
            match response.json::<T>().await {
                Ok(data) => Ok(data),
                Err(err) => Err(Error(err.to_string()))
            }
        } else {
            match response.json::<ErrorResponse>().await {
                Ok(error) => Err(Error(error.error)),
                Err(err) => Err(Error(err.to_string()))
            }
        }
    }
}
