package com.example.tierpark.services.impl;

import com.example.tierpark.entities.User;
import com.example.tierpark.services.CrudOperations;
import com.example.tierpark.util.JdbcSQLServerConnection;
import javafx.scene.control.Alert;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
                CurrentUserService.setCurrentUser(object);
                JdbcSQLServerConnection.changeConfiguration(object.getRoleId());
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
                .login(resultSet.getString("login"))
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

    @Override
    public void delete(int id) {
        deleteKeeperFromDependedTables(id, "Care");
        deleteKeeperFromDependedTables(id, "Feed_animals");
        super.delete(id);
    }

    private void deleteKeeperFromDependedTables(int id, String tableName){
        Connection connection = JdbcSQLServerConnection.connect();

        String sql = "DELETE FROM " + tableName + " WHERE keeper_id=?";

        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            if (statement.executeUpdate() > 0){
                System.out.println("Record deleted");
            }
        }
        catch (SQLException e) {
            if ("42000".equals(e.getSQLState()) || e.getErrorCode() == 229) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erlaubnis verweigert");
                alert.setContentText("Die DELETE-Erlaubnis wurde für die Tabelle '" + tableName + "' verweigert");
                alert.showAndWait();
            } else {
                // Handle other SQL exceptions
                e.printStackTrace();
            }
        }
        finally {
            JdbcSQLServerConnection.close();
        }
    }

    @Override
    public List<User> readAll() {
        if (CurrentUserService.getCurrentUser().getRoleId() == 3){
            return null;
        }
        List<User> users = super.readAll();
        users.removeIf(user -> user.getRoleId() == 1);
        return users;
    }

    public boolean isUserExistedByLogin(String login){
        Connection connection = JdbcSQLServerConnection.connect();
        String sql = "SELECT * FROM " + getTableName() + " WHERE login=?";
        User object = null;
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, login);
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
            if ("42000".equals(e.getSQLState()) || e.getErrorCode() == 229) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erlaubnis verweigert");
                alert.setContentText("Die SELECT-Erlaubnis wurde für die Tabelle '" + getTableName() + "' verweigert");
                alert.showAndWait();
            } else {
                // Handle other SQL exceptions
                e.printStackTrace();
            }
        }
        finally {
            JdbcSQLServerConnection.close();
        }

        return object != null;
    }
}
