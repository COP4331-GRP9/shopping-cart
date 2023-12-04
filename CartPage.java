import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CartPage {
    private JFrame frame;
    private Cart cart;
    private JButton checkoutButton; // Declare the checkout button as a class member

    public CartPage(Cart cart) {
        this.cart = cart;
        frame = new JFrame("Cart");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        frame.add(panel);

        for (Product item : cart.getItems()) {
            JPanel itemPanel = new JPanel();
            itemPanel.setLayout(new FlowLayout());

            JLabel itemLabel = new JLabel(item.name);
            JLabel priceLabel = new JLabel("$" + item.price.toString());
            JLabel quantityLabel = new JLabel("Qty: " + cart.getItemQuantity(item));
            JButton removeButton = new JButton("Remove");

            removeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cart.removeItem(item.name);
                    frame.dispose();
                    new CartPage(cart); // Refresh the cart page
                }
            });

            itemPanel.add(itemLabel);
            itemPanel.add(priceLabel);
            itemPanel.add(quantityLabel);
            itemPanel.add(removeButton);
            panel.add(itemPanel);
        }

        // Display total price
        JLabel totalPriceLabel = new JLabel("Total Price: $" + String.format("%.2f", cart.getTotalPrice()));
        panel.add(totalPriceLabel);

        checkoutButton = new JButton("Proceed to Checkout");
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CheckoutPage(); // Open the checkout page
            }
        });

        // Disable the checkout button if the cart is empty
        checkoutButton.setEnabled(!cart.getItems().isEmpty());

        panel.add(checkoutButton);

        frame.setVisible(true);
    }
}
