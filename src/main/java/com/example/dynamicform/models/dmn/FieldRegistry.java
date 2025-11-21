
package com.example.dynamicform.models.dmn;

import com.example.dynamicform.domain.DataType;

import java.util.*;


public final class FieldRegistry {

    public record FieldTemplate(String code, String label, DataType dataType) {}

    private static final Map<String, FieldTemplate> REGISTRY = Map.of(
            "TZP_CARD_ID", new FieldTemplate("TZP_CARD_ID", "Číslo TZP preukazu", DataType.STRING),
            "SPOUSE_PHONE", new FieldTemplate("SPOUSE_PHONE", "Telefónne číslo manželky", DataType.STRING),
            "EMERGENCY_CONTACT", new FieldTemplate("EMERGENCY_CONTACT", "Kontaktná osoba pre núdzové situácie", DataType.STRING),
            "ADDRESS_PROOF_DOCUMENT", new FieldTemplate("ADDRESS_PROOF_DOCUMENT", "Doklad o potvrdení adresy", DataType.STRING)
    );
    public static Optional<FieldTemplate> lookup(String key) {
        return Optional.ofNullable(REGISTRY.get(key));
    }
}
