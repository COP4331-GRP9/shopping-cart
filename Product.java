/**
 * Represents a product with a name and price
 */
public class Product {
    /**
     * Name of the product
     */
    public String name;
    /**
     * Price of the product
     */
    public Double price;

    /**
     * Constructs a new Product with the specified name and price
     * @param name  the name of the product
     * @param price the price of the product
     */
    Product(String name, Double price) {
        this.price = price;
        this.name = name;
    }

    /**
     * Indicates whether some other object is "equal to" this one
     * The result is true if and only if the argument is not null and is a Product object that represents the same name as this object
     * @param obj the reference object with which to compare
     * @return true if this object is the same as the obj argument; false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Product product = (Product) obj;
        return name.equals(product.name);
    }

    /**
     * Returns a hash code value for the object. This method is supported for the benefit of hash tables such as those provided by HashMap.
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
