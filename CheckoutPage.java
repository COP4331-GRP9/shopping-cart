

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class CheckoutPage {
    private JFrame frame;
    private Cart cart;

    public CheckoutPage(Cart cart) {
        frame = new JFrame("Checkout");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.cart = cart; // Store the cart object

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

        /**
         * Creates a "Pay Now" button and adds an action listener to it
         * When the button is clicked, it checks if all fields are filled and if the card number is numeric
         * If yes, it shows a message dialog saying "Payment Processed!" and closes the checkout window
         * If not, it shows a message dialog saying "Please fill all fields correctly."
         */
        JButton payNowButton = new JButton("Pay Now");
        payNowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform validation here

                String itemsPurchased = getItemsPurchased();
                double totalAmount = getTotalAmount();

                // Generate an order number (could be more sophisticated in a real application)
                String orderNumber = UUID.randomUUID().toString();

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

    private void logCustomerPurchase(String orderNumber, String email, String items, double totalAmount) {
        String customerData = orderNumber + "," + email + "," + items + "," + totalAmount + "\n";
        writeToFile("customer_purchases.csv", customerData);
    }
    
    private void logSellerSales(String orderNumber, String email, String items, double totalAmount) {
        Map<String, Double> productCosts = readProductCosts();
        double totalProfit = 0.0;
    
        // Split the purchased items to calculate the profit for each
        String[] purchasedItems = items.split(", ");
        for (String item : purchasedItems) {
            String itemName = item.split(" \\(Qty: ")[0]; // Assuming item format is "ItemName (Qty: X)"
            double sellingPrice = cart.getProductPrice(itemName); // Assuming Cart class has a method to get the price of a product
            double costPrice = productCosts.getOrDefault(itemName, 0.0);
            double profit = sellingPrice - costPrice;
            totalProfit += profit;
        }
    
        String sellerData = orderNumber + "," + email + "," + items + "," + totalAmount + "," + totalProfit + "\n";
        writeToFile("seller_sales.csv", sellerData);
    }
      

    private void writeToFile(String fileName, String data) {
        try (FileWriter fw = new FileWriter(fileName, true); BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getItemsPurchased() {
        return cart.getItemsDescription(); // This method needs to be implemented in Cart class
    }

    private double getTotalAmount() {
        return cart.getTotalAmount(); // This method needs to be implemented in Cart class
    }

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
    
}