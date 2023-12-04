
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a shopping cart
 */
public class Cart {
    /**
     * A map to store the products in the cart and their quantities
     */
    private Map<Product, Integer> items;

    /**
     * Constructor for the Cart class
     */
    public Cart() {
        items = new HashMap<>();
    }

    /**
     * This method adds an item to the cart
     * @param itemName The name of the item
     * @param price The price of the item
     */
    public void addItem(String itemName, Double price) {
        var item = new Product(itemName, price);
        items.put(item, items.getOrDefault(item, 0) + 1);
    }

    /**
     * This method removes an item from the cart
     * @param itemName The name of the item
     */
    public void removeItem(String itemName) {
        for (Product product : items.keySet()) {
            if (product.name.equals(itemName)) {
                int count = items.get(product);
                if (count > 1) {
                    items.put(product, count - 1);
                } else {
                    items.remove(product);
                }
                break;
            }
        }
    }

    /**
     * This method returns a list of items in the cart
     * @return The list of products
     */
    public List<Product> getItems() {
        return new ArrayList<>(items.keySet());
    }

    /**
     * Gets the total price of items in the cart
     * @return The total price
     */
    public double getTotalPrice() {
        double total = 0;
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            total += entry.getKey().price * entry.getValue();
        }
        return total;
    }

    /**
     * Returns the quantity of a specific product in the cart
     * @param product The product
     * @return The quantity of the product
     */
    public int getItemQuantity(Product product) {
        return items.getOrDefault(product, 0);
    }
}
