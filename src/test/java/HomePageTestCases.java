import dataFactories.ProductDataFactory;
import dataFactories.RegisterDataFactory;
import dataObjects.ProductDetails;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjectsModels.*;

import java.util.List;
import java.util.Objects;

public class HomePageTestCases extends Base {

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
        // Step 2: Get product data from factory
        HomePageObject homePage = new HomePageObject(driver);
        ProductDetails productDetails = ProductDataFactory.addOneProduct();

        // Step 3: Add product to cart
        homePage.addProductsToCart(productDetails.getName());

        // Step 4: Verify product is in cart
        verifyProductInMiniCart(homePage, productDetails.getName());
    }

    // Test: Add multiple products to cart and verify all appear in cart items list
    @Test
    public void addToCartMultipleProducts() {
        // Step 1: Initialize home page object // Step 2: Get multiple products data from factory
        HomePageObject homePage = new HomePageObject(driver);
        List<ProductDetails> productDetails = ProductDataFactory.MultiPleProduct();

        // Step 3: Add all products to cart
        for (ProductDetails product : productDetails) {
            homePage.addProductsToCart(product.getName());
        }

        // Step 4: Verify all products are in cart
        for (ProductDetails product : productDetails) {
            verifyProductInMiniCart(homePage, product.getName());
        }
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
        String products = ProductDataFactory.addOneProduct().getName();
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

    /**
     * Verifies that a product is displayed in the shopping cart
     *
     * @param homePage    Home page object instance
     * @param productName Name of the product to verify
     */
    private void verifyProductInMiniCart(HomePageObject homePage, String productName) {
        List<String> miniCartItems = homePage.getMiniCartItems();
        Assert.assertTrue(
                homePage.isProductInCart(productName),
                "Product not added to cart: " + miniCartItems
        );
    }
}
