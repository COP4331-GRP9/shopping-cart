import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CheckoutPage {
    private JFrame frame;

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

        // Pay Now Button
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

    private boolean areAllFieldsFilled(JTextField... fields) {
        for (JTextField field : fields) {
            if (field.getText().trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private boolean isNumeric(String text) {
        try {
            Double.parseDouble(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
