

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CartPage {
    private JFrame frame;
    private Cart cart;

    public CartPage(Cart cart) {
        this.cart = cart;
        frame = new JFrame("Cart");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        frame.add(panel);

        for (String item : cart.getItems()) {
            JPanel itemPanel = new JPanel();
            itemPanel.setLayout(new FlowLayout());

            JLabel itemLabel = new JLabel(item);
            JButton removeButton = new JButton("Remove");

            removeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cart.removeItem(item);
                    frame.dispose();
                    new CartPage(cart); // Refresh the cart page
                }
            });

            itemPanel.add(itemLabel);
            itemPanel.add(removeButton);
            panel.add(itemPanel);
        }

        JButton checkoutButton = new JButton("Proceed to Checkout");
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CheckoutPage(); // Open the checkout page
            }
        });
        panel.add(checkoutButton);

        frame.setVisible(true);
    }
}
