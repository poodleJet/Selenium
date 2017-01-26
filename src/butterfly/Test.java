package butterfly;
import static java.nio.file.StandardOpenOption.*;
import java.nio.file.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.Select;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

public class Test {

  public static void main(String[] args) {
	  log("Hello world!");
  }
  
  /**
   * Returns the web driver for the browser specified.
   * @param browserName
   * @return
   */
  public static WebDriver wDriver(String browserName) {	  
	  
	WebDriver d;
	  
	if (browserName.equals("Firefox")) {
		  ProfilesIni allProfiles = new ProfilesIni();
		  FirefoxProfile myProfile = allProfiles.getProfile("default");
		  myProfile.setAcceptUntrustedCertificates(true);
		  myProfile.setAssumeUntrustedCertificateIssuer(true);
		  d = new FirefoxDriver(myProfile);
	  } else if (browserName.equals("Chrome")) {
		  d = null;
	  } else if (browserName.equals("Edge")) {
		  d = null;
	  } else if (browserName.equals("IE")) {
		  d = null;
	  } else {
		  log("Unknown browser " + browserName + "!");
		  return null;
	  }
	  
	  return d;
  }

  /**
   * Log a message to a log file (whose location is currently hard-coded).
   * @param args
   */
  public static void logg(String args) {
    // Convert the string to a
    // byte array.
	
	String s = timeStamp() + " " + args + "\n";		
	byte data[] = s.getBytes();
	
    //Path p = Paths.get("./logfile.txt");
    Path p = Paths.get("C://Users//ward//Desktop//Selenium//logFile.log");

    try (OutputStream out = new BufferedOutputStream(
      Files.newOutputStream(p, CREATE, APPEND))) {
      out.write(data, 0, data.length);
      out.close();
    } catch (IOException x) {
      System.err.println(x);
    }
  }
  
  /**
   * Log a success message to the log file.
   * @param args
   */
  public static void pass(String args) {
	  logg(":-) " + args);
  }
  
  /**
   * Log a failure message to the log file.
   * @param args
   */
  public static void warn(String args) {
	  logg("??? " + args);	  
  }
  
  /**
   * Log a warning message to the log file.
   * @param args
   */
  public static void fail(String args) {
	  logg(":-( " + args);
  }

  
  /**
   * Log a debug message to the log file.
   * @param args
   */
  public static void debug(String args) {
	  logg("DBG " + args);
  }

  /**
   * Log a general message to the log file.
   * @param args
   */
  public static void log(String args) {
	  logg("### " + args);
  }
  
  /**
   * Log a returned value (not known in advance) to the log file.
   * @param args
   */
  public static void note(String args) {
	  logg(">>> " + args);
  }

  /**
   * Returns a timestamp string with millisecond precision.
   * @return
   */
  private static String timeStamp() {
	  
	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS");
	  String timeS = sdf.format(new Date());
	  
	  return timeS;
  }
  
  /**
   * Checks that a screen object exists, is visible and is active.
   * @param driver
   * @param idString
   */
  public static int checkPresent(WebDriver driver, String idString) {
					
		WebElement w = we(driver, idString);
		
		if (w == null) {
			fail("Object " + idString + " not found.");
			Shoot(driver);
			return 2;
		} else {
			pass("Object " + idString + " found.");
			return 0;
		}
  }
  
  /**
   * Take a screen shot of the current screen
   * @param driver
   */
  public static void Shoot(WebDriver driver) {	  
	
	try {
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

		// Generate a new (hopefully unique) file name:		
		Date date2 = new Date(); 
		Long time2 = date2.getTime();		
		File screenShot = new File("C:\\Users\\ward\\Desktop\\Selenium\\Screenshots\\" + time2 + ".png");		
		FileUtils.copyFile(scrFile, screenShot);
		log("Screen shot captured to " + screenShot);
	} catch (Exception e) {
		log("Unable to capture screenshot." + e.getMessage());
	}
  }
  
