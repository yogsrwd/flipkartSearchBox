package base;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import io.github.bonigarcia.wdm.WebDriverManager;
import utils.ReportsManager;

public class Base {
	
	public WebDriver driver;
	
	 @BeforeSuite
	    public void beforeSuite() {
		 ReportsManager.initReport(); // Initialize the report once before the suite runs
	    }
	 @AfterSuite
	 public void afterSuite() {
		 
		 ReportsManager.flushReport();
	 }
	
	@Parameters("browser")
	@BeforeMethod
	public void setup(String browser , ITestContext context) {
		
		if(browser.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		}else if (browser.equalsIgnoreCase("firefox")) {
			
			WebDriverManager.firefoxdriver().setup();
			
			driver  = new FirefoxDriver();
		}else if (browser.equalsIgnoreCase("edge")) {
			
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}
			
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://www.flipkart.com/");
		
		
		
		context.setAttribute("driver", driver);
		
	
		
		
	}
	
	@AfterMethod
	public void tearDown() {
	    try {
	        if (driver != null) {
	            driver.quit();
	            driver = null; // Optionally set driver to null after quitting
	        }
	    } catch (Exception e) {
	        System.err.println("Error while closing the driver: " + e.getMessage());
	    }
	}


}
