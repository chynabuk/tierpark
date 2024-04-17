package com.example.tierpark.entities;

public enum Gender {
    MAN(){
        @Override
        public int getId() {
            return 1;
        }
    },
    WOMAN(){
        @Override
        public int getId() {
            return 2;
        }
    };

    public abstract int getId();
}
