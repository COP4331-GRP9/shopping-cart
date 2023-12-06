
import javax.swing.*;
import java.awt.*;
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
        this.userRole = userRole;
        frame = new JFrame("eBay");
        frame.setSize(800, 860);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Set the icon
        ImageIcon icon = new ImageIcon("logo.png"); // Relative path to the logo
        frame.setIconImage(icon.getImage());

        cart = new Cart();
        productPanels = new HashMap<>();
        productPrices = new HashMap<>();

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Increased empty border
        mainPanel.setBackground(new Color(245, 245, 245));
        frame.add(mainPanel);

        // Add logo panel at the top left
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST; // Align to the top left corner
        gbc.weightx = 0; // Do not allow horizontal stretching
        gbc.insets = new Insets(-70, -50, 0, 0); // No margins
        mainPanel.add(createLogoPanel(), gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 10, 0); // Increased insets for spacing between components

        JLabel welcomeLabel = new JLabel("Welcome back, " + (userRole.equals("seller") ? "seller!" : "customer!"));
        mainPanel.add(welcomeLabel, gbc);

        gbc.gridy++;
        setupSearchPanel(mainPanel, gbc);

        gbc.gridy++;
        loadProductsFromCSV(mainPanel, "./products.csv", gbc);

        gbc.gridy++;
        JButton viewCartButton = new JButton("View Cart");
        viewCartButton.addActionListener(e -> new CartPage(cart));
        mainPanel.add(viewCartButton, gbc);

        gbc.gridy++;
        JButton accountButton = new JButton("Account");
        accountButton.addActionListener(e -> new AccountPage(userRole));
        mainPanel.add(accountButton, gbc);

        frame.setVisible(true);
    }

    private JPanel createLogoPanel() {
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoPanel.setBackground(new Color(245, 245, 245)); // Match the background color of the main panel
    
        ImageIcon originalIcon = new ImageIcon("logo.png");
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(110, 50, Image.SCALE_SMOOTH); // Set the desired width and height
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
    
        JLabel logoLabel = new JLabel(resizedIcon);
        logoPanel.add(logoLabel);
    
        return logoPanel;
    }    

    private void setupSearchPanel(JPanel panel, GridBagConstraints gbc) {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Increased hgap and vgap
        searchPanel.setBackground(new Color(245, 245, 245));

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

        setupSearchButtonListener(searchField, minPriceField, maxPriceField, searchButton);
        panel.add(searchPanel, gbc);
    }

    private void setupSearchButtonListener(JTextField searchField, JTextField minPriceField, JTextField maxPriceField,
            JButton searchButton) {
        searchButton.addActionListener(e -> {
            String searchText = searchField.getText().toLowerCase();

            // Use 0 as default for minPrice if the field is empty
            double minPrice = minPriceField.getText().isEmpty() ? 0.0 : parseDouble(minPriceField.getText());

            // Use a very high number as default for maxPrice if the field is empty
            double maxPrice = maxPriceField.getText().isEmpty() ? Double.MAX_VALUE
                    : parseDouble(maxPriceField.getText());

            for (Map.Entry<String, JPanel> entry : productPanels.entrySet()) {
                boolean nameMatches = entry.getKey().toLowerCase().contains(searchText);
                double price = productPrices.getOrDefault(entry.getKey(), 0.0);
                boolean priceMatches = (price >= minPrice) && (price <= maxPrice);

                entry.getValue().setVisible(nameMatches && priceMatches);
            }
        });
    }

    private void loadProductsFromCSV(JPanel panel, String filePath, GridBagConstraints gbc) {
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
                    gbc.gridy++;
                    addProductToPanel(panel, values[0], values[1], values[3], gbc);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addProductToPanel(
            JPanel panel,
            String productName,
            String productPriceStr,
            String imageFileName,
            GridBagConstraints gbc) {
        JPanel productPanel = new JPanel(new FlowLayout());
        productPanel.setBackground(Color.WHITE); // White background for product panels
        productPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY)); // Subtle border

        // Load and display the image
        ImageIcon originalIcon = new ImageIcon(imageFileName);
        Image image = originalIcon.getImage();
        Image newimg = image.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH); // Scale it to fit your layout
        ImageIcon imageIcon = new ImageIcon(newimg);
        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(imageIcon);

        JLabel nameLabel = new JLabel(productName);
        JLabel priceLabel = new JLabel("$" + productPriceStr);
        JButton addButton = new JButton("Add to Cart");

        addButton.addActionListener(e -> {
            cart.addItem(productName, parseDouble(productPriceStr)); // Add item to cart
            JOptionPane.showMessageDialog(frame, productName + " added to cart!");
        });

        productPanel.add(imageLabel);
        productPanel.add(nameLabel);
        productPanel.add(priceLabel);
        productPanel.add(addButton);

        panel.add(productPanel, gbc);

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
