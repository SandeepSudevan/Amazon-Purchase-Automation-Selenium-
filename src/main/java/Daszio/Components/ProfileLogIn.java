package Daszio.Components;


import java.util.List;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Daszio.Abstract.Abstract;

public class ProfileLogIn extends Abstract {
	WebDriver driver;

	public ProfileLogIn(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "nav-link-accountList")
	WebElement AccandList;
	@FindBy(xpath = "(//a[@rel='nofollow'])[1]")
	WebElement signIn;
	@FindBy(css = "input[type='email']")
	WebElement phoneNo;
	@FindBy(css = "input[type='submit']")
	WebElement phoneNoSubmit;
	@FindBy(css = "input[type='password']")
	WebElement passwordtext;
	@FindBy(id = "signInSubmit")
	WebElement login;
	@FindBy(xpath = "(//div[@role='alert'])")
	List<WebElement> error;
;
	

	public Items phoneSignIn(String phoneno, String password) {
		actions(AccandList);
		signIn.click();
		phoneNo.sendKeys(phoneno);
		phoneNoSubmit.click();
		try {
			passwordtext.sendKeys(password);
		} catch (StaleElementReferenceException e) {
			passwordtext.sendKeys(password);
		}
		login.click();
		return new Items(driver);
	}
	
	public void enterPhoneNo(String phoneno) {
		actions(AccandList);
		signIn.click();
		phoneNo.sendKeys(phoneno);
		phoneNoSubmit.click();
		
	}
	
	public void enterPassword(String password) {
		try {
			passwordtext.sendKeys(password);
		} catch (StaleElementReferenceException e) {
			passwordtext.sendKeys(password);
		}
		login.click();
	}
	
	public String errorMessage() throws InterruptedException {
		String errorMessage = null;
		Thread.sleep(3000);
		if(error.get(1).isDisplayed()) {
			 errorMessage = error.get(1).getText();
		}else if (error.get(0).isDisplayed()) {
			errorMessage = error.get(0).getText();
		}
		return errorMessage;
	}

}
