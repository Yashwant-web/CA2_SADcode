/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainlibrary;

import java.awt.Frame;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import static mainlibrary.LibrarianSuccess.ThisLogined;

/**
 *
 * @author bikash
 */
public class BookForm extends javax.swing.JFrame {

    public BookForm() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        // UI components initialization (same as before)
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // Validate the inputs
        String bookName = BookName.getText().trim();
        String author = Author.getText().trim();
        String publisher = Publisher.getText().trim();
        String genre = Genre.getText().trim();
        String shelf = Shelf.getText().trim();
        String row = Row.getText().trim();

        // Ensure no fields are left empty
        if (bookName.isEmpty() || author.isEmpty() || publisher.isEmpty() || genre.isEmpty() || shelf.isEmpty() || row.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if publisher exists in the database
        if (!BookDao.PublisherValidate(publisher)) {
            if (BookDao.AddPublisher(publisher) == 0) {
                JOptionPane.showMessageDialog(this, "Unable to add publisher.", "Publisher Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // Ensure shelf and row are numeric
        try {
            Integer.parseInt(shelf);
            Integer.parseInt(row);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Shelf and Row must be numeric.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Add the book to the database
        if (BookDao.SaveBook(bookName, author, publisher, shelf, row, genre) != 0) {
            JOptionPane.showMessageDialog(this, "The book has been added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Unable to add the book.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to clear all the fields
    private void clearFields() {
        BookName.setText("");
        Author.setText("");
        Publisher.setText("");
        Genre.setText("");
        Shelf.setText("");
        Row.setText("");
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        // Close this form and go back to the previous one
        this.dispose();
        LibrarianSuccess.ThisLogined.setVisible(true);
    }

    // Main method to display the form
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BookForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Author;
    private javax.swing.JTextField BookName;
    private javax.swing.JTextField Genre;
    private javax.swing.JTextField Publisher;
    private javax.swing.JTextField Row;
    private javax.swing.JTextField Shelf;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    // End of variables declaration//GEN-END:variables
}
