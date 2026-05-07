package webFTP.features.file;

import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import net.thucydides.junit.annotations.Qualifier;
import net.thucydides.junit.annotations.UseTestDataFrom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import webFTP.steps.serenity.LoginPageSteps;
import webFTP.steps.serenity.NewFileSteps;

@RunWith(SerenityParameterizedRunner.class)
@UseTestDataFrom("src/test/resources/NewFileInvalidData.csv")
public class NewFileInvalidParameterizedTest {

    @Managed(uniqueSession = true)
    public WebDriver webdriver;

    @Steps
    public LoginPageSteps loginPageSteps;

    @Steps
    public NewFileSteps newFileSteps;

    private String filename;
    private String message;

    @Qualifier
    public String getQualifier() {
        return filename;
    }

    @Test
    public void create_invalid_new_file() {
        webdriver.manage().window().maximize();

        // Login first
        loginPageSteps.go_to_Login_page();
        loginPageSteps.login_steps("localhost", "vvta1", "vvta1");

        // Go to new file and create
        newFileSteps.click_new_file();
        newFileSteps.enter_file_name(filename);
        newFileSteps.save_file();

        // We could also verify the error message here
        // newFileSteps.verify_status_message(message);

        // Back to list
        newFileSteps.back_to_account();
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        if (filename == null)
            this.filename = "";
        else
            this.filename = filename;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
