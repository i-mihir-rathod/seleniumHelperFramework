package dataFactories;

import dataObjects.ProductDetails;
import java.util.ArrayList;
import java.util.List;

public class ProductDataFactory {

    public static ProductDetails addOneProduct(){

        // Create an object and add one product and return it to be used in test case
        var productDetails = new ProductDetails();
        productDetails.setName("MacBook");
        return productDetails;
    }

    public static ProductDetails MultiPleProduct(){

        // Create an object and add one product and return it to be used in test case
        var productDetails = new ProductDetails();

        productDetails.setName("MacBook", "iPhone");

        return productDetails;
    }
}
