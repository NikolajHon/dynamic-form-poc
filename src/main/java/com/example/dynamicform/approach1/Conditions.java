
package com.example.dynamicform.approach1;

import com.example.dynamicform.domain.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public final class Conditions {

    private Conditions() {}

    public static Predicate<EvaluationContext> hasDiscount(Discount d) {
        return ctx -> ctx.getDiscounts().contains(d);
    }

    public static Predicate<EvaluationContext> hasAnyDiscount() {
        return EvaluationContext::hasAnyDiscount;
    }

    public static Predicate<EvaluationContext> minAge(int min) {
        return ctx -> ctx.getAge() >= min;
    }

    public static Predicate<EvaluationContext> zoneIn(Zone... zones) {
        Set<Zone> set = new HashSet<>(Arrays.asList(zones));
        return ctx -> set.contains(ctx.getZone());
    }
}
