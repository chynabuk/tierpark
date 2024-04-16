package com.example.tierpark.util;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcSQLServerConnection {
    private static final String SERVER = "Kuba\\DIGGER";
    private static final String DB_URL = "jdbc:sqlserver://" + SERVER + ";databaseName=Tierpark;encrypt=false";
    private static final String USER = "sa";
    private static final String PASSWORD = "1234";
    private static Connection connection;

    public static Connection connect(){
        connection = null;

        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            if (connection != null) {
                DatabaseMetaData dm = connection.getMetaData();
                System.out.println("Driver name: " + dm.getDriverName());
                System.out.println("Driver version: " + dm.getDriverVersion());
                System.out.println("Product name: " + dm.getDatabaseProductName());
                System.out.println("Product version: " + dm.getDatabaseProductVersion());
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return connection;
    }
}
