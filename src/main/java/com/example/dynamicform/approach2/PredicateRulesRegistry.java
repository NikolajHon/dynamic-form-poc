
package com.example.dynamicform.approach2;

import com.example.dynamicform.domain.*;

import java.util.*;

public final class PredicateRulesRegistry {

    private static final Map<String, Map<CardScheme, List<PredicateRule>>> REGISTRY = new HashMap<>();

    static {
        register("TENANT_A", CardScheme.BASIC, PredicateRule.list(
            ctx -> ctx.hasDiscount(Discount.TZP)
                    ? Optional.of(new FieldDefinition("TZP_CARD_ID", "Číslo TZP preukazu",
                        DataType.STRING, true, Optional.empty(), "MANUAL"))
                    : Optional.empty(),
            ctx -> (ctx.hasAnyDiscount() && ctx.getAge() >= 65 && (ctx.getZone() == Zone.A || ctx.getZone() == Zone.B))
                    ? Optional.of(new FieldDefinition("SPOUSE_PHONE", "Telefónne číslo manželky",
                        DataType.STRING, true, ctx.getProfile().spousePhone(), "PROFILE_OR_MANUAL"))
                    : Optional.empty()
        ));

        register("TENANT_A", CardScheme.GOLD, PredicateRule.list(
            ctx -> ctx.hasDiscount(Discount.TZP)
                    ? Optional.of(new FieldDefinition("TZP_CARD_ID", "Číslo TZP preukazu",
                        DataType.STRING, true, Optional.empty(), "MANUAL"))
                    : Optional.empty()
        ));
    }

    private static void register(String tenant, CardScheme scheme, List<PredicateRule> rules) {
        REGISTRY.computeIfAbsent(tenant, t -> new EnumMap<>(CardScheme.class))
                .put(scheme, new ArrayList<>(rules));
    }

    public static List<PredicateRule> forTenantAndScheme(String tenant, CardScheme scheme) {
        return REGISTRY.getOrDefault(tenant, Map.of()).getOrDefault(scheme, List.of());
    }
}