  /**
   * Sends a key press to the application.
   * @param driver
   * @param keyPressed e.g. ESC
   * @return
   */
  public static int keys(WebDriver driver, String keyPressed) {
	  
	  Keys keyToPress;
	  
	  switch(keyPressed.toUpperCase()) {
	  	case "ESC":  keyToPress = Keys.ESCAPE;
	  		break;
	  	case "CTRL": keyToPress = Keys.CONTROL;
	  		break;
	  	case "BKSP": keyToPress = Keys.BACK_SPACE;
	  		break;
	  	case "UP": keyToPress = Keys.ARROW_UP;
  			break;
	  	case "DOWN": keyToPress = Keys.ARROW_DOWN;
  			break;
	  	case "LEFT": keyToPress = Keys.ARROW_LEFT;
			break;
	  	case "RIGHT": keyToPress = Keys.ARROW_RIGHT;
			break;
	  	case "ALT": keyToPress = Keys.ALT;
	  		break;
	  	case "DEL": keyToPress = Keys.DELETE;
	  		break;
	  	case "END": keyToPress = Keys.END;
	  		break;
	  	case "HOME": keyToPress = Keys.HOME;
	  		break;
	  	case "PGDN": keyToPress = Keys.PAGE_DOWN;
	  		break;
	  	case "PGUP": keyToPress = Keys.PAGE_UP;
	  		break;
	  	case "TAB": keyToPress = Keys.TAB;
	  		break;	  			
	  	default:
	  		fail("Unknown key " + keyPressed);
	  		return 2;
	  }
	  
	  Actions action = new Actions(driver);
	  action.sendKeys(keyToPress);
	  
	  return 0;	  	 
  }
  
  /**
   * Returns text from a web element.
   * @param driver
   * @param idString
   * @return
   */
  public static String getText(WebDriver driver, String idString) {
	  
	  WebElement w = we(driver, idString);

	  if (w == null) {
			return "";
	  }			  
	  
	  return w.getText();
  }
  
  public static String getFlatText(WebDriver driver, String idString) {
	  
	  String s = getText(driver, idString);
	  
	  return s.replaceAll("(\\r|\\n)", "");
  }
  
  /**
   * Compare the text found in an object to some expected text.
   * @param driver
   * @param idString
   * @param toCompare
 * @return 
   */
  public static int verifyText(WebDriver driver, String idString, String toCompare) {
	  
		WebElement w = we(driver, idString);

		if (w == null) {
			return 2;
		}
		
		String s = w.getText();
		
		if (s.equals(toCompare)) {
			pass(idString + " contains exactly '" + s + "'");
			return 0;
		}
		
		if (s.contains(toCompare)) {
			pass("Text '" + toCompare + "' found in " + idString);
			return 0;			
		}
		
		fail("Text '" + toCompare + "' not found in " + idString + ", found '" + s + "'.");
		Shoot(driver);
		return 1;
	  
  }
  
  /**
   * remove a tick in an input box to de-select it. If there is no tick anyway, do nothing.
   * @param driver
   * @param idString
   * @return
   */
  public static int unset(WebDriver driver, String idString) {
	  
	  	WebElement w = we(driver, idString);
	  	
		if (w == null) {
			return 2;
		}

		if (w.isSelected()) {
			w.click();
			debug("Unset " + idString);
		} else {
			debug(idString + " was already not set");
		}
		
		return 0;
  }
  
  /**
   * Set a cross in an input box. If there is already a cross there, do nothing.
   * 
   */
  public static int set(WebDriver driver, String idString) {
	  
	  	WebElement w = we(driver, idString);
	  	
		if (w == null) {
			return 2;
		}

		if (!w.isSelected()) {
			w.click();
			debug("Set " + idString);
		} else {
			debug(idString + " was already set");
		}
		
		return 0;
  }
  
  /**
   * Default invocation of clickMe, with implicit WaitToDisappear = false.
   * @param driver
   * @param idString
   * @return
   */
  public static int clickMe(WebDriver driver, String idString) {
	  
	  return clickMe(driver, idString, false);
	  
  }
  
