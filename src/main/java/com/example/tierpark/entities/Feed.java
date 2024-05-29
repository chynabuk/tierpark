package com.example.tierpark.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Feed {
    int id;
    String name;
    String measure;
    int pricePerUnit;
}
