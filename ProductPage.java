

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductPage {
    private JFrame frame;
    private Cart cart; // Assuming a Cart class exists

    public ProductPage() {
        frame = new JFrame("Product Page");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cart = new Cart(); // Initialize the cart

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        frame.add(panel);

        // Example products
        addProductToPanel(panel, "Product 1", "Description of Product 1");
        addProductToPanel(panel, "Product 2", "Description of Product 2");
        // Add more products as needed

        // Button to view cart
        JButton viewCartButton = new JButton("View Cart");
        viewCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CartPage(cart); // Open the cart page
            }
        });
        panel.add(viewCartButton);

        frame.setVisible(true);
    }

    private void addProductToPanel(JPanel panel, String productName, String productDescription) {
        JPanel productPanel = new JPanel();
        productPanel.setLayout(new FlowLayout());

        JLabel nameLabel = new JLabel(productName);
        JLabel descriptionLabel = new JLabel(productDescription);
        JButton addButton = new JButton("Add to Cart");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cart.addItem(productName); // Add item to the cart
                JOptionPane.showMessageDialog(frame, productName + " added to cart!");
            }
        });

        productPanel.add(nameLabel);
        productPanel.add(descriptionLabel);
        productPanel.add(addButton);
        panel.add(productPanel);
    }
}
