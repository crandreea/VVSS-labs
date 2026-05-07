package webFTP.steps.serenity;

import net.thucydides.core.annotations.Step;
import webFTP.pages.AccountPage;
import webFTP.pages.CopyPage;

public class CopySteps {
    AccountPage accountPage;
    CopyPage copyPage;

    @Step
    public void click_copy() {
        accountPage.click_copy();
    }

    @Step
    public void enter_target_directory(String dir) {
        copyPage.enter_target_directory(dir);
    }

    @Step
    public void submit_copy() {
        copyPage.click_submit();
    }
}
