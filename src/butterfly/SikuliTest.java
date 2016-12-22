package butterfly;
import org.sikuli.script.*;
import org.sikuli.basics.*;

public class SikuliTest {
	
	  public static void main(String[] args) {
		  try {
		    Debug.setDebugLevel(3);
		    Settings.OcrTextSearch = true;
		    Settings.OcrTextRead = true;
		    
		    Screen s = new Screen();
		    
		    s.click("C:\\Users\\ward\\Documents\\Sikulix\\calc.sikuli\\1479121813465.png");
		    
		    
		    s.find(s.userCapture().getFile()).highlight(2);

		    System.out.println("Attempting text recovery");
		    Region reg = new Region(160,299,130,187);
		    System.out.println(reg.text());
		    System.out.println("Text recovery done");
		    
		  } catch (Exception x) {
			  //System.out.println(x.getMessage());
			  System.err.println(x.getMessage());
		  }
	  }
}
