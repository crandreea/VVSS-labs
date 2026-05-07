package webFTP.features.scenario;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import webFTP.steps.serenity.AccountPageSteps;
import webFTP.steps.serenity.CopySteps;
import webFTP.steps.serenity.DeleteDirectoryPageSteps;
import webFTP.steps.serenity.LoginPageSteps;
import webFTP.steps.serenity.LogoutPageSteps;
import webFTP.steps.serenity.NewFileSteps;
import webFTP.steps.serenity.RenameSteps;
import webFTP.steps.serenity.NewDirectoryPageSteps;

import static org.junit.Assert.assertTrue;

@RunWith(SerenityRunner.class)
public class FileScenarioTest {

    @Managed(uniqueSession = true)
    public WebDriver webdriver;

    @Steps
    public LoginPageSteps loginPageSteps;

    @Steps
    public AccountPageSteps accountPageSteps;

    @Steps
    public NewFileSteps newFileSteps;

    @Steps
    public RenameSteps renameSteps;

    @Steps
    public CopySteps copySteps;

    @Steps
    public DeleteDirectoryPageSteps deleteSteps;

    @Steps
    public LogoutPageSteps logoutPageSteps;

    @Steps
    public NewDirectoryPageSteps newDirectoryPageSteps;

    @Test
    public void file_management_scenario() {
        webdriver.manage().window().maximize();
        
        // 1. Login
        loginPageSteps.go_to_Login_page();
        loginPageSteps.login_steps("localhost", "vvta1", "vvta1");

        String baseFileName = "scenario_test_file.txt";
        String renamedFileName = "scenario_renamed_file.txt";
        
        // 2. New File
        newFileSteps.click_new_file();
        newFileSteps.enter_file_name(baseFileName);
        newFileSteps.save_file();
        newFileSteps.back_to_account();

        // Check file exists
        accountPageSteps.should_be_able_to_see_new_directory(baseFileName);

        // 3. Rename File
        accountPageSteps.select_directory_to_delete(baseFileName); // Checkbox
        renameSteps.click_rename();
        renameSteps.enter_new_name(renamedFileName);
        renameSteps.submit_rename();
        // back? Submit rename returns to list or shows back? Let's go back just in case, or just click back button.
        deleteSteps.back();

        // Verify rename
        accountPageSteps.should_be_able_to_see_new_directory(renamedFileName);

        // 4. Copy File
        accountPageSteps.select_directory_to_delete(renamedFileName);
        copySteps.click_copy();
        copySteps.enter_target_directory("/home/vvta1/backup");
        copySteps.submit_copy();
        deleteSteps.back();

        // After copy, the file should still be present in the original directory
        accountPageSteps.should_be_able_to_see_new_directory(renamedFileName);

        // 5. Delete File
        accountPageSteps.select_directory_to_delete(renamedFileName);
        accountPageSteps.delete_selected_directory();
        deleteSteps.delete_directory();
        deleteSteps.back();

        // Verify delete
        accountPageSteps.should_not_be_able_to_see_new_directory(renamedFileName);

        // Logout
        accountPageSteps.logout();
    }
}
