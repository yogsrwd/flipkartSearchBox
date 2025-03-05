package runner;

import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;
import base.Base;
import pages.HomePage;

public class SimpleTest extends Base {
    
    @Test
    public void simpleSearchTest() throws InterruptedException {
        HomePage homePage = new HomePage(driver);
        homePage.searchBox().sendKeys("test", Keys.ENTER);
        Thread.sleep(2000); // Wait for search results
        Assert.assertTrue(driver.getTitle().contains("test"), "Title does not contain search term");
    }
}
