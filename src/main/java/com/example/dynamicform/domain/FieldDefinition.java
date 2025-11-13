
package com.example.dynamicform.domain;

import java.util.Objects;
import java.util.Optional;

public class FieldDefinition {
    private final String code; // registry key
    private final String label;
    private final DataType dataType;
    private final boolean mandatory;
    private final Optional<String> preFilledValue;
    private final String source;

    public FieldDefinition(String code, String label, DataType dataType, boolean mandatory, Optional<String> preFilledValue, String source) {
        this.code = code;
        this.label = label;
        this.dataType = dataType;
        this.mandatory = mandatory;
        this.preFilledValue = preFilledValue;
        this.source = source;
    }

    public String code() { return code; }
    public String label() { return label; }
    public DataType dataType() { return dataType; }
    public boolean mandatory() { return mandatory; }
    public Optional<String> preFilledValue() { return preFilledValue; }
    public String source() { return source; }

    @Override
    public String toString() {
        return "FieldDefinition{" +
                "code='" + code + '\'' +
                ", label='" + label + '\'' +
                ", dataType=" + dataType +
                ", mandatory=" + mandatory +
                ", preFilledValue=" + preFilledValue.map(x -> "\""+x+"\"").orElse("∅") +
                ", source='" + source + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FieldDefinition that)) return false;
        return mandatory == that.mandatory && Objects.equals(code, that.code) && Objects.equals(label, that.label) && dataType == that.dataType && Objects.equals(preFilledValue, that.preFilledValue) && Objects.equals(source, that.source);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, label, dataType, mandatory, preFilledValue, source);
    }
}
