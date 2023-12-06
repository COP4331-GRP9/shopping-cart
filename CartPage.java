import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class represents the Cart Page in the shopping cart application
 * It displays the items in the cart, their quantity, and the total price
 * It also provides the option to remove items from the cart and proceed to
 * checkout
 */
public class CartPage {
    private JFrame frame;
    private Cart cart;
    private JButton checkoutButton; // Declare the checkout button as a class member

    /**
     * Constructor for the CartPage class
     * Initializes the frame, sets its properties, and adds components to it
     *
     * @param cart the cart object containing the items to be displayed
     */
    public CartPage(Cart cart) {
        this.cart = cart;
        frame = new JFrame("eBay Cart");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center the frame

        // Set the icon
        ImageIcon icon = new ImageIcon("logo.png"); // Relative path to the logo
        frame.setIconImage(icon.getImage());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        frame.add(panel);

        for (Product item : cart.getItems()) {
            JPanel itemPanel = new JPanel();
            itemPanel.setLayout(new FlowLayout());

            JLabel itemLabel = new JLabel(item.name);
            JLabel priceLabel = new JLabel("$" + String.format("%.2f", item.price * cart.getItemQuantity(item)));
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
        JLabel totalPriceLabel = new JLabel("Total Price: $" + String.format("%.2f", cart.getTotalAmount()));
        panel.add(totalPriceLabel);

        checkoutButton = new JButton("Proceed to Checkout");
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CheckoutPage(cart); // Pass the cart object to the CheckoutPage
            }
        });

        // Disable the checkout button if the cart is empty
        checkoutButton.setEnabled(!cart.getItems().isEmpty());

        panel.add(checkoutButton);

        frame.setVisible(true);
    }
}
