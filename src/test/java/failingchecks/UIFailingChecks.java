package failingchecks;

import com.ministryoftesting.api.TimesheetManagerApplication;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Duration;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = TimesheetManagerApplication.class)
@ActiveProfiles("dev")
// Start of the class definition for UIFailingChecks.
public class UIFailingChecks {

    // Declaration of private variables for WebDriverWait and WebDriver.
    private WebDriverWait wait;
    private WebDriver driver;

    // Method called before each test method is executed.
    @BeforeEach
    public void beforeEach() {
        // Set up the ChromeDriver using WebDriverManager.
        WebDriverManager.chromedriver().setup();

        // Initialize a new ChromeDriver instance.
        driver = new ChromeDriver();

        // Initialize a WebDriverWait instance with a timeout of 5 seconds.
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    // Method called after each test method is executed.
    @AfterEach
    public void afterEach() {
        // Close the current browser window.
        driver.close();

        // Quit the WebDriver and close all associated windows.
        driver.quit();
    }

    // Test method to verify if projects are shown after creation.
    @Test
    public void testProjectsAreShownAfterCreation() {
        // Open the specified URL in the Chrome browser.
        driver.get("http://localhost:8080");

        // Wait until the element with the name "email" is visible.
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));

        // Find the elements necessary to complete the login form
        driver.findElement(By.name("email")).sendKeys("admin@test.com");
        driver.findElement(By.name("password")).sendKeys("password123");
        driver.findElement(By.cssSelector("button")).click();

        // Wait until the element with class name "card-title" is visible.
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("card-title")));

        // Navigate to the specified URL.
        driver.get("http://localhost:8080/#/manage/projects");

        // Find the elements necessary to create a new project
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#name")));
        driver.findElement(By.cssSelector("#name")).sendKeys("Project 2");
        driver.findElement(By.cssSelector(".btn-primary")).click();

        // Navigate to the specified URL.
        driver.get("http://localhost:8080/#/projects");

        // Find all elements with CSS selector ".col-8 .list-group-item".
        List<WebElement> projects = driver.findElements(By.xpath("//a[contains(text(),'Project ')]"));

        // Verify if the number of projects is equal to 2.
        assertEquals(2, projects.size());
    }

    // Test method to verify page updates to the project page after login.
    @Test
    public void testPageUpdatesToProjectPageAfterLogin() {
        // Open the specified URL in the Chrome browser.
        driver.get("http://localhost:8080");

        // Wait until the element with the name "email" is visible.
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));

        // Find the elements necessary to complete the login form
        driver.findElement(By.name("email")).sendKeys("admin@test.com");
        driver.findElement(By.name("password")).sendKeys("password123");
        driver.findElement(By.cssSelector("button")).click();

        // Wait until the element with class name "card-title" is visible.
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("card-title")));

        // Find the element with CSS selector ".card-title" and get its text.
        String title = driver.findElement(By.cssSelector(".card-title")).getText();

        // Verify if the obtained title matches the expected value "Projects".
        assertEquals("Projects", title);
    }

}
