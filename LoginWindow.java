



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents a login window for a shopping cart application
 */
public class LoginWindow {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    /**
     * Constructor for the LoginWindow class
     * Initializes the frame and its components
     */
    public LoginWindow() {
        frame = new JFrame("eBay");
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
    
        // Load the logo and add it as a JLabel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Span across two columns
        gbc.anchor = GridBagConstraints.CENTER;
        ImageIcon originalIcon = new ImageIcon("logo.png");
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(110, 50, Image.SCALE_SMOOTH); // width and height are the new dimensions
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        
        JLabel logoLabel = new JLabel(resizedIcon);
        panel.add(logoLabel, gbc);
    
        // Reset gridwidth and position for the username label
        gbc.gridwidth = 1;
        gbc.gridy++;
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
        loginButton.setForeground(Color.BLACK);
        loginButton.setFocusPainted(false);
        panel.add(loginButton, gbc);
    
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
    }

    /**
     * Handles the login process when the login button is clicked
     * Validates the entered username and password
     */
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

    /**
     * Checks if the entered credentials are valid for a customer
     * @param username The entered username
     * @param password The entered password
     * @return true if the credentials are valid, false otherwise
     */
    private boolean isValidCustomerCredentials(String username, String password) {
        // Placeholder validation logic for customer
        return "c".equals(username) && "c".equals(password);
    }

    /**
     * Checks if the entered credentials are valid for a seller
     * @param username The entered username
     * @param password The entered password
     * @return true if the credentials are valid, false otherwise
     */
    private boolean isValidSellerCredentials(String username, String password) {
        // Placeholder validation logic for seller
        return "s".equals(username) && "s".equals(password);
    }
}
