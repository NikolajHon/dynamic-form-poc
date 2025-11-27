
package com.example.dynamicform.domain;

import java.util.Optional;

public class Profile {
//    TODO parent profile and second mobile were creating only for test of flexibility SpEL methody
    private final String spousePhone;
    private final String fathersPhone;
    private final Profile parentProfile;

    public Profile(String spousePhone, String fathersPhone, Profile parentProfile) {
        this.spousePhone = spousePhone;
        this.fathersPhone = fathersPhone;
        this.parentProfile = parentProfile;
    }

    public String getSpousePhone() {
        return spousePhone;
    }

    public String getFathersPhone() {
        return fathersPhone;
    }

    public Profile getParentProfile() {
        return parentProfile;
    }

    public Optional<String> spousePhone() { return Optional.ofNullable(spousePhone); }
    public Optional<String> fathersPhone() { return Optional.ofNullable(fathersPhone); }
    public Optional<Profile> parentProfile() { return Optional.ofNullable(parentProfile); }

    @Override
    public String toString() {
        return "Profile{spousePhone=" + (spousePhone == null ? "null" : "***") + "}";
    }
}
