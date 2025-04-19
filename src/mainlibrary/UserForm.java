/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainlibrary;

import java.util.Calendar;
import javax.swing.JOptionPane;

/**
 *
 * @author bikash
 */
public class UserForm extends javax.swing.JFrame {

    public UserForm() {
        initComponents();
    }

    private void initComponents() {
        // UI components initialization (same as before)
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        // Validate user inputs
        String userName = UserName.getText();
        String userEmail = Email.getText();
        String userPass = String.valueOf(Password.getPassword());
        String position = Position.getText();
        String program = Program.getText();
        String year = Year.getText();

        if (userName.isEmpty() || userEmail.isEmpty() || userPass.isEmpty() || position.isEmpty() || program.isEmpty() || year.isEmpty()) {
            JOptionPane.showMessageDialog(UserForm.this, "All fields must be filled!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate email format
        if (!isValidEmail(userEmail)) {
            JOptionPane.showMessageDialog(UserForm.this, "Invalid email format!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (UsersDao.CheckIfAlready(userName)) {
            JOptionPane.showMessageDialog(UserForm.this, "Username already taken!", "Adding new User Error!", JOptionPane.ERROR_MESSAGE);
        } else {
            // Hash the password before storing (this is just a placeholder; replace with actual hashing)
            String hashedPassword = hashPassword(userPass);

            // Get current date for registration
            Calendar cal = Calendar.getInstance();
            String regDate = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DATE);

            // Add the user to the database
            if (UsersDao.AddUser(userName, hashedPassword, userEmail, regDate) != 0) {
                JOptionPane.showMessageDialog(UserForm.this, "User added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
            } else {
                JOptionPane.showMessageDialog(UserForm.this, "Unable to add new user.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Helper method to validate email format
    private boolean isValidEmail(String email) {
        String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailPattern);
    }

    // Placeholder for password hashing function (use a real hashing method like SHA-256 or bcrypt)
    private String hashPassword(String password) {
        return password; // Replace with actual hashing logic
    }

    // Helper method to clear input fields
    private void clearFields() {
        UserName.setText("");
        Password.setText("");
        Email.setText("");
        Position.setText("");
        Program.setText("");
        Year.setText("");
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
        LibrarianSuccess.ThisLogined.setVisible(true);
    }

    // Main method to display the form
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JTextField Email;
    private javax.swing.JPasswordField Password;
    private javax.swing.JTextField Position;
    private javax.swing.JTextField Program;
    private javax.swing.JTextField UserName;
    private javax.swing.JTextField Year;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    // End of variables declaration
}
