package by.northdakota.Entity;

public enum Role {
    ADMIN,
    USER,
    ERROR;

    public static Role findRole(String role) {
        return switch (role.toLowerCase()) {
            case "admin" -> ADMIN;
            case "user" -> USER;
            default -> ERROR;
        };
    }
}
