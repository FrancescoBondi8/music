package com.example.music.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {

    private final static String URL = "jdbc:mysql://localhost:3306/music";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "test174643";

    public static Connection open() throws SQLException {
        return DriverManager.getConnection(URL,USERNAME,PASSWORD);
    }

}
