import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A simple GUI for adding products to a shopping cart.
 */
public class AddingToCart {
    private JFrame frame;
    private JComboBox<String> productComboBox;
    private JButton addToCartButton;
    private JTextArea cartTextArea;

    /**
     * Constructor for the AddingToCart class
     * Initializes the GUI on the Event Dispatch Thread
     */
    public AddingToCart() {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    /**
     * Creates and displays the GUI
     */
    private void createAndShowGUI() {
        frame = new JFrame("Adding to Cart");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    /**
     * Places components on the panel
     * @param panel The panel on which to place the components
     */
    private void placeComponents(JPanel panel) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel productLabel = new JLabel("Choose Product:");
        panel.add(productLabel);

        String[] products = {"Product 1", "Product 2", "Product 3"};
        productComboBox = new JComboBox<>(products);
        panel.add(productComboBox);

        addToCartButton = new JButton("Add to Cart");
        panel.add(addToCartButton);

        cartTextArea = new JTextArea(10, 30);
        panel.add(cartTextArea);

        addToCartButton.addActionListener(new ActionListener() {
            /**
             * Handles the action of clicking the "Add to Cart" button
             * @param e The event that triggers this action
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedProduct = (String) productComboBox.getSelectedItem();
                cartTextArea.append("Added to Cart: " + selectedProduct + "\n");
                
            }
        });
    }

    /**
     * The main method that launches the application
     * @param args The command-line arguments
     */
    public static void main(String[] args) {
        new AddingToCart();
    }
}
