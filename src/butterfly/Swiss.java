package butterfly;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Swiss {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		//Create Firefox driver to drive the browser		
		WebDriver driver;
		
		System.setProperty("webdriver.gecko.driver", "C:\\Software\\Selenium\\geckodriver.exe");
		driver = new FirefoxDriver();

		Test.start(driver);
				
		driver.get("http://www.swiss.com");
		
		Test.enter(driver, "name=Origin", "London (LON)");
		Test.enter(driver,  "name=Destination",  "Zürich (ZRH)");

		Test.clickMe(driver, "class=btn-submit");

		Test.enter(driver, "OutboundDate", "08.12.2016");
		Test.enter(driver, "InboundDate", "20.12.2016");
		
		Test.clickMe(driver, "xpath=///section[@id='bookingbar-flight']/div/form/div/div/div/button");
		
		Test.checkPresent(driver, "flightsData");
		
		//Look at a few prices:
		List<WebElement> myList = driver.findElements(By.className("book-bundle-button--price"));
		for(WebElement e1: myList) {
			if (!e1.getText().equals("")) {
				Test.log("Price: " + e1.getText());
			}
		}
		
		
		Test.stop();
		
		
		
		
		
		
		//From job search results page get page title and jobs count message. 

		//Close browser
//		driver.quit();
//		Test.log("Closed browser");
	}
	
}
