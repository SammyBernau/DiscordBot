package org.gamerbot.support.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class Database {
    private static String HOST = ""; // website ip address
    private static String USER = "";
    private static String PASS = "";
    private static String DATABASE = "";

    private static Connection connection;

    protected static Connection getInstance() {
        if (connection == null) {
            connection = connect();
        }
        return connection;
    }

    private static Connection connect() {
        try {
            return DriverManager.getConnection("jdbc:mysql://"+HOST+":3306/"+DATABASE+"?autoReconnect=true", USER, PASS);
        } catch (SQLException e) {
            System.out.println("Failing connecting to database!");
            return null;
        }
    }



}
