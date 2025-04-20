/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainlibrary;

import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import static mainlibrary.LibrarianSuccess.ThisLogined;

/**
 *
 * @author bikash
 */
public class IssueBookForm extends javax.swing.JFrame {

    public IssueBookForm() {
        initComponents();
        // Initialize the current date
        Calendar cal = Calendar.getInstance();
        IYear.setText(String.valueOf(cal.get(Calendar.YEAR)));
        IMonth.setText(String.valueOf(cal.get(Calendar.MONTH) + 1));
        IDate.setText(String.valueOf(cal.get(Calendar.DATE)));

        // Set the return date to 15 days from now
        cal.add(Calendar.DAY_OF_YEAR, 15);
        RYear.setText(String.valueOf(cal.get(Calendar.YEAR)));
        RMonth.setText(String.valueOf(cal.get(Calendar.MONTH) + 1));
        RDate.setText(String.valueOf(cal.get(Calendar.DATE)));
    }

    private void initComponents() {
        // UI component initialization (same as before)
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // Validate that both User ID and Book ID are provided
        String bookIdText = BookID.getText().trim();
        String userIdText = UserID.getText().trim();

        if (bookIdText.isEmpty() || userIdText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both Book ID and User ID.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int bookId = Integer.parseInt(bookIdText);
        int userId = Integer.parseInt(userIdText);

        // Construct issue and return dates
        String issueDate = IYear.getText() + "-" + IMonth.getText() + "-" + IDate.getText();
        String returnDate = RYear.getText() + "-" + RMonth.getText() + "-" + RDate.getText();

        // Validate if the book and user exist in the database
        if (TransBookDao.BookValidate(bookIdText) && TransBookDao.UserValidate(userIdText)) {

            // Check if the user has already reached the maximum number of issued books
            if (TransBookDao.Check(userId) == 0) {
                JOptionPane.showMessageDialog(this, "User has already issued the maximum number of books.", "Issue Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // Issue the book
                if (TransBookDao.IssueBook(bookId, userId, issueDate, returnDate) != 0) {
                    JOptionPane.showMessageDialog(this, "The book has been issued successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Unable to issue the book. Please try again later.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            if (!TransBookDao.BookValidate(bookIdText)) {
                JOptionPane.showMessageDialog(this, "The book is not available in the library database.", "Issue Error", JOptionPane.ERROR_MESSAGE);
            }
            if (!TransBookDao.UserValidate(userIdText)) {
                JOptionPane.showMessageDialog(this, "The user is not available in the library database.", "Issue Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        this.dispose();
        LibrarianSuccess.ThisLogined.setVisible(true);
    }

    // Clear all the fields after a successful book issue
    private void clearFields() {
        BookID.setText("");
        UserID.setText("");
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IssueBookForm().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField BookID;
    private javax.swing.JTextField IDate;
    private javax.swing.JTextField IMonth;
    private javax.swing.JTextField IYear;
    private javax.swing.JTextField RDate;
    private javax.swing.JTextField RMonth;
    private javax.swing.JTextField RYear;
    private javax.swing.JTextField UserID;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    // End of variables declaration//GEN-END:variables
}
