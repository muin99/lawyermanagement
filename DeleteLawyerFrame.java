import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteLawyerFrame extends JFrame {

    private JTextField txtLawyerID;

    public DeleteLawyerFrame() {
        setTitle("Delete Lawyer");
        setSize(300, 150);
        setLayout(new GridLayout(3, 2)); // Adjusted to add the back button

        JLabel lblLawyerID = new JLabel("Lawyer ID:");
        txtLawyerID = new JTextField();
        JButton btnDelete = new JButton("Delete Lawyer");
        JButton btnBack = new JButton("Back to Home");

        btnDelete.addActionListener(e -> deleteLawyer());
        btnBack.addActionListener(e -> goBackToHome());

        // Add components
        add(lblLawyerID);
        add(txtLawyerID);
        add(new JLabel("")); // Empty cell for spacing
        add(btnDelete);
        add(new JLabel("")); // Empty cell for spacing
        add(btnBack); // Add the back button

        setLocationRelativeTo(null); // Center the frame
        setVisible(true);
    }

    private void deleteLawyer() {
        String lawyerID = txtLawyerID.getText();

        if (lawyerID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill Lawyer ID");
            return;
        }

        try {
            Connection connection = DatabaseConnection.getConnection();
            String checkSql = "SELECT * FROM Lawyer WHERE Lawyer_ID = ?";
            PreparedStatement checkPst = connection.prepareStatement(checkSql);
            checkPst.setString(1, lawyerID);
            ResultSet rs = checkPst.executeQuery();

            if (rs.next()) {
                String sql = "DELETE FROM Lawyer WHERE Lawyer_ID = ?";
                PreparedStatement pst = connection.prepareStatement(sql);
                pst.setString(1, lawyerID);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Lawyer Deleted Successfully");
            } else {
                JOptionPane.showMessageDialog(this, "Lawyer ID not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void goBackToHome() {
        dispose(); // Close the current frame
        new HomePage(); // Open the home page
    }
}
