package helper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {
	private Properties prop;
	private InputStream input;

	public PropertiesLoader(String tester) throws Exception {
		Exception exc = new Exception("User configuration file is not found!");
		switch (tester) {
		case "marwan":
			try {
				input = new FileInputStream("./resources/conf[Marwan].properties");
				prop = new Properties();
				prop.load(input);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "aaron":
			try {
				input = new FileInputStream("./resources/conf[Aaron].properties");
				prop = new Properties();
				prop.load(input);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "dami":
			try {
				input = new FileInputStream("./resources/conf[Dami].properties");
				prop = new Properties();
				prop.load(input);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "shola":
			try {
				input = new FileInputStream("./resources/conf[Shola].properties");
				prop = new Properties();
				prop.load(input);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			throw exc;
		}
	}

	public String fetchProperty(String propertyName) throws FileNotFoundException, IOException {
		// get the property value and print it out
		return prop.getProperty(propertyName);
	}
}
