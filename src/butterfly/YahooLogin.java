package butterfly;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class YahooLogin {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		//Create Firefox driver to drive the browser		
		WebDriver driver;
		
		System.setProperty("webdriver.gecko.driver", "C:\\Software\\Selenium\\geckodriver.exe");
		driver = new FirefoxDriver();

		Test.start("");
				
		// Open Indeed home page		
		driver.get("http://www.yahoo.com");
		
		Test.clickMe(driver, "uh-signin");
		Test.enter(driver, "login-username", "cornelius.codswallop@yahoo.com");
		Test.clickMe(driver, "login-signin");
		Test.enter(driver, "login-passwd", "Password1234");
		Test.clickMe(driver, "login-signin");
		Test.checkPresent(driver, "xpath=//button[@title='Profile']");
		Test.clickMe(driver,  "xpath=//button[@title='Profile']");
		Test.clickMe(driver, "uh-signout");
		System.exit(0);
		Test.checkPresent(driver, "uh-signin");
	
		Test.stop();
		
		
		
		//From job search results page get page title and jobs count message. 

		//Close browser
//		driver.quit();
//		Test.log("Closed browser");
	}
	
}
