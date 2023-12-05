

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginWindow {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginWindow() {
        frame = new JFrame("ebay");
        frame.setSize(400, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center the frame

        // Set the icon
        ImageIcon icon = new ImageIcon("logo.png"); // Relative path to the logo
        frame.setIconImage(icon.getImage());

        // Set a modern look and feel
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.WHITE); // Set a light background
        frame.add(panel);

        placeComponents(panel, gbc);

        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel, GridBagConstraints gbc) {
        gbc.insets = new Insets(4, 4, 4, 4); // Margin around components
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        panel.add(new JLabel("Username:"), gbc);

        gbc.gridy++;
        usernameField = new JTextField(20);
        panel.add(usernameField, gbc);

        gbc.gridy++;
        panel.add(new JLabel("Password:"), gbc);

        gbc.gridy++;
        passwordField = new JPasswordField(20);
        panel.add(passwordField, gbc);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(255, 255, 255));
        loginButton.setForeground(Color.BLACK); // Black text
        loginButton.setFocusPainted(false);
        panel.add(loginButton, gbc);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String role;

        if (isValidCustomerCredentials(username, password)) {
            role = "customer";
        } else if (isValidSellerCredentials(username, password)) {
            role = "seller";
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid username or password.");
            return;
        }

        frame.dispose(); // Close the login window
        new ProductPage(role); // Open the product page with the user role
    }

    private boolean isValidCustomerCredentials(String username, String password) {
        // Placeholder validation logic for customer
        return "c".equals(username) && "c".equals(password);
    }

    private boolean isValidSellerCredentials(String username, String password) {
        // Placeholder validation logic for seller
        return "s".equals(username) && "s".equals(password);
    }

    public static void main(String[] args) {
        new LoginWindow();
    }
}
