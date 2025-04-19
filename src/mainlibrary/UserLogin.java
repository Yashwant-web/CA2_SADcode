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

    @SuppressWarnings("unchecked")
    private void initComponents() {
        // Initialize components here
        // Other components initialization remains unchanged

        // Initialize the username and password fields
        username = new javax.swing.JTextField();
        password = new javax.swing.JPasswordField();
        
        // Initialize buttons
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        // Set up button 1 (Login)
        jButton1.setText("Login");
        jButton1.addActionListener(evt -> jButton1ActionPerformed(evt));

        // Set up button 2 (Back)
        jButton2.setText("Back");
        jButton2.addActionListener(evt -> jButton2ActionPerformed(evt));

        // Set layout and other component setups...
        // (For brevity, layout code is omitted here)

    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        String user = username.getText().trim();
        char[] passChars = password.getPassword();  // Get the password
        String pass = new String(passChars);  // Convert to string (avoid printing plain password in logs)

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
