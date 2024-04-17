package com.example.tierpark.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Keeper{
    int id;
    int genderId;
    int roleId;
    Date birthDate;
    String lastname;
    String name;
}
