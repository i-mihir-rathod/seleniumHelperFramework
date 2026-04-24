import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjectsModels.UploadFilePageObject;

public class uploadFile extends Base{

    @Test
    public void verifyToUploadFile() {
        // initialize page objects
        var uploadFilePage =  new UploadFilePageObject(driver);

        // navigate to upload file page
        navigateToUploadFilePage();
        String fileName = "Screenshot.png";

        // upload file
        uploadFilePage.setFileUploadInput(fileName);
        uploadFilePage.clickOnUploadBtn();

        // verify status text
        Assert.assertEquals(uploadFilePage.getUploadFileStatusText(), "File Uploaded!");

        // verify uploaded file name
        Assert.assertEquals(uploadFilePage.getUploadedFileName(), fileName);
    }

    @Test
    public void uploadFileUsingDragAndDrop() {
        // initialize page objects
        var uploadFilePage =  new UploadFilePageObject(driver);

        // navigate to upload file page
        navigateToUploadFilePage();

        // upload file
        String fileName = "Screenshot.png";
        uploadFilePage.uploadFileUsingDragAndDrop(fileName);
//        uploadFilePage.clickOnUploadBtn();
//
//        // verify status text
//        Assert.assertEquals(uploadFilePage.getUploadFileStatusText(), "File Uploaded!");
//
//        // verify uploaded file name
//        Assert.assertEquals(uploadFilePage.getUploadedFileName(), fileName);
    }

    // ============ Private Helper Methods ============
    private void navigateToUploadFilePage(){
        driver.get("https://the-internet.herokuapp.com/upload");
    }
}
