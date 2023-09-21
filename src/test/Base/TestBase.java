package Base;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.github.romankh3.image.comparison.ImageComparison;
import com.github.romankh3.image.comparison.ImageComparisonUtil;
import com.github.romankh3.image.comparison.model.ImageComparisonResult;


public class TestBase {

	protected static WebDriver driver;
	public static Properties envConfig;
	public static String path;
	WebDriverWait wait;


	@BeforeSuite
	public void suiteSetup() throws Exception {

		InputStream configFile = new FileInputStream(System.getProperty("user.dir")
				+ "\\src\\test\\PropertyFiles\\" + "config.properties");
		envConfig = new Properties();
		envConfig.load(configFile);

		String BROWSER = envConfig.getProperty("browser");
		path = envConfig.getProperty("path");
		System.out.println(path);
		System.out.println(BROWSER);
		if (BROWSER.equals("Firefox")) {
			driver = new FirefoxDriver();
		} else if (BROWSER.equals("Chrome")) {

			driver = new ChromeDriver();
		} else if (BROWSER.equals("IE")) {
			driver = new InternetExplorerDriver();
		} else {
			throw new RuntimeException("Browser type unsupported");
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	}

	@BeforeMethod()
	public void loadBaseUrl(Method method) {
		driver.get(envConfig.getProperty("baseUrl"));
	}
	
	public void takeScreennshot(String paths) throws IOException {
		System.out.println(path);
		TakesScreenshot scrShot = ((TakesScreenshot) driver);
		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
		File DestFile = new File(paths);
		FileUtils.copyFile(SrcFile, DestFile);
		System.out.println("screenshto done");
	}

	public void imageDiff(String expectedPath, String actualPath,String filename)
	{
		BufferedImage expectedImage = ImageComparisonUtil
				.readImageFromResources(expectedPath);
		BufferedImage actualImage = ImageComparisonUtil
				.readImageFromResources(actualPath);
		ImageComparisonResult imageComparisonResult = new ImageComparison(expectedImage, actualImage).compareImages();
		System.out.println(imageComparisonResult.getDifferencePercent());
		if (imageComparisonResult.getDifferencePercent() > 0) {
			File resultDestination = new File("errorScreenshots\\" + filename+"_result.png");
			ImageComparisonUtil.saveImage(resultDestination, imageComparisonResult.getResult());
		} else {
			System.out.println("Both images are similar.");
		}
		//ImageComparisonUtil.saveImage(resultDestination, imageComparisonResult.getResult());

	}
	@AfterSuite
	public void tearDown() {
		driver.quit();
	}

}
