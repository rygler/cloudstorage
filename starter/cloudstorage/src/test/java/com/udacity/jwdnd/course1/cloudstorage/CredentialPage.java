package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.ListCredential;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CredentialPage {

    @FindBy(id = "navCredentialsTab")
    private WebElement navCredentialsTab;

    @FindBy(id = "addCredentialButton")
    private WebElement addCredentialsButton;

    @FindBy(className = "delete-credential-button")
    private WebElement deleteCredentialButton;

    @FindBy(className = "edit-credential-button")
    private WebElement editCredentialButton;

    @FindBy(className = "credential-url-list-view")
    private WebElement urlListView;

    @FindBy(className = "credential-username-list-view")
    private WebElement usernameListView;

    @FindBy(className = "credential-encrypted-password-list-view")
    private WebElement encryptedPasswordListView;

    @FindBy(id = "credential-url")
    private WebElement urlField;

    @FindBy(id = "credential-username")
    private WebElement usernameField;

    @FindBy(id = "credential-password")
    private WebElement decryptedPasswordField;

    @FindBy(id = "credential-modal-submit-button")
    private WebElement submitButton;

    @FindBy(id = "credential-modal-close-button")
    private WebElement closeButton;


    public CredentialPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void createCredential(WebDriver driver, String url, String username, String password) {
        navCredentialsTab.click();
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(addCredentialsButton)).click();
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(urlField)).sendKeys(url);
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(usernameField)).sendKeys(username);
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(decryptedPasswordField)).sendKeys(password);
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(submitButton)).click();
    }

    public void editFirstCredential(WebDriver driver, String url, String username, String password) {
        navCredentialsTab.click();
        String n = Keys.chord(Keys.CONTROL, "A");
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(editCredentialButton)).click();
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(urlField)).sendKeys(n, url);
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(usernameField)).sendKeys(n, username);
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(decryptedPasswordField)).sendKeys(n, password);
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(submitButton)).click();
    }

    public void deleteCredential(WebDriver driver) {
        navCredentialsTab.click();
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(deleteCredentialButton)).click();
    }

    public ListCredential getFirstCredential(WebDriver driver) {
        ListCredential listCredential = null;
        navCredentialsTab.click();

        if (urlListView != null && usernameListView != null && encryptedPasswordListView != null && urlListView.getText() != null && usernameListView.getText() != null && encryptedPasswordListView.getText() != null) {
            listCredential = new ListCredential();
            String url = new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(urlListView)).getText();
            String username = new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(usernameListView)).getText();
            String encryptedPassword = new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(encryptedPasswordListView)).getText();
            new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(editCredentialButton)).click();
            String decryptedPassword = new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(decryptedPasswordField)).getAttribute("value");
            listCredential.setUrl(url);
            listCredential.setUsername(username);
            listCredential.setPassword(decryptedPassword);
            listCredential.setEncryptedPassword(encryptedPassword);
            new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(closeButton)).click();
        }

        return listCredential;
    }
}
