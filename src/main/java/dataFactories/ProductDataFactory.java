package dataFactories;

import dataObjects.ProductDetails;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductDataFactory {

    public static List<ProductDetails> addOneProduct() {

        // Create an object and add one product and return it to be used in test case
        ProductDetails product = new ProductDetails();
        // Product 1: MacBook - Price $602.00, Quantity 1

        product.setName("MacBook");
        product.setPrice("602.00");
        product.setWithoutTax("500.00"); // Assuming a tax of $102.00 for demonstration
        product.setQuantity(1);

        return Collections.singletonList(product);
    }

    public static List<ProductDetails> MultiPleProduct() {

        // Create an object and add multiple products and return it to be used in test case
        List<ProductDetails> products = new ArrayList<>();

        // Product 1: MacBook - Price $602.00, Quantity 1
        ProductDetails product1 = new ProductDetails();
        product1.setName("MacBook");
        product1.setPrice("602.00");
        product1.setWithoutTax("500.00"); // Assuming a tax of $102.00 for demonstration
        product1.setQuantity(1);
        products.add(product1);

        // Product 2: iPhone - Price $299.50, Quantity 2
        ProductDetails product2 = new ProductDetails();
        product2.setName("iPhone");
        product2.setPrice("123.20");
        product2.setWithoutTax("101.00");
        product2.setQuantity(2);
        products.add(product2);

        return products;
    }
}
