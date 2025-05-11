use crate::models::error_respose::ErrorResponse;
use crate::models::file_content::FileContent;
use crate::models::my_error::MyError;
use crate::models::plagiarism::Plagiarism;

pub struct StorageRepo {
    client: reqwest::Client,
    storage_url: String
}

impl StorageRepo {
    pub fn new(storage_url: String) -> Self {
        Self {
            client: reqwest::Client::new(),
            storage_url
        }
    }

    pub async fn get_file_content(&self, id: i32) -> Result<String, Box<dyn std::error::Error>> {
        let response = self.client
            .get(format!("{}/files/content/{}", self.storage_url, id))
            .send()
            .await?;

        let status = response.status();
        let text = response.text().await?;
        
        if status == 200 {
            let r = serde_json::from_str::<FileContent>(&text)?;
            Ok(r.content)
        } else {
            let error_message = match serde_json::from_str::<ErrorResponse>(&text) {
                Ok(error_resp) => error_resp.message,
                Err(_) => format!("Неизвестная ошибка. Статус: {}, Текст: {}", status, text),
            };

            Err(Box::new(MyError(error_message)))
        }
    }

    pub async fn check_plagiarism(&self, id: i32) -> Result<bool, Box<dyn std::error::Error>> {
        let response = self.client
            .get(format!("{}/files/plagiarism/{}", self.storage_url, id))
            .send()
            .await?;
        
        let status = response.status();
        let text = response.text().await?;
        
        if status == 200 {
            let r = serde_json::from_str::<Plagiarism>(&text)?;
            Ok(r.plagiarism)
        } else {
            let error_message = match serde_json::from_str::<ErrorResponse>(&text) {
                Ok(error_resp) => error_resp.message,
                Err(_) => format!("Неизвестная ошибка. Статус: {}, Текст: {}", status, text),
            };

            Err(Box::new(MyError(error_message)))
        }
    }
}