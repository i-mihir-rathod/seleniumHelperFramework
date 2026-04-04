package pageObjectsModels;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class ShoppingCartPageObject extends BasePageObject {

    // ============ Locators ============
    private final By emptyCartMessage = By.xpath("//div[@id = 'content']/p");
    private final By continueBtn = By.xpath("//a[text() = 'Continue']");
    private final By cartItems = By.xpath("//table[contains(@class, 'table-bordered')]//td[@class = 'text-left']/a");

    // ============ Constructor ============
    public ShoppingCartPageObject(WebDriver driver) {
        super(driver);
    }

    // ============ Actions ============

    public String getEmptyCartMessage() {
        return seleniumHelper.getText(emptyCartMessage);
    }

    public List<String> getCartItems() {
        return seleniumHelper.getTextFromList(cartItems);
    }

    public void clickContinueButton() {
        seleniumHelper.clickOnElement(continueBtn);
    }
}
