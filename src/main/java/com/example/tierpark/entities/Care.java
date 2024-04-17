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
public class Care {
    int id;
    Date done;
    int careTypeId;
    int keeperId;
    int animalId;
}
