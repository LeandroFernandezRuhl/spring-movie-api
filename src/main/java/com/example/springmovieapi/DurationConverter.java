package com.example.springmovieapi;

import jakarta.persistence.*;
import java.time.Duration;

@Converter
public class DurationConverter implements AttributeConverter<Duration, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Duration attribute) {
        return (int) attribute.getSeconds();
    }

    @Override
    public Duration convertToEntityAttribute(Integer dbData) {
        return Duration.ofSeconds(dbData);
    }
}

