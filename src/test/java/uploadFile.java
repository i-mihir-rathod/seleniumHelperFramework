import org.testng.annotations.Test;
import pageObjectsModels.UploadFilePageObject;

public class uploadFile extends Base{

    @Test
    public void verifyToUploadFile() {
        // initialize page objects
        var uploadFilePage =  new UploadFilePageObject(driver);

        // navigate to upload file page
        navigateToUploadFilePage();

        // upload file
        uploadFilePage.setFileUploadInput("Screenshot.png");
        uploadFilePage.clickOnUploadBtn();
    }

    @Test
    public void uploadFileUsingDragAndDrop() {
        // initialize page objects
        var uploadFilePage =  new UploadFilePageObject(driver);

        // navigate to upload file page
        navigateToUploadFilePage();

        // upload file
        uploadFilePage.uploadFileUsingDragAndDrop("Screenshot.png");
//        uploadFilePage.clickOnUploadBtn();
    }

    // ============ Private Helper Methods ============
    private void navigateToUploadFilePage(){
        driver.get("https://the-internet.herokuapp.com/upload");
    }
}
