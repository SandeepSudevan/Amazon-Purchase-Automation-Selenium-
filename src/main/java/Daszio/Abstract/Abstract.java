package Daszio.Abstract;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Daszio.Components.Filters;

public class Abstract {
	WebDriver driver;
	public Abstract (WebDriver driver) {
		this.driver=driver;
	}
	
	@FindBy(id="twotabsearchtextbox")
	WebElement search;
	
	public void actions(WebElement moveActionElement) {
		Actions a = new Actions(driver);
		a.moveToElement(moveActionElement).build().perform();
	}
	
	public void visibilityOfElementWait(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	public void visibilityOfAllElementWait(List<WebElement> element) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfAllElements(element));
		
	}
		public void elementToBeClickable(WebElement element) {
			WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
			wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	public Filters searchBar(String searchElement) {
		search.sendKeys(searchElement, Keys.ENTER);
		return new Filters(driver);
	}
	
	public void parentWindow() {
		Set<String> windows = driver.getWindowHandles();
		Iterator<String> it = windows.iterator();
		String parentWindow = it.next();
		driver.switchTo().window(parentWindow);
	}
	
	public void childWindow() {
		Set<String> windows = driver.getWindowHandles();
		Iterator<String> it = windows.iterator();
		it.next();
		String childWindow = it.next();
		driver.switchTo().window(childWindow);
	}
}
