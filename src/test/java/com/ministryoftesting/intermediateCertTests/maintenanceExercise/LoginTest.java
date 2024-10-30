package com.ministryoftesting.intermediateCertTests.maintenanceExercise;

import com.ministryoftesting.api.TimesheetManagerApplication;
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
        // Set up the WebDriver for Chrome using WebDriverManager.
        WebDriverManager.chromedriver().setup();

        // Initialize a new instance of the ChromeDriver.
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        WebDriver driver = new ChromeDriver(options);

        // Open a web page with the given URL in the Chrome browser.
        driver.get("http://localhost:8080");

        driver.findElement(By.name("username")).sendKeys("admin@test.com");
        driver.findElement(By.name("password")).sendKeys("password123");
        driver.findElement(By.cssSelector("button")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".card-title")));

        // Assert that the title of the ProjectsPage is equal to the expected value "Projects".
        String title = driver.findElement(By.cssSelector(".card-title")).getText();
        assertEquals("Projects", title);

        // Close the Chrome browser window.
        driver.close();

        // Quit the WebDriver, ending the browser session.
        driver.quit();
    }
}
