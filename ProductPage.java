

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ProductPage {
    private JFrame frame;
    private Cart cart; // Assuming a Cart class exists
    private Map<String, JPanel> productPanels;
    private Map<String, Double> productPrices;
    private String userRole; // Variable to store the user role

    public ProductPage(String userRole) {
        this.userRole = userRole; // Initialize the user role
        frame = new JFrame("Product Page");
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cart = new Cart(); // Initialize the cart
        productPanels = new HashMap<>();
        productPrices = new HashMap<>();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        frame.add(panel);

        // Search panel setup
        setupSearchPanel(panel);

        loadProductsFromCSV(panel, "./products.csv");

        // Button to view cart
        JButton viewCartButton = new JButton("View Cart");
        viewCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CartPage(cart); // Open the cart page
            }
        });
        panel.add(viewCartButton);

        // Account button
        JButton accountButton = new JButton("Account");
        accountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AccountPage(userRole); // Open the account page with the user role
            }
        });
        panel.add(accountButton);

        frame.setVisible(true);
    }

    private void setupSearchPanel(JPanel panel) {
        JPanel searchPanel = new JPanel();
        JTextField searchField = new JTextField(20);
        JTextField minPriceField = new JTextField(5);
        JTextField maxPriceField = new JTextField(5);
        JButton searchButton = new JButton("Search");

        searchPanel.add(new JLabel("Name:"));
        searchPanel.add(searchField);
        searchPanel.add(new JLabel("Min Price:"));
        searchPanel.add(minPriceField);
        searchPanel.add(new JLabel("Max Price:"));
        searchPanel.add(maxPriceField);
        searchPanel.add(searchButton);
        panel.add(searchPanel);

        setupSearchButtonListener(searchField, minPriceField, maxPriceField, searchButton);
    }

    private void setupSearchButtonListener(JTextField searchField, JTextField minPriceField, JTextField maxPriceField, JButton searchButton) {
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = searchField.getText().toLowerCase();
                double minPrice = parseDouble(minPriceField.getText());
                double maxPrice = parseDouble(maxPriceField.getText());

                for (Map.Entry<String, JPanel> entry : productPanels.entrySet()) {
                    boolean nameMatches = entry.getKey().toLowerCase().contains(searchText);
                    double price = productPrices.get(entry.getKey());
                    boolean priceMatches = (price >= minPrice) && (price <= maxPrice);

                    entry.getValue().setVisible(nameMatches && priceMatches);
                }
            }
        });
    }

    private void loadProductsFromCSV(JPanel panel, String filePath) {
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        String line;
        boolean firstLine = true;

        while ((line = br.readLine()) != null) {
            if (firstLine) {
                firstLine = false; // Skip the header line
                continue;
            }

            String[] values = line.split(",");
            if (values.length >= 2) {
                addProductToPanel(panel, values[0], values[1]);
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    private void addProductToPanel(JPanel panel, String productName, String productPriceStr) {
    JPanel productPanel = new JPanel();
    productPanel.setLayout(new FlowLayout());

    JLabel nameLabel = new JLabel(productName);
    JLabel priceLabel = new JLabel("$" + productPriceStr);
    JButton addButton = new JButton("Add to Cart");

    addButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            cart.addItem(productName, parseDouble(productPriceStr)); // Add item to cart
            JOptionPane.showMessageDialog(frame, productName + " added to cart!");
        }
    });

    productPanel.add(nameLabel);
    productPanel.add(priceLabel);
    productPanel.add(addButton);
    panel.add(productPanel);

    productPanels.put(productName, productPanel);
    productPrices.put(productName, Double.parseDouble(productPriceStr));
    }

    private double parseDouble(String text) {
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}