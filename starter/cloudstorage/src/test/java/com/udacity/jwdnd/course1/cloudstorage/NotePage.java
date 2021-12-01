package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;

public class NotePage {
    @FindBy(id = "navNotesTab")
    private WebElement navNotesTab;

    @FindBy(id = "createNoteButton")
    private WebElement createNoteButton;

    @FindBy(id = "note-title")
    private WebElement noteTitleField;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionField;

    @FindBy(id = "noteModalSubmitButton")
    private WebElement noteModalSubmitButton;

    @FindBy(className = "edit-note-button")
    private WebElement editNoteButton;

    @FindBy(className = "delete-note-button")
    private WebElement deleteNoteButton;

    @FindBy(className = "note-title-list-view")
    private WebElement noteTitleListView;

    @FindBy(className = "note-description-list-view")
    private WebElement noteDescriptionListView;

    public NotePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void createNote(WebDriver driver, String noteTitle, String noteDescription) {
        navNotesTab.click();
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(createNoteButton)).click();
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(noteTitleField)).sendKeys(noteTitle);
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(noteDescriptionField)).sendKeys(noteDescription);
        noteModalSubmitButton.click();
    }

    public Note getFirstNote(WebDriver driver) throws NoSuchElementException {
        navNotesTab.click();
        Note note = null;

        if (noteTitleListView != null && noteDescriptionListView != null && noteTitleListView.getText() != null && noteDescriptionListView.getText() != null) {
            note = new Note();
            String noteTitle = new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(noteTitleListView)).getText();
            String noteDescription = new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(noteDescriptionListView)).getText();
            note.setNoteTitle(noteTitle);
            note.setNoteDescription(noteDescription);
        }

        return note;
    }

    public void editFirstNote(WebDriver driver, String newNoteTitle, String newNoteDescription) {
        navNotesTab.click();
        String n = Keys.chord(Keys.CONTROL, "A");
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(editNoteButton)).click();
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(noteTitleField)).sendKeys(n, newNoteTitle);
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(noteDescriptionField)).sendKeys(n, newNoteDescription);
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(noteModalSubmitButton)).click();
    }

    public void deleteFirstNote(WebDriver driver) {
        navNotesTab.click();
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(deleteNoteButton)).click();
    }

}
