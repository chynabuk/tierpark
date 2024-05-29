package com.example.tierpark.services.impl;

import com.example.tierpark.entities.Animal;
import com.example.tierpark.entities.Gender;
import com.example.tierpark.services.CrudOperations;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AnimalService extends CrudOperations<Animal> {
    public AnimalService() {
        super(
                "Animals",
                "INSERT INTO Animals (name, birthdate, animal_type_id, building_id, gender_id) VALUES (?, ?, ?, ?, ?)",
                "UPDATE Animals SET name=?, birthdate=?, animal_type_id=?, building_id=?, gender_id=? where id=?");
    }

    @Override
    public Animal build(ResultSet resultSet) throws SQLException {
        return Animal.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .birthdate(resultSet.getDate("birthdate"))
                .animalTypeId(resultSet.getInt("animal_type_id"))
                .buildingId(resultSet.getInt("building_id"))
                .gender(resultSet.getInt("gender_id") == 1 ? Gender.MAN : Gender.WOMAN)
                .build();
    }

    @Override
    public void prepareStatementCreatingSetup(Animal object, PreparedStatement statement) throws SQLException {
        statement.setString(1, object.getName());
        statement.setDate(2, object.getBirthdate());
        statement.setInt(3, object.getAnimalTypeId());
        statement.setInt(4, object.getBuildingId());
        statement.setInt(5, object.getGender().getId());
    }

    @Override
    public void prepareStatementUpdatingSetup(Animal object, PreparedStatement statement) throws SQLException {
        prepareStatementCreatingSetup(object, statement);
        statement.setInt(6, object.getId());

    }
}
