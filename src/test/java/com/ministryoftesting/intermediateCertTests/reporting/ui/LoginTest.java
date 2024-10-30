package com.ministryoftesting.intermediateCertTests.reporting.ui;

import com.ministryoftesting.api.TimesheetManagerApplication;
import com.ministryoftesting.intermediateCertTests.reporting.Screenshotter;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = TimesheetManagerApplication.class)
@ActiveProfiles("dev")
@ExtendWith(Screenshotter.class)
@DisplayName("Test logging in")
public class LoginTest {

    @Test
    @DisplayName("Test logging in")
    public void testStandardUserCanLogin() {
        DataBuilder dataBuilder = new DataBuilder();
        TimesheetCredential credentials = dataBuilder.getUserCredentials("admin");

        // Set up the WebDriver for Chrome using WebDriverManager.
        WebDriverManager.chromedriver().setup();

        // Initialize a new instance of the ChromeDriver.
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        WebDriver driver = new ChromeDriver(options);
        Screenshotter.setDriver(driver);

        // Open a web page with the given URL in the Chrome browser.
        driver.get("http://localhost:8080");

        // Create an instance of the LoginPage class, which is a custom class.
        LoginPage loginPage = new LoginPage(driver);

        // Perform actions on the login page: sending email, password, and submitting the form.
        loginPage.sendEmail(credentials.getEmail());
        loginPage.sendPassword(credentials.getPassword());
        loginPage.submitForm();

        // Create an instance of the ProjectsPage class.
        ProjectsPage projectsPage = new ProjectsPage(driver);

        // Get the title of the ProjectsPage and store it in the 'title' variable.
        String title = projectsPage.getTitle();

        // Assert that the title of the ProjectsPage is equal to the expected value "Projects".
        assertEquals("Projects", title);

        // Close the Chrome browser window.
        driver.close();

        // Quit the WebDriver, ending the browser session.
        driver.quit();
    }

    @AfterEach
    public void addLogToAllure(TestInfo testInfo) throws IOException {
        String log = new String(Files.readAllBytes(Paths.get("target/screenshots/" + testInfo.getDisplayName() + ".png")));

        Allure.addAttachment(testInfo.getDisplayName(), log);
    }

}
