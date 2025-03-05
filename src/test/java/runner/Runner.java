package runner;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import base.Base;
import pages.HomePage;
import utils.ReportsManager;

import java.time.Duration;
import java.util.List;

public class Runner extends Base {

    @Parameters("browser")
    @Test
    public void searchBoxTest(String browser) {
        String status = "pass";
        HomePage homePage = null;

        try {
            ReportsManager.createTest("Search Box Test :- ", browser);
            homePage = new HomePage(driver);
            String searchTerm = "Galaxy S25";  

    
            homePage.searchBox().sendKeys(searchTerm, Keys.ENTER);
            
            Thread.sleep(2000);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            List<WebElement> allOccurrences = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.xpath("//*[contains(text(), '" + searchTerm + "')]")
            ));

            if (allOccurrences.size() <= 2) {
                status = "fail";
                Thread.sleep(1000);
                Assert.fail("No products found - only found " + allOccurrences.size() + " occurrences");
            }

            ReportsManager.captureScreenshot(driver, status, browser);

        } catch (Exception e) {
            status = "fail";
            System.out.println("Test failed: " + e.getMessage());
            try {
                Thread.sleep(1000);
                ReportsManager.captureScreenshot(driver, status, browser);
            } catch (Exception se) {
                System.out.println("Screenshot failed: " + se.getMessage());
            }
            Assert.fail("Test failed: " + e.getMessage());
        }
    }
}
