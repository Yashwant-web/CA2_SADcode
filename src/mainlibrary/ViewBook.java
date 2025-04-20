package mainlibrary;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewBook extends javax.swing.JFrame { 
    private static final Logger logger = Logger.getLogger(ViewBook.class.getName());

    
    private javax.swing.JTable jTable1;

    public ViewBook() throws SQLException {
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        initComponents();
        populateTable("SELECT * FROM Books", null); // Fetch all books initially
    }

    private void initComponents() {
        // Initialize components here (table, buttons, radio buttons, etc.)
        jTable1 = new javax.swing.JTable();
        SearchField = new javax.swing.JTextField();
        NameRadio = new javax.swing.JRadioButton("Name");
        AuthorRadio = new javax.swing.JRadioButton("Author");
        ALL = new javax.swing.JRadioButton("ALL");
        Search = new javax.swing.JButton("Search");
        jButton1 = new javax.swing.JButton("Close");

        // Initialize table model
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {"Book ID", "Book Name", "Author", "Publisher", "Year"}
        ));

        // Set up button actions
        Search.addActionListener(evt -> handleSearchAction(evt));
        ALL.addActionListener(evt -> handleALLAction(evt));
        NameRadio.addActionListener(evt -> handleRadioAction(evt));
        AuthorRadio.addActionListener(evt -> handleRadioAction(evt));
        jButton1.addActionListener(evt -> handleCloseAction(evt));
    }

    private void handleSearchAction(java.awt.event.ActionEvent evt) {
        String searchTerm = "%" + SearchField.getText().trim() + "%";
        if (NameRadio.isSelected()) {
            populateTable("SELECT * FROM Books WHERE BookName LIKE ?", searchTerm);
        } else if (AuthorRadio.isSelected()) {
            populateTable("SELECT * FROM Books WHERE Author LIKE ?", searchTerm);
        } else {
            JOptionPane.showMessageDialog(this, "Select Name or Author", "No Selection!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleALLAction(java.awt.event.ActionEvent evt) {
        populateTable("SELECT * FROM Books", null); // Show all books
    }

    private void handleRadioAction(java.awt.event.ActionEvent evt) {
        // Deselect other radio buttons when one is selected
        AuthorRadio.setSelected(false);
        ALL.setSelected(false);
    }

    private void handleCloseAction(java.awt.event.ActionEvent evt) {
        this.dispose();  // Close the window
    }

    // Helper method to clear the table
    private void clearTable() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);  // Clears all rows from the table
    }

    // Helper method to populate the table with data from the database
    private void populateTable(String query, String searchTerm) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        clearTable();  // Clear existing data in the table
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
                model.addRow(row);
            }
            // If no results found, show a message
            if (model.getRowCount() == 0) {
                String[] noRow = new String[colnum];
                noRow[0] = "No result found";
                for (int i = 1; i < colnum; i++) {
                    noRow[i] = "";
                }
                model.addRow(noRow);  // Add "No result found" message
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while querying database", e);  // Log the error
        }
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
    // End of variables declaration//GEN-END:variables
}
