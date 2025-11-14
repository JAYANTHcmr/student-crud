package com.student.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.io.InputStream;

/**
 * Simple DB connection helper.
 * It loads db.url, db.user, db.password from src/main/resources/config.properties
 */
public class DBConnection {
    private static String url;
    private static String user;
    private static String password;

    static {
        try (InputStream in = DBConnection.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties p = new Properties();
            if (in != null) {
                p.load(in);
                url = p.getProperty("db.url");
                user = p.getProperty("db.user");
                password = p.getProperty("db.password");
            } else {
                System.err.println("config.properties not found on classpath. Create src/main/resources/config.properties from config.sample.properties");
            }
            // ensure driver class is loaded
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(url, user, password);
    }
}
