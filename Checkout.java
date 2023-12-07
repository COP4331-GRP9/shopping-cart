import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A simple Swing application for handling the checkout process.
 */
public class Checkout {

    private JFrame frame;
    private JButton checkoutButton;
    private JTextArea orderSummaryTextArea;

    /**
     * Constructs the Checkout application.
     */
    public Checkout() {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    /**
     * Creates and displays the GUI.
     */
    private void createAndShowGUI() {
        frame = new JFrame("Checkout");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    /**
     * Places components on the panel.
     *
     * @param panel The panel to which components are added.
     */
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

    /**
     * The main method to launch the Checkout application.
     *
     * @param args The command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        new Checkout();
    }
}
