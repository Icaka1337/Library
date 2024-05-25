package com.example.bookmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseService {
    private static final String CONNECTION_STRING = "jdbc:sqlserver://localhost;databaseName=Library;user=SA;password=Icaka-1337;trustServerCertificate=true;integratedSecurity=false;";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(CONNECTION_STRING);
    }
}
