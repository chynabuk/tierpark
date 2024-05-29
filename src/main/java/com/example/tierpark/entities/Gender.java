package com.example.tierpark.entities;

public enum Gender {
    MAN(){
        @Override
        public int getId() {
            return 1;
        }

        @Override
        public String toString() {
            return "Männlich";
        }
    },
    WOMAN(){
        @Override
        public int getId() {
            return 2;
        }

        @Override
        public String toString() {
            return "Weiblich";
        }
    };

    public abstract int getId();
}
