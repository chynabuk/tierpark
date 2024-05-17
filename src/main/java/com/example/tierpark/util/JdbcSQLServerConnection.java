package com.example.tierpark.util;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcSQLServerConnection {
    private static final String SERVER = "KUBA\\DIGGER";
    private static final String DB_URL = "jdbc:sqlserver://" + SERVER + ";databaseName=Tierpark;encrypt=false";
    private static final String USER = "sa";
    private static final String PASSWORD = "1234";
    private static Connection connection;

    public static Connection connect(){
        connection = null;

        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return connection;
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
