package com.example.tierpark.services.impl;

import com.example.tierpark.entities.Keeper;
import com.example.tierpark.services.CrudOperations;
import com.example.tierpark.util.JdbcSQLServerConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class KeeperService extends CrudOperations<Keeper> {
    public KeeperService() {
        super(
                "Keepers",
                "INSERT INTO Keepers (name, lastname, birthdate, gender_id, role_id) VALUES (?, ?, ?, ?, ?)",
                "UPDATE Keepers SET name=?, lastname=?, birthdate=? WHERE id=?");
    }

    @Override
    public void update(Keeper object) {
        Connection connection = JdbcSQLServerConnection.connect();
        String sql = "UPDATE Keepers SET name=?, lastname=?, birthdate=?, gender_id=?, role_id=? WHERE id=?";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, object.getName());
            statement.setString(2, object.getLastname());
            statement.setDate(3, object.getBirthDate());
            statement.setInt(4, object.getGenderId());
            statement.setInt(5, object.getRoleId());
            statement.setInt(6, object.getId());

            if (statement.executeUpdate() > 0){
                System.out.println("Record updated");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            JdbcSQLServerConnection.close();
        }

        JdbcSQLServerConnection.close();
    }

    @Override
    public Keeper build(ResultSet resultSet) throws SQLException {
        return Keeper.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .lastname(resultSet.getString("lastname"))
                .birthDate(resultSet.getDate("birthdate"))
                .genderId(resultSet.getInt("gender_id"))
                .roleId(resultSet.getInt("role_id"))
                .build();
    }

    @Override
    public void prepareStatementCreatingSetup(Keeper object, PreparedStatement statement) throws SQLException {
        statement.setString(1, object.getName());
        statement.setString(2, object.getLastname());
        statement.setDate(3, object.getBirthDate());
        statement.setInt(4, object.getGenderId());
        statement.setInt(5, object.getRoleId());
    }

    @Override
    public void prepareStatementUpdatingSetup(Keeper object, PreparedStatement statement) throws SQLException {
        statement.setString(1, object.getName());
        statement.setString(2, object.getLastname());
        statement.setDate(3, object.getBirthDate());
        statement.setInt(4, object.getId());
    }
}
