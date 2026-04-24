import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjectsModels.HomePageObject;
import pageObjectsModels.ShoppingCartPageObject;

import java.util.Objects;

public class cartPageTestCases extends Base {

    // Test: Open shopping cart when empty
    @Test
    public void openEmptyShoppingCart() {
        // Step 1: Initialize helper method
        ShoppingCartPageObject cartPage = navigateToCartPage();

        // Step 4: Verify empty cart message
        String expectedMessage = "Your shopping cart is empty!";
        String actualMessage = cartPage.getEmptyCartMessage();

        Assert.assertEquals(actualMessage, expectedMessage, "Empty cart message does not match");
    }

    @Test
    public void openShoppingCartAndClickOnContinueButton() {
        // Step 1: Initialize helper method
        ShoppingCartPageObject cartPage = navigateToCartPage();

        // Step 2: Verify empty cart message
        String expectedMessage = "Your shopping cart is empty!";
        String actualMessage = cartPage.getEmptyCartMessage();

        Assert.assertEquals(actualMessage, expectedMessage, "Empty cart message does not match");

        // Step 3: Click on continue button
        cartPage.clickContinueButton();

        // Step 4: Verify redirection to home page
        String homePageUrl = driver.getCurrentUrl();
        Assert.assertNotNull(homePageUrl);
        Assert.assertTrue(homePageUrl.contains("home"), "User Not redirected to home page");
    }

    // ============ Helper Methods ============

    private ShoppingCartPageObject navigateToCartPage() {
        // Step 1: Initialize page objects
        HomePageObject homePage = new HomePageObject(driver);
        ShoppingCartPageObject cartPage = new ShoppingCartPageObject(driver);

        // Step 2: Open shopping cart
        homePage.clickOnShoppingCartLink();

        // Step 3: Verify shopping cart page URL
        String cartUrl = driver.getCurrentUrl();
        Assert.assertTrue(Objects.requireNonNull(cartUrl).contains("cart"), "Not on shopping cart page or URL mismatched");

        return cartPage;
    }
}
