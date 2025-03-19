package com.finance.classes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.finance.values.Operation;

public abstract class FileParser {
    protected String readFromFile(String filename) {
        try {
            return new String(Files.readAllBytes(Paths.get(filename)));
        } catch (IOException e) {
            throw new RuntimeException("Проблем при чтении файла: " + filename, e);
        }
    }
    
    protected abstract List<Operation> parseFromString(String content);

    public List<Operation> parseFromFile(String filename) {
        String content = readFromFile(filename);
        return parseFromString(content);
    }

    public abstract void exportToFile(List<Operation> operations, String filename);
}