

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AccountPage {
    private JFrame frame;
    private String userRole; // Customer or Seller

    public AccountPage(String userRole) {
        this.userRole = userRole;

        frame = new JFrame("ebay Account");
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
