package com.example.tierpark.services.impl;

import com.example.tierpark.entities.User;
import com.example.tierpark.services.CrudOperations;
import com.example.tierpark.util.JdbcSQLServerConnection;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService extends CrudOperations<User> {
    public UserService() {
        super(
                "Users",
                "INSERT INTO Users (name, lastname, birthdate, gender_id, role_id, login, password) VALUES (?, ?, ?, ?, ?, ?, ?)",
                "UPDATE Users SET name=?, lastname=?, birthdate=? WHERE id=?");
    }

    public User login(String login, String password){
        Connection connection = JdbcSQLServerConnection.connect();
        String sql = "SELECT * FROM Users WHERE login=? AND password=?";
        User object = null;
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, login);
            statement.setString(2, hashingPassword(password));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                object = build(resultSet);
                System.out.println("Record read");
            }
            else {
                System.out.println("Record is not found");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            JdbcSQLServerConnection.close();
        }

        return object;
    }

    @Override
    public User build(ResultSet resultSet) throws SQLException {
        return User.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .lastname(resultSet.getString("lastname"))
                .birthDate(resultSet.getDate("birthdate"))
                .genderId(resultSet.getInt("gender_id"))
                .roleId(resultSet.getInt("role_id"))
                .build();
    }

    @Override
    public void prepareStatementCreatingSetup(User object, PreparedStatement statement) throws SQLException {
        statement.setString(1, object.getName());
        statement.setString(2, object.getLastname());
        statement.setDate(3, object.getBirthDate());
        statement.setInt(4, object.getGenderId());
        statement.setInt(5, object.getRoleId());
        statement.setString(6, object.getLogin());
        statement.setString(7, hashingPassword(object.getPassword()));
    }

    @Override
    public void prepareStatementUpdatingSetup(User object, PreparedStatement statement) throws SQLException {
        statement.setString(1, object.getName());
        statement.setString(2, object.getLastname());
        statement.setDate(3, object.getBirthDate());
        statement.setInt(4, object.getId());
    }

    private String hashingPassword(String password){
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(password.getBytes());
            byte[] array = md5.digest(md5.digest());
            StringBuffer hashedPassword = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                hashedPassword.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return hashedPassword.toString();

        } catch (NoSuchAlgorithmException e) {
            return password;
        }

    }
}