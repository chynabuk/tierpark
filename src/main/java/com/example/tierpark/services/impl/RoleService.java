package com.example.tierpark.services.impl;

import com.example.tierpark.entities.Role;
import com.example.tierpark.services.CrudOperations;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleService extends CrudOperations<Role>{
    public RoleService() {
        super(
                "Roles",
                "INSERT INTO Roles (name) VALUES (?)",
                "UPDATE Roles SET name=? WHERE id=?");
    }

    @Override
    public Role build(ResultSet resultSet) throws SQLException {
        return Role.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .build();
    }

    @Override
    public void prepareStatementCreatingSetup(Role object, PreparedStatement statement) throws SQLException {
        statement.setString(1, object.getName());
    }

    @Override
    public void prepareStatementUpdatingSetup(Role object, PreparedStatement statement) throws SQLException {
        prepareStatementCreatingSetup(object, statement);
        statement.setInt(2, object.getId());
    }
}