  /**
   * Click an object on the screen.
   * @param driver
   * @param idString
 * @return 
   */
  public static int clickMe(WebDriver driver, String idString, Boolean WaitToDisappear) {				
		
	  	WebElement w = we(driver, idString);
	  	
		if (w == null) {
			return 2;
		}
		
		try {
			w.click();
			debug("Clicked " + idString);
			
			if (WaitToDisappear) {
				
				long t = 0;
				while (w.isDisplayed()) {
					doze(100);
					t++;
				}
				if (t>1) {
					debug("Element " + idString + " disappeared after " + t + " dozes.");
				}
			}
			return 0;
		} catch (Exception e) {
			// Something went wrong
		}
		
		return 2;
  }
  
  /**
   * Navigate to a URL in the open browser.
   * @param driver
   * @param Url
   * @return
   */
  public static int openUrl(WebDriver driver, String Url) {
	  
	  try {
		  driver.get(Url);
		  log("Opened " + Url);
		  return 0;
	  } catch (Exception e) {
		  fail(e.toString());
	  }
	  
	  return 2;
  }
  
  /**
   * Clicks the first of a number of objects described by the idString.
   * @param driver
   * @param idString
   */
  public static void clickFirst(WebDriver driver, String idString) {
	  Actions action = new Actions(driver);
	  
	 
  }
  
  /**
   * Logs the start of a test, with a message. 
   * @param testName
 * @return 
   */
  public static int start(String testName) {
	  log("######################################################");
	  log("Starting test " + testName);
	  return 0;	  	 
  }
  
  /**
   * Log the start of a test.
   */
  public static int start(WebDriver driver) {
	  log("######################################################");
	  log("Browser: " + driver.toString());
	  log("Starting test");
	  return 0;
  }
  
  /**
   * Log the end of a test
 * @return 
   */
  public static int stop() {
	  log("End of test");
	  log("######################################################");
	  return 0;
  }
  
