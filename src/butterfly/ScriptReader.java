package butterfly;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.sikuli.script.Screen;
 
public class ScriptReader {
	
	public static void main(String[] args) {
		
		/**
		 * Read script file produced from spreadsheet
		 */
		
		String scriptFile = "C://temp//Script.dat";
		BufferedReader inputStream = null;
		Boolean eof = false;
		int failure = 0;
		String browserName = "";
		
		System.setProperty("webdriver.gecko.driver", "C:\\Software\\Selenium\\geckodriver.exe");
		System.setProperty("webdriver.chrome.driver", "C:\\Software\\Selenium\\chromedriver.exe");
		System.setProperty("webdriver.ie.driver", "C:\\Software\\Selenium\\IEDriverServer32.exe");
		System.setProperty("webdriver.edge.driver","C:\\Software\\Selenium\\MicrosoftWebDriver.exe");
		System.setProperty("webdriver.ie.logfile", "C:\\temp\\IEDriverServer.log");

		// Prepare for Selenium commands:
		WebDriver driver = null;
		Actions actions = null;
		// Prepare for image recognition using Sikuli:
		Screen screen = new Screen();

		try {
			inputStream = new BufferedReader(new FileReader(scriptFile));
			
			Test.startSuite();
			
			while (!eof) {
				try {
					// Parse script file by tabs into action - object - value
					String foo = inputStream.readLine();
					
					if (foo == null) { // EOF
						eof = true;
					} else {
					
						Test.debug("Command: " + foo);
						String[] details = foo.split("	");
						
						// Ignore comments (first character #)
						if (!details[0].startsWith("#")) {														
							
							if(args[0].equals("DEBUG")) {
								System.in.read();	// Wait for the user to press any character							
							}
							
							//Test.doze(200); // Slow execution	
													
							switch (details[0]) {
								case "Open":
									try {
										failure = 0;
										switch (details[1]) {
											case "Headless":
												browserName = "ActionUriServer.exe";
												driver = new HtmlUnitDriver();
												break;
											case "FireFox":
												browserName = "firefox.exe";
												driver = new FirefoxDriver();
												break;
											case "Chrome":
												browserName = "chrome.exe";
												driver = new ChromeDriver();
												break;
											case "IE - not supported":
												browserName = "iexplore.exe";
												// Fix for 2 browsers opening at once (http://stackoverflow.com/questions/37338367/selenium-after-click-a-link-ie-opens-two-windows-instead-of-one-window)
												//DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
												//ieCapabilities.setCapability("nativeEvents", true);
												//ieCapabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
												//driver = new InternetExplorerDriver(ieCapabilities);
												driver = new InternetExplorerDriver();
												break;												
											case "Edge":
												browserName = "MicrosoftEdge.exe";
												driver = new EdgeDriver();												
												break;
											case "current":	//	We continue to use the currently open browser
												break;
											default:
												Test.fail("Unknown or unsupported browser " + details[1] + ".");
												failure = 2;
												break;
										}
									} catch (Exception e) {
										Test.fail(e.toString());
										failure = 2;
									}
									if (failure==0) {
										try {
											driver.get(details[2]);
											actions = new Actions(driver);
										} catch (TimeoutException|NoSuchWindowException e) {
											Test.fail("Timeout error, cannot determine loading status for " + details[2]);
											failure = 2;
										} catch (SessionNotCreatedException e) {
											Test.fail(e.toString());
											failure = 2;											
										} catch (Exception e) {
											Test.fail(e.toString());
											failure = 2;
										}
										Test.log("Opened " + details[2] + " in " + details[1]);										
									}
									break;
								case "Choose":
									failure = Test.clickDown(driver, details[1], details[2]);
									break;
								case "Enter":
									failure = Test.enter(driver, details[1], details[2]);
									break;
								case "Click":
									failure = Test.clickMe(driver, details[1]);
									break;
								case "Select":
									failure = Test.select(driver, details[1], details[2]);
								case "ClickImage":
									failure = Test.clickImage(screen, details[1]);
									break;
								case "VerifyImage":
									failure = Test.findImage(screen, details[1]);
									break;
								case "Hover":
									failure = Test.hover(driver, actions, details[1]);
									break;
								case "ClickVanish":
									failure = Test.clickMe(driver, details[1], true);
									break;
								case "ClickFirst":
									failure = Test.clickDown(driver, details[1], details[2]);
									break;
								case "ListSize":
									failure = Test.listSize(driver, details[1]);
									break;
								case "Lowest":
									failure = Test.Lowest();
									break;
								case "Verify":
									failure = Test.verifyText(driver, details[1], details[2]);
									break;
								case "Start":
									failure = Test.start(details[2]);								
									break;
								case "Check":
									failure = Test.checkPresent(driver, details[1]);
									break;
								case "Log":	//	Write an entry in the log file
									Test.log(details[1] + " " + details[2]);
									break;
								case "Note":
									try {
										if (details[2].length() > 0) {
											Test.note(details[2] + ": " + Test.getFlatText(driver, details[1]));
										} else {
											Test.note("Value of " + details[1] + ": '" + Test.getFlatText(driver, details[1]) + "'");
										}
									} catch (Exception e) {
										Test.fail(e.getMessage());
									}
									break;
								case "Pause":
									Test.log("Stopping for user interaction.");
									Test.log("######################################################");
									System.exit(1); // Stop script execution.
									break;
								case "Stop":
									failure = Test.stop();
									Runtime.getRuntime().exec("taskkill /F /IM " + browserName);
									try {
										
										driver.quit();
										driver = null;
									} catch (Exception e) {
										// We don't care
									}
									break;
								default:
									Test.fail("Script contains unknown command '" + details[0] + "'!");
									failure = 2; // cause test to abort									
							}
							
							if (failure == 1) {
								// Note error and continue test							
							} else if (failure == 2) {
								// Log error and abort test
								Test.abort();
								// Give user 10 seconds to halt suite execution before browser killed.
								Test.doze(10000);
								
								Runtime.getRuntime().exec("taskkill /F /IM " + browserName);
								
								try {
									driver.quit();
									driver = null;
								} catch (Exception e) {
									// We don't care
								}
								
								// Move on to the next test (if there is one):
								
								while (!inputStream.readLine().startsWith("#")) {
									// Get next record...
								}						
							}
						}
					}
				} catch (IOException e) {
					//Test.log(e.getMessage());
					e.printStackTrace();
					eof = true;
				}
			}

			Test.log("################### End of test suite");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			Test.log("Test script not found!");
			e.printStackTrace();
		}
		
	}

}
