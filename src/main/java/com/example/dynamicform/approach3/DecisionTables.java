package com.example.dynamicform.approach3;

import com.example.dynamicform.domain.CardScheme;
import com.example.dynamicform.domain.Discount;
import com.example.dynamicform.domain.Zone;

import java.util.*;

public final class DecisionTables {

    private static final Map<String, Map<CardScheme, List<DecisionRow>>> TABLES = new HashMap<>();

    static {

        // ---------- TENANT_A / BASIC ----------

        TABLES.computeIfAbsent("TENANT_A", t -> new EnumMap<>(CardScheme.class))
                .put(CardScheme.BASIC, List.of(

                        // -----------------------------------------------------
                        // RULE 1: Discount = TZP → show TZP_CARD_ID
                        // -----------------------------------------------------
                        DecisionRow.rule(
                                Map.of(
                                        "hasDiscount", Discount.TZP
                                ),
                                List.of("TZP_CARD_ID")
                        ),

                        // -----------------------------------------------------
                        // RULE 2: Any discount + age >= 65 + zone A/B
                        //         → show SPOUSE_PHONE + TZP_CARD_ID
                        // -----------------------------------------------------
                        DecisionRow.rule(
                                Map.of(
                                        "hasAnyDiscount", true,
                                        "age >=", 65,
                                        "zone in", Set.of(Zone.A, Zone.B)
                                ),
                                List.of("SPOUSE_PHONE", "TZP_CARD_ID")
                        ),

                        // -----------------------------------------------------
                        // RULE 3: CHILD rule (new field!)
                        // age < 15 + zone C + spousePhone empty
                        // → EMERGENCY_CONTACT
                        // -----------------------------------------------------
                        DecisionRow.rule(
                                Map.of(
                                        "age <", 15,
                                        "zone in", Set.of(Zone.C),
                                        "profile.spousePhone empty", true
                                ),
                                List.of("EMERGENCY_CONTACT")
                        ),

                        DecisionRow.rule(
                                Map.of(
                                        "addressVerified =", false,
                                        "age >=", 18,
                                        "zone in", Set.of(Zone.A, Zone.B)
                                ),
                                List.of("ADDRESS_PROOF_DOCUMENT")
                        )

                ));


        TABLES.computeIfAbsent("TENANT_A", t -> new EnumMap<>(CardScheme.class))
                .put(CardScheme.GOLD, List.of(
                        DecisionRow.rule(
                                Map.of(
                                        "hasDiscount", Discount.TZP
                                ),
                                List.of("TZP_CARD_ID")
                        )
                ));
    }

    public static List<DecisionRow> forTenantAndScheme(String tenant, CardScheme scheme) {
        return TABLES.getOrDefault(tenant, Map.of()).getOrDefault(scheme, List.of());
    }
}
