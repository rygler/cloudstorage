package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    @FindBy(id = "input-username")
    private WebElement usernameField;

    @FindBy(id = "input-password")
    private WebElement passwordField;

    @FindBy(id = "submit-button")
    private WebElement submitButton;

    public LoginPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    public void loginUser(String username, String password) {
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        submitButton.click();
    }
}
