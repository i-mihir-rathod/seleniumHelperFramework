package utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptHelper {

    protected JavascriptExecutor js;
    protected WaitUtils wait;

    public JavaScriptHelper(WebDriver driver) {
        js = (JavascriptExecutor) driver;
        wait = new WaitUtils(driver);
    }

    private boolean isElementInViewport(WebElement element) {
        return (Boolean) js.executeScript(
                "var rect = arguments[0].getBoundingClientRect();" +
                        "return (" +
                        "rect.top >= 0 && " +
                        "rect.left >= 0 && " +
                        "rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) && " +
                        "rect.right <= (window.innerWidth || document.documentElement.clientWidth));",
                element
        );
    }

    // used this method when element is not in view port
    void scrollToElementCenter(WebElement element) {
        var jsQuery =
                """
                            const elementRect = arguments[0].getBoundingClientRect();
                            const absoluteElementTop = elementRect.top + window.pageYOffset;
                            const middle = absoluteElementTop - (window.innerHeight / 2);
                            window.scrollTo(0, middle);
                        """;
        js.executeScript(jsQuery, element);
    }

    // use this method to check and ensure element is in view port
    void scrollToElementIfNotInView(WebElement element) {
        if (!isElementInViewport(element)) {
            scrollToElementCenter(element);
        }
    }

    public String getTextUsingJS(WebElement element) {
        return (String) js.executeScript("arguments[0].textContent;", element);
    }

    public void uploadFile(WebElement element, String filePath, boolean isMultiple) {
        var jsQuery =
                """
                var target = arguments[0],
                offsetX = arguments[1],
                offsetY = arguments[2],
                document = target.ownerDocument || document,
                window = document.defaultView || window;
        
                var input = document.createElement('INPUT');
                input.type = 'file';
                """
                        + (isMultiple ? "input.multiple = true;" : "")
                        + """
        input.onchange = function () {
         var rect = target.getBoundingClientRect(),
          x = rect.left + (offsetX || (rect.width >> 1)),
          y = rect.top + (offsetY || (rect.height >> 1)),
          dataTransfer = { files: this.files };

        ['dragenter', 'dragover', 'drop'].forEach(function (name) {
          var evt = document.createEvent('MouseEvent');
          evt.initMouseEvent(name, !0, !0, window, 0, 0, 0, x, y, !1, !1, !1, !1, 0, null);
          evt.dataTransfer = dataTransfer;
          target.dispatchEvent(evt);
        });

        setTimeout(function () { document.body.removeChild(input); }, 25);
      };
      document.body.appendChild(input);
      return input;
      """;

        var fileInput = (WebElement) js.executeScript(jsQuery, element, 0, 0);
        if (fileInput != null) fileInput.sendKeys(filePath);
        else throw new RuntimeException("File input button not available for upload file");
    }

    public void clickOnElementUsingJs(WebElement element) {
        js.executeScript("arguments[0].click();", element);
    }
}
