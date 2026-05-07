package webFTP.pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.pages.PageObject;

public class CopyPage extends PageObject {

    @FindBy(name="headerDirectory")
    private WebElementFacade targetDirInput;

    @FindBy(xpath="//a[@title='Submit (accesskey v)']|//*[@title='Submit (accesskey v)']|//a/img[contains(@title, 'Submit')]/..")
    private WebElementFacade submitButton;

    public void enter_target_directory(String dir) {
        targetDirInput.clear();
        targetDirInput.type(dir);
    }

    public void click_submit() {
        submitButton.click();
    }
}
