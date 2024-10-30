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

public class LoginPage {

    @FindBy(how = How.NAME, using = "email")
    private WebElement email;

    @FindBy(how = How.NAME, using = "password")
    private WebElement password;

    @FindBy(how = How.CSS, using = "button")
    private WebElement submit;

    private WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        //below line needed because of the how being used in the FindBys above
        PageFactory.initElements(driver, this);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button")));
    }

    public void sendEmail(String value) {
        email.sendKeys(value);
    }

    public void sendPassword(String value) {
        password.sendKeys(value);
    }

    public void submitForm() {
        submit.click();
    }

}
