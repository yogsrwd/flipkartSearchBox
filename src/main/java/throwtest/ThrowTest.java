package throwtest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.opentelemetry.exporter.logging.SystemOutLogRecordExporter;

public class ThrowTest   {
	
	
	
	
	@Test
	public void searchBoxTest1() {
		
		
		WebDriverManager.firefoxdriver().setup();
		
		WebDriver driver  = new FirefoxDriver();
		
		driver.get("https://www.flipkart.com/search?q=lenovo%20vibe%20x3&otracker=search&otracker1=search&marketplace=FLIPKART&as-show=off&as=off");
		
		
		
	System.out.println(driver.getTitle());
		
		
		
		
		
		
				
		
	
		
		
	}

	
}
