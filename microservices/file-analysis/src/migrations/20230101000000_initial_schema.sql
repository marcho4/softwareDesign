CREATE TABLE IF NOT EXISTS analysis (
    id INT PRIMARY KEY,
    location TEXT NOT NULL,
    paragraphs INT NOT NULL,
    words INT NOT NULL,
    symbols INT NOT NULL,
    plagiarism BOOLEAN NOT NULL
);