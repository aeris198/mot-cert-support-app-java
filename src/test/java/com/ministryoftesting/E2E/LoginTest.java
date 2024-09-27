package com.ministryoftesting.E2E;

import com.ministryoftesting.api.TimesheetManagerApplication;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
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

    @Test
    public void testPageUpdatesToProjectPageAfterLogin() {
        // Arrange
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        WebDriver driver = new ChromeDriver(options);

        driver.get("http://localhost:8080");

        WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));

        // Act
        driver.findElement(By.name("email")).sendKeys("admin@test.com");
        driver.findElement(By.name("password")).sendKeys("password123");
        driver.findElement(By.cssSelector("button")).click();

        WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".card-title")));

        // Assert
        String title = driver.findElement(By.cssSelector(".card-title")).getText();
        assertEquals("Projects", title);

        driver.close();
        driver.quit();

    }
}
