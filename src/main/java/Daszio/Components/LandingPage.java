package Daszio.Components;

import org.openqa.selenium.WebDriver;

import Daszio.Abstract.Abstract;



public class LandingPage extends Abstract{
	
	WebDriver driver;
	public LandingPage (WebDriver driver) {
		super(driver);
		this.driver=driver;
	}
	
	public ProfileLogIn goToWebsite() {
		driver.get("https://www.amazon.in/");
		return new ProfileLogIn(driver);
	}

}
