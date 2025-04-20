package mainlibrary;

import javax.swing.JOptionPane;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserLogin extends javax.swing.JFrame {

    private static final Logger logger = Logger.getLogger(UserLogin.class.getName());

    // Declare username, password, and buttons as instance variables
    private javax.swing.JTextField username;
    private javax.swing.JPasswordField password;
    private javax.swing.JButton jButton1;  // Declare button 1 (Login)
    private javax.swing.JButton jButton2;  // Declare button 2 (Back)

    public UserLogin() {
        initComponents();  // Initialize components
    }

    private void initComponents() {
        // Initialize the username, password fields, and buttons
        username = new javax.swing.JTextField();
        password = new javax.swing.JPasswordField();
        
        // Initialize buttons
        jButton1 = createLoginButton();
        jButton2 = createBackButton();

        // Layout code here (omitted for brevity)
        // You would add the components to the layout here
    }

    private javax.swing.JButton createLoginButton() {
        javax.swing.JButton loginButton = new javax.swing.JButton();
        loginButton.setText("Login");
        loginButton.addActionListener(evt -> jButton1ActionPerformed(evt));
        return loginButton;
    }

    private javax.swing.JButton createBackButton() {
        javax.swing.JButton backButton = new javax.swing.JButton();
        backButton.setText("Back");
        backButton.addActionListener(evt -> jButton2ActionPerformed(evt));
        return backButton;
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        String user = username.getText().trim();
        String pass = String.valueOf(password.getPassword());  // Retrieve the password directly as String

        // Validate inputs
        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username or Password cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate the credentials using the DAO method
        if (UsersDao.validate(user, pass)) {
            this.dispose();
            UserLoginSuccess.main(new String[]{user});
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Username or Password", "Login Error", JOptionPane.ERROR_MESSAGE);
            username.setText("");
            password.setText("");
        }
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
        MainLibrary.main(new String[]{});
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new UserLogin().setVisible(true);
        });
    }
}
