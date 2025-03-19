package com.finance.classes.parsers;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.finance.classes.FileParser;
import com.finance.values.Operation;

public class JSONParser extends FileParser {
    @Override
    public void exportToFile(List<Operation> operations, String filename) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.enable(SerializationFeature.INDENT_OUTPUT); 
        try {
            String json = mapper.writeValueAsString(operations);
            Files.write(Paths.get(filename), json.getBytes());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected List<Operation> parseFromString(String content) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.enable(SerializationFeature.INDENT_OUTPUT); 
        try {
            List<Operation> operations = mapper.readValue(
                content, 
                mapper.getTypeFactory()
                .constructCollectionType(List.class, Operation.class)
            );

            return operations;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
