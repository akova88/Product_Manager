import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProductServiceInFile {
    private static final String path = "./data/product.csv";

    public static List<Product> products;

    public List<Product> readFile() {
        List<Product> products = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader("./data/products.csv");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] items = line.split(",");
                long idProduct = Long.parseLong(items[0]);
                double priceProduct = Double.parseDouble(items[2]);
                int quantityProduct = Integer.parseInt(items[3]);
                Product p = new Product(idProduct,items[1],priceProduct,quantityProduct,items[4]);
                products.add(p);
            }
            fileReader.close();
            bufferedReader.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return products;
    }
    public void writeFile(List<Product> products) {
        try {
            FileWriter fileWriter = new FileWriter("./data/products.csv");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (Product item : products) {
                bufferedWriter.write(item.toString()+"\n");
            }
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
