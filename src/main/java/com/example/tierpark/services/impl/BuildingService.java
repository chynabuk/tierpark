package com.example.tierpark.services.impl;

import com.example.tierpark.entities.Building;
import com.example.tierpark.services.CrudOperations;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BuildingService extends CrudOperations<Building> {
    public BuildingService() {
        super(
                "Buildings",
                "INSERT INTO Buildings (name, built_date) VALUES (?, ?)",
                "UPDATE Buildings SET name=?, built_date=? WHERE id=?");
    }

    @Override
    public Building build(ResultSet resultSet) throws SQLException {
        return Building.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .builtDate(resultSet.getDate("built_date"))
                .build();
    }

    @Override
    public void prepareStatementCreatingSetup(Building object, PreparedStatement statement) throws SQLException {
        statement.setString(1, object.getName());
        statement.setDate(2, object.getBuiltDate());
    }

    @Override
    public void prepareStatementUpdatingSetup(Building object, PreparedStatement statement) throws SQLException {
        prepareStatementCreatingSetup(object, statement);
        statement.setInt(3, object.getId());
    }
}
