import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * The ProductPage class represents a graphical user interface for displaying
 * a list of products, allowing users to search and add items to a shopping cart
 */
public class ProductPage {
    /**
     * The main frame for the product page
     */
    private JFrame frame;

    /**
     * The shopping cart associated with the product page
     */
    private Cart cart; // Assuming a Cart class exists

    /**
     * A map to store product names and their corresponding panels
     */
    private Map<String, JPanel> productPanels; // Map to store product names and their panels

    /**
     * A map to store product names and their prices
     */
    private Map<String, Double> productPrices; // Map to store product names and their prices

    /**
     * Constructs a new ProductPage, initializing the frame, cart, and data structures
     */
    public ProductPage() {
        frame = new JFrame("Product Page");
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cart = new Cart(); // Initialize the cart
        productPanels = new HashMap<>(); // Initialize the map
        productPrices = new HashMap<>(); // Initialize the price map

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        frame.add(panel);

        // Search field and buttons
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

        frame.setVisible(true);
    }

    /**
     * Adds a product to the specified panel, creating a panel with product information
     * Also updates the productPanels and productPrices maps
     * @param panel            The panel to which the product panel will be added
     * @param productName      The name of the product
     * @param productPriceStr  The string representation of the product price
     */
    private void addProductToPanel(JPanel panel, String productName, String productPriceStr) {
        JPanel productPanel = new JPanel();
        productPanel.setLayout(new FlowLayout());

        JLabel nameLabel = new JLabel(productName);
        JLabel priceLabel = new JLabel("$" + productPriceStr);
        JButton addButton = new JButton("Add to Cart");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cart.addItem(productName, parseDouble(productPriceStr)); // Add item to the cart
                JOptionPane.showMessageDialog(frame, productName + " added to cart!");
            }
        });

        productPanel.add(nameLabel);
        productPanel.add(priceLabel);
        productPanel.add(addButton);
        panel.add(productPanel);

        // Add product panel to map
        productPanels.put(productName, productPanel);
        productPrices.put(productName, Double.parseDouble(productPriceStr));
    }

    /**
     * Loads product data from a CSV file and adds corresponding panels to the panel
     * @param panel    The panel to which product panels will be added
     * @param filePath The path to the CSV file containing product data
     */
    private void loadProductsFromCSV(JPanel panel, String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                // Skip the header line
                if (firstLine) {
                    firstLine = false;
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

    /**
     * Parses a string to a double, handling NumberFormatException
     * @param text The string to be parsed
     * @return The parsed double value, or 0.0 if parsing fails
     */
    private double parseDouble(String text) {
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}
