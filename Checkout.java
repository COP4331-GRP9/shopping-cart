import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A Checkout window for the shopping cart application.
 */
public class Checkout {
    private JFrame frame;
    private JButton checkoutButton;
    private JTextArea orderSummaryTextArea;

    /**
     * Constructor for the Checkout class
     * It initializes the GUI on the Event Dispatch Thread
     */
    public Checkout() {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    /**
     * Sets up the GUI components and makes the frame visible
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
     * This method adds components to the panel
     * @param panel The panel to which components are added
     */
    private void placeComponents(JPanel panel) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        checkoutButton = new JButton("Begin Checkout");
        panel.add(checkoutButton);

        orderSummaryTextArea = new JTextArea(10, 30);
        panel.add(orderSummaryTextArea);

        checkoutButton.addActionListener(new ActionListener() {
            /**
             * Defines the action to be taken when the checkout button is clicked
             * @param e The event that triggers this action
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add logic for processing the order and updating inventory
                orderSummaryTextArea.setText("Order Processed!\n");
            }
        });
    }

    /**
     * The main method that launches the Checkout window
     * @param args Command-line arguments. Not used in this application
     */
    public static void main(String[] args) {
        new Checkout();
    }
}
