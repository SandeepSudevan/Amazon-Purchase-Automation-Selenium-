package Daszio.AmazonPurchase;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Daszio.Components.Cart;
import Daszio.Components.Filters;
import Daszio.Components.Items;
import Daszio.TestComponents.BaseTest;
import Daszio.TestComponents.Retry;

public class AppTest extends BaseTest{
	
	@Test(dataProvider = "testData1", retryAnalyzer = Retry.class)
	public void addCart(HashMap<String, String> input) throws InterruptedException {
		Items additem = login.phoneSignIn(input.get("phone"), input.get("password"));
		Filters filter = additem.searchBar(input.get("search"));
		Cart cart = filter.shoeBrandCheckboxFilter(input.get("brand"));
		String actualAddedItem = additem.scanItems(input.get("product"));
		System.out.println("ActualItem = " + actualAddedItem);
		Assert.assertTrue(actualAddedItem.contains(input.get("product")));
		cart.childWindow();
		cart.addToCart();
		cart.parentWindow();
		String expectedItem = cart.goToCartPage();
		System.out.println("ExpectedItem = " + expectedItem);
		Assert.assertTrue(expectedItem.contains(input.get("product")));

	}
	@Test(dataProvider = "testData2", retryAnalyzer = Retry.class)
	public void deleteItems(HashMap<String, String> input) throws InterruptedException {
		Items additem = login.phoneSignIn(input.get("phone"), input.get("password"));
		Filters filter = additem.searchBar(input.get("search"));
		Cart cart = filter.shoeBrandCheckboxFilter(input.get("brand"));
		additem.scanItems(input.get("product"));
		cart.childWindow();
		cart.addToCart();
		cart.parentWindow();
		cart.goToCartPage();
		String emptyItemText = cart.cartDelete();
		System.out.println("emptyItemText = " + emptyItemText);
		Assert.assertTrue(emptyItemText.contains("Your Amazon Cart is empty."));
	}
	@Test(dataProvider = "testData1", retryAnalyzer = Retry.class)
	public void storeDataInExcel(HashMap<String, String> input) throws IOException {
		Items additem = login.phoneSignIn(input.get("phone"), input.get("password"));
		Filters filter = additem.searchBar(input.get("search"));
		additem.excelData();
	}
	
	

	@DataProvider
	public Object[][] testData1() throws IOException {
		List<HashMap<String, String>> data = getJSONData(
				System.getProperty("user.dir") + "\\src\\test\\java\\Daszio\\Data\\data.json");
		return new Object[][] { { data.get(0) }, { data.get(1) } };
	}
	
	@DataProvider
	public Object[][] testData2() throws IOException {
		List<HashMap<String, String>> data = getJSONData(
				System.getProperty("user.dir") + "\\src\\test\\java\\Daszio\\Data\\Delete data.json");
		return new Object[][] { { data.get(0) }};
	}
}
