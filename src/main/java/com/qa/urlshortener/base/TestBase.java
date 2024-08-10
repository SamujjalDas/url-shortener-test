package com.qa.urlshortener.base;

import com.qa.urlshortener.util.TestUtil;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestBase {

	public static Properties prop;
	public static String path = System.getProperty("user.dir");
	public static WebDriver driver;

	public TestBase() {

		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream(
					path + "\\src\\main\\java\\com\\qa\\urlshortener\\config\\config.properties");
			prop.load(ip);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static WebDriver webDriverInitialization(String browserName) {
		//String browserName = prop.getProperty("browser");

		if(browserName.equals("chrome")) {
			WebDriverManager.chromedriver().clearDriverCache().setup();
			driver = new ChromeDriver();
		}
		else if(browserName.equals("firefox")) {
			WebDriverManager.firefoxdriver().clearDriverCache().setup();
			driver = new FirefoxDriver();
		}
		else if(browserName.equals("edge")) {
			WebDriverManager.edgedriver().clearDriverCache().setup();
			driver = new EdgeDriver();
		}
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);
		driver.get(prop.getProperty("url"));
		return driver;
	}

	public static void tearDown(WebDriver driver) {
		driver.quit();
	}

	public void captureScreenshot(String methodName) {
		long timestamp = System.currentTimeMillis();

		try {
			File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(file, new File("C:\\SD\\Projects\\url-shortener-test\\src\\main\\resources\\"+methodName+timestamp+".jpg"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
