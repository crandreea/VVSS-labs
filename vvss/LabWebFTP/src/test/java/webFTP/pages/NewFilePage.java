package webFTP.pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.By;

public class NewFilePage extends PageObject {

    @FindBy(name = "entry")
    private WebElementFacade fileNameInput;

    public WebElementFacade getFileNameInput() {
        return fileNameInput;
    }

    @FindBy(xpath = "//a[@title='Save (accesskey s)']|//*[@title='Save (accesskey s)']|//a/img[contains(@title, 'Save')]/..")
    private WebElementFacade saveButton;

    @FindBy(xpath = "//a[@title='Back (accesskey b)']|//*[@title='Back (accesskey b)']|//a/img[contains(@title, 'Back')]/..|//a[contains(text(), 'Go back')]")
    private WebElementFacade backButton;

    public void enter_file_name(String fileName) {
        fileNameInput.type(fileName);
    }

    public void click_save() {
        saveButton.click();
    }

    public void click_back() {
        try {
            backButton.click();
        } catch (Exception e) {
            evaluateJavascript("arguments[0].click();", backButton);
        }
    }

    public String getStatusMessage() {
        return find(By.tagName("body")).getText();
    }
}
