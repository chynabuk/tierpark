package com.example.tierpark.services.impl;

import com.example.tierpark.entities.AnimalType;
import com.example.tierpark.services.CrudOperations;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AnimalTypeService extends CrudOperations<AnimalType> {
    public AnimalTypeService(){
        super(
                "Animal_types",
                "INSERT INTO Animal_types (name, family_id) VALUES (?, ?)",
                "UPDATE Animal_types SET name=? WHERE id=?");
    }

    @Override
    public AnimalType build(ResultSet resultSet) throws SQLException {
        return AnimalType.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .familyId(resultSet.getInt("family_id"))
                .build();
    }

    @Override
    public void prepareStatementCreatingSetup(AnimalType object, PreparedStatement statement) throws SQLException {
        statement.setString(1, object.getName());
        statement.setInt(2, object.getFamilyId());
    }

    @Override
    public void prepareStatementUpdatingSetup(AnimalType object, PreparedStatement statement) throws SQLException {
        statement.setString(1, object.getName());
        statement.setInt(2, object.getId());
    }
}
