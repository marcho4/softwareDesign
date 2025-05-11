#[cfg(test)]
mod tests {
    use std::fs::File;
    use std::io::Read;
    use reqwest::multipart::Form;
    use serde::{Deserialize, Serialize};
    use storage_service::api::dto::FileContentResponse;

    #[derive(Debug, Serialize, Deserialize)]
    pub struct SuccessResponse {
        pub id: i32,
    }

    #[tokio::test]
    async fn analysis_test() {
        let client = reqwest::Client::new();
                
        let form = Form::new()
            .file("file", "content/test.txt")
            .await
            .unwrap();
        
        let response = client.post("http://localhost:8000/files/upload")
            .multipart(form)
            .send()
            .await
            .unwrap();

        let response = response.json::<SuccessResponse>().await.unwrap();
        let id = response.id;
        
        let form_2 = Form::new()
            .file("file", "content/test_copy.txt")
            .await
            .unwrap();
        
        let response_2 = client.post("http://localhost:8000/files/upload")
            .multipart(form_2)
            .send()
            .await
            .unwrap();

        let response_2 = response_2.json::<SuccessResponse>().await.unwrap();
        let id_2 = response_2.id;

        assert_eq!(id, id_2);
        
        let response = client
            .get(&format!("http://localhost:8000/files/content/{}", id))
            .send()
            .await.unwrap();
        
        let response = response.json::<FileContentResponse>().await.unwrap();
        
        let mut content = String::new();
        
        let file_text = File::open("content/test.txt").unwrap().read_to_string(&mut content).unwrap();
        
        assert_eq!(response.content, content);
    }
}