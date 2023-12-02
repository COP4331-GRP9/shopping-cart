package ShoppingCart;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Login window for both customers and sellers.
 */
public class LoginWindow {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton customerLoginButton;
    private JButton sellerLoginButton;

    /**
     * Constructor for the LoginWindow class
     * Initializes the frame and places the components on the panel
     */
    public LoginWindow() {
        frame = new JFrame("ShoppingCart");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    /**
     * Places components on the panel
     * @param panel the panel on which to place the components
     */
    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel userLabel = new JLabel("User");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        usernameField = new JTextField(20);
        usernameField.setBounds(100, 20, 165, 25);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(100, 50, 165, 25);
        panel.add(passwordField);

        customerLoginButton = new JButton("Customer Login");
        customerLoginButton.setBounds(10, 80, 130, 25);
        panel.add(customerLoginButton);

        sellerLoginButton = new JButton("Seller Login");
        sellerLoginButton.setBounds(150, 80, 130, 25);
        panel.add(sellerLoginButton);

        ActionListener customerLoginActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (isValidCustomerCredentials(username, password)) {
                    // Proceed with login
                    frame.dispose(); // Close the login window
                    new CustomerHomePage(); // Open the customer home page
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password.");
                }
            }
        };

        ActionListener sellerLoginActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (isValidSellerCredentials(username, password)) {
                    // Proceed with login
                    frame.dispose(); // Close the login window
                    new SellerHomePage(); // Open the seller home page
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password.");
                }
            }
        };

        customerLoginButton.addActionListener(customerLoginActionListener);
        sellerLoginButton.addActionListener(sellerLoginActionListener);
    }

    /**
     * Check if the provided username and password are valid for a customer
     * @param username the username to validate
     * @param password the password to validate
     * @return true if the credentials are valid, false otherwise
     */
    private boolean isValidCustomerCredentials(String username, String password) {
        // Validate the username and password (e.g., check against a database)
        // This is just a placeholder. In a real application, you should do this securely.
        return "test1".equals(username) && "test1".equals(password);
    }

    /**
     * Check if the provided username and password are valid for a seller
     * @param username the username to validate
     * @param password the password to validate
     * @return true if the credentials are valid, false otherwise
     */
    private boolean isValidSellerCredentials(String username, String password) {
        // Validate the username and password (e.g., check against a database)
        // This is just a placeholder. In a real application, you should do this securely.
        return "test2".equals(username) && "test2".equals(password);
    }

    /**
     * The main method that launches the login window
     * @param args command-line arguments (not used)
     */

    public static void main(String[] args) {
        new LoginWindow();
    }
}
