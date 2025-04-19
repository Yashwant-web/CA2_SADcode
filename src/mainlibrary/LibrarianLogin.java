package mainlibrary;

import javax.swing.JOptionPane;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LibrarianLogin extends javax.swing.JFrame {

    private static final Logger logger = Logger.getLogger(LibrarianLogin.class.getName());

    // Declare username and password fields as instance variables
    private javax.swing.JTextField username;
    private javax.swing.JPasswordField password;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;

    public LibrarianLogin() {
        initComponents();  // Initialize components
    }

    private void initComponents() {
        // Initialize the components for the UI (same as original)
        
        // Initialize the username and password fields
        username = new javax.swing.JTextField();
        password = new javax.swing.JPasswordField();
        
        // Initialize the buttons
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        
        // Set up the button action listeners
        jButton1.setText("Login");
        jButton1.addActionListener(evt -> jButton1ActionPerformed(evt));

        jButton2.setText("Back");
        jButton2.addActionListener(evt -> jButton2ActionPerformed(evt));

        // Layout and other component setups...
        // (For brevity, layout code is omitted here)
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        String Uname = username.getText().trim();
        char[] passChars = password.getPassword();
        String Pass = new String(passChars);

        if (Uname.isEmpty() || Pass.isEmpty()) {
            JOptionPane.showMessageDialog(LibrarianLogin.this, "Username or Password cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate the credentials using LibrarianDao
        boolean isValid = LibrarianDao.validate(Uname, Pass);

        if (isValid) {
            this.dispose();  // Close login window
            LibrarianSuccess.main(new String[]{Uname});
        } else {
            JOptionPane.showMessageDialog(LibrarianLogin.this, "Sorry, Username or Password Error", "Login Error", JOptionPane.ERROR_MESSAGE);
            username.setText("");  // Clear input fields
            password.setText("");  // Clear input fields
        }
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
        MainLibrary.main(new String[]{});
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new LibrarianLogin().setVisible(true);
        });
    }
}
