package com.example.dynamicform.approach3;

import com.example.dynamicform.domain.Discount;
import com.example.dynamicform.domain.EvaluationContext;
import com.example.dynamicform.domain.Zone;

import java.util.*;

/**
 * Universal rule-row that supports dynamic, extensible condition map.
 */
public final class DecisionRow {

    private final Map<String, Object> conditions;
    private final List<String> outputFieldKeys;

    public DecisionRow(Map<String, Object> conditions, List<String> outputFieldKeys) {
        this.conditions = Map.copyOf(conditions);
        this.outputFieldKeys = List.copyOf(outputFieldKeys);
    }

    public boolean matches(EvaluationContext ctx) {
        for (var entry : conditions.entrySet()) {
            var key = entry.getKey();
            var value = entry.getValue();

            switch (key) {

                // --------------------
                // AGE RULES
                // --------------------
                case "age <" -> {
                    if (!(ctx.getAge() < (int) value)) return false;
                }
                case "age >" -> {
                    if (!(ctx.getAge() > (int) value)) return false;
                }
                case "age >=" -> {
                    if (!(ctx.getAge() >= (int) value)) return false;
                }
                case "age <=" -> {
                    if (!(ctx.getAge() <= (int) value)) return false;
                }

                // --------------------
                // ZONE RULES
                // --------------------
                case "zone in" -> {
                    Set<Zone> allowed = (Set<Zone>) value;
                    if (!allowed.contains(ctx.getZone())) return false;
                }
                case "zone not in" -> {
                    Set<Zone> forbidden = (Set<Zone>) value;
                    if (forbidden.contains(ctx.getZone())) return false;
                }

                // --------------------
                // DISCOUNT RULES
                // --------------------
                case "hasDiscount" -> {
                    if (!ctx.hasDiscount((Discount) value)) return false;
                }
                case "hasAnyDiscount" -> {
                    if (!ctx.hasAnyDiscount()) return false;
                }

                // --------------------
                // PROFILE RULES
                // --------------------
                case "profile.spousePhone empty" -> {
                    if (ctx.getProfile().spousePhone().isPresent()) return false;
                }
                case "profile.spousePhone present" -> {
                    if (ctx.getProfile().spousePhone().isEmpty()) return false;
                }

                // --------------------
                // TENANT RULES
                // --------------------
                case "tenant = " -> {
                    if (!ctx.getTenant().equals(value)) return false;
                }

                // --------------------
                // CARD SCHEME RULES
                // --------------------
                case "cardScheme = " -> {
                    if (ctx.getCardScheme() != value) return false;
                }

                // --------------------
                // Address verified RULES
                // --------------------
                case "addressVerified =" -> {
                    boolean expected = (boolean) value;
                    if (ctx.isAddressVerified() != expected) return false;
                }
                case "addressVerified !=" -> {
                    boolean unexpected = (boolean) value;
                    if (ctx.isAddressVerified() == unexpected) return false;
                }

                default -> throw new IllegalStateException("Unknown condition: " + key);
            }
        }

        return true;
    }

    public List<String> outputs() {
        return outputFieldKeys;
    }

    public static DecisionRow rule(Map<String, Object> conditions, List<String> outputs) {
        return new DecisionRow(conditions, outputs);
    }
}
