package butterfly;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;


public class IndeedJobSearch {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		//Create Firefox driver to drive the browser		
		WebDriver driver;
		
		System.setProperty("webdriver.gecko.driver", "C:\\Software\\Selenium\\geckodriver.exe");
		driver = new FirefoxDriver();
		String tempString;
		
		Test.log("Starting Test");		
				
		// Open Indeed home page		
		driver.get("http://www.indeed.co.uk");
		
		//Find what field and enter Selenium		
		Test.enter(driver, "what", "Selenium");
		
		// Find location field and enter London
		Test.enter(driver,  "where",  "London");
		
		//Find FindJobs button and click on it.
		Test.clickMe(driver, "fj");
		
		Test.clickMe(driver, "prime-popover-x");			
		
		//How many jobs are there?
		tempString = driver.findElement(By.id("searchCount")).getText();
		Test.log(tempString);
		
		//Look at a few jobs:
		List<WebElement> myList = driver.findElements(By.className("jobtitle"));
		for(WebElement e1: myList) {
			Test.log("Job: " + e1.getText());
		}
		
		
		
		
		
		
		
		//From job search results page get page title and jobs count message. 

		//Close browser
		driver.quit();
		Test.log("Closed browser");
	}
	
}
