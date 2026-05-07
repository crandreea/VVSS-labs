package webFTP.steps.serenity;

import net.thucydides.core.annotations.Step;
import webFTP.pages.AccountPage;
import webFTP.pages.RenamePage;

public class RenameSteps {
    AccountPage accountPage;
    RenamePage renamePage;

    @Step
    public void click_rename() {
        accountPage.click_rename();
    }

    @Step
    public void enter_new_name(String newName) {
        renamePage.enter_new_name(newName);
    }

    @Step
    public void submit_rename() {
        renamePage.click_submit();
    }
}
