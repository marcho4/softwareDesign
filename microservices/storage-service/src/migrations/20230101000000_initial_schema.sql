CREATE TABLE IF NOT EXISTS files (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    hash TEXT NOT NULL,
    location TEXT NOT NULL 
);