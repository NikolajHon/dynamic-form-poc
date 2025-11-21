package com.example.dynamicform.domain;

import java.util.Optional;

public record FieldDefinition(
        String code,
        String label,
        DataType dataType,
        boolean mandatory,
        Optional<String> preFilledValue,
        String source
) {}
