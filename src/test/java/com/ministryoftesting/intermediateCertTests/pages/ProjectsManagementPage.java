package com.ministryoftesting.intermediateCertTests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProjectsManagementPage {


    public ProjectsManagementPage(WebDriver driver) {
        PageFactory.initElements(driver, this);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Manage Project")));
    }
}
