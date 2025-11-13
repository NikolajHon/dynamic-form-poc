package com.example.dynamicform.models.analyticky_model;

import com.example.dynamicform.domain.*;

import java.util.*;

import static com.example.dynamicform.models.analyticky_model.Conditions.*;

public final class AnalyticalModelConfig {

    private static final Map<String, Map<CardScheme, List<AdditionalDataDefinition>>> CONFIG = new HashMap<>();

    static {
        register("TENANT_A", CardScheme.BASIC, List.of(
            new AdditionalDataDefinition(
                "TZP_CARD_ID",
                "Číslo TZP preukazu",
                DataType.STRING,
                true,
                hasDiscount(Discount.TZP),
                ctx -> Optional.empty(),
                "MANUAL"
            ),
            new AdditionalDataDefinition(
                "SPOUSE_PHONE",
                "Telefónne číslo manželky",
                DataType.STRING,
                true,
                hasAnyDiscount().and(minAge(65)).and(zoneIn(Zone.A, Zone.B)),
                ctx -> ctx.getProfile().spousePhone(),
                "PROFILE_OR_MANUAL"
            )
        ));

        register("TENANT_A", CardScheme.GOLD, List.of(
            new AdditionalDataDefinition(
                "TZP_CARD_ID",
                "Číslo TZP preukazu",
                DataType.STRING,
                true,
                hasDiscount(Discount.TZP),
                ctx -> Optional.empty(),
                "MANUAL"
            )
        ));
    }

    private static void register(String tenant, CardScheme scheme, List<AdditionalDataDefinition> defs) {
        CONFIG.computeIfAbsent(tenant, t -> new EnumMap<>(CardScheme.class))
                .put(scheme, new ArrayList<>(defs));
    }

    public static List<AdditionalDataDefinition> forTenantAndScheme(String tenant, CardScheme scheme) {
        return CONFIG.getOrDefault(tenant, Map.of())
                .getOrDefault(scheme, List.of());
    }
}
