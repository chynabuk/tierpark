package com.example.tierpark.services.impl;

import com.example.tierpark.entities.AnimalClass;
import com.example.tierpark.services.CrudOperations;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AnimalClassService extends CrudOperations<AnimalClass> {
    public AnimalClassService() {
        super(
                "Classes",
                "INSERT INTO Classes (name) VALUES (?)",
                "UPDATE Classes SET name=? WHERE id=?");
    }

    @Override
    public AnimalClass build(ResultSet resultSet) throws SQLException {
        return AnimalClass.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .build();
    }

    @Override
    public void prepareStatementCreatingSetup(AnimalClass object, PreparedStatement statement) throws SQLException {
        statement.setString(1, object.getName());
    }

    @Override
    public void prepareStatementUpdatingSetup(AnimalClass object, PreparedStatement statement) throws SQLException {
        prepareStatementCreatingSetup(object, statement);
        statement.setInt(2, object.getId());
    }
}