

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a login window for a shopping cart application
 */
public class LoginWindow {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    private Map<String, Integer> failedLoginAttempts = new HashMap<>();
    private final int MAX_ATTEMPTS = 3; // Maximum failed attempts before lockout
    private final long LOCKOUT_DURATION = 30000; // Lockout duration in milliseconds (e.g., 30000 ms = 30 seconds)
    private Map<String, Long> lockoutTime = new HashMap<>();

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
     * Handles the login process when the login button is clicked.
     * This method first checks if the user is currently locked out due to too many failed attempts.
     * If not locked out, it validates the entered username and password.
     * If validation fails, it increments the failed login attempt count and potentially locks out the user.
     * If validation succeeds, it resets the attempt count and proceeds to the product page.
     */
    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Check if user is currently locked out
        if (isUserLockedOut(username)) {
            JOptionPane.showMessageDialog(frame, "Account locked due to too many failed attempts. Please try again later.");
            return;
        }

        if (isValidCustomerCredentials(username, password) || isValidSellerCredentials(username, password)) {
            // Successful login, reset failed attempts
            failedLoginAttempts.remove(username);
            lockoutTime.remove(username);

            // Proceed with normal login
            frame.dispose();
            new ProductPage(username.equals("c") ? "customer" : "seller");
        } else {
            // Failed login, increase attempt count
            int attempts = failedLoginAttempts.getOrDefault(username, 0);
            failedLoginAttempts.put(username, attempts + 1);

            if (attempts + 1 >= MAX_ATTEMPTS) {
                // Lock out the user
                lockoutTime.put(username, System.currentTimeMillis() + LOCKOUT_DURATION);
                JOptionPane.showMessageDialog(frame, "Too many failed attempts. Account locked for 30 seconds.");
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid username or password.");
            }
        }
    }

    /**
     * Checks if the specified user is currently locked out.
     * A user is considered locked out if the current time is less than the stored lockout end time.
     *
     * @param username The username to check for lockout status.
     * @return true if the user is currently locked out, false otherwise.
     */
    private boolean isUserLockedOut(String username) {
        if (!lockoutTime.containsKey(username)) {
            return false;
        }

        long lockoutEnd = lockoutTime.get(username);
        if (System.currentTimeMillis() > lockoutEnd) {
            // Lockout period has ended
            lockoutTime.remove(username);
            return false;
        }

        return true;
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
