package webFTP.pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.pages.PageObject;

public class RenamePage extends PageObject {

    @FindBy(name="newNames[1]")
    private WebElementFacade newNameInput;

    @FindBy(xpath="//a[@title='Submit (accesskey v)']|//*[@title='Submit (accesskey v)']|//a/img[contains(@title, 'Submit')]/..")
    private WebElementFacade submitButton;

    public void enter_new_name(String newName) {
        newNameInput.clear();
        newNameInput.type(newName);
    }

    public void click_submit() {
        submitButton.click();
    }
}
