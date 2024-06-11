package com.example.tierpark.util;
import com.example.tierpark.entities.RoleEnum;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcSQLServerConnection {
    private static final String SERVER = "KUBA\\DIGGER";
    private static final String DB_URL = "jdbc:sqlserver://" + SERVER + ";databaseName=Tierpark2;encrypt=false";
    private static RoleEnum CURRENT_USER = RoleEnum.ADMIN;
    private static final String PASSWORD = "1234";
    private static Connection connection;

    public static Connection connect(){
        connection = null;

        try {
            connection = DriverManager.getConnection(DB_URL, CURRENT_USER.toString(), PASSWORD);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return connection;
    }

    public static void changeConfiguration(int roleId){
        RoleEnum role = RoleEnum.getRoleById(roleId);
        CURRENT_USER = role;

    }

    public static void close(){
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
    }
}
