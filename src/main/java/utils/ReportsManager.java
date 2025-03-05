package utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ReportsManager {

	private static ExtentReports extent;
	// Create a new ExtentTest instance per test case to avoid overwriting
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();

	public static void initReport() {
		if (extent == null) {
			String reportPath = "extent-reports/Flipkart_Test_Report_" + getCurrentTime();
			ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);

			spark.config().setTheme(Theme.DARK);
			spark.config().setDocumentTitle("Flipkart KDTest Report");
			spark.config().setReportName("Automation Test Report");

			extent = new ExtentReports();
			extent.attachReporter(spark);
			extent.setSystemInfo("Environment", "QA");
			extent.setSystemInfo("Tester", "Yogesh");
		}
	}

	// Create a new test for each case
	public static void createTest(String testName ,String browser) {
		test.set(extent.createTest(testName + "on" + browser));
	}

	public static void captureScreenshot(WebDriver driver, String status, String browser) {
		String threadName = Thread.currentThread().getName();
		File screenshotFileDir = new File("extent-reports/screenshots/");

		if (!screenshotFileDir.exists()) {
			boolean dirCreated = screenshotFileDir.mkdir();
			if (dirCreated) {
				System.out.println("Screenshot directory created at: " + screenshotFileDir.getAbsolutePath());
			} else {
				System.out.println("Failed to create screenshot directory");
			}
		}

		File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		// Updated path to ensure that screenshots go into the correct folder
		String screenshotPath = "C:\\Users\\yoges\\remote_workspace2\\FlipkartExcel\\extent-reports\\screenshots\\" 
				+ status + "_" + browser + "_" + threadName + "_" + getCurrentTime() + ".png";

		try {
			FileHandler.copy(screenshotFile, new File(screenshotPath));

			 if (status.equalsIgnoreCase("pass")) {
		            test.get().pass("Test passed. Screenshot:", 
		                MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
		        } else if (status.equalsIgnoreCase("fail")) {
		            test.get().fail("Test failed. Screenshot:", 
		                MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
		        } else {
		            test.get().info("Screenshot:", 
		                MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
		        }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void flushReport() {
		if (extent != null) {
			extent.flush();
			System.out.println("Extent report flushed.");
		}
	}

	private static String getCurrentTime() {
		return new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	}
}
