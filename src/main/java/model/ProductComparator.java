package model;

import model.Product;

import java.util.Comparator;

public class ProductComparator implements Comparator<Product> {
    private final boolean ascending;

    public ProductComparator(boolean ascending) {
        this.ascending = ascending;
    }

    @Override
    public int compare(Product product1, Product product2) {
        int result = Double.compare(product1.getPrice(), product2.getPrice());
        return ascending ? result : -result;
    }
}

