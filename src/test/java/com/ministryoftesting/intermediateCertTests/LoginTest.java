package com.ministryoftesting.intermediateCertTests;

import com.ministryoftesting.api.TimesheetManagerApplication;
import com.ministryoftesting.intermediateCertTests.pages.LoginPage;
import com.ministryoftesting.intermediateCertTests.pages.ProjectsPage;
import com.ministryoftesting.models.DataBuilder;
import com.ministryoftesting.models.TimesheetCredential;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = TimesheetManagerApplication.class)
@ActiveProfiles("dev")
public class LoginTest {

    @Test
    public void testStandardUserCanLogin() {
        DataBuilder dataBuilder = new DataBuilder();
        TimesheetCredential credentials = dataBuilder.getUserCredentials("admin");

        // Set up the WebDriver for Chrome using WebDriverManager.
        WebDriverManager.chromedriver().setup();

        // Initialize a new instance of the ChromeDriver.
        WebDriver driver = new ChromeDriver();

        // Open a web page with the given URL in the Chrome browser.
        driver.get("http://localhost:8080");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.sendEmail(credentials.getEmail());
        loginPage.sendPassword(credentials.getPassword());
        loginPage.submitForm();

        ProjectsPage projectsPage = new ProjectsPage(driver);
        String title = projectsPage.getTitle();

        assertEquals("Projects", title);

        driver.close();
        driver.quit();

    }
}
