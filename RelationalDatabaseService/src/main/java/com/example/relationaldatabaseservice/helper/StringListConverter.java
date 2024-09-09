package com.example.relationaldatabaseservice.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<String> stringList) {
        try {
            return objectMapper.writeValueAsString(stringList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert list to JSON", e);
        }
    }

    @Override
    public List<String> convertToEntityAttribute(String json) {
        try {
            return Arrays.asList(objectMapper.readValue(json, String[].class));
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert JSON to list", e);
        }
    }
}
