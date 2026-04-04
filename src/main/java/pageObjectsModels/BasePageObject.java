package pageObjectsModels;

import org.openqa.selenium.WebDriver;
import utils.JavascriptHelper;
import utils.SeleniumHelper;
import utils.WaitUtils;

public class BasePageObject {
    protected WaitUtils wait;
    protected JavascriptHelper js;
    protected SeleniumHelper seleniumHelper;

    // ============ Constructor  ============
    public BasePageObject(WebDriver driver) {
        this.wait = new WaitUtils(driver);
        this.js = new JavascriptHelper(driver);
        this.seleniumHelper = new SeleniumHelper(driver);
    }
}
