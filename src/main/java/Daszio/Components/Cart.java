package Daszio.Components;

import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Daszio.Abstract.Abstract;

public class Cart extends Abstract {
	WebDriver driver;

	public Cart(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//input[@id='add-to-cart-button']")
	WebElement addToCart;

	@FindBy(id = "nav-cart")
	WebElement goToCartPage;

	@FindBy(xpath = "(//span[@class='a-truncate-full a-offscreen'])[1]")
	WebElement cartItem;

	@FindBy(xpath = "//input[@value='Delete']")
	WebElement cartDelete;
	
	@FindBy(xpath = "//h2[contains(text(),'empty.')]")
	WebElement emptyCartText;

	public void addToCart() {
		addToCart.click();
	}

	public String goToCartPage() {

		goToCartPage.click();

		String cartText = cartItem.getAttribute("textContent");
		return cartText;
	}

	public String cartDelete() throws InterruptedException {
		try {
	        // While there are still items in the cart, keep deleting
	        while (true) {
	            // Wait until the delete button is visible and click it
	            visibilityOfElementWait(cartDelete);
	            cartDelete.click();
	            
	            // Wait for a brief moment to ensure the item is removed from the DOM
	            Thread.sleep(2000);

	            // Re-assign the cartDelete element to check if more items are available
	            if (!cartDelete.isDisplayed()) {
	                break; // Exit loop if the delete button is not visible (no more items)
	            }
	        }
	    } catch (NoSuchElementException e) {
	        // This exception is expected when there are no more items to delete
	 System.out.println(e.toString());
	    }
		visibilityOfElementWait(emptyCartText);
		return emptyCartText.getText();
	}
}
