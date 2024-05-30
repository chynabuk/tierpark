package com.example.tierpark.entities;

public enum RoleEnum {
    GAST,
    MITARBEITER,
    ADMIN() {
        @Override
        public String toString() {
            return "sa";
        }
    };

    public static RoleEnum getRoleById(int id){
        return switch (id) {
            case 2 -> MITARBEITER;
            case 1 -> ADMIN;
            default -> GAST;
        };
    }
}
