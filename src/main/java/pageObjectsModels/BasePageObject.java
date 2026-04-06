package pageObjectsModels;

import org.openqa.selenium.WebDriver;
import utils.SeleniumHelper;

public class BasePageObject {
    protected SeleniumHelper seleniumHelper;

    // ============ Constructor  ============
    public BasePageObject(WebDriver driver) {
        this.seleniumHelper = new SeleniumHelper(driver);
    }
}
