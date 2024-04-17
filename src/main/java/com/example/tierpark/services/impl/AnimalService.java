package com.example.tierpark.services.impl;

import com.example.tierpark.entities.Animal;
import com.example.tierpark.services.CrudOperations;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AnimalService extends CrudOperations<Animal> {
    public AnimalService() {
        super(
                "Animals",
                "INSERT INTO Animals (name, birthdate, animal_type_id, building_id, gender_id) VALUES (?, ?, ?, ?, ?)",
                "UPDATE Animals SET name=?, birthdate=?");
    }

    @Override
    public Animal build(ResultSet resultSet) throws SQLException {
        return Animal.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .birthdate(resultSet.getDate("birthdate"))
                .animalTypeId(resultSet.getInt("animal_type_id"))
                .buildingId(resultSet.getInt("building_id"))
                .genderId(resultSet.getInt("gender_id"))
                .build();
    }

    @Override
    public void prepareStatementCreatingSetup(Animal object, PreparedStatement statement) throws SQLException {
        statement.setString(1, object.getName());
        statement.setDate(2, object.getBirthdate());
        statement.setInt(3, object.getAnimalTypeId());
        statement.setInt(4, object.getBuildingId());
        statement.setInt(5, object.getGenderId());
    }

    @Override
    public void prepareStatementUpdatingSetup(Animal object, PreparedStatement statement) throws SQLException {

    }
}
