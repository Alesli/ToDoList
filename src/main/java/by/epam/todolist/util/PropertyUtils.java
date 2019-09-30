package by.epam.todolist.util;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

/**
 * Class for reading data from a connection.property file when connecting to a database
 *
 * @author Alesia Skarakhod
 */
public class PropertyUtils {

    public static Properties getProperties(String fileName) {
        Properties properties = new Properties();
        try {
            properties.load(PropertyUtils.class.getClassLoader()
                                    .getResourceAsStream("property/" + fileName)
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
