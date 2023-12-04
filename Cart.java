

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {
    private Map<String, Integer> items;

    public Cart() {
        items = new HashMap<>();
    }

    public void addItem(String item) {
        items.put(item, items.getOrDefault(item, 0) + 1);
    }

    public void removeItem(String item) {
        if (items.containsKey(item) && items.get(item) > 1) {
            items.put(item, items.get(item) - 1);
        } else {
            items.remove(item);
        }
    }

    public List<String> getItems() {
        return new ArrayList<>(items.keySet());
    }
}
