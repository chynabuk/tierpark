package com.example.tierpark.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FeedAnimal {
    int id;
    Timestamp feedDateTime;
    int feedAmount;
    int feedId;
    int keeperId;
    int animalId;
}
