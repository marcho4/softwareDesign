package com.finance.classes.fabrics;

import com.finance.classes.FileParser;
import com.finance.classes.parsers.CSVParser;
import com.finance.classes.parsers.JSONParser;
import com.finance.classes.parsers.YAMLParser;

public class ParserFabric {
    public static FileParser getParser(String type) {
        switch (type) {
            case "csv":
                return new CSVParser();
            case "json":
                return new JSONParser();
            case "yaml":
                return new YAMLParser();
            default:
                throw new IllegalArgumentException("Unsupported file type: " + type);
        }
    }
    public static FileParser getParserById(String index) {
        switch (index) {
            case "1":
                return new CSVParser();
            case "2":
                return new JSONParser();
            case "3":
                return new YAMLParser();
            default:
                throw new IllegalArgumentException("Unsupported file type: " + index);
        }
    }
}
