package com.ministryoftesting.intermediateCertTests.flakeyTestsExercise.e2e;

import com.ministryoftesting.api.TimesheetManagerApplication;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v94.network.Network;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Duration;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = TimesheetManagerApplication.class)
@ActiveProfiles("dev")
public class LoginTest {

    @Test
    @DisplayName("Test that a standard user can login")
    public void testStandardUserCanLogin() {
        DataBuilder dataBuilder = new DataBuilder();
        TimesheetCredential credentials = dataBuilder.getUserCredentials("admin");

        // Set up the WebDriver for Chrome using WebDriverManager.
        WebDriverManager.chromedriver().setup();

        // Initialize a new instance of the ChromeDriver that emulates a slow network
        WebDriver driver = new ChromeDriver();
//        DevTools devTools = ((ChromeDriver) driver).getDevTools();
//        devTools.createSession();
//        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
//        devTools.send(Network.emulateNetworkConditions(false, 1000, 0, 0, Optional.empty()));

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



}
