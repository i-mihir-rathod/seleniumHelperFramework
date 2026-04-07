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

    public void uploadFile(WebElement element, String fileName, boolean isMultiple) {
        var file = FilePathHelper.getUploadFilePath(fileName);
        String script =
                "var target = arguments[0];" +
                        "var input = document.createElement('input');" +
                        "input.type = 'file';" +
                        (isMultiple ? "input.multiple = true;" : "") +

                        "input.onchange = function() {" +
                        "  var dataTransfer = { files: this.files };" +

                        "  ['dragenter','dragover','drop'].forEach(function(eventName) {" +
                        "    var event = new DragEvent(eventName, { dataTransfer: dataTransfer });" +
                        "    target.dispatchEvent(event);" +
                        "  });" +

                        "  document.body.removeChild(input);" +
                        "};" +

                        "document.body.appendChild(input);" +
                        "return input;";

        var input = (WebElement) js.executeScript(script, element);

        if (input != null) {
            input.sendKeys(file);
        } else {
            throw new RuntimeException("File upload failed - unable to attach file");
        }
    }
}
