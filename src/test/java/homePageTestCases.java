import dataFactories.ProductDataFactory;
import dataObjects.ProductDetails;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjectsModels.*;

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
        var productDetails = ProductDataFactory.addOneProduct().getName();

        // Step 3: Add product to cart
        homePage.addProductsToCart(productDetails);

        // Step 4: Verify product is in cart
        verifyProductInMiniCart(productDetails);
    }

    // Test: Add multiple products to cart and verify all appear in cart items list
    @Test
    public void addToCartMultipleProducts() {
        // Step 1: Initialize home page object
        HomePageObject homePage = new HomePageObject(driver);

        // Step 2: Get multiple products data from factory
        var productDetails = ProductDataFactory.MultiPleProduct().getName();

        // Step 3: Add all products to cart
        homePage.addProductsToCart(productDetails);

        // Step 4: Verify all products are in cart
        verifyProductInMiniCart(productDetails);
    }

    @Test
    public void verifyToCalculateTotalPriceInMiniCart() {
        // Step 1: Initialize home page object
        var homePage = new HomePageObject(driver);

        // Step 2: Get multiple products data from factory
        var productDetails = ProductDataFactory.MultiPleProduct().getName();

        // Step 3: Add all products to cart
        homePage.addProductsToCart(productDetails);

        // Step 4: Verify all products are in cart
        verifyProductInMiniCart(productDetails);

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
        var products = ProductDataFactory.addOneProduct().getName();
        homePage.addProductsToCart(products);

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

    private void verifyProductInMiniCart(String... productName) {
        // Initialize Page Object
        var homePage =  new HomePageObject(driver);

        // Click on Mini Cart Button
        homePage.clickOnMiniCart();

        for (var product : productName) {
            Assert.assertTrue(
                    homePage.isProductInCart(product),
                    "Product not added to cart: " + product
            );
        }
    }

    /**
     * Calculates the total price in mini cart by:
     * 1. Getting the list of products and their prices from mini cart
     * 2. Calculating subtotal by summing all product amounts
     * 3. Adding $2 eco tax for each item
     * 4. Adding 20% VAT on (subtotal + eco tax)
     * 5. Returning the final total amount
     *
     * @return Final total price as a double value
     */
    private void calculateTotalPrice(String... productName) {

        // Step 1: Initialize page object
        HomePageObject homePage = new HomePageObject(driver);

        // Step 2: Get the list of products and their prices in mini cart
        List<String> cartItems = homePage.getMiniCartItemsList();
//        List<String> cartItemsPrices = homePage.getMiniCartItemsPriceText();
        int itemCount = cartItems.size();

        // Step 3: Calculate subtotal by summing all product amounts

        double subTotal = 0.00;
        for (var productPrice : productName) {
            double tax = parsePrice(homePage.getProductPriceWithoutTax(productPrice));
            subTotal += tax;
        }

        Assert.assertEquals(homePage.getMiniCartSubTotalPriceText(), "$" + String.format("%.2f", subTotal), "Subtotal does not match sum of product prices");

        // Step 4: Calculate eco tax ($2 for each item)
        double ecoTaxAmount = 2.0 * itemCount;
        Assert.assertEquals(homePage.getMiniCartEcoTaxPrice(), "$" + String.format("%.2f", ecoTaxAmount), "Eco tax does not match expected amount");

        // Step 5: Calculate VAT (20% of subtotal)
        double vatAmount = subTotal * 0.20; // 20% VAT
        Assert.assertEquals(homePage.getMiniCartVatPrice(), "$" + String.format("%.2f", vatAmount), "Vat does not match expected amount");

        // Step 6: Calculate and return the final total
        double finalTotal = subTotal + ecoTaxAmount + vatAmount;
        Assert.assertEquals(homePage.getMiniCartTotalPrice(), "$" + String.format("%.2f", finalTotal), "Total does not match expected amount");
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
