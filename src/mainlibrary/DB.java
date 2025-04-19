package mainlibrary;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Yash
 */
public class DB {

    private static final Logger logger = Logger.getLogger(DB.class.getName());
    private static Properties properties = new Properties();

    static {
        try (FileInputStream input = new FileInputStream("config.properties")) {
            properties.load(input);  // Load configuration from file
        } catch (IOException ex) {
            // Log the exception without printing stack trace to console
            logger.log(Level.SEVERE, "Failed to load configuration file", ex);
        }
    }

    public static Connection getConnection() {
        try {
            String user = properties.getProperty("db.user");
            String password = properties.getProperty("db.password");
            String url = properties.getProperty("db.url");

            // The driver is now automatically registered, no need for Class.forName
            // Return the connection
            return DriverManager.getConnection(url, user, password);

        } catch (SQLException e) {
            // Log the exception properly instead of printing stack trace
            logger.log(Level.SEVERE, "Database connection failed", e);
        }
        return null;  // Return null if the connection fails
    }
}
