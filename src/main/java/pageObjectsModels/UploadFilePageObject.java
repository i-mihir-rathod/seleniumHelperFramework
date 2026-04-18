package pageObjectsModels;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class UploadFilePageObject extends BasePageObject{

    // ============ Locators ============
    private final By fileUploadInput = By.xpath("//input[@id = 'file-upload']");
    private final By dragAndDropFileInput = By.id("drag-drop-upload");
    private final By uploadBtn = By.id("file-submit");

    // ============ Constructor ============
    public UploadFilePageObject(WebDriver driver) {
        super(driver);
    }

    // ============ Action Methods ============
    public void setFileUploadInput(String fileName) {
        seleniumHelper.uploadFile(fileUploadInput, fileName);
    }

    public void uploadFileUsingDragAndDrop(String fileName) {
        seleniumHelper.uploadFile(dragAndDropFileInput, fileName);
    }

    public void clickOnUploadBtn() {
        seleniumHelper.clickOnElement(uploadBtn);
    }

}
