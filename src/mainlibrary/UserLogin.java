package mainlibrary;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Random;

public class UserLogin extends javax.swing.JFrame {

    private static final Logger logger = Logger.getLogger(UserLogin.class.getName());

    // Declare username, password, and buttons as instance variables
    private javax.swing.JTextField username;
    private javax.swing.JPasswordField password;
    private javax.swing.JButton jButton1;  // Login button
    private javax.swing.JButton jButton2;  // Back button

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
        String pass = String.valueOf(password.getPassword());
    
        // Sanitize user input
        user = XSSProtectionUtil.sanitize(user);
        pass = XSSProtectionUtil.sanitize(pass);
    
        // Validate inputs
        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username or Password cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        // Validate the credentials using the DAO method
        if (UsersDao.validate(user, pass)) {
            // If credentials are correct, trigger MFA
            triggerMFA(user);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Username or Password", "Login Error", JOptionPane.ERROR_MESSAGE);
            username.setText("");
            password.setText("");
        }
    }

    private void triggerMFA(String user) {
        String mfaCode = generateRandomMFA();
    
        // Display MFA prompt
        String inputCode = JOptionPane.showInputDialog(this, "Enter the MFA code sent to your device:", "MFA Authentication", JOptionPane.PLAIN_MESSAGE);
    
        // Sanitize the input code
        inputCode = XSSProtectionUtil.sanitize(inputCode);
    
        if (inputCode != null && inputCode.equals(mfaCode)) {
            startSession(user);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid MFA code", "MFA Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String generateRandomMFA() {
        // Generate a 6-digit random MFA code
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000));
    }

    private void startSession(String user) {
        // Store session data locally in the application's memory
        SessionManager.setUser(user);  // Store the logged-in user session
        JOptionPane.showMessageDialog(this, "Login successful!", "Welcome", JOptionPane.INFORMATION_MESSAGE);
        this.dispose();  // Close the login window
        UserLoginSuccess.main(new String[]{user});  // Redirect to the user success page
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
        MainLibrary.main(new String[]{});  // Go back to main library
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new UserLogin().setVisible(true);
        });
    }
}
