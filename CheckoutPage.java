

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CheckoutPage {
    private JFrame frame;

    public CheckoutPage() {
        frame = new JFrame("Checkout");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        frame.add(panel);

        // Shipping Information
        JTextField shippingAddressField = new JTextField(20);
        panel.add(new JLabel("Shipping Address:"));
        panel.add(shippingAddressField);

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
                // Handle payment processing
                JOptionPane.showMessageDialog(frame, "Payment Processed!");

                frame.dispose(); // Close the checkout window
            }
        });
        panel.add(payNowButton);

        frame.setVisible(true);
    }
}
