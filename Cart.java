
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {
    private Map<Product, Integer> items;

    public Cart() {
        items = new HashMap<>();
    }

    public void addItem(String itemName, Double price) {
        var item = new Product(itemName, price);
        items.put(item, items.getOrDefault(item, 0) + 1);
    }

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

    public List<Product> getItems() {
        return new ArrayList<>(items.keySet());
    }

    public double getTotalPrice() {
        double total = 0;
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            total += entry.getKey().price * entry.getValue();
        }
        return total;
    }

    public int getItemQuantity(Product product) {
        return items.getOrDefault(product, 0);
    }
}
