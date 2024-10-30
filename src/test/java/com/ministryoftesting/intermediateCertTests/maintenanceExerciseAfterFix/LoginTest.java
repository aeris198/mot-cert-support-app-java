package com.ministryoftesting.intermediateCertTests.maintenanceExerciseAfterFix;

import com.ministryoftesting.api.TimesheetManagerApplication;
import com.ministryoftesting.intermediateCertTests.pages.LoginPage;
import com.ministryoftesting.intermediateCertTests.pages.ProjectsPage;
import com.ministryoftesting.models.DataBuilder;
import com.ministryoftesting.models.TimesheetCredential;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = TimesheetManagerApplication.class)
@ActiveProfiles("dev")
public class LoginTest {

    // This is a method in the class, annotated with @Test, indicating it's a test case.
    @Test
    public void testPageUpdatesToProjectPageAfterLogin() {
        // Using DataBuilder to get credentials
        DataBuilder dataBuilder = new DataBuilder();
        TimesheetCredential credentials = dataBuilder.getUserCredentials("admin");

        // Set up the WebDriver for Chrome using WebDriverManager.
        WebDriverManager.chromedriver().setup();

        // Initialize a new instance of the ChromeDriver.
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        WebDriver driver = new ChromeDriver(options);

        // Open a web page with the given URL in the Chrome browser.
        driver.get("http://localhost:8080");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.sendEmail(credentials.getEmail());
        loginPage.sendPassword(credentials.getPassword());
        loginPage.submitForm();

        ProjectsPage projectsPage = new ProjectsPage(driver);
        String title = projectsPage.getTitle();

        // Assert that the title of the ProjectsPage is equal to the expected value "Projects".
        assertEquals("Projects", title);

        // Close the Chrome browser window.
        driver.close();

        // Quit the WebDriver, ending the browser session.
        driver.quit();
    }
}
