package mainlibrary;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AllStudent extends javax.swing.JFrame {

    // Declare GUI components as instance variables
    private javax.swing.JButton Search;
    private javax.swing.JTextField SearchField;
    private javax.swing.JRadioButton NameRadio;
    private javax.swing.JRadioButton AuthorRadio;
    private javax.swing.JRadioButton ALL; // Declare ALL radio button
    private javax.swing.JTable jTable1;
    private javax.swing.JButton jButton1;

    private static final Logger logger = Logger.getLogger(AllStudent.class.getName());

    public AllStudent() throws SQLException {
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        initComponents();  // Initialize the components
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        populateTable(model, "SELECT * FROM Users", null);
    }

    private void initComponents() {
        // Component initialization (GUI layout, buttons, text fields, etc.)
        // Make sure to initialize all components like jTable1, SearchField, NameRadio, etc.

        jTable1 = new javax.swing.JTable();
        SearchField = new javax.swing.JTextField();
        NameRadio = new javax.swing.JRadioButton();
        AuthorRadio = new javax.swing.JRadioButton();
        ALL = new javax.swing.JRadioButton(); // Initialize the ALL radio button
        Search = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        // Initialize jTable1
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {"User ID", "UserPass", "RegDate", "UserName", "Email"}
        ));

        // Set up button and field actions
        jButton1.setText("Close");
        jButton1.addActionListener(evt -> jButton1ActionPerformed(evt));

        Search.setText("Search");
        Search.addActionListener(evt -> SearchActionPerformed(evt));

        NameRadio.setText("Name");
        NameRadio.addActionListener(evt -> NameRadioActionPerformed(evt));

        AuthorRadio.setText("Email");
        AuthorRadio.addActionListener(evt -> AuthorRadioActionPerformed(evt));

        ALL.setText("ALL");
        ALL.addActionListener(evt -> ALLActionPerformed(evt)); // Add action listener for ALL

        // Layout code for components, panel setup, etc.
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();  // Close the window
    }

    private void SearchActionPerformed(java.awt.event.ActionEvent evt) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        while (model.getRowCount() > 0) {
            model.removeRow(model.getRowCount() - 1);
        }

        String searchTerm = "%" + SearchField.getText().trim() + "%";
        if (NameRadio.isSelected()) {
            populateTable(model, "SELECT * FROM Users WHERE UserName LIKE ?", searchTerm);
        } else if (AuthorRadio.isSelected()) {
            populateTable(model, "SELECT * FROM Users WHERE Email LIKE ?", searchTerm);
        } else {
            JOptionPane.showMessageDialog(AllStudent.this, "Select Name or Email", "No Selection!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ALLActionPerformed(java.awt.event.ActionEvent evt) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        while (model.getRowCount() > 0) {
            model.removeRow(model.getRowCount() - 1);
        }
        populateTable(model, "SELECT * FROM Users", null);
    }

    private void populateTable(DefaultTableModel model, String query, String searchTerm) {
        try (Connection con = DB.getConnection()) {
            PreparedStatement ps = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            if (searchTerm != null) {
                ps.setString(1, searchTerm);
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

            if (model.getRowCount() == 0) {
                String[] noRow = new String[colnum];
                noRow[0] = "No result found";
                for (int i = 1; i < colnum; i++) {
                    noRow[i] = "";
                }
                model.addRow(noRow);
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while querying database", e);
        }
    }

    // Event handler for NameRadio button action
    private void NameRadioActionPerformed(java.awt.event.ActionEvent evt) {
        AuthorRadio.setSelected(false);  // Deselect other radio button
        ALL.setSelected(false);  // Deselect ALL button if it's selected
    }

    // Event handler for AuthorRadio button action
    private void AuthorRadioActionPerformed(java.awt.event.ActionEvent evt) {
        NameRadio.setSelected(false);  // Deselect other radio button
        ALL.setSelected(false);  // Deselect ALL button if it's selected
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
