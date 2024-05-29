package com.example.tierpark.services.impl;

import com.example.tierpark.entities.Care;
import com.example.tierpark.services.CrudOperations;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CareService extends CrudOperations<Care> {
    public CareService() {
        super(
                "Care",
                "INSERT INTO Care (done, care_type_id, keeper_id, animal_id) VALUES (?, ?, ?, ?)",
                "UPDATE Care SET done=?, care_type_id=?, keeper_id=?, animal_id=? WHERE id=?");
    }

    @Override
    public Care build(ResultSet resultSet) throws SQLException {
        return Care.builder()
                .id(resultSet.getInt("id"))
                .done(resultSet.getDate("done"))
                .careTypeId(resultSet.getInt("care_type_id"))
                .keeperId(resultSet.getInt("keeper_id"))
                .animalId(resultSet.getInt("animal_id"))
                .build();
    }

    @Override
    public void prepareStatementCreatingSetup(Care object, PreparedStatement statement) throws SQLException {
        statement.setDate(1, object.getDone());
        statement.setInt(2, object.getCareTypeId());
        statement.setInt(3, object.getKeeperId());
        statement.setInt(4, object.getAnimalId());
    }

    @Override
    public void prepareStatementUpdatingSetup(Care object, PreparedStatement statement) throws SQLException {
        prepareStatementCreatingSetup(object, statement);
        statement.setInt(5, object.getId());
    }
}
