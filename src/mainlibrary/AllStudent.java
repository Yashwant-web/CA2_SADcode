package mainlibrary;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AllStudent extends javax.swing.JFrame {
    private static final Logger logger = Logger.getLogger(AllStudent.class.getName());

    private javax.swing.JButton Search;
    private javax.swing.JTextField SearchField;
    private javax.swing.JRadioButton NameRadio;
    private javax.swing.JRadioButton AuthorRadio;
    private javax.swing.JRadioButton ALL;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton jButton1;

    public AllStudent() throws SQLException {
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        initComponents();  // Initialize the components
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        populateTable(model, "SELECT * FROM Users", null);  // Fetch all users initially
    }

    private void initComponents() {
        // Initialize components like jTable1, SearchField, NameRadio, etc.
        jTable1 = new javax.swing.JTable();
        SearchField = new javax.swing.JTextField();
        NameRadio = new javax.swing.JRadioButton();
        AuthorRadio = new javax.swing.JRadioButton();
        ALL = new javax.swing.JRadioButton();
        Search = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        // Set up table model and actions
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {"User ID", "UserPass", "RegDate", "UserName", "Email"}
        ));

        // Set up button actions
        jButton1.setText("Close");
        jButton1.addActionListener(evt -> closeWindow(evt));
        Search.setText("Search");
        Search.addActionListener(evt -> searchAction(evt));
        NameRadio.setText("Name");
        NameRadio.addActionListener(evt -> radioButtonAction(evt));
        AuthorRadio.setText("Email");
        AuthorRadio.addActionListener(evt -> radioButtonAction(evt));
        ALL.setText("ALL");
        ALL.addActionListener(evt -> allAction(evt));  // Add action listener for ALL
    }

    private void closeWindow(java.awt.event.ActionEvent evt) {
        this.dispose();  // Close the window
    }

    private void searchAction(java.awt.event.ActionEvent evt) {
        String searchTerm = "%" + SearchField.getText().trim() + "%";  // Sanitize input
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        clearTable(model);  // Clear existing data
        if (NameRadio.isSelected()) {
            populateTable(model, "SELECT * FROM Users WHERE UserName LIKE ?", searchTerm);
        } else if (AuthorRadio.isSelected()) {
            populateTable(model, "SELECT * FROM Users WHERE Email LIKE ?", searchTerm);
        } else {
            JOptionPane.showMessageDialog(AllStudent.this, "Select Name or Email", "No Selection!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void allAction(java.awt.event.ActionEvent evt) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        clearTable(model);  // Clear existing data
        populateTable(model, "SELECT * FROM Users", null);  // Show all users
    }

    // Helper method to clear the table
    private void clearTable(DefaultTableModel model) {
        model.setRowCount(0); // A more efficient way to clear the table
    }

    // Helper method to populate the table with data from the database
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

            // Show a "No result found" message if no rows are returned
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

    // Event handler for NameRadio and AuthorRadio button action
    private void radioButtonAction(java.awt.event.ActionEvent evt) {
        // Deselect other radio button and ALL button if selected
        if (evt.getSource() == NameRadio) {
            AuthorRadio.setSelected(false);
        } else if (evt.getSource() == AuthorRadio) {
            NameRadio.setSelected(false);
        }
        ALL.setSelected(false);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new AllStudent().setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(AllStudent.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
