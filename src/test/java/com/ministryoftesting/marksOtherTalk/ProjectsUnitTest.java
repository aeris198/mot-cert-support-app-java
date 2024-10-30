package com.ministryoftesting.marksOtherTalk;

import com.ministryoftesting.intermediateCertTests.pages.LoginPage;
import com.ministryoftesting.intermediateCertTests.pages.ProjectsManagementPage;
import com.ministryoftesting.intermediateCertTests.pages.ProjectsPage;
import com.ministryoftesting.models.DataBuilder;
import com.ministryoftesting.models.TimesheetCredential;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class ProjectsUnitTest {

    @Test
    @DisplayName("Test an admin can create a new project")
    public void testAddingANewProject() throws InterruptedException {
        DataBuilder dataBuilder = new DataBuilder();
        TimesheetCredential credentials = dataBuilder.getUserCredentials("admin");

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless");
        WebDriver driver = new ChromeDriver(options);

        driver.get("http://localhost:8080");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.sendEmail(credentials.getEmail());
        loginPage.sendPassword(credentials.getPassword());
        loginPage.submitForm();

        ProjectsPage projectsPage = new ProjectsPage(driver);
        projectsPage.clickManageProject();

        ProjectsManagementPage projectsManagementPage = new ProjectsManagementPage(driver);
        //having difficulty running this so abandoned for now
    }
}
