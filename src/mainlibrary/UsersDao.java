package mainlibrary;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsersDao {

    // Initialize logger for java.util.logging
    private static final Logger logger = Logger.getLogger(UsersDao.class.getName());

    // Validate user credentials
    public static boolean validate(String name, String password) {
        boolean status = false;
        String select = "SELECT id FROM Users WHERE UserName = ? AND UserPass = ?";

        try (Connection con = DB.getConnection(); 
             PreparedStatement ps = con.prepareStatement(select)) {
             
            ps.setString(1, name);
            ps.setString(2, password);
            
            try (ResultSet rs = ps.executeQuery()) {
                status = rs.next(); // Check if a matching record exists
            }
        } catch (SQLException e) {
            // Correct logging with exception using java.util.logging
            logger.log(Level.SEVERE, String.format("Error while validating user: %s", name), e);
        }
        return status;
    }

    // Check if the user already exists
    public static boolean CheckIfAlready(String UserName) {
        boolean status = false;
        String select = "SELECT id FROM Users WHERE UserName = ?";

        try (Connection con = DB.getConnection(); 
             PreparedStatement ps = con.prepareStatement(select)) {
             
            ps.setString(1, UserName);
            
            try (ResultSet rs = ps.executeQuery()) {
                status = rs.next(); // Check if a matching record exists
            }
        } catch (SQLException e) {
            // Correct logging with exception using java.util.logging
            logger.log(Level.SEVERE, String.format("Error while checking if user already exists: %s", UserName), e);
        }
        return status;
    }

    // Add a new user to the database
    public static int AddUser(String User, String UserPass, String UserEmail, String Date) {
        int status = 0;
        String insert = "INSERT INTO Users(UserPass, RegDate, UserName, Email) VALUES(?, ?, ?, ?)";

        try (Connection con = DB.getConnection(); 
             PreparedStatement ps = con.prepareStatement(insert)) {
             
            ps.setString(1, UserPass);
            ps.setString(2, Date);
            ps.setString(3, User);
            ps.setString(4, UserEmail);
            
            status = ps.executeUpdate();
        } catch (SQLException e) {
            // Correct logging with exception using java.util.logging
            logger.log(Level.SEVERE, String.format("Error while adding user: %s", User), e);
        }
        return status;
    }
}
