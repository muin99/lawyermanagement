import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddLawyerFrame extends JFrame {

    private JTextField txtLawyerID, txtSpecialization, txtPersonID, txtName, txtPhone, txtEmail;

    public AddLawyerFrame() {
        setTitle("Add Lawyer");
        setSize(400, 350);  // Adjusted size to accommodate new fields
        setLayout(new GridLayout(8, 2));  // Updated layout for additional fields

        // Lawyer and Person Data Fields
        JLabel lblLawyerID = new JLabel("Lawyer ID:");
        txtLawyerID = new JTextField();
        JLabel lblSpecialization = new JLabel("Specialization:");
        txtSpecialization = new JTextField();
        JLabel lblPersonID = new JLabel("Person ID:");
        txtPersonID = new JTextField();
        JLabel lblName = new JLabel("Name:");
        txtName = new JTextField();  // New field for Name
        JLabel lblPhone = new JLabel("Phone:");
        txtPhone = new JTextField();
        JLabel lblEmail = new JLabel("Email:");
        txtEmail = new JTextField();

        JButton btnAdd = new JButton("Add Lawyer");
        JButton btnBack = new JButton("Back to Home");

        // Add button action listeners
        btnAdd.addActionListener(e -> addLawyer());
        btnBack.addActionListener(e -> {
            dispose();  // Close current frame
            new HomePage();  // Navigate back to HomePage
        });

        // Adding fields and buttons to the form
        add(lblLawyerID); add(txtLawyerID);
        add(lblSpecialization); add(txtSpecialization);
        add(lblPersonID); add(txtPersonID);
        add(lblName); add(txtName);  // Added Name field
        add(lblPhone); add(txtPhone);
        add(lblEmail); add(txtEmail);
        add(new JLabel("")); add(btnAdd);  // Add empty label for layout spacing
        add(new JLabel("")); add(btnBack);

        setLocationRelativeTo(null);  // Center the frame
        setVisible(true);
    }

    private void addLawyer() {
        String lawyerID = txtLawyerID.getText();
        String specialization = txtSpecialization.getText();
        String personID = txtPersonID.getText();
        String name = txtName.getText();  // Get the name from the field
        String phone = txtPhone.getText();
        String email = txtEmail.getText();

        // Check if all fields are filled
        if (lawyerID.isEmpty() || specialization.isEmpty() || personID.isEmpty() || name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields");
            return;
        }

        Connection connection = null;
        PreparedStatement pstPerson = null;
        PreparedStatement pstLawyer = null;

        try {
            connection = DatabaseConnection.getConnection();

            // Insert into the Person table
            String sqlPerson = "INSERT INTO Person (Person_ID, Name, Phone, Email) VALUES (?, ?, ?, ?)";
            pstPerson = connection.prepareStatement(sqlPerson);
            pstPerson.setString(1, personID);
            pstPerson.setString(2, name);  // Insert Name
            pstPerson.setString(3, phone);
            pstPerson.setString(4, email);
            pstPerson.executeUpdate();

            // Insert into the Lawyer table
            String sqlLawyer = "INSERT INTO Lawyer (Lawyer_ID, Lawyer_Specialization, Person_ID) VALUES (?, ?, ?)";
            pstLawyer = connection.prepareStatement(sqlLawyer);
            pstLawyer.setString(1, lawyerID);
            pstLawyer.setString(2, specialization);
            pstLawyer.setString(3, personID);
            pstLawyer.executeUpdate();

            JOptionPane.showMessageDialog(this, "Lawyer and Person Added Successfully");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error inserting data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } /*finally {
            try {
                if (pstPerson != null) pstPerson.close();
                if (pstLawyer != null) pstLawyer.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }*/
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AddLawyerFrame::new);
    }
}
