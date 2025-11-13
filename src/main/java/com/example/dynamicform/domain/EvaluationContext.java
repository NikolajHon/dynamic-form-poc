
package com.example.dynamicform.domain;

import java.util.*;

public class EvaluationContext {
    private final String tenant;
    private final CardScheme cardScheme;
    private final Set<Discount> discounts;
    private final int age;
    private final Zone zone;
    private final Profile profile;
    boolean addressVerified;


    public EvaluationContext(String tenant, CardScheme cardScheme, Set<Discount> discounts, int age, Zone zone, Profile profile, boolean addressVerified) {
        this.tenant = tenant;
        this.cardScheme = cardScheme;
        this.discounts = Collections.unmodifiableSet(new HashSet<>(discounts));
        this.age = age;
        this.zone = zone;
        this.profile = profile;
        this.addressVerified = addressVerified;
    }

    public String getTenant() { return tenant; }
    public CardScheme getCardScheme() { return cardScheme; }
    public Set<Discount> getDiscounts() { return discounts; }
    public int getAge() { return age; }
    public Zone getZone() { return zone; }
    public Profile getProfile() { return profile; }
    public boolean isAddressVerified() {
        return addressVerified;
    }

    // Helpers
    public boolean hasDiscount(Discount d) { return discounts.contains(d); }
    public boolean hasAnyDiscount() { return !discounts.isEmpty(); }

    @Override
    public String toString() {
        return "EvaluationContext{" +
                "tenant='" + tenant + '\'' +
                ", cardScheme=" + cardScheme +
                ", discounts=" + discounts +
                ", age=" + age +
                ", zone=" + zone +
                ", profile=" + profile +
                '}';
    }
}
