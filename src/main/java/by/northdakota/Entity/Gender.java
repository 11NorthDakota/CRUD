package by.northdakota.Entity;

import java.util.Arrays;
import java.util.Optional;

public enum Gender {
    MALE,
    FEMALE,
    ERROR;

    public static Gender findGender(String gender) {
        return switch (gender.toLowerCase()) {
            case "male" -> MALE;
            case "female" -> FEMALE;
            default -> ERROR;
        };
    }
}
