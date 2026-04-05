import dataFactories.ProductDataFactory;
import dataObjects.ProductDetails;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjectsModels.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class homePageTestCases extends Base {

    // Test: Select currency from dropdown menu
    @Test
    public void selectCurrency() {
        // Step 1: Initialize home page object // Step 2: Select US Dollar currency
        HomePageObject homePage = new HomePageObject(driver);
        homePage.selectCurrency("US Dollar");
    }

    @Test
    public void verifyToSearchBlankProduct() {
        // Initialize page objects
        var homePage = new HomePageObject(driver);
        var searchPage = new SearchPageObject(driver);

        // search for blank product
        homePage.searchProduct("");

        // verify search results page (assuming URL contains search term)
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(Objects.requireNonNull(currentUrl).contains("search"), "Not redirected to search results page");

        // verify product not found message
        Assert.assertEquals(searchPage.getNoProductFoundMessage(), "There is no product that matches the search criteria.", "No product found message does not match");
    }

    // Test: Search for a product and verify search functionality
    @Test
    public void verifyToSearchProduct() {
        // Step 1: Initialize home page object
        HomePageObject homePage = new HomePageObject(driver);
        SearchPageObject searchPage = new SearchPageObject(driver);

        // Step 2: Search for a product
        String productName = "MacBook";
        homePage.searchProduct(productName);

        // Step 3: Verify search results page (assuming URL contains search term)
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl != null && currentUrl.contains("search"), "Not redirected to search results page");

        // Step 4: Verify the search product appears in search results
        List<String> searchResults = searchPage.getSearchResults();

        System.out.println("Total Search Results: " + searchResults.size());

        for (String searchResult : searchResults) {
            Assert.assertTrue(searchResult.contains(productName), "Search result does not match");
        }
    }

    // Test: Add a single product to cart and verify it appears in cart items list
    @Test
    public void addToCartOneProduct() {
        // Step 1: Initialize home page object
        var homePage = new HomePageObject(driver);

        // Step 2: Get product data from factory
        var productDetails = ProductDataFactory.addOneProduct();

        // Step 3: Add product to cart
        homePage.addProductsToCart((ProductDetails) productDetails);

        // Step 4: Verify product is in cart
        verifyProductInMiniCart((ProductDetails) productDetails);

        calculateTotalPrice(productDetails);
    }

    // Test: Add multiple products to cart and verify all appear in cart items list
    @Test
    public void addToCartMultipleProducts() {
        // Step 1: Initialize home page object
        HomePageObject homePage = new HomePageObject(driver);

        // Step 2: Get multiple products data from factory
        var productDetails = ProductDataFactory.MultiPleProduct();

        // Step 3: Add all products to cart
        for (var product : productDetails) {
            homePage.addProductsToCart(product);
        }

        // Step 4: Verify all products are in cart
        for (var product : productDetails) {
            verifyProductInMiniCart(product);
        }
    }

    @Test
    public void verifyToCalculateTotalPriceInMiniCart() {
        // Step 1: Initialize home page object
        var homePage = new HomePageObject(driver);

        // Step 2: Get multiple products data from factory
        var productDetails = ProductDataFactory.MultiPleProduct();

        // Step 3: Add all products to cart
        for (var product : productDetails) {
            homePage.addProductsToCart(product);
        }

        // Step 4: Verify all products are in cart
        for (var product : productDetails) {
            verifyProductInMiniCart(product);
        }

        // Calculate and verify total price in mini cart
        calculateTotalPrice(productDetails);
    }

    // Test: Verify home page title
    @Test
    public void verifyHomePageTitle() {
        // Step 1: Initialize home page object
        HomePageObject homePage = new HomePageObject(driver);

        // Step 2: Verify page title
        String expectedTitle = "Your Store";
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, expectedTitle, "Home page title does not match");
    }

    // Test: Navigate to login page and verify
    @Test
    public void navigateToLoginPage() {
        // Step 1: Initialize home page object
        HomePageObject homePage = new HomePageObject(driver);

        // Step 2: Navigate to login
        homePage.navigateToLogin();

        // Step 3: Verify login page title or element
        LoginPageObject loginPage = new LoginPageObject(driver);
        String expectedTitle = "Returning Customer";
        String actualTitle = loginPage.getReturningCustomerTitle();
        Assert.assertEquals(actualTitle, expectedTitle, "Not on login page");
    }

    @Test
    public void verifyToClickOnViewCartBtnInMiniCart() {
        // Initialize page objects
        var homePage = new HomePageObject(driver);

        // add to cart product
        var products = ProductDataFactory.addOneProduct();
//        homePage.addProductsToCart(products);

        // click on mini cart and view cart button
        homePage.clickOnMiniCart();
        homePage.clickOnViewCartBtn();

        String cartUrl = driver.getCurrentUrl();
        Assert.assertTrue(Objects.requireNonNull(cartUrl).contains("cart"), "User Not redirected on cart page");
    }

    @Test
    public void ignore() {
        driver.get("https://the-internet.herokuapp.com/upload");

        var element = driver.findElement(By.id("file-upload"));
    }

    // ============ Helper Methods ============

    private void verifyProductInMiniCart(ProductDetails productName) {
        // Initialize Page Object
        var homePage = new HomePageObject(driver);

        // Click on Mini Cart Button
        homePage.clickOnMiniCart();
        Assert.assertTrue(
                homePage.isProductInCart(productName),
                "Product not added to cart: " + productName
        );

    }

    /**
     * Calculates the total price in mini cart by using data factory calculations
     * and verifies against the actual values displayed in the UI:
     *
     * Calculation Formula:
     * 1. SubTotal = Price × Quantity for each product (sum of all products)
     * 2. Eco Tax = $2 per quantity (across all items in cart)
     * 3. VAT = 20% of SubTotal (calculated on subtotal only, not including eco tax)
     * 4. Final Total = SubTotal + Eco Tax + VAT
     *
     * @param productDetails List of products in the cart
     */
    private void calculateTotalPrice(List<ProductDetails> productDetails) {

        // Step 1: Initialize page object
        HomePageObject homePage = new HomePageObject(driver);

        // Step 2: Calculate SubTotal (price × quantity for each item)
        double expectedSubtotal = 0.0;
        for (ProductDetails product : productDetails) {
            double price = Double.parseDouble(product.getPrice());
            int quantity = product.getQuantity();
            double itemAmount = price * quantity;
            expectedSubtotal += itemAmount;
        }
        Assert.assertEquals(homePage.getMiniCartSubTotalPriceText(), "$" + String.format("%.2f", expectedSubtotal), "Sub-total price does not match");

        // Step 3: Calculate total quantity across all items for Eco Tax calculation
        int totalQuantity = 0;
        for (ProductDetails product : productDetails) {
            totalQuantity += product.getQuantity();
        }

        // Step 4: Calculate Eco Tax ($2 per quantity)
        double expectedEcoTax = 2.0 * totalQuantity;
        Assert.assertEquals(homePage.getMiniCartEcoTaxPrice(), "$" + String.format("%.2f", expectedEcoTax), "Eco-Tax does not match");

        // Step 5: Calculate VAT (20% of SubTotal only, not including eco tax)
        double expectedVat = expectedSubtotal * 0.20;
        Assert.assertEquals(homePage.getMiniCartVatPrice(), "$" + String.format("%.2f", expectedVat), "Vat does not match");

        // Step 6: Calculate Final Total (SubTotal + Eco Tax + VAT)
        double expectedTotal = expectedSubtotal + expectedEcoTax + expectedVat;
        Assert.assertEquals(homePage.getMiniCartTotalPrice(), "$" + String.format("%.2f", expectedTotal), "Total does not match");
    }

    /**
     * Helper method to parse price string and extract numeric value
     * Removes currency symbols and returns the price as double
     *
     * @param priceStr The price string (e.g., "$50.00")
     * @return The price as a double value
     */
    private double parsePrice(String priceStr) {
        if (priceStr == null || priceStr.isEmpty()) {
            return 0.0;
        }
        // Remove all non-numeric characters except the decimal point
        return Double.parseDouble(priceStr.replaceAll("[^\\d.]", ""));
    }
}
