package butterfly;
import java.io.FileReader;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;

public class JsonTest {

	public static void main(String[] args) {
				
		JSONParser parser = new JSONParser();
		
		try {
			
			Object obj = parser.parse(new FileReader("C://Users//ward/Desktop//Selenium//family.JSON"));
			
			JSONObject jsonObject = (JSONObject) obj;
			
			String name = (String) jsonObject.get("name");
			System.out.println("Name: " + name);
			
			String birthday = (String) jsonObject.get("birthday");
			System.out.println("Born: " + birthday);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		

	}

}
