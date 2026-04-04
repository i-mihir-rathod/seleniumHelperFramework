package dataFactories;

import dataObjects.ProductDetails;
import java.util.ArrayList;
import java.util.List;

public class ProductDataFactory {

    public static ProductDetails addOneProduct(){

        // Create an object and add one product and return it to be used in test case
        var productDetails = new ProductDetails();
        productDetails.setName("MacBook");
        productDetails.setPrice("602.00");
        return productDetails;
    }

    public static List<ProductDetails> MultiPleProduct(){

        // Create an object and add one product and return it to be used in test case
        var productDetails1 = new ProductDetails("MacBook", "602.00");
        var productDetails2 = new ProductDetails("iPhone", "123.20");

        var productsList = new ArrayList<ProductDetails>();
        productsList.add(productDetails1);
        productsList.add(productDetails2);
        return productsList;
    }
}
