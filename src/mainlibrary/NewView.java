package mainlibrary;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

public class NewView extends javax.swing.JFrame {

    private static final Logger logger = Logger.getLogger(NewView.class.getName());

    public NewView() throws SQLException {
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        initComponents();
        populateTable("select IssuedBook.BookID, IssuedBook.UserID, Books.BookName, IssuedBook.IssueDate, IssuedBook.ReturnDate from Books, IssuedBook where Books.BookID = IssuedBook.BookID");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        // UI component initialization code (same as original)
    }

    private void SearchActionPerformed(java.awt.event.ActionEvent evt) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        while (model.getRowCount() > 0) {
            model.removeRow(model.getRowCount() - 1);
        }

        String searchTerm = "%" + SearchField.getText().trim() + "%";
        String query = "";

        if (NameRadio.isSelected()) {
            query = "select IssuedBook.BookID, IssuedBook.UserID, Books.BookName, IssuedBook.IssueDate, IssuedBook.ReturnDate from Books, IssuedBook where Books.BookID = IssuedBook.BookID and Books.BookName like ?";
        } else if (BookIDRadio.isSelected()) {
            int bookID = Integer.parseInt(searchTerm);  // Convert to integer if searching by Book ID
            query = "select IssuedBook.BookID, IssuedBook.UserID, Books.BookName, IssuedBook.IssueDate, IssuedBook.ReturnDate from Books, IssuedBook where Books.BookID = IssuedBook.BookID and IssuedBook.BookID = ?";
        } else if (UserIDRadio.isSelected()) {
            int userID = Integer.parseInt(searchTerm);  // Convert to integer if searching by User ID
            query = "select IssuedBook.BookID, IssuedBook.UserID, Books.BookName, IssuedBook.IssueDate, IssuedBook.ReturnDate from Books, IssuedBook where Books.BookID = IssuedBook.BookID and IssuedBook.UserID = ?";
        }

        if (!query.isEmpty()) {
            populateTable(query, searchTerm);
        } else {
            JOptionPane.showMessageDialog(NewView.this, "Select a search criteria (Name, Book ID, or User ID)", "No Selection!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void populateTable(String query, String... params) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        try (Connection con = DB.getConnection()) {
            PreparedStatement ps = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            // Set the search parameters (if any)
            if (params.length > 0) {
                if (params[0] != null) {
                    ps.setString(1, params[0]);
                }
            }

            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int colnum = rsmd.getColumnCount();

            String[] row;
            while (rs.next()) {
                row = new String[colnum];
                for (int i = 1; i <= colnum; i++) {
                    row[i - 1] = rs.getString(i);
                }
                model.addRow(row);
            }

            if (model.getRowCount() == 0) {
                String[] noRow = new String[7];
                noRow[1] = "NO";
                noRow[2] = "RESULT";
                model.addRow(noRow);
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error executing query", e);
            JOptionPane.showMessageDialog(this, "Error occurred while fetching data", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ALLActionPerformed(java.awt.event.ActionEvent evt) {
        populateTable("select IssuedBook.BookID, IssuedBook.UserID, Books.BookName, IssuedBook.IssueDate, IssuedBook.ReturnDate from Books, IssuedBook where Books.BookID = IssuedBook.BookID");
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
        LibrarianSuccess.ThisLogined.setVisible(true);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new NewView().setVisible(true);
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton ALL;
    private javax.swing.JRadioButton BookIDRadio;
    private javax.swing.JRadioButton NameRadio;
    private javax.swing.JButton Search;
    private javax.swing.JTextField SearchField;
    private javax.swing.JRadioButton UserIDRadio;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
