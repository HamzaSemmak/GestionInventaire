package com.example.gestioninventairesystem.Config;

import com.example.gestioninventairesystem.Logger.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static final String URL = "jdbc:mysql://localhost:3306/GestionInventaire";

    private static final String USER = "root";

    private static final String PASSWORD = "";

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    private static Connection connection = null;

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            com.example.gestioninventairesystem.Logger.Logger.error(e.toString());
            e.printStackTrace();
        }
    }

    public static Connection connection() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Logger.info("DriverManager True, connected to database : " + connection.toString());
        } catch (SQLException e) {
            Logger.error(e.toString());
            throw new RuntimeException(e);
        }
        return connection;
    }

    public static void close() throws SQLException {
        connection.close();
    }

}
