package Daszio.AmazonPurchase;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Daszio.TestComponents.BaseTest;
import Daszio.TestComponents.Retry;

public class ErrorValidation extends BaseTest {

	@Test(dataProvider ="testData_1", retryAnalyzer=Retry.class)
	public void wrongPhoneNo(HashMap<String,String> input) throws InterruptedException {
		login.enterPhoneNo(input.get("wrongphone"));
		if(login.errorMessage().contains("Incorrect phone number")){
			Assert.assertTrue(true);
		}else if(login.errorMessage().contains("Invalid mobile number")) {
			Assert.assertTrue(true);
		}else {
			Assert.assertTrue(false);
		}
	}
	
	@Test(dataProvider ="testData_1", retryAnalyzer=Retry.class)
	public void wrongPassword(HashMap<String,String> input) throws InterruptedException {
		login.enterPhoneNo(input.get("correctphone"));
		login.enterPassword(input.get("password"));
		Assert.assertTrue(login.errorMessage().contains("Your password is incorrect"));
	}

	
	@DataProvider
	public Object[][] testData_1() throws IOException {
		List<HashMap<String, String>> data = getJSONData(
				System.getProperty("user.dir") + "\\src\\test\\java\\Daszio\\Data\\Error data.json");
		return new Object[][] { { data.get(0) } };
	}
}
