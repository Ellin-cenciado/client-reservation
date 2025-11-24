package com.reservation.common.converter;

import com.reservation.common.model.Reservation.Work;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class WorkListConverter implements AttributeConverter<List<Work>, String> {

    @Override
    public String convertToDatabaseColumn(List<Work> works) {
        if (works == null || works.isEmpty()) {
            return "";
        }
        // This saves it as "PIERCING,TATTOO"
        return works.stream()
                .map(Enum::name)
                .collect(Collectors.joining(","));
    }

    @Override
    public List<Work> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.trim().isEmpty()) {
            return new ArrayList<>();
        }

        // Remove all possible array delimiters and quotes
        String cleanData = dbData.replace("{", "")
                .replace("}", "")
                .replace("[", "")
                .replace("]", "")
                .replace("\"", "");

        return Arrays.stream(cleanData.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(this::fromString)  // Use helper method to handle both formats
                .collect(Collectors.toList());
    }

    /**
     * Converts a string to Work enum, handling both enum names (PIERCING)
     * and display names (Piercing)
     */
    private Work fromString(String value) {
        // First try direct enum name match (PIERCING, TATTOO, etc.)
        try {
            return Work.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            // If that fails, try matching by display name
            for (Work work : Work.values()) {
                if (work.toString().equalsIgnoreCase(value)) {
                    return work;
                }
            }
            throw new IllegalArgumentException("No Work enum found for value: " + value);
        }
    }
}