use std::error::Error;
use std::fs;
use std::io::Read;
use std::path::{Path, PathBuf};
use sha2::{Digest, Sha256};

pub struct FileService;

impl FileService {
    pub fn get_file_hash(path: &Path) -> Result<String, Box<dyn Error>> {
        let mut file = fs::File::open(path)?;
        let mut hasher = Sha256::new();
        let mut buffer = [0u8; 4096];
        loop {
            let n = file.read(&mut buffer)?;
            if n == 0 {
                break;
            }
            hasher.update(&buffer[..n]);
        }
        let hash = hasher.finalize();
        let hash_hex = format!("{:x}", hash);
        Ok(hash_hex)
    }

    pub fn save_file(file_path: &Path, file_name: String) -> Result<(), Box<dyn Error>> {
        let mut dest = PathBuf::from("content");
        dest.push(&file_name);
        fs::copy(file_path, &dest)?;
        Ok(())
    }


    pub fn get_file_content(file_path: String) -> Result<String, Box<dyn Error>> {
        let file_path = Path::new(&file_path);
        let mut file = fs::File::open(file_path)?;
        let mut content = String::new();
        file.read_to_string(&mut content)?;
        Ok(content)
    }
}
