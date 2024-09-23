import javax.swing.*;
import java.awt.*;

public class HomePage extends JFrame {

    public HomePage() {
        setTitle("Lawyer Management System");
        setSize(800, 600); // Set frame size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a custom JPanel to paint the background image
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon("bk.jpg"); // Replace with your image path
                Image image = backgroundImage.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this); // Scale image to fit the frame size
            }
        };

        backgroundPanel.setLayout(new BorderLayout()); // Use BorderLayout to arrange header and buttons

        // Create the header panel with blue background
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.BLUE);
        headerPanel.setLayout(new FlowLayout());

        JLabel headerLabel = new JLabel("Legal Connect: Legal Management System");
        headerLabel.setForeground(Color.WHITE); // White text color
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Header font style and size
        headerPanel.add(headerLabel);

        // Create buttons
        JButton btnAdd = new JButton("Add Lawyer");
        JButton btnUpdate = new JButton("Update Lawyer");
        JButton btnDelete = new JButton("Delete Lawyer");
        JButton btnView = new JButton("View Lawyers");

        // Create a panel to hold buttons in a row
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 200)); // Centered row of buttons with spacing
        buttonPanel.setOpaque(false); // Transparent background for button panel

        // Add buttons to the panel
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnView);

        // Add action listeners
        btnAdd.addActionListener(e -> new AddLawyerFrame());
        btnUpdate.addActionListener(e -> new UpdateLawyerFrame());
        btnDelete.addActionListener(e -> new DeleteLawyerFrame());
        btnView.addActionListener(e -> new ViewLawyerFrame());

        // Add the header and button panels to the background panel
        backgroundPanel.add(headerPanel, BorderLayout.NORTH); // Add header at the top
        backgroundPanel.add(buttonPanel, BorderLayout.CENTER); // Add button panel in the center

        // Add the background panel to the frame
        add(backgroundPanel);

        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HomePage::new);
    }
}
