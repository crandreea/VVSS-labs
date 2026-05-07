package webFTP.steps.serenity;

import net.thucydides.core.annotations.Step;
import webFTP.pages.AccountPage;
import webFTP.pages.NewFilePage;
import static org.junit.Assert.assertTrue;

public class NewFileSteps {
    AccountPage accountPage;
    NewFilePage newFilePage;

    @Step
    public void click_new_file() {
        accountPage.click_new_file();
    }

    @Step
    public void enter_file_name(String fileName) {
        newFilePage.getFileNameInput().waitUntilEnabled();
        newFilePage.enter_file_name(fileName);
    }

    @Step
    public void save_file() {
        newFilePage.click_save();
    }

    @Step
    public void back_to_account() {
        newFilePage.click_back();
    }

    @Step
    public void verify_status_message(String expectedMessage) {
        String actualMessage = newFilePage.getStatusMessage();
        assertTrue("Expected message to contain: " + expectedMessage + ", but was: " + actualMessage,
                   actualMessage.contains(expectedMessage));
    }
}
