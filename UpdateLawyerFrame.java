import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateLawyerFrame extends JFrame {

    private JTextField txtLawyerID, txtSpecialization, txtName, txtPhone, txtEmail;

    public UpdateLawyerFrame() {
        setTitle("Update Lawyer and Person");
        setSize(400, 350);  // Adjusted size for consistent look
        setLayout(new GridLayout(7, 2));  // Updated layout for additional fields

        // Lawyer and Person Data Fields
        JLabel lblLawyerID = new JLabel("Lawyer ID:");
        txtLawyerID = new JTextField();
        JLabel lblSpecialization = new JLabel("New Specialization:");
        txtSpecialization = new JTextField();
        JLabel lblName = new JLabel("New Name:");
        txtName = new JTextField();  
        JLabel lblPhone = new JLabel("New Phone:");
        txtPhone = new JTextField();
        JLabel lblEmail = new JLabel("New Email:");
        txtEmail = new JTextField();

        JButton btnUpdate = new JButton("Update Lawyer and Person");
        JButton btnBack = new JButton("Back to Home");

        // Add button action listeners
        btnUpdate.addActionListener(e -> updateLawyerAndPerson());
        btnBack.addActionListener(e -> {
            dispose();  // Close current frame
            new HomePage();  // Navigate back to HomePage
        });

        // Adding fields and buttons to the form
        add(lblLawyerID); add(txtLawyerID);
        add(lblSpecialization); add(txtSpecialization);
        add(lblName); add(txtName);  
        add(lblPhone); add(txtPhone);
        add(lblEmail); add(txtEmail);
        add(new JLabel("")); add(btnUpdate);  // Add empty label for layout spacing
        add(new JLabel("")); add(btnBack);

        setLocationRelativeTo(null);  // Center the frame
        setVisible(true);
    }

    private void updateLawyerAndPerson() {
        String lawyerID = txtLawyerID.getText();
        String specialization = txtSpecialization.getText();
        String name = txtName.getText();  
        String phone = txtPhone.getText();
        String email = txtEmail.getText();

        // Check if all fields are filled
        if (lawyerID.isEmpty() || specialization.isEmpty() || name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields");
            return;
        }

        Connection connection = null;

        try {
            connection = DatabaseConnection.getConnection();

            // Update Lawyer
            String checkLawyerSql = "SELECT * FROM Lawyer WHERE Lawyer_ID = ?";
            PreparedStatement checkLawyerPst = connection.prepareStatement(checkLawyerSql);
            checkLawyerPst.setString(1, lawyerID);
            ResultSet lawyerRs = checkLawyerPst.executeQuery();

            if (lawyerRs.next()) {
                String updateLawyerSql = "UPDATE Lawyer SET Lawyer_Specialization = ? WHERE Lawyer_ID = ?";
                PreparedStatement updateLawyerPst = connection.prepareStatement(updateLawyerSql);
                updateLawyerPst.setString(1, specialization);
                updateLawyerPst.setString(2, lawyerID);
                updateLawyerPst.executeUpdate();

                // Get Person ID associated with this Lawyer
                String personID = lawyerRs.getString("Person_ID");

                // Update Person
                String updatePersonSql = "UPDATE Person SET Name = ?, Phone = ?, Email = ? WHERE Person_ID = ?";
                PreparedStatement updatePersonPst = connection.prepareStatement(updatePersonSql);
                updatePersonPst.setString(1, name);
                updatePersonPst.setString(2, phone);
                updatePersonPst.setString(3, email);
                updatePersonPst.setString(4, personID);
                updatePersonPst.executeUpdate();

                JOptionPane.showMessageDialog(this, "Lawyer and Person Updated Successfully");
            } else {
                JOptionPane.showMessageDialog(this, "Lawyer ID not found");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error updating data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(UpdateLawyerFrame::new);
    }
}
