package com.ministryoftesting.intermediateCertTests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProjectsPage {

    @FindBy(how = How.CSS, using = ".card-title")
    private WebElement title;

    @FindBy(how = How.LINK_TEXT, using = "Manage Project")
    private WebElement manageProjectLink;

    public ProjectsPage(WebDriver driver) {
        PageFactory.initElements(driver, this);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".card-title")));
    }

    public String getTitle() {
        return title.getText();
    }

    public void clickManageProject() {
        manageProjectLink.click();
    }
}