  public static void abort() {
	  fail("Test was aborted");
	  fail("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
  }
  
  /**
   * Pause test execution for a number of milliseconds.
   * @param milliseconds
   */
  public static void doze(long milliseconds) {
	  try {
		  Thread.sleep(milliseconds);
	  } catch (InterruptedException e) {
		  fail("Error dozing...");
		  fail(e.toString());
	  }
	  
  }
  
  /**
   * WebElement returns a web element which exists, is visible, is active, and 
   * is not in a stale state, so can then be clicked, have text entered, etc.
   * 
   * If the web element does not exist, is not visible, is not active, or is in a stale state,
   * the method tries again a number of times and then gives up and returns null.
   * 
   * @param driver
   * @param idString
   * @return
   * @throws InterruptedException 
   */
  public static WebElement we(WebDriver driver, String idString) {
	  	  
	  	// Initial assignment of webElement, to avoid later problems.
	  	WebElement webElement;
	  	boolean looking = true; 
	  	long t=0;
	  	String errorDesc = "";
	  	  
		//How is the element identified? (Default is by ID.)
	  			
		while (looking) {
			try {
				// Don't wait more than 20 seconds:
				if (t > 200) {
					throw new ArithmeticException();
				}
				
				String parts[] = idString.split("=");
				
				if (parts.length == 1) {
					webElement = driver.findElement(By.id(idString));
				} else {
					if (parts[0].equals("css")) {
						webElement = driver.findElement(By.cssSelector(parts[1]));
					} else if (parts[0].equals("name")) {
						webElement = driver.findElement(By.name(parts[1]));
					} else if (parts[0].equals("link")) {
						webElement = driver.findElement(By.linkText(parts[1]));
					} else if (parts[0].equals("xpath")) {
						webElement = driver.findElement(By.xpath(idString.substring(7)));
					} else if (parts[0].equals("class")) {
						webElement = driver.findElement(By.className(parts[1]));
					} else {
						fail("Illegal element specification " + idString);
						webElement = null;						
					}
				}
				
				if (t == 199) {
					debug(idString + " <" + webElement.getTagName() + "> Displayed: " + webElement.isDisplayed() + " Enabled: " + webElement.isEnabled());
				}
				
				// is the element something we are going to do something with, or is it just an element from which we recover text?
				//debug(webElement.getText() + " " + webElement.getTagName() + " " + webElement.isEnabled() + " " + webElement.isDisplayed());
				
			  	// The element must be enabled and displayed before it can be returned:			  	
			  	if (webElement.isEnabled() && webElement.isDisplayed()) {
				  	// Log any element that takes a long time to appear:
					if (t > 0) {
						debug("Found " + idString + " after " + t+1 + " attempts (" + errorDesc + ")");
					}
					
					looking = false;
					return webElement;
			  	} else {
			  		throw new InvalidElementStateException();
			  	}
			  	

			} catch (NoSuchElementException|InvalidElementStateException|StaleElementReferenceException|UnreachableBrowserException e) {
				t++;
				errorDesc = e.toString().split(":")[0];
				doze(100);
			} catch (WebDriverException e) {
				t++;
				errorDesc = e.toString();//.split(":")[0];
				doze(100);				
			} catch (ArithmeticException e) {
				fail("No '" + idString + "'  after 20 seconds (" + errorDesc + ")!");
				Shoot(driver);
				looking = false;
			} catch (Exception e) {
				fail("Error found looking for object " + idString);
				fail(e.toString());
				Shoot(driver);
				e.printStackTrace();
				looking = false;
			}
		}	  	
		
		return null;
	  
  }
  
  /**
   * Enter text to an object on the screen.
   * @param driver
   * @param idString
   * @param textToEnter
 * @return 
   */
  public static int enter(WebDriver driver, String idString, String textToEnter) {
	  	  
	  try {
		  WebElement w = we(driver, idString);
		  
			if (w == null) {
				return 2;
			}
			
		  try {
			  w.clear();
		  } catch (Exception e) {
			  // Do nothing, for now, just ignore the exception.
		  }
		  
		  w.clear();
		  w.sendKeys(textToEnter);
	
		  debug("Entered '" + textToEnter + "' in " + idString);
		  return 0;
	  } catch (Exception e) {
		  // Something went wrong
		  fail(e.getMessage());
	  }
	  
	  return 2;
  }
  
  /**
   * Enter text to an object on the screen, then click on the first suggestion (immediately below the object). The entered text must be 
   * sufficiently precise that the first suggestion will be the correct one.
   * @param driver
   * @param idString
   * @param textToEnter
   */
  public static int clickDown(WebDriver driver, String idString, String textToEnter) {
	  
	  WebElement w = we(driver, idString);
	  
		if (w == null) {
			return 2;
		}
		
	  Dimension dimension = w.getSize();
	  
	  int xPos = dimension.getWidth() / 2;
	  int yPos = dimension.getHeight() * 3 / 2;
	  
	  Actions action = new Actions(driver);
	  
	  try {
		  w.click();
		  w.clear();
		  w.sendKeys(textToEnter);
	
		  doze(200);	// Give the GUI time to update
		  
		  //action.moveToElement(w, xPos, yPos);
		  //action.moveByOffset(0, dimension.height);
		  //w.sendKeys(Keys.ARROW_DOWN);
		  //doze(200);	// Give the GUI time to update
		  w.sendKeys(Keys.ENTER);
		  debug("Entered text '" + textToEnter + "' into " + idString);
		  
		  return 0;
	  } catch (WebDriverException e) {
		  fail(e.getMessage());
		  return 2;
	  }
	  
  }
  
  /**
   * Click an object which must exist somewhere on the screen.
   * @param screen
   * @param imageId
   * @return
   */
  public static int clickImage(Screen screen, String imageId) {
	  
	  return clickImage(screen, imageId, 0, 0);
	  
  }
  
  public static int select(WebDriver driver, String objectId, String text) {
	  
	WebElement w = we(driver, objectId);
	  
	if (w == null) {
			return 2;
	}
	
	try {
		new Select(w).selectByVisibleText(text);
		return 0;
	} catch (Exception e) {
		fail(e.getMessage());
	}

	return 2;
	  
  }
  
  /**
   * Use Sikuli to confirm whether an image is present on the screen.
   * @param screen
   * @param imageId
   * @return
   */
  public static int findImage(Screen screen, String imageId) {
	  
	  debug("Attempting to find image " + imageId);
	  
	  String image = "C:\\Users\\ward\\Documents\\Sikulix\\" + imageId + ".PNG";
	  
	  try {
		  Pattern pattern = new Pattern(image);		  
		  Match r = screen.exists(pattern,1);
		  pass("Image " + imageId + " found.");
		  return 0;	// Success
	  } catch (Exception e) {
		  fail("Image " + imageId + ": " + e.getMessage());
		  e.printStackTrace();
	  }
	  return 2;	// Something failed!
  }
  
  /**
   * Click an image on the screen at offset to parent window.
   * @param screen
   * @param imageId
   * @param xOffset
   * @param yOffset
   * @return
   */
  public static int clickImage(Screen screen, String imageId, int xOffset, int yOffset) {
	  
	  debug("Attempting to click image " + imageId);
	  String image = "C:\\Users\\ward\\Documents\\Sikulix\\" + imageId + ".PNG";
	  	  
	  try {
		  Pattern pattern = new Pattern(image).targetOffset(xOffset, yOffset);		  
		  Match r = screen.exists(pattern,1);
		  doze(100);
		  screen.click(r, 1);
		  
		  debug("Clicked image " + imageId);
		  return 0;	// success
	  } catch (Exception e) {
		  fail("Image " + imageId + ": " + e.getMessage());
		  e.printStackTrace();
	  }
	  return 2; // Something failed!
	  
  }
  
  public static int getRidOf(WebDriver driver, String idString) {
	  
	  WebElement webElement;
	  
	  doze(100); // Wait for screen to finish painting
	  
	  String parts[] = idString.split("=");
		
	  try {
		  if (parts.length == 1) {
			  webElement = driver.findElement(By.id(idString));
			} else {
				if (parts[0].equals("css")) {
					webElement = driver.findElement(By.cssSelector(parts[1]));
				} else if (parts[0].equals("name")) {
					webElement = driver.findElement(By.name(parts[1]));
				} else if (parts[0].equals("link")) {
					webElement = driver.findElement(By.linkText(parts[1]));
				} else if (parts[0].equals("xpath")) {
					webElement = driver.findElement(By.xpath(idString.substring(7)));
				} else if (parts[0].equals("class")) {
					webElement = driver.findElement(By.className(parts[1]));
				} else {
					fail("Illegal element specification " + idString);
					webElement = null;
					return 0;
				}
			}
		  	webElement.click();
	  
	  } catch (Exception e) {
		  // do Nothing.
	  }
		
	  return 0;	  
  }
  
  public static int clickImage(Screen screen, String imageId, String offset) {
	  
	  try {
		  String xy[] = offset.split("[,;]");
		  
		  int xOff = Integer.parseInt(xy[0]);
		  int yOff = Integer.parseInt(xy[1]);
		  
		  clickImage(screen, imageId, xOff, yOff);
		  return 0; // success
	  } catch (Exception e) {
		  fail("Can't parse offset value '" + offset + "': " + e.getMessage());		  
	  }
	  
	  return 2; //Failed!
	  
	  
  }
  
  /**
   * Insert a few lines into the log file at the start of suite execution to identify user and computer.
   */
  public static void startSuite() {
		
	  //	Insert 2 blank lines into logfile for readability.
	  log("################### Start of test suite");
	  
	  /*
	  Map<String, String> env = System.getenv();
	  
	  for (String envName : env.keySet()) {
          System.out.format("%s=%s%n",
                            envName,
                            env.get(envName));
      }
	  
	  log("User: " + env.get("USERNAME") + " at " + env.get("USERDNSDOMAIN"));
	  log("Host: " + env.get("COMPUTERNAME"));
	  */		
  }
  
  public static int hover(WebDriver driver, Actions actions, String objectId) {
	  
	  	WebElement w = we(driver, objectId);
		if (w == null) {
			return 2;
		}

		try {
		  	actions.moveToElement(w);
			debug("Hovered " + objectId);
			return 0;
		} catch (Exception e) {
			fail(e.getMessage());
			// Something went wrong
		}
	  return 2;
	  
  }
  
  
}