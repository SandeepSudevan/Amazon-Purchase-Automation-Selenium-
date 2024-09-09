package Daszio.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import Daszio.Components.Cart;
import Daszio.Components.LandingPage;
import Daszio.Components.ProfileLogIn;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	public WebDriver driver;
	public LandingPage landingPage;
	public ProfileLogIn login;

	public WebDriver initializeBrowser() throws IOException {
		Properties prop = new Properties();
		FileInputStream fir = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\main\\java\\Daszio\\properties\\GlobalData.properties");
		prop.load(fir);

		String browserCMD = System.getProperty("browser");
		String browserprop = prop.getProperty("browser");

		if (browserCMD != null) {
			if (browserCMD.contains("chrome")) {
				WebDriverManager.chromedriver().setup();
				ChromeOptions opt = new ChromeOptions();
				if (browserCMD.contains("headless")) {
					opt.addArguments("headless");
				}
				driver = new ChromeDriver(opt);
			}
		} else if (browserprop != null) {
			if (browserprop.contains("chrome")) {
				WebDriverManager.chromedriver().setup();
				ChromeOptions opt = new ChromeOptions();
				if (browserprop.contains("headless")) {
					opt.addArguments("headless");
				}
				driver = new ChromeDriver(opt);
			}

		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		return driver;

	}

	@BeforeMethod(alwaysRun = true)
	public void openBrowser() throws IOException {
		driver = initializeBrowser();
		landingPage = new LandingPage(driver);
		login = landingPage.goToWebsite();
	}

	@AfterMethod(alwaysRun = true)
	public void closeBrowser() {
		driver.quit();
	}

	public List<HashMap<String, String>> getJSONData(String fileName) throws IOException {
		String readJSONFile = FileUtils.readFileToString(new File(fileName), StandardCharsets.UTF_8);
		ObjectMapper map = new ObjectMapper();
		List<HashMap<String, String>> data = map.readValue(readJSONFile,
				new TypeReference<List<HashMap<String, String>>>() {
				});
		return data;
	}
	
	public String screenshot(String testcaseName, WebDriver driver) throws IOException {
		TakesScreenshot ts = (TakesScreenshot)driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File file = new File(System.getProperty("user.dir")+"//reports//"+testcaseName+".png");
		FileUtils.copyFile(source, file);
		return System.getProperty("user.dir")+"//reports//"+testcaseName+".png";
	}
}