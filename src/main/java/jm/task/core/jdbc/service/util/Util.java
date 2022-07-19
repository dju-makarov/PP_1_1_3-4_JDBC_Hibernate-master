package jm.task.core.jdbc.service.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    public static Connection getMySQLConnection() {
        String hostName = "localhost";

        String dbName = "db1";
        String userName = "dju";
        String password = "1234";
        String keys = "?useSSL=false";

        return getMySQLConnection(hostName, dbName, userName, password, keys);
    }

 public static Connection getMySQLConnection(String hostName, String dbName,
                                                String userName, String password, String keys) {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName + keys;
            Connection connector = DriverManager.getConnection(connectionURL, userName,
                    password);
            connector.setAutoCommit(false);
            return connector;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("database connection error");
            return null;
        }




    }
}