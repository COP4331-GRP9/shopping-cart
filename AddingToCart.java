import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddingToCart {
    private JFrame frame;
    private JComboBox<String> productComboBox;
    private JButton addToCartButton;
    private JTextArea cartTextArea;

    public AddingToCart() {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private void createAndShowGUI() {
        frame = new JFrame("Adding to Cart");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

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

    public static void main(String[] args) {
        new AddingToCart();
    }
}
