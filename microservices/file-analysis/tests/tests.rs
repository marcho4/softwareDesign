#[cfg(test)]
mod tests {
    use reqwest::multipart::Form;
    use file_analysis::models::analysis_metadata::AnalysisMetadata;
    use serde::{Deserialize, Serialize};

    #[derive(Debug, Serialize, Deserialize)]
    pub struct SuccessResponse {
        pub id: i32,
    }

    #[tokio::test]
    async fn analysis_test() {
        let client = reqwest::Client::new();
                
        let form = Form::new()
            .file("file", "images/test.txt")
            .await
            .unwrap();
        
        let response = client.post("http://localhost:8000/files/upload")
            .multipart(form)
            .send()
            .await
            .unwrap();

        let response = response.json::<SuccessResponse>().await.unwrap();
        let id = response.id;
        
        let response = client
            .get(format!("http://localhost:8000/analysis/{}", id))
            .send()
            .await.unwrap();
        
        let body = response.text().await.unwrap();
        let analysis: AnalysisMetadata = serde_json::from_str(&body).unwrap();  

        assert_eq!(analysis.symbols, 849);
        assert_eq!(analysis.paragraphs, 3);
        assert_eq!(analysis.words, 98);

        let response = match client
            .get(&format!("http://localhost:8000/analysis/word-cloud/{}", id))
            .send()
            .await {
                Ok(resp) => resp,
                Err(body) => {
                    panic!("Unable to get word cloud: {}", body);
                }
            };

        let image = response.bytes().await.unwrap();
        let image_bytes = image.to_vec();
        assert!(image_bytes.len() > 0);
    }
}