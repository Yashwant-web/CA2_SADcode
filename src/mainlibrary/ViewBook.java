package mainlibrary;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewBook extends javax.swing.JFrame {

    private static final Logger logger = Logger.getLogger(ViewBook.class.getName());

    public ViewBook() throws SQLException {
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        initComponents();
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        populateTable(model, "SELECT * FROM Books", null); // Fetch all books initially
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        // Component initialization (same as original)
        // Ensure all components like jTable1, SearchField, NameRadio, etc. are properly initialized
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();  // Close the window
    }

    private void SearchActionPerformed(java.awt.event.ActionEvent evt) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        while (model.getRowCount() > 0) {
            model.removeRow(model.getRowCount() - 1);
        }

        String searchTerm = "%" + SearchField.getText().trim() + "%";  // Sanitize input
        if (NameRadio.isSelected()) {
            populateTable(model, "SELECT * FROM Books WHERE BookName LIKE ?", searchTerm);
        } else if (AuthorRadio.isSelected()) {
            populateTable(model, "SELECT * FROM Books WHERE Author LIKE ?", searchTerm);
        } else {
            JOptionPane.showMessageDialog(ViewBook.this, "Select Name or Author", "No Selection!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ALLActionPerformed(java.awt.event.ActionEvent evt) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        while (model.getRowCount() > 0) {
            model.removeRow(model.getRowCount() - 1);
        }
        populateTable(model, "SELECT * FROM Books", null);  // Show all books
    }

    private void populateTable(DefaultTableModel model, String query, String searchTerm) {
        try (Connection con = DB.getConnection()) {
            PreparedStatement ps = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            if (searchTerm != null) {
                ps.setString(1, searchTerm);  // Set the search parameter for LIKE query
            }
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int colnum = rsmd.getColumnCount();

            while (rs.next()) {
                String[] row = new String[colnum];
                for (int i = 1; i <= colnum; i++) {
                    row[i - 1] = rs.getString(i);
                }
                model.addRow(row);  // Add row to the table
            }

            if (model.getRowCount() == 0) {
                String[] noRow = new String[colnum];
                noRow[0] = "No result found";
                for (int i = 1; i < colnum; i++) {
                    noRow[i] = "";
                }
                model.addRow(noRow);  // Add no result message
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while querying database", e);  // Log the error
        }
    }

    // Event handler methods for radio buttons (NameRadio, AuthorRadio) 
    private void NameRadioActionPerformed(java.awt.event.ActionEvent evt) {
        AuthorRadio.setSelected(false);
        ALL.setSelected(false);
    }

    private void AuthorRadioActionPerformed(java.awt.event.ActionEvent evt) {
        NameRadio.setSelected(false);
        ALL.setSelected(false);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new ViewBook().setVisible(true);
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, "Error starting ViewBook", ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton ALL;
    private javax.swing.JRadioButton AuthorRadio;
    private javax.swing.JRadioButton NameRadio;
    private javax.swing.JButton Search;
    private javax.swing.JTextField SearchField;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

}
