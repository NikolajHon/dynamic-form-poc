
package com.example.dynamicform.domain;

import java.util.Optional;

public class Profile {
    private final String spousePhone;

    public Profile(String spousePhone) {
        this.spousePhone = spousePhone;
    }

    public Optional<String> spousePhone() {
        return Optional.ofNullable(spousePhone);
    }

    @Override
    public String toString() {
        return "Profile{spousePhone=" + (spousePhone == null ? "null" : "***") + "}";
    }
}
