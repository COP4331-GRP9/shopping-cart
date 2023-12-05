import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents a checkout page.
 * Creates a GUI form with fields for name, email, address, and credit card information
 * Includes a "Pay Now" button to submit the form
 */
public class CheckoutPage {
    private JFrame frame;

    /**
     * Constructor for the CheckoutPage class
     * Initializes the frame and adds all the necessary components
     */
    public CheckoutPage() {
        frame = new JFrame("Checkout");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        frame.add(panel);

        // Name field
        JTextField nameField = new JTextField(20);
        panel.add(new JLabel("Name:"));
        panel.add(nameField);

        // Email field
        JTextField emailField = new JTextField(20);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);

        // Address fields
        JTextField addressField = new JTextField(20);
        JTextField cityField = new JTextField(15);
        JTextField stateField = new JTextField(15);
        JTextField zipField = new JTextField(10);

        panel.add(new JLabel("Street Address:"));
        panel.add(addressField);
        panel.add(new JLabel("City:"));
        panel.add(cityField);
        panel.add(new JLabel("State:"));
        panel.add(stateField);
        panel.add(new JLabel("Zip Code:"));
        panel.add(zipField);

        // Credit Card Information
        JTextField cardNumberField = new JTextField(16);
        panel.add(new JLabel("Card Number:"));
        panel.add(cardNumberField);

        // Hard-coded shipping option
        JLabel shippingOptionLabel = new JLabel("Shipping Option: Standard Delivery (5-7 days)");
        panel.add(shippingOptionLabel);

        /**
         * Creates a "Pay Now" button and adds an action listener to it
         * When the button is clicked, it checks if all fields are filled and if the card number is numeric
         * If yes, it shows a message dialog saying "Payment Processed!" and closes the checkout window
         * If not, it shows a message dialog saying "Please fill all fields correctly."
         */
        JButton payNowButton = new JButton("Pay Now");
        payNowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (areAllFieldsFilled(
                        nameField,
                        emailField,
                        addressField,
                        cityField,
                        stateField,
                        zipField,
                        cardNumberField) &&
                        isNumeric(cardNumberField.getText())) {
                    JOptionPane.showMessageDialog(frame, "Payment Processed!");
                    frame.dispose(); // Close the checkout window
                } else {
                    JOptionPane.showMessageDialog(frame, "Please fill all fields correctly.");
                }
            }
        });
        panel.add(payNowButton);

        frame.setVisible(true);
    }

    /**
     * Checks if all given text fields are filled
     * @param fields The text fields to check
     * @return true if all fields are filled, false otherwise
     */
    private boolean areAllFieldsFilled(JTextField... fields) {
        for (JTextField field : fields) {
            if (field.getText().trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if a given text is numeric
     * @param text The text to check
     * @return true if the text is numeric, false otherwise
     */
    private boolean isNumeric(String text) {
        try {
            Double.parseDouble(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
