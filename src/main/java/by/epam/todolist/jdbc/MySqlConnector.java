package by.epam.todolist.jdbc;

import by.epam.todolist.util.PropertyUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Class for establishing a connection with MySQL database
 *
 * @author Alesia Skarakhod
 */
public class MySqlConnector {

    private static MySqlConnector instance;
    private static Connection connection;

    /**
     * The method for connecting to the MySQL server by property
     * readable from the connection.property file
     */
    private MySqlConnector() {
        Properties properties = PropertyUtils.getProperties("connection.properties");
        Connection conn = null;
        try {
            Class.forName(properties.getProperty("driver"));
            try {
                conn = DriverManager.getConnection(
                        properties.getProperty("url"),
                        properties.getProperty("username"),
                        properties.getProperty("password")
                );
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        connection = conn;
    }

    /**
     * The static method, returns a single instance of the class,
     * checking whether it was not created before
     * (object creation at the first method call)
     *
     * @return instance
     */
    public static MySqlConnector getInstance() {
        if (instance == null) {
            instance = new MySqlConnector();
        }
        return instance;
    }

    /**
     * The method gets connection
     */
    public Connection getConnection() {
        return connection;
    }
}
