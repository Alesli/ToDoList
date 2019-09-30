package by.epam.todolist.jdbc;

import by.epam.todolist.util.PropertyUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * The class for establishing a connection with MySQL database
 *
 * @author Alesia Skarakhod
 */
public class MySqlQuery {

    private static MySqlQuery instance;
    private static Map<String, String> queries;

    /**
     * The method for connecting to the MySQL server by property
     * readable from the connection.property file
     */
    private MySqlQuery() {
        Properties properties = PropertyUtils.getProperties("mysql_query.properties");
        Map<String, String> map = new HashMap<>();
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            map.put((String) entry.getKey(), (String) entry.getValue());
        }
        queries = map;
    }

    /**
     * The static method, returns a single instance of the class,
     * checking whether it was not created before
     *
     * @return instance
     */
    public static MySqlQuery getInstance() {
        if (instance == null) {
            instance = new MySqlQuery();
        }
        return instance;
    }

    /**
     * The method will receive a request to the MySQL server
     * from the mysql_queries.property file by key
     *
     * @param key String, request name
     * @return queries.get(key)
     */
    public String getQuery(String key) {
        return queries.get(key);
    }
}