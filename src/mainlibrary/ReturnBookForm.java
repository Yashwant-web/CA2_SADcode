package mainlibrary;

import javax.swing.JOptionPane;  // For JOptionPane
import java.util.Calendar;  // For Calendar

public class ReturnBookForm extends javax.swing.JFrame {

    public ReturnBookForm() {
        initComponents();
        Calendar cal = Calendar.getInstance();
        IDate.setText(String.valueOf(cal.get(Calendar.DATE)));
        IMonth.setText(String.valueOf(cal.get(Calendar.MONTH) + 1));
        IYear.setText(String.valueOf(cal.get(Calendar.YEAR)));
    }

    private void initComponents() {
        // Initialize components
    
        // Add action listeners
        jButton1.addActionListener(evt -> handleReturnBookAction());
        jButton2.addActionListener(evt -> handleBackAction());
        UserID.addActionListener(evt -> handleFieldAction(evt));
        BookID.addActionListener(evt -> handleFieldAction(evt));
        IDate.addActionListener(evt -> handleFieldAction(evt));
        IMonth.addActionListener(evt -> handleFieldAction(evt));
        IYear.addActionListener(evt -> handleFieldAction(evt));
    }

    private void handleReturnBookAction() {
        try {
            int bookID = Integer.parseInt(BookID.getText());
            int userID = Integer.parseInt(UserID.getText());
            String returnDate = IYear.getText() + "-" + IMonth.getText() + "-" + IDate.getText();

            if (isValidBookAndUser(bookID, userID)) {
                if (TransBookDao.CheckIssuedBook(bookID)) {
                    if (TransBookDao.ReturnBook(bookID, userID) != 0) {
                        showMessage("Book is returned by the User!", "Returning Book Successful!", JOptionPane.INFORMATION_MESSAGE);
                        resetFields();
                    } else {
                        showMessage("Unable to Return Book!", "Returning Book Error!", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    showMessage("The Book is NOT Issued by THIS User!", "Issuing Book Error!", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException e) {
            showMessage("Invalid input! Please enter valid Book ID and User ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleBackAction() {
        this.dispose();
        LibrarianSuccess.ThisLogined.setVisible(true);
    }

    private void handleFieldAction(java.awt.event.ActionEvent evt) {
        // Can add additional logic here if needed for each field
    }

    private boolean isValidBookAndUser(int bookID, int userID) {
        if (TransBookDao.BookValidate(String.valueOf(bookID)) && TransBookDao.UserValidate(String.valueOf(userID))) {
            return true;
        } else {
            showMessage("The Book and/or User is NOT available in Library Database!", "Returning Book Error!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }

    private void resetFields() {
        UserID.setText("");
        BookID.setText("");
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new ReturnBookForm().setVisible(true));
    }

    // Variables declaration (no changes here)
    private javax.swing.JTextField BookID;
    private javax.swing.JTextField IDate;
    private javax.swing.JTextField IMonth;
    private javax.swing.JTextField IYear;
    private javax.swing.JTextField UserID;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    // End of variables declaration
}

