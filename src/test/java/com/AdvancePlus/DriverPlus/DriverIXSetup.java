package com.AdvancePlus.DriverPlus;

import java.net.URL;
import java.util.Scanner;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverIXSetup {
	WebDriver driver;
	String nodeURL;

	public DriverIXSetup(WebDriver driver, String browser, String URL) throws Exception {
		if (URL.contains("https://www.testonlive.com")) {
			Scanner scan = new Scanner(System.in);
			String url;
			Exception e = new Exception("Test will not run on live");

			System.out.println("!WARNING! - You are about to execute this test on live? Type 'yes' to continue...");
			url = scan.nextLine();

			if (!url.equals("yes")) {
				scan.close();
				throw e;
			}
			scan.close();
		}
		this.driver = driver;
	}

	public WebDriver setup(WebDriver driver, String browser) throws Exception {
		Exception e = new Exception("Browser choice is not valid");
		switch (browser) {
		case "chrome":
			System.setProperty("webdriver.chrome.driver", ".//WebDrivers//chromedriver.exe");
			driver = new ChromeDriver();
			break;
		case "firefox":
			System.setProperty("webdriver.gecko.driver", ".//WebDrivers//geckodriver.exe");
			driver = new FirefoxDriver();
			break;
		case "edge":
			System.setProperty("webdriver.edge.driver", ".//WebDrivers//msedgedriver.exe");
			driver = new EdgeDriver();
			break;
		case "safari":
			nodeURL = "http://192.168.178.128:15370/wd/hub";
			DesiredCapabilities caps = DesiredCapabilities.safari();
			caps.setBrowserName("safari");
			caps.setPlatform(Platform.MAC);
			driver = new RemoteWebDriver(new URL(nodeURL), caps);
			break;
		default:
			throw e;
		}
		return driver;
	}
}
