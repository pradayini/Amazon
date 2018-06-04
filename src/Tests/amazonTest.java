package Tests;


import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import businessLibrary.Action;
import genericLibrary.Locators;



public class amazonTest {

	Action action = new Action();


	@BeforeTest()
	public void beforeClass() {
		action.openBrowser();
		action.waitForPageToLoad();

	}


	//verify title of the page
	@Test(priority=1)
	public void getTitle(){
		String Expected = "Online Shopping site in India: Shop Online for Mobiles, Books, Watches, Shoes and More - Amazon.in";
		String Actual = action.driver.getTitle();
		System.out.println(Actual);
		Assert.assertEquals(Expected,Actual );
	}


	//verify search box
	@Test(priority=2)
	public void verifySearchBox(){
		String Expected = "Showing most relevant results. See all results for book for kids.";
		action.searchProduct("book for kids");
		String Actual = action.driver.findElement(By.xpath(Locators.searchresultconfirm)).getText();
		System.out.println(Actual);
		Assert.assertEquals(Expected,Actual );	

	}

	//verify all links on navigation bar
	@Test(priority=3)
	public void verifyLinksOnNavigationBar(){
		try {

			WebElement mySelectElement = action.driver.findElement(By.xpath(Locators.navigationbar));
			List<WebElement> no = mySelectElement.findElements(By.tagName("a"));
			int nooflinks = no.size(); 
			System.out.println(nooflinks);
			for (WebElement pagelink : no)
			{
				String linktext = pagelink.getText();
				String link = pagelink.getAttribute("href"); 
				System.out.println(linktext+" ->");
				System.out.println(link);
			}
		}catch (Exception e){
			System.out.println("error "+e);
		}

	}

	//verify list in the drop down
	@Test(dependsOnMethods={"verifySearchBox"})
	public void dropDown(){
		int count = 0;
		String[] op = {"Relevance", "Price: Low to High", "Price: High to Low", "Avg. Customer Review", "Newest Arrivals"};
		WebElement mySelectElement = action.driver.findElement(By.id("sort"));
		Select dropdown= new Select(mySelectElement);
		List<WebElement> options = dropdown.getOptions();
		for (int i=0; i<options.size(); i++) {
			String listoption = options.get(i).getText();
			if(listoption.equals(op[i])){
				count++;
			}
		}

		if (count == op.length) {
			System.out.println("matched");
		} else {
			System.out.println("Not matched");
		}


	}


	// Add item to cart
	@Test(priority=4)
	public void addItemsToCart() throws InterruptedException{
		//search for a product
		action.searchProduct("Motorola mobile");
		action.delay(1000);
		// this is used to scroll down to a desired location
		JavascriptExecutor jse = (JavascriptExecutor)action.driver;
		jse.executeScript("scroll(0, 300)");
		action.delay(1000);
		String productName = action.driver.findElement(By.xpath(Locators.productText)).getText();
		System.out.println(productName);
		//to navigate to product detail page
		action.driver.findElement(By.xpath(Locators.productText)).click();
		action.delay(1000);

		//switch control to product detail page
		action.switchWindow();

		String productNameOnProductPage = action.driver.findElement(By.xpath(Locators.productTextOnProductDetailpage)).getText();
		System.out.println(productNameOnProductPage);

		//to verify item is same as selected
		if(productName.equals(productNameOnProductPage)){

			//if product is same in produt detail page add item to cart
			action.driver.findElement(By.xpath(Locators.addToCartBtn)).click();

			// confirm item added to cart

			action.driver.findElement(By.xpath(Locators.cartIcon));
			action.delay(1000);

			action.switchBackToMainWindow();

		}


	}

	//Delete item in cart
	@Test(priority=5)
	public void deleteItemInCart(){
		action.driver.findElement(By.xpath(Locators.cartIconOnNavigationbar)).click();
		action.delay(1000);
		action.driver.findElement(By.xpath(Locators.deleteLink)).click();
		action.delay(1000);
		String res = action.driver.findElement(By.xpath(Locators.deleteconfirmText)).getText();
		System.out.println(res);
		action.delay(1000); 
	}


	//To close browser
	@AfterClass(alwaysRun = true)
	public void tearDown() {
		action.driver.quit();

	}
		  
		}

        
       





		
		
		 
	
	


