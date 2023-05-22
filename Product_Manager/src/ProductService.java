import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private List<Product> products;

    public ProductService() {
        this.products = new ArrayList<>();
        products.add(new Product(12344, "Coca cola", 10000,
                5, "Vị nguyên bản"));

        products.add(new Product(12345, "Red Bull", 15000,
                10, "Vị cafe"));

        products.add(new Product(12346, "Pepsi", 12000,
                8, "Vị truyền thống"));
    }
    public List<Product> findAllProduct() {
        return this.products;
    }
    public Product findProductById(long idProduct) {
        for (Product product : products) {
            if (product.getId() == idProduct) {
                return product;
            }
        }
        return null;
    }
    public void editProduct(long idProduct, Product product) {
        for (Product item : products) {
            if (item.getId() == idProduct) {
                item.setName(product.getName());
                item.setPrice(product.getPrice());
                item.setQuantity(product.getQuantity());
                item.setDescription(product.getDescription());
            }
        }
    }
    public void addProduct(Product newProduct) {
        products.add(newProduct);
    }
    public boolean removeProduct(long idProduct) {
        Product productToRemove;
        productToRemove = findProductById(idProduct);
        if (productToRemove == null) {
            return false;
        } else {
            products.remove(productToRemove);
            return true;
        }
    }
    public void sortProductByPrice() {
        products.sort(new PriceComparator());
    }

    public Product findMaxPrice(List<Product> products) {

        double priceMax = products.get(0).getPrice();
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getPrice() > priceMax) {
                priceMax = products.get(i).getPrice();
            }
        }
        return findProductByPrice(priceMax);
    }
    public Product findProductByPrice(double price) {
        for (Product p : products) {
            if (p.getPrice() == price)
                return p;
        }
        return null;
    }
}

