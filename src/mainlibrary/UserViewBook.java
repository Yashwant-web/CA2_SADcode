package mainlibrary;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

public class UserViewBook extends javax.swing.JFrame {

    private static final Logger logger = Logger.getLogger(UserViewBook.class.getName());

    public UserViewBook() throws SQLException {
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        initComponents();
        populateTable("select Books.BookID, Books.BookName, Books.Genre, Books.Author, Books.Publisher, Books.Row, Books.Shelf, IssuedBook.UserID from Books left outer join IssuedBook on Books.BookID = IssuedBook.BookID");
    }

    private void initComponents() {
        // UI component initialization code (same as original)
    }

    private void SearchActionPerformed(java.awt.event.ActionEvent evt) {
        String searchTerm = SearchField.getText().trim();
        if (searchTerm.isEmpty()) {
            JOptionPane.showMessageDialog(UserViewBook.this, "Search Field is Empty", "Search Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String query = "";
        if (ALL.isSelected()) {
            query = "select Books.BookID, Books.BookName, Books.Genre, Books.Author, Books.Publisher, Books.Row, Books.Shelf, IssuedBook.UserID from Books left outer join IssuedBook on Books.BookID = IssuedBook.BookID";
        } else if (NotIssued.isSelected()) {
            query = "select Books.BookID, Books.BookName, Books.Genre, Books.Author, Books.Publisher, Books.Row, Books.Shelf, IssuedBook.UserID from Books left outer join IssuedBook on Books.BookID = IssuedBook.BookID where IssuedBook.UserID is null";
        } else if (NameRadio.isSelected()) {
            query = "select Books.BookID, Books.BookName, Books.Genre, Books.Author, Books.Publisher, Books.Row, Books.Shelf, IssuedBook.UserID from Books left outer join IssuedBook on Books.BookID = IssuedBook.BookID where Books.BookName like ?";
        } else if (AuthorRadio.isSelected()) {
            query = "select Books.BookID, Books.BookName, Books.Genre, Books.Author, Books.Publisher, Books.Row, Books.Shelf, IssuedBook.UserID from Books left outer join IssuedBook on Books.BookID = IssuedBook.BookID where Books.Author like ?";
        }

        if (!query.isEmpty()) {
            populateTable(query, "%" + searchTerm + "%");
        } else {
            JOptionPane.showMessageDialog(UserViewBook.this, "Select Search Criteria", "No Selection", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void populateTable(String query, String... params) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        try (Connection con = DB.getConnection()) {
            PreparedStatement ps = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            // Set parameters if any
            if (params.length > 0 && params[0] != null) {
                ps.setString(1, params[0]);
            }

            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int colnum = rsmd.getColumnCount();

            model.setRowCount(0); // clear existing rows

            while (rs.next()) {
                String[] row = new String[colnum];
                for (int i = 1; i <= colnum; i++) {
                    row[i - 1] = rs.getString(i);
                }

                // Check if book is issued or not and add the status to the last column
                if (rs.getString(colnum) == null) {
                    row[colnum - 1] = "Not Issued";
                } else {
                    row[colnum - 1] = "Issued";
                }

                model.addRow(row);
            }

            if (model.getRowCount() == 0) {
                String[] noResultRow = new String[colnum];
                noResultRow[3] = "NO";
                noResultRow[4] = "RESULT";
                model.addRow(noResultRow);
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while fetching data", e);
            JOptionPane.showMessageDialog(this, "Error occurred while fetching data", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ShowALLActionPerformed(java.awt.event.ActionEvent evt) {
        AuthorRadio.setSelected(false);
        NameRadio.setSelected(false);
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); // clear existing rows
        populateTable("select Books.BookID, Books.BookName, Books.Genre, Books.Author, Books.Publisher, Books.Row, Books.Shelf, IssuedBook.UserID from Books left outer join IssuedBook on Books.BookID = IssuedBook.BookID");
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new UserViewBook().setVisible(true);
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton ALL;
    private javax.swing.JRadioButton AuthorRadio;
    private javax.swing.JRadioButton NameRadio;
    private javax.swing.JRadioButton NotIssued;
    private javax.swing.JButton Search;
    private javax.swing.JTextField SearchField;
    private javax.swing.JButton ShowALL;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
