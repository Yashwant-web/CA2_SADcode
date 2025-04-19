package mainlibrary;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LibrarianDao {

    // Initialize logger
    private static final Logger logger = Logger.getLogger(LibrarianDao.class.getName());

    // Save a new librarian record into the database
    public static int save(String name, String password, String email, String address, String city, String contact) {
        int status = 0;
        String query = "INSERT INTO librarian(name, password, email, address, city, contact) VALUES(?, ?, ?, ?, ?, ?)";

        try (Connection con = DB.getConnection(); 
             PreparedStatement ps = con.prepareStatement(query)) {
             
            ps.setString(1, name);
            ps.setString(2, password);
            ps.setString(3, email);
            ps.setString(4, address);
            ps.setString(5, city);
            ps.setString(6, contact);
            status = ps.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while saving librarian", e);
        }
        return status;
    }

    // Delete a librarian record based on ID
    public static int delete(int id) {
        int status = 0;
        String query = "DELETE FROM librarian WHERE id = ?";

        try (Connection con = DB.getConnection(); 
             PreparedStatement ps = con.prepareStatement(query)) {
             
            ps.setInt(1, id);
            status = ps.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while deleting librarian", e);
        }
        return status;
    }

    // Validate librarian credentials
    public static boolean validate(String name, String password) {
        boolean status = false;
        // Specifying only the necessary columns (e.g., id and password)
        String query = "SELECT id FROM librarian WHERE UserName = ? AND Password = ?";

        try (Connection con = DB.getConnection(); 
             PreparedStatement ps = con.prepareStatement(query)) {
             
            ps.setString(1, name);
            ps.setString(2, password);
            
            try (ResultSet rs = ps.executeQuery()) {
                status = rs.next();  // Check if a matching record exists
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while validating librarian", e);
        }
        return status;
    }
}
