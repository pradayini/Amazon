package businessLibrary;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import genericLibrary.Locators;



public class Action {
	public WebDriver driver;

	Action action;
	public  String parentID ;
	public String childID;


	//implicitly wait
	public void waitForPageToLoad(){
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS) ;

	}

	// system delay
	public void delay(int delayInSecs){

		try {
			Thread.sleep(delayInSecs);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("EXCEPTION IN DELAY THREAD!!");
		}

	}

	//method to open browser
	public void openBrowser(){
		String workingDir = System.getProperty("user.dir");
		System.setProperty("webdriver.chrome.driver", workingDir+ "/Drivers/chromedriver.exe");

		// Initialize browser
		driver =new ChromeDriver();

		// Open amazon
		driver.get("https://www.amazon.in/");

		// Maximize browser
		driver.manage().window().maximize();

	}

	public String getTitle(){
		String res= driver.getTitle();
		return res;
	}

	// method to search for a given string in the search bar
	public void searchProduct(String item){

		driver.findElement(By.xpath(Locators.searchbox)).clear();
		driver.findElement(By.xpath(Locators.searchbox)).sendKeys(item);
		driver.findElement(By.xpath(Locators.searchsubmitbtn)).click();

	}


	//method to switch window
	public void switchWindow(){
		// get all Windows id which are open
		Set <String> winHandle = driver.getWindowHandles();

		System.out.println("There are total "+winHandle.size()+" tabs open");

		//Capture window ids from set

		Iterator <String> it = winHandle.iterator();
		parentID = it.next();
		childID = it.next();

		// switching to the newly opened tab/browser
		driver.switchTo().window(childID);

	}

	// method to switch back to main window
	public void switchBackToMainWindow(){


		driver.close();
		System.out.println("closing the current tab and giving the control back to parent window");
		driver.switchTo().window(parentID);


	}
}
