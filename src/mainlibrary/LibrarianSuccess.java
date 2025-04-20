package mainlibrary;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class LibrarianSuccess extends javax.swing.JFrame {
    public static JFrame ThisLogined;
    public static String Name, LibrarianID, Email;

    public JFrame GetLibrarianLogin() {
        return ThisLogined;
    }

    public LibrarianSuccess() {
        initComponents();
        jTextField1.setText(Name);
        jTextField2.setText(LibrarianID);
        jTextField3.setText(Email);

        // Initialize button actions
        initializeButtonActions();
    }

    private void initComponents() {
        // Component initialization for buttons, labels, etc. remains the same
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeButtonActions() {
        // Create a Map with button names and corresponding buttons
        Map<String, JButton> buttonActionMap = new HashMap<>();
        buttonActionMap.put("IssueBookForm", jButton2);
        buttonActionMap.put("ReturnBookForm", jButton3);
        buttonActionMap.put("DeleteBook", jButton4);
        buttonActionMap.put("AllStudent", jButton6);
        buttonActionMap.put("UserForm", jButton8);
        buttonActionMap.put("BookForm", jButton5);
        buttonActionMap.put("ViewBook", jButton1);
        buttonActionMap.put("NewView", jButton9);

        // Add action listeners dynamically to each button in the map
        for (Map.Entry<String, JButton> entry : buttonActionMap.entrySet()) {
            entry.getValue().addActionListener(evt -> handleButtonAction(evt, "mainlibrary." + entry.getKey()));
        }

        // LogOut button action listener
        jButton7.addActionListener(evt -> {
            LibrarianSuccess.this.dispose();
            MainLibrary.main(new String[]{});
        });
    }

    private void handleButtonAction(ActionEvent evt, String formClassName) {
        try {
            // Dynamically load the class and call its main method
            Class<?> formClass = Class.forName(formClassName);
            formClass.getMethod("main", String[].class).invoke(null, (Object) new String[]{});  // Call the main method of the form class
        } catch (Exception e) {
            // Handle the error gracefully if form fails to open
            JOptionPane.showMessageDialog(this, "Error opening form: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            ThisLogined = new LibrarianSuccess();
            ThisLogined.setVisible(true);
        });

        String User = args[0];
        String Pass = args[1];
        try (Connection Con = DB.getConnection()) {
            PreparedStatement ps = Con.prepareStatement("select * from Librarian where UserName=? and Password=?");
            ps.setString(1, User);
            ps.setString(2, Pass);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Name = rs.getString("FullName");
                LibrarianID = rs.getString("LibrarianID");
                Email = rs.getString("Email");
            }
            Con.close();
        } catch (Exception f) {
            System.out.println(f);
        }
    }

    // Variables declaration - do not modify
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration
}
