import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ProductView {
    private static Scanner scanner = new Scanner(System.in);
    private ProductService productService;
    private ProductServiceInFile productServiceInFile;

    public ProductView() {
        productService = new ProductService();
        productServiceInFile = new ProductServiceInFile();
    }

    public void launch() {
        boolean actionMenu = true;
        while (actionMenu) {
            try {
                System.out.println("                        ╔═══════════════════════════════════════════════╗");
                System.out.println("                        ║                                               ║");
                System.out.println("                        ╠----------------QUẢN LÝ SẢN PHẨM---------------╣");
                System.out.println("                        ║                                               ║");
                System.out.println("                        ╠═══════════════════════════════════════════════╣");
                System.out.println("                        ║ ► [1] Hiển thị danh sách sản phẩm             ║");
                System.out.println("                        ║ ► [2].Thêm sản phẩm                           ║");
                System.out.println("                        ║ ► [3].Sửa thông tin sản phẩm                  ║");
                System.out.println("                        ║ ► [4].Xoá sản phẩm                            ║");
                System.out.println("                        ║ ► [5].Sắp xếp sản phẩm                        ║");
                System.out.println("                        ║ ► [6].Tìm kiếm sản phẩm có giá lớn nhất       ║");
                System.out.println("                        ║ ► [7].Đọc danh sách sản phẩm từ file          ║");
                System.out.println("                        ║ ► [8].Ghi danh sách sản phẩm vào file         ║");
                System.out.println("                        ║ ► [0].Thoát chương trình                      ║");
                System.out.println("                        ╚═══════════════════════════════════════════════╝");
                System.out.print("Nhập lựa chọn của bạn: ");

                int input = Integer.parseInt(scanner.nextLine());
                switch (input) {
                    case 1:
                        showProducts(productService.findAllProduct());
                        break;
                    case 2:
                        showCreateProduct();
                        showProducts(productService.findAllProduct());
                        System.out.println("Thêm sản phẩm thành công!");
                        break;
                    case 3:
                        showProducts(productService.findAllProduct());
                        editProduct();
                        break;
                    case 4:
                        showProducts(productService.findAllProduct());
                        Product productToDel = inputIdProduct();
                        if (productService.removeProduct(productToDel.getId())) {
                            System.out.println("Xoá thành công!");
                        } else {
                            System.out.println("Không tìm thấy sp để xoá");
                        }
                        showProducts(productService.findAllProduct());
                        break;
                    case 5:
                        System.out.println("Sắp xếp sản phẩm");
                        productService.sortProductByPrice();
                        showProducts(productService.findAllProduct());
                        break;
                    case 6:
                        System.out.println("Tìm kiếm sản phẩm có giá đắt nhất");
                        Product productFind;
                        productFind = productService.findMaxPrice(productService.findAllProduct());
                        System.out.println(productFind);
                        break;
                    case 7:

                        System.out.println("Cảnh báo: Sẽ xoá toàn bộ danh sách sản phẩm trong bộ nhớ");
                        boolean check;
                        do {
                            try {
                                System.out.println("Bạn có muốn tiếp tục hay không");
                                System.out.println("Nhập 1. Y");
                                System.out.println("Nhập 2. N");
                                int actionContinue = Integer.parseInt(scanner.nextLine());
                                switch (actionContinue) {
                                    case 1:
                                        List<Product> productList = productServiceInFile.readFile();
                                        showProducts(productList);
                                        System.out.println("Bộ nhớ đã cập nhập từ file!");
                                        check = false;
                                        break;
                                    case 2:
                                        check = false;
                                        break;
                                    default:
                                        System.out.println("Nhập không đúng vui lòng nhập lại");
                                    check = true;
                                }
                            } catch (NumberFormatException numberFormatException) {
                                System.out.println("Định dạng không đúng. Vui lòng nhập lại");
                                check = true;
                            }
                        } while (check);

                        break;
                    case 8:
                        System.out.println("Cảnh báo: Sẽ lưu toàn bộ danh sách sản phẩm vào file");
                        if (checkContinue()) {
                            productServiceInFile.writeFile(productService.findAllProduct());
                            System.out.println("Lưu dữ liệu thành công!");
                        } else {
                            System.out.println("Bạn chưa lưu dữ liệu");
                        }
                        break;
                    case 0:
                        actionMenu = false;
                        break;
                    default:
                        System.out.println("Bạn nhập không hợp lệ, Vui lòng nhập lại");
                }
            }catch (NumberFormatException numberFormatException) {
                System.out.println("Định dạng không đúng. Vui lòng nhập lại");
            }
        }
}

    private void editProduct() {
        Product productToEit = inputIdProduct();
        if (productToEit != null) {
            boolean checkActionEdit = false;
            do{
                System.out.println("Bạn muốn sửa thông tin gì");
                System.out.println("Nhập 1: Sửa tên:");
                System.out.println("Nhập 2: Sửa mô tả:");
                System.out.println("Nhập 3: Sửa giá:");
                System.out.println("Nhập 4: Sửa số lượng:");
                System.out.println("Nhập 0: Quay lại");
                int actionEdit = Integer.parseInt(scanner.nextLine());
                switch (actionEdit) {
                    case 1:
                        inputNameProduct(productToEit);
                        break;
                    case 2:
                        inputDescProduct(productToEit);
                        break;
                    case 3:
                        inputPriceProduct(productToEit);
                        break;
                    case 4:
                        inputQuantityProduct(productToEit);
                        break;
                    case 0:
                        checkActionEdit = false;
                        break;
                    default:
                        System.out.println("Nhâp sai. Vui lòng nhập lại");
                        checkActionEdit = true;
                }
            } while (checkActionEdit);
        }
    }

    private void inputQuantityProduct(Product productToEdit) {
        System.out.println("Thông tin sản phẩm hiện tại");
        System.out.println(productToEdit);

        System.out.println("Nhập số lượng mới:");
        int quantityEdit = Integer.parseInt(scanner.nextLine());

        productToEdit.setQuantity(quantityEdit);
        productService.editProduct(productToEdit.getId(), productToEdit);

        System.out.println("Sửa thành công!");
        System.out.println(productToEdit);
    }

    private void inputPriceProduct(Product productToEit) {
        System.out.println("Thông tin sản phẩm hiện tại");
        System.out.println(productToEit);

        System.out.println("Nhập giá mới:");
        float priceEdit = Float.parseFloat(scanner.nextLine());

        productToEit.setPrice(priceEdit);
        productService.editProduct(productToEit.getId(), productToEit);

        System.out.println("Sửa thành công!");
        System.out.println(productToEit);
    }

    private void inputDescProduct(Product productToEit) {
        System.out.println("Thông tin sản phẩm hiện tại");
        System.out.println(productToEit);

        System.out.println("Nhập mô tả mới:");
        String descEdit = scanner.nextLine();

        productToEit.setDescription(descEdit);
        productService.editProduct(productToEit.getId(), productToEit);

        System.out.println("Sửa thành công!");
        System.out.println(productToEit);
    }

    private void inputNameProduct(Product productToEdit) {
        System.out.println("Thông tin sản phẩm hiện tại");
        System.out.println(productToEdit);

        System.out.println("Nhập tên mới:");
        String nameEdit = scanner.nextLine();

        productToEdit.setName(nameEdit);
        productService.editProduct(productToEdit.getId(), productToEdit);

        System.out.println("Sửa thành công!");
        System.out.println(productToEdit);
    }

    private Product inputIdProduct() {
        Product product = null;
        boolean checkEditProductValid;
        do{
            try {
                checkEditProductValid = false;
                System.out.println("Nhập id sản phẩm:");
                long idProduct = Long.parseLong(scanner.nextLine());
                product = productService.findProductById(idProduct);
                if (product == null) {
                    System.out.println("ID sản phẩm không hợp lệ");
                    System.out.println("Chọn 1: Nhập lại");
                    System.out.println("Chọn 2: Quay lại");
                    int actionEditId = Integer.parseInt(scanner.nextLine());
                    switch (actionEditId) {
                        case 1:
                            checkEditProductValid = true;
                            break;
                        case 2:
                            checkEditProductValid = false;
                            break;
                    }
                }
            } catch (NumberFormatException n) {
                System.out.println("ID sản phẩm không hợp lệ");
                checkEditProductValid = true;
            }
        } while (checkEditProductValid);
        System.out.println("Sản phẩm được tìm thấy");
        return product;
    }

    private void showCreateProduct() {
        System.out.println("Thêm sản phẩm");
        System.out.println("Nhập tên sản phẩm:");
        String nameNew = scanner.nextLine();

        System.out.println("Nhập giá sản phẩm:");
        float priceNew = Float.parseFloat(scanner.nextLine());

        System.out.println("Nhập số lượng sản phẩm:");
        int quantityNew = Integer.parseInt(scanner.nextLine());
        System.out.println("Nhập mô tả:");
        String descNew = scanner.nextLine();

        Product productNew = new Product(System.currentTimeMillis() / 10000, nameNew, priceNew, quantityNew, descNew);
        productService.addProduct(productNew);
    }

    private void showProducts(List<Product> allProduct) {
        System.out.printf("%-10s %-15s %-10s %-10s %-20s\n", "ID", "Name", "Price", "Quantity", "Description");
        for (Product product : allProduct) {
            System.out.printf("%-10s %-15s %-10s %-10s %-20s\n", product.getId(), product.getName(), product.getPrice(), product.getQuantity(), product.getDescription());
        }
    }
    public static boolean checkContinue() {
        do {
            try {
                System.out.println("Bạn có muốn tiếp tục hay không");
                System.out.println("Nhập 1. Y");
                System.out.println("Nhập 2. N");
                int actionContinue = Integer.parseInt(scanner.nextLine());
                switch (actionContinue) {
                    case 1:
                        return true;
                    case 2:
                        return false;
                    default:
                        System.out.println("Nhập không đúng vui lòng nhập lại");

                }
            } catch (NumberFormatException numberFormatException) {
                System.out.println("Định dạng không đúng. Vui lòng nhập lại");
            }
        } while (true);
    }
}
