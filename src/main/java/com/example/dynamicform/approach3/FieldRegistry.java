
package com.example.dynamicform.approach3;

import com.example.dynamicform.domain.DataType;
import com.example.dynamicform.domain.EvaluationContext;
import com.example.dynamicform.domain.FieldDefinition;

import java.util.*;
import java.util.function.Function;

/**
 * Registry maps output KEY → FieldDefinition builder.
 * This isolates FE contract (labels, types) from rule table.
 */
public final class FieldRegistry {

    public record FieldTemplate(
            String code,
            String label,
            DataType dataType,
            boolean mandatory,
            Function<EvaluationContext, Optional<String>> prefill,
            String source
    ) {
        public FieldDefinition build(EvaluationContext ctx) {
            return new FieldDefinition(code, label, dataType, mandatory, prefill.apply(ctx), source);
        }
    }

    private static final Map<String, FieldTemplate> REGISTRY = new HashMap<>();

    static {
        REGISTRY.put("TZP_CARD_ID", new FieldTemplate(
                "TZP_CARD_ID",
                "Číslo TZP preukazu",
                DataType.STRING,
                true,
                ctx -> Optional.empty(),
                "MANUAL"
        ));
        REGISTRY.put("SPOUSE_PHONE", new FieldTemplate(
                "SPOUSE_PHONE",
                "Telefónne číslo manželky",
                DataType.STRING,
                true,
                ctx -> ctx.getProfile().spousePhone(),
                "PROFILE_OR_MANUAL"
        ));
        REGISTRY.put("EMERGENCY_CONTACT", new FieldTemplate(
                "EMERGENCY_CONTACT",
                "Kontaktná osoba pre núdzové situácie",
                DataType.STRING,
                true,
                ctx -> Optional.empty(),
                "MANUAL"
        ));
        REGISTRY.put("ADDRESS_PROOF_DOCUMENT", new FieldTemplate(
                "ADDRESS_PROOF_DOCUMENT",
                "Doklad o potvrdení adresy",
                DataType.STRING,
                true,
                ctx -> Optional.empty(),
                "MANUAL"
        ));



    }

    public static Optional<FieldTemplate> lookup(String key) {
        return Optional.ofNullable(REGISTRY.get(key));
    }

    public static void ensureKeysExistOrThrow(Collection<String> keys) {
        for (var k : keys) {
            if (!REGISTRY.containsKey(k)) {
                throw new IllegalStateException("Unknown field key: " + k);
            }
        }
    }
}
