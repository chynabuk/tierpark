package com.example.tierpark.entities;

public enum RoleEnum {
    VISITOR,
    EMPLOYEE,
    ADMIN() {
        @Override
        public String toString() {
            return "sa";
        }
    };

    public static RoleEnum getRoleById(int id){
        return switch (id) {
            case 2 -> EMPLOYEE;
            case 3 -> ADMIN;
            default -> VISITOR;
        };
    }
}
