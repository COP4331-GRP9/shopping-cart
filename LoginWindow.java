
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginWindow {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton customerLoginButton;
    private JButton sellerLoginButton;

    public LoginWindow() {
        frame = new JFrame("ShoppingCart");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

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

        customerLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin(true);
            }
        });

        sellerLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin(false);
            }
        });
    }

    private void handleLogin(boolean isCustomer) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if ((isCustomer && isValidCustomerCredentials(username, password)) ||
                (!isCustomer && isValidSellerCredentials(username, password))) {
            frame.dispose(); // Close the login window
            new ProductPage(); // Open the product page
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid username or password.");
        }
    }

    private boolean isValidCustomerCredentials(String username, String password) {
        // Placeholder validation logic
        return "t".equals(username) && "t".equals(password);
    }

    private boolean isValidSellerCredentials(String username, String password) {
        // Placeholder validation logic
        return "test2".equals(username) && "test2".equals(password);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new LoginWindow();
    }
}
