package com.example.tierpark.services.impl;

import com.example.tierpark.entities.AnimalFamily;
import com.example.tierpark.services.CrudOperations;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AnimalFamilyService extends CrudOperations<AnimalFamily> {
    public AnimalFamilyService() {
        super(
                "Families",
                "INSERT INTO Families (name, class_id) VALUES (?, ?)",
                "UPDATE Families SET name=? WHERE id=?");
    }

    @Override
    public AnimalFamily build(ResultSet resultSet) throws SQLException {
        return AnimalFamily.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .classId(resultSet.getInt("class_id"))
                .build();
    }

    @Override
    public void prepareStatementCreatingSetup(AnimalFamily object, PreparedStatement statement) throws SQLException {
        statement.setString(1, object.getName());
        statement.setInt(2, object.getClassId());
    }

    @Override
    public void prepareStatementUpdatingSetup(AnimalFamily object, PreparedStatement statement) throws SQLException {
        statement.setString(1, object.getName());
        statement.setInt(2, object.getId());
    }
}
