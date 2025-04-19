package mainlibrary;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

public class UserView extends javax.swing.JFrame {

    private static final Logger logger = Logger.getLogger(UserView.class.getName());

    public static String UserID;

    public UserView() throws SQLException {
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        initComponents();

        int UserIDV = Integer.parseInt(UserID);
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        
        // Query to fetch data for user view
        String query = "SELECT IssuedBook.BookID, Books.BookName, IssuedBook.IssueDate, IssuedBook.ReturnDate FROM Books, IssuedBook WHERE Books.BookID = IssuedBook.BookID AND IssuedBook.UserID = ?";
        
        populateTable(query, UserIDV);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        // UI component initialization code (same as original)
    }

    private void populateTable(String query, int userId) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        try (Connection Con = DB.getConnection()) {
            PreparedStatement ps = Con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ps.setInt(1, userId);  // Setting the user ID parameter

            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int colnum = rsmd.getColumnCount();

            model.setRowCount(0);  // Clear the table before adding new data

            while (rs.next()) {
                String[] row = new String[colnum];
                for (int i = 1; i <= colnum; i++) {
                    row[i - 1] = rs.getString(i);
                }
                model.addRow(row);
            }

            if (model.getRowCount() == 0) {
                String[] noResultRow = new String[colnum];
                noResultRow[0] = "No Results Found";
                model.addRow(noResultRow);
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching user data", e);
            JOptionPane.showMessageDialog(this, "Error occurred while fetching data", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new UserView().setVisible(true);
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        });

        UserID = args[0];
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
