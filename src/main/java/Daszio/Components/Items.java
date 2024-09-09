package Daszio.Components;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;


import Daszio.Abstract.Abstract;

public class Items extends Abstract {
	WebDriver driver;
	

	public Items(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[@data-cy='title-recipe']/h2/a/span")
	 List<WebElement> Item;

	@FindBy(css = ".s-pagination-item.s-pagination-next")
	WebElement nextButton;
	
	@FindBy(xpath = "//a[contains(@class,'s-no-hover')]/span[@class='a-price']")
	List<WebElement> price;
	
	@FindBy(css = "span[class='a-size-base-plus a-color-base']")
	List<WebElement> brand;
	
	public void excelData() throws IOException {
		File file = new File(System.getProperty("user.dir")+"\\Excel\\Item List.xlsx");
		XSSFWorkbook workbook;
		XSSFSheet createExcel;
		if(file.exists()) {
			FileInputStream fir = new FileInputStream(file);
			workbook = new XSSFWorkbook(fir);
			createExcel = workbook.getSheet("Data");
			fir.close();
		}else {
			workbook = new XSSFWorkbook();
			createExcel = workbook.createSheet("Data");
			List<String> data = excelHeaders();

			 Row row = createExcel.createRow(0);
			for (int i = 0; i < data.size(); i++) {
				Cell cell = row.createCell(i);
	           cell.setCellValue(data.get(i));
			}
		}
		
		int rowcount = createExcel.getLastRowNum()+1;
		int itemCount = rowcount;
		List<WebElement> requiredItem;
		List<WebElement> requiredprice;
		//List<WebElement> requiredbrand;
		for(int i = 0;i<Item.size();i++) {
			requiredItem=Item;
			requiredprice=price;
			//requiredbrand=brand;
			
			visibilityOfAllElementWait(requiredItem);
			visibilityOfAllElementWait(requiredprice);

			List<String> data1 = new ArrayList<>(Arrays.asList(
					Integer.toString(itemCount++), 
					requiredItem.get(i).getText(), 
					requiredprice.get(i).getText()));
			
			XSSFRow row2 = createExcel.createRow(rowcount++);
			for(int j = 0;j<data1.size();j++) {
				XSSFCell cell = row2.createCell(j);
				cell.setCellValue(data1.get(j));
			}
			
		}
		
		FileOutputStream createFile = new FileOutputStream(file);
		workbook.write(createFile);
		workbook.close();
		createFile.close();
	}
	
	public List<String> excelHeaders() {
		return new ArrayList<>(Arrays.asList("S.NO", "Product Name", "Price"));
	}
	

	public String scanItems(String product) {
	    int flag = 0;
	    int page = 1;
	    String actualItem = null;
	    
	    try {
	        while (nextButton.isDisplayed()) {
	            // Retrieve the list of items on the current page
	            List<WebElement> requiredItem = Item;
	            for (int i = 0; i < requiredItem.size(); i++) {
	                if (requiredItem.get(i).getText().contains(product)) {
	                    flag++;
	                    visibilityOfAllElementWait(requiredItem);
	                    actualItem = requiredItem.get(i).getText();
	                    System.out.println(actualItem);
	                    requiredItem.get(i).click();
	                    break; // Break out of the loop once the item is found
	                }
	            }
	            if (flag > 0) {
	                break; // Exit the outer loop if the item was found
	            } else {
	                page++;
	                System.out.println("Navigating to page: " + page);

	                // Check if the next button is disabled (i.e., no more pages)
	                if (nextButton.getAttribute("class").contains("s-pagination-disabled")) {
	                    System.out.println("Item not found on any page.");
	                    break; // Exit the loop if no more pages are available
	                }

	                // If next button is enabled, click it to go to the next page
	                nextButton.click();
	                Thread.sleep(2000); // Wait for the page to load after clicking next
	            }
	        }
	    } catch (Exception e) {
	        System.out.println("Exception occurred: " + e.getMessage());
	    }

	    return actualItem;
	}

	
	
}
