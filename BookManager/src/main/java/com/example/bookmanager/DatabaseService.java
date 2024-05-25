package com.example.bookmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseService {
    private static String host= "CHANGE_ME";
    private static String dbName = "CHANGE_ME";
    private static String user = "CHANGE_ME";
    private static String password = "CHANGE_ME";

    private static final String CONNECTION_STRING = String.format("jdbc:sqlserver://%s;databaseName=%s;user=%s;password=%s;trustServerCertificate=true;integratedSecurity=false;", host, dbName, user, password);

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(CONNECTION_STRING);
    }
}
