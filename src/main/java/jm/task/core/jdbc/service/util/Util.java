package jm.task.core.jdbc.service.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Util {
    public static final String hostName = "localhost";
    public static final String dbName = "db1";
    public static final String userName = "dju";
    public static final String password = "1234";
    public static final String keys = "?useSSL=false";
    public static Connection getMySQLConnection() {
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
            System.out.println("Ошибка подключения к БД через JDBC");
            return null;
        }
    }

    public SessionFactory getSessionFactory() {
        return createSessionFactory();
    }

    private SessionFactory createSessionFactory() {
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(getSetting()).build();
        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        Metadata metadata = metadataSources.buildMetadata();
        return metadata.getSessionFactoryBuilder().build();
    }

    private Map<String, String> getSetting() {
        Map<String, String> map = new HashMap<>();
        map.put("connection.driver_class", "com.mysql.cj.jdbc.Driver");
        map.put("dialect", "org.hibernate.dialect.MySQL8Dialect");
        map.put("hibernate.connection.url", "jdbc:mysql://" + hostName + ":3306/" + dbName + keys);
        map.put("hibernate.connection.username", userName);
        map.put("hibernate.connection.password", password);
        map.put("hibernate.current_session_context_class", "thread");
        map.put("hibernate.show_sql", "true");
        map.put("hibernate.format_sql", "true");
        return map;
    }
}