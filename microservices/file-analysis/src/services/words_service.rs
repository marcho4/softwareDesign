use serde_json::json;

pub struct WordsService {
    client: reqwest::Client,
}

const URL: &str = "https://quickchart.io/wordcloud";

impl WordsService {
    pub fn new() -> Self {
        Self {
            client: reqwest::Client::new(),
        }
    }

    pub async fn get_words_image_bytes(&self, content: String) -> Result<Vec<u8>, reqwest::Error> {
        let body = json!({
            "text": content,
            "format": "png",
        });
        
        let response = self.client
            .post(URL)
            .body(body.to_string())
            .header("Content-Type", "application/json")  
            .send()
            .await?;
        
        let bytes = response.bytes().await?;
        let vec = bytes.as_ref().to_owned();
        
        Ok(vec)
    }
}