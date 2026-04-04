package pageObjectsModels;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class SearchPageObject extends BasePageObject {
    // ============ Locators ============
    private final By noProductFoundMsg = By.xpath("//input[@id = 'button-search']/following-sibling::p");

    private final By listOfSearchProducts = By.xpath("//div[@class = 'caption']//a");

    // ============ Constructors ============
    public SearchPageObject(WebDriver driver) {
        super(driver);
    }

    // ============ Actions ============
    public String getNoProductFoundMessage() {
        return seleniumHelper.getText(noProductFoundMsg);
    }

    public List<String> getSearchResults() {
        return seleniumHelper.getTextFromList(listOfSearchProducts);
    }
}
