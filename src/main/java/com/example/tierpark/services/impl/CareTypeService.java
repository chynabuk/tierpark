package com.example.tierpark.services.impl;

import com.example.tierpark.entities.CareType;
import com.example.tierpark.services.CrudOperations;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CareTypeService extends CrudOperations<CareType> {
    public CareTypeService() {
        super(
                "Care_types",
                "INSERT INTO Care_types (name, description) VALUES (?, ?)",
                "UPDATE Care_types SET name=?, description=? WHERE id=?");
    }

    @Override
    public CareType build(ResultSet resultSet) throws SQLException {
        return CareType.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .build();
    }

    @Override
    public void prepareStatementCreatingSetup(CareType object, PreparedStatement statement) throws SQLException {
        statement.setString(1, object.getName());
        statement.setString(2, object.getDescription());
    }

    @Override
    public void prepareStatementUpdatingSetup(CareType object, PreparedStatement statement) throws SQLException {
        prepareStatementCreatingSetup(object, statement);
        statement.setInt(3, object.getId());
    }
}
