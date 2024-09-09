package Daszio.Components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Daszio.Abstract.Abstract;

public class Filters extends Abstract{
	WebDriver driver;
public Filters(WebDriver driver) {
	super(driver);
	this.driver=driver;
	PageFactory.initElements(driver, this);
}

	
	public Cart shoeBrandCheckboxFilter(String name) {
		WebElement brandNameshoe = driver.findElement(By.xpath("//span[@class='a-size-base a-color-base'][normalize-space()='"+name+"']"));
		brandNameshoe.click();
		return new Cart(driver);
	}
	

}
