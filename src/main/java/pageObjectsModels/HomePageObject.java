package pageObjectsModels;

import dataObjects.ProductDetails;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class HomePageObject extends BasePageObject {

    // ============ Locators ============

    private final By currencyBtn = By.xpath("//span[text() = 'Currency']/parent::button");
    private final By currencyLogo = By.xpath("//span[text() = 'Currency']/preceding-sibling::strong");
    private final By myAccountBtn = By.xpath("//a[@title = 'My Account']");
    private final By registerLink = By.xpath("//a[@title = 'My Account']/following-sibling::ul//a[text() = 'Register']");
    private final By loginLink = By.xpath("//a[@title = 'My Account']/following-sibling::ul//a[text() = 'Login']");
    private final By searchInput = By.name("search");
    private final By searchBtn = By.xpath("//input[@name = 'search']/following-sibling::span/button");
    private final By shoppingCartLink = By.xpath("//a[@title = 'Shopping Cart']");
    private final By successAlert = By.xpath("//div[contains(@class, 'alert-success')]");

    // ============ miniCart popup Locators ============
    private final By miniCartBtn = By.xpath("//div[@id = 'cart']/button");
    private final By cartItemsPriceList = By.xpath("//table[contains(@class, 'table-striped')]//td[contains(text(), '$')]");
    private final By subTotalPrice = By.xpath("//table[contains(@class, 'table-bordered')]//strong[text() = 'Sub-Total']/parent::td/following-sibling::td");
    private final By ecoTaxPrice = By.xpath("//table[contains(@class, 'table-bordered')]//strong[text() = 'Eco Tax (-2.00)']/parent::td/following-sibling::td");
    private final By vatPrice = By.xpath("//table[contains(@class, 'table-bordered')]//strong[text() = 'VAT (20%)']/parent::td/following-sibling::td");
    private final By totalPrice = By.xpath("//table[contains(@class, 'table-bordered')]//strong[text() = 'Total']/parent::td/following-sibling::td");
    private final By viewCartBtn = By.xpath("//i[contains(@class, 'fa-shopping-cart')]/parent::strong");
    private final By checkOutBtn = By.xpath("//i[contains(@class, 'fa-share')]/parent::strong");

    // ============ Dynamic Locators ============

    private By currencyDropdown(String currency) {
        return By.xpath("//ul[@class = 'dropdown-menu']/li/button[contains(text(), '" + currency + "')]");
    }

    private By getTotalCartItemDropdown(int number) {
        return By.xpath("//span[contains(text(),'" + number + " item(s)')]");
    }

    private By getProductNameInCart(String productName) {
        return By.xpath("//table//a[text()='" + productName + "']");
    }

    private By productAddToCartBtn(String itemName) {
        return By.xpath("//a[contains(text(), '" + itemName + "')]/ancestor::div[@class = 'caption']/following-sibling::div//button[contains(@onclick, 'cart.add')]");
    }

    // ============ Constructor ============

    public HomePageObject(WebDriver driver) {
        super(driver);
    }

    // ============ Actions ============

    /**
     * Selects a currency from the currency dropdown menu
     *
     * @param currency The currency code to select
     */
    public void selectCurrency(String currency) {
        seleniumHelper.clickOnElement(currencyBtn);
        seleniumHelper.clickOnElement(currencyDropdown(currency));
    }

    public void navigateToRegister() {
        clickMyAccountMenu();
        seleniumHelper.clickOnElement(registerLink);
    }

    public void navigateToLogin() {
        clickMyAccountMenu();
        seleniumHelper.clickOnElement(loginLink);
    }

    /**
     * Searches for a product by name
     *
     * @param productName The name of the product to search
     */
    public void searchProduct(String productName) {
        seleniumHelper.enterText(searchInput, productName);
        seleniumHelper.clickOnElement(searchBtn);
    }

    /**
     * Adds one or multiple products to the shopping cart
     *
     * @param itemNames Variable length parameter for product names to add
     */
    public void addProductsToCart(ProductDetails itemNames) {
        seleniumHelper.clickUsingFluent(productAddToCartBtn(itemNames.getName()));
    }

    public void openShoppingCart() {
        seleniumHelper.clickOnElement(shoppingCartLink);
    }

    public void clickOnMiniCart() {
        waitForSuccessMessage();
        seleniumHelper.clickOnElement(miniCartBtn);
    }

    /**
     * Retrieves the list of items currently in the shopping cart
     *
     * @return List of product names in the cart
     */

    public List<String> getMiniCartItemsPriceText() {
        return seleniumHelper.getTextFromList(cartItemsPriceList);
    }

    public String getMiniCartSubTotalPriceText() {
        return seleniumHelper.getText(subTotalPrice);
    }

    public String getMiniCartEcoTaxPrice() {
        return seleniumHelper.getText(ecoTaxPrice);
    }

    public String getMiniCartVatPrice() {
        return seleniumHelper.getText(vatPrice);
    }

    public String getMiniCartTotalPrice() {
        return seleniumHelper.getText(totalPrice);
    }

    public boolean isCartItemsCountIncreased(int number) {
        return seleniumHelper.isElementDisplayed(getTotalCartItemDropdown(number));
    }

    /**
     * Verifies if a product is displayed in the shopping cart
     *
     * @param productName The name of the product to verify
     * @return true if product is displayed, false otherwise
     */
    public boolean isProductInCart(ProductDetails productName) {
        return seleniumHelper.isElementDisplayed(getProductNameInCart(productName.getName()));
    }

    public void clickOnViewCartBtn() {
        seleniumHelper.clickOnElement(viewCartBtn);
    }

    public void clickOnCheckOutBtn() {
        seleniumHelper.clickOnElement(checkOutBtn);
    }

    // ============ Private Helper Methods ============

    private void waitForSuccessMessage() {
        seleniumHelper.isElementDisplayed(successAlert);
    }

    private void clickMyAccountMenu() {
        seleniumHelper.clickOnElement(myAccountBtn);
    }
}
