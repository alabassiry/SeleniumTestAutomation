package helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Commons {
	public static String APILogsDirectory = "APITestLogs/";
	
	public String getXpath(WebElement element) {
		int n = element.findElements(By.xpath("./ancestor::*")).size();
		String path = "";
		WebElement current = element;
		for (int i = n; i > 0; i--) {
			String tag = current.getTagName();
			int lvl = current.findElements(By.xpath("./preceding-sibling::" + tag)).size() + 1;
			path = String.format("/%s[%d]%s", tag, lvl, path);
			current = current.findElement(By.xpath("./parent::*"));
		}
		return "/" + current.getTagName() + path;
	}

	public String getRandomString(int length) {
		StringBuilder sb = new StringBuilder();
		String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		for (int i = 0; i < length; i++) {
			int index = (int) (Math.random() * characters.length());
			sb.append(characters.charAt(index));
		}
		return sb.toString();
	}
	
	public static String getRandomFilePrefix(int length) {
		StringBuilder sb = new StringBuilder();
		String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		for (int i = 0; i < length; i++) {
			int index = (int) (Math.random() * characters.length());
			sb.append(characters.charAt(index));
		}
		return sb.toString();
	}
	
	public static String joiningChar() {
		String character = "_";
		return character;
	}
	
	public static String fileType(String type) {
		switch (type) {
			case "txt": return ".txt";
			case "log": return ".log";
			default: return ".log";
		}
	}
	
	public static void fileReader(String directory, String fileName) {
	    try {
	      File myObj = new File(directory + fileName);
	      Scanner myReader = new Scanner(myObj);
	      while (myReader.hasNextLine()) {
	        String data = myReader.nextLine();
	        System.out.println(data);
	      }
	      myReader.close();
	    } catch (FileNotFoundException e) {
	      System.out.println("An error occurred.");
	      e.printStackTrace();
	    }
	  }
}
