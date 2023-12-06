

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class represents the account page for a user on a shopping cart application
 * The user can either be a customer or a seller
 */
public class AccountPage {
    private JFrame frame;
    private String userRole; // Customer or Seller

    /**
     * Constructs an AccountPage object and initializes the GUI
     * @param userRole the role of the user, either "customer" or "seller"
     */
    public AccountPage(String userRole) {
        this.userRole = userRole;

        frame = new JFrame("eBay Account");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center the frame

        // Set the icon
        ImageIcon icon = new ImageIcon("logo.png"); // Relative path to the logo
        frame.setIconImage(icon.getImage());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        frame.add(panel);

        if ("customer".equals(userRole)) {
            displayCustomerOrderHistory(panel);
        } else if ("seller".equals(userRole)) {
            displaySellerSalesReport(panel);
        }

        frame.setVisible(true);
    }

    /**
     * Displays the order history of a customer.
     * @param panel the JPanel to which the order history is added
     */
    private void displayCustomerOrderHistory(JPanel panel) {
        JTextArea orderHistoryArea = new JTextArea(15, 50);
        orderHistoryArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(orderHistoryArea);
        panel.add(scrollPane);

        try (BufferedReader reader = new BufferedReader(new FileReader("customer_purchases.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                orderHistoryArea.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays the sales report of a seller
     * @param panel the JPanel to which the sales report is added
     */
    private void displaySellerSalesReport(JPanel panel) {
        JTextArea salesReportArea = new JTextArea(15, 50);
        salesReportArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(salesReportArea);
        panel.add(scrollPane);

        try (BufferedReader reader = new BufferedReader(new FileReader("seller_sales.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                salesReportArea.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
