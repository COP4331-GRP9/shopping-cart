import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A simple Swing application for adding products to a shopping cart.
 */
public class AddingToCart {

    private JFrame frame;
    private JComboBox<String> productComboBox;
    private JButton addToCartButton;
    private JTextArea cartTextArea;

    /**
     * Constructs the AddingToCart application.
     */
    public AddingToCart() {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    /**
     * Creates and displays the GUI.
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
     * Places components on the panel.
     *
     * @param panel The panel to which components are added.
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
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedProduct = (String) productComboBox.getSelectedItem();
                cartTextArea.append("Added to Cart: " + selectedProduct + "\n");
                // You can add logic here to update the shopping cart.
            }
        });
    }

    /**
     * The main method to launch the AddingToCart application.
     *
     * @param args The command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        new AddingToCart();
    }
}
