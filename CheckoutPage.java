
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a checkout page
 * Creates a GUI form with fields for name, email, address, and credit card information
 * Includes a "Pay Now" button to submit the form
 */
public class CheckoutPage {
    private JFrame frame;
    private Cart cart;

    /**
     * Constructor for the CheckoutPage class
     * Initializes the frame and adds all the necessary components
     */
    public CheckoutPage(Cart cart) {
        frame = new JFrame("eBay Checkout");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center the frame
        this.cart = cart; // Store the cart object

        // Set the icon
        ImageIcon icon = new ImageIcon("logo.png"); // Relative path to the logo
        frame.setIconImage(icon.getImage());

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
        JTextField expDateField = new JTextField(16);
        panel.add(new JLabel("Exp Date:"));
        panel.add(expDateField);
        JTextField cardNumberCVVField = new JTextField(16);
        panel.add(new JLabel("CVV:"));
        panel.add(cardNumberCVVField);

        // Hard-coded shipping option
        JLabel shippingOptionLabel = new JLabel("Shipping Option: UPS Ground Delivery (3-5 days)");
        panel.add(shippingOptionLabel);

        /**
         * Creates a "Pay Now" button and adds an action listener to it
         * When the button is clicked, it checks if all fields are filled and if the
         * card number is numeric
         * If yes, it shows a message dialog saying "Payment Processed!" and closes the
         * checkout window
         * If not, it shows a message dialog saying "Please fill all fields correctly."
         */
        JButton payNowButton = new JButton("Pay Now");
        payNowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform validation here
                if (!(areAllFieldsFilled(
                        nameField,
                        emailField,
                        addressField,
                        cityField,
                        stateField,
                        zipField,
                        cardNumberField) &&
                        isNumeric(cardNumberField.getText()) &&
                        isNumeric(zipField.getText()) &&
                        isNumeric(cardNumberCVVField.getText()))) {
                    JOptionPane.showMessageDialog(frame, "Please fill all fields correctly.");
                    return;
                }

                String itemsPurchased = getItemsPurchased();
                double totalAmount = getTotalAmount();

                // Generate an order number
                String orderNumber = generateRandomOrderNumber();

                // Log customer purchase and seller sales
                logCustomerPurchase(orderNumber, emailField.getText(), itemsPurchased, totalAmount);
                logSellerSales(orderNumber, emailField.getText(), itemsPurchased, totalAmount);

                JOptionPane.showMessageDialog(frame, "Payment Processed!");
                frame.dispose();
            }
        });
        panel.add(payNowButton);

        frame.setVisible(true);
    }

    /**
     * Generates a random 5-digit order number
     * @return The generated order number as a string
     */
    private String generateRandomOrderNumber() {
        Random random = new Random();
        int orderNumber = random.nextInt(90000) + 10000; // Generates a random 5-digit number
        return String.valueOf(orderNumber);
    }

    /**
     * Logs the details of a customer's purchase
     * @param orderNumber The order number of the purchase
     * @param email The email address of the customer
     * @param items The items purchased by the customer
     * @param totalAmount The total amount of the purchase.
     */
    private void logCustomerPurchase(String orderNumber, String email, String items, double totalAmount) {
        String customerData = orderNumber + "," + email + "," + items + "," + totalAmount + "\n";
        writeToFile("customer_purchases.csv", customerData);
    }

    /**
     * Logs the details of a seller's sales
     * @param orderNumber The order number of the sale
     * @param email The email address of the seller
     * @param items The items sold by the seller
     * @param totalAmount The total amount of the sale
     */
    private void logSellerSales(String orderNumber, String email, String items, double totalAmount) {
        Map<String, Double> productCosts = readProductCosts();
        double totalProfit = 0.0;

        // Split the purchased items to calculate the profit for each
        String[] purchasedItems = items.split(", ");
        for (String item : purchasedItems) {
            String itemName = item.split(" \\(Qty: ")[0]; // Assuming item format is "ItemName (Qty: X)"
            double sellingPrice = cart.getProductPrice(itemName); // Assuming Cart class has a method to get the price
                                                                  // of a product
            double costPrice = productCosts.getOrDefault(itemName, 0.0);
            double profit = sellingPrice - costPrice;
            totalProfit += profit;
        }

        String sellerData = orderNumber + "," + email + "," + items + "," + totalAmount + "," + totalProfit + "\n";
        writeToFile("seller_sales.csv", sellerData);
    }

    /**
     * Writes data to a file
     * @param fileName The name of the file to write to
     * @param data The data to write to the file
     */
    private void writeToFile(String fileName, String data) {
        try (FileWriter fw = new FileWriter(fileName, true); BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the description of the items purchased.
     * @return A string describing the items purchased.
     */
    private String getItemsPurchased() {
        return cart.getItemsDescription(); // This method needs to be implemented in Cart class
    }

    /**
     * Gets the total amount of the purchase
     * @return The total amount of the purchase
     */
    private double getTotalAmount() {
        return cart.getTotalAmount(); // This method needs to be implemented in Cart class
    }

    /**
     * Reads the costs of the products from a file
     * @return A map with the product names as keys and their costs as values
     */
    private Map<String, Double> readProductCosts() {
        Map<String, Double> productCosts = new HashMap<>();
        String line;
        boolean firstLine = true; // Flag to identify the first line (header)

        try (BufferedReader br = new BufferedReader(new FileReader("products.csv"))) {
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false; // Skip the first line (header)
                    continue;
                }
                String[] values = line.split(",");
                if (values.length >= 3) {
                    String productName = values[0];
                    double costPrice = Double.parseDouble(values[2]); // Fetching cost price from the third column
                    productCosts.put(productName, costPrice);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productCosts;
    }

    /**
     * Checks if all fields in a form are filled
     * @param fields The fields to check
     * @return True if all fields are filled, false otherwise
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
     * Checks if a string can be parsed to a number
     * @param text The string to check
     * @return True if the string can be parsed to a number, false otherwise
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
