use std::error::Error;
use std::fs::File;
use std::io::{Read, Write};
use std::path::{Path, PathBuf};

pub struct FileService;

impl FileService {
    pub fn save_image(buffer: Vec<u8>, file_name: String) -> Result<(), Box<dyn Error>> {
        let mut dest = PathBuf::from("images");
        dest.push(&file_name);
        let mut file = File::create(dest)?;
        file.write_all(buffer.as_slice())?;
        Ok(())
    }

    pub fn get_image(file_path: String) -> Result<Vec<u8>, Box<dyn Error>> {
        let mut dest = PathBuf::from("images");
        dest.push(&file_path);
        let mut file = File::open(dest)?;
        let mut content = Vec::new();
        file.read_to_end(&mut content)?;
        Ok(content)
    }
}
