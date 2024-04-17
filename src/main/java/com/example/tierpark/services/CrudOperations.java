package com.example.tierpark.services;

import com.example.tierpark.util.JdbcSQLServerConnection;
import lombok.RequiredArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public abstract class CrudOperations<DT>{
    private final String tableName;
    private final String creatingSQL;
    private final String updatingSQL;

    public void insert(DT object){
        Connection connection = JdbcSQLServerConnection.connect();
        String sql = creatingSQL;
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            prepareStatementCreatingSetup(object, statement);

            if (statement.executeUpdate() > 0){
                System.out.println("Record inserted");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            JdbcSQLServerConnection.close();
        }
    }

    public void update(DT object){
        Connection connection = JdbcSQLServerConnection.connect();
        String sql = updatingSQL;
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            prepareStatementUpdatingSetup(object, statement);
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
    }

    public void delete(int id){
        Connection connection = JdbcSQLServerConnection.connect();
        String sql = "DELETE FROM " + tableName + " WHERE id=?";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            if (statement.executeUpdate() > 0){
                System.out.println("Record deleted");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            JdbcSQLServerConnection.close();
        }
    }

    public DT readById(int id) {
        Connection connection = JdbcSQLServerConnection.connect();
        String sql = "SELECT * FROM " + tableName + " WHERE id=?";
        DT object = null;
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
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

    public List<DT> readAll(){
        Connection connection = JdbcSQLServerConnection.connect();
        String sql = "SELECT * FROM " + tableName;
        List<DT> objects = null;
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            objects = new ArrayList<>();
            while (resultSet.next()){
                DT object = build(resultSet);
                objects.add(object);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            JdbcSQLServerConnection.close();
        }
        return objects;
    }

    public abstract DT build(ResultSet resultSet) throws SQLException;
    public abstract void prepareStatementCreatingSetup(DT object, PreparedStatement statement) throws SQLException;
    public abstract void prepareStatementUpdatingSetup(DT object, PreparedStatement statement) throws SQLException;

}
