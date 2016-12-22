package butterfly;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class EasyJet {

	/**
	 * main method, runs test case.
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		//Create Firefox driver to drive the browser		
		//WebDriver driver = Test.wDriver("Firefox");
		
		System.setProperty("webdriver.gecko.driver", "C:\\Software\\Selenium\\geckodriver.exe");

		WebDriver driver = new FirefoxDriver();

		Test.start(driver);
				
		driver.get("http://www.easyjet.com");
		//Test.clickMe(driver,  "css=a.calendar > img");
		
		Test.clickDown(driver, "name=origin", "LGW");
		Test.clickDown(driver,  "name=destination",  "ZRH");
		
		Test.clickMe(driver,  "class=outbound-date-picker");
		Test.clickMe(driver, "xpath=///h3[text()='Dezember 2016']/following-sibling::div/div[@data-date='2016-12-15']");
		Test.clickMe(driver,  "class=return-date-picker");
		Test.clickMe(driver, "xpath=///h3[text()='Dezember 2016']/following-sibling::div/div[@data-date='2016-12-28']");
		/*
		Test.clickMe(driver,  "xpath=//div[@data-date=\"2016-12-28\""); // outbound date
		Test.clickMe(driver,  "xpath=//div[@data-date=\"2016-12-30\""); // outbound date
		*/
		Test.clickMe(driver,  "xpath=///button[text()='Flüge anzeigen']");
		
		Test.clickMe(driver, "xpath=///div[@id='OutboundFlightDetails']//span[text()='Niedrigster Flugpreis']");
		Test.clickMe(driver, "xpath=///div[@id='ReturnFlightDetails']//span[text()='Niedrigster Flugpreis']");
		Test.log("Cheapest flight: " + Test.getFlatText(driver, "class=formatedPrice"));
		
/*		Test.checkPresent(driver, "flightsData");
		
		//Look at a few prices:
		List<WebElement> myList = driver.findElements(By.className("book-bundle-button--price"));
		for(WebElement e1: myList) {
			Test.log("Price: " + e1.getText());
		}
	*/	
		
		Test.stop();
		
		
		
		
		
		
		//From job search results page get page title and jobs count message. 

		//Close browser
//		driver.quit();
//		Test.log("Closed browser");

	}
	
}
