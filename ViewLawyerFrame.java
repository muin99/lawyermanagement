import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class ViewLawyerFrame extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;

    public ViewLawyerFrame() {
        setTitle("View Lawyers");
        setSize(600, 450);
        setLayout(new BorderLayout());

        // Create a table model
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Lawyer ID");
        tableModel.addColumn("Specialization");
        tableModel.addColumn("Person ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Phone");
        tableModel.addColumn("Email");

        // Create a table and set the model
        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        table.setRowHeight(25);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setSelectionBackground(new Color(220, 220, 255));
        table.setSelectionForeground(Color.BLACK);
        table.setGridColor(Color.LIGHT_GRAY);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnBack = new JButton("Back to Home");
        bottomPanel.add(btnBack);

        btnBack.addActionListener(e -> {
            dispose();  // Close current frame
            new HomePage();  // Navigate back to HomePage
        });

        add(bottomPanel, BorderLayout.SOUTH);

        showLawyers();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void showLawyers() {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            connection = DatabaseConnection.getConnection();
            String sql = "SELECT Lawyer.Lawyer_ID, Lawyer.Lawyer_Specialization, Person.Person_ID, Person.Name, Person.Phone, Person.Email " +
                         "FROM Lawyer " +
                         "JOIN Person ON Lawyer.Person_ID = Person.Person_ID";
            stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);

            // Clear previous data
            tableModel.setRowCount(0);

            while (rs.next()) {
                Object[] rowData = {
                    rs.getString("Lawyer_ID"),
                    rs.getString("Lawyer_Specialization"),
                    rs.getString("Person_ID"),
                    rs.getString("Name"),
                    rs.getString("Phone"),
                    rs.getString("Email")
                };
                tableModel.addRow(rowData);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ViewLawyerFrame());
    }
}