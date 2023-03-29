package service;

import model.Product;

import java.util.List;
import java.util.Scanner;

public class ProductService {
    private final static Scanner scanner = new Scanner(System.in);
    public static void deleteProduct(Product product, List<Product> products){

        while (true)
        {
            System.out.print("Are you sure you want to delete " + product.getName() + "? [Y(es) | n]: ");
            String opt = scanner.nextLine();

            if(opt.equalsIgnoreCase("y")) {
                products.remove(product);
                System.out.println("Product removed successfully!");
                break;
            }
            else if (opt.equalsIgnoreCase("n")){
                break;
            }
            else {
                System.out.print("\u001B[31m");
                System.out.println("Invalid option!");
                System.out.print("\u001B[0m");
            }
        }

    }
}
