package com.example.tierpark.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Animal {
    int id;
    String name;
    Date birthdate;
    int animalTypeId;
    int buildingId;
    int genderId;
}
