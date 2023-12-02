import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Checkout {
    private JFrame frame;
    private JButton checkoutButton;
    private JTextArea orderSummaryTextArea;

    public Checkout() {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private void createAndShowGUI() {
        frame = new JFrame("Checkout");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        checkoutButton = new JButton("Begin Checkout");
        panel.add(checkoutButton);

        orderSummaryTextArea = new JTextArea(10, 30);
        panel.add(orderSummaryTextArea);

        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add logic for processing the order and updating inventory
                orderSummaryTextArea.setText("Order Processed!\n");
            }
        });
    }

    public static void main(String[] args) {
        new Checkout();
    }
}
