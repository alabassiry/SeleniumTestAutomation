package com.pagefactory;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.AdvancePlus.DriverPlus.DriverIXMethods;
import helper.CrazyLogger;
import helper.PropertiesLoader;

public class Loginpage {
	JavascriptExecutor jsDriver;
	WebDriver driver;
	WebDriverWait wait;
	CrazyLogger logger;
	Actions action;
	DriverIXMethods dIXMethods;
	PropertiesLoader properties;

	@FindBy(id = "email")
	public WebElement email;

	@FindBy(id = "pass")
	public WebElement pass;

	@FindBy(name = "login")
	public WebElement loginButton;

	@FindBy(xpath = "//div[contains(text(),'The email address')]")
	public WebElement wrongEmailLoginMessage;

	@FindBy(xpath = "//div[contains(text(),'The password that you')]")
	public WebElement wrongPasswordMessage1;

	@FindBy(xpath = "//span[contains(text(),'Log in as James Alfeeiigbecbh Riceman')]")
	public WebElement checkUsernameErrorMessage;

	/**
	 * Class constructor: a driver of type WebDriver and wait of type
	 * WebDriverWait must be passed as arguments
	 * 
	 * @param driver
	 * @param wait
	 */
	public Loginpage(WebDriver driver, WebDriverWait wait, JavascriptExecutor jsDriver, CrazyLogger logger,
			Actions action) {

		this.jsDriver = jsDriver;
		this.wait = wait;
		this.driver = driver;
		this.logger = logger;
		this.action = action;

		// This initElements method will create all WebElements

		PageFactory.initElements(driver, this);
		dIXMethods = new DriverIXMethods(driver, wait, jsDriver, logger, action);

	}

	/**
	 * This method will get the username and password from the conf. properties
	 * and login automatically
	 * @throws Exception 
	 */
	public void login(String tester) throws Exception {
		properties = new PropertiesLoader(tester);
		String username = properties.fetchProperty("facebook.user");
		System.out.println("This is the user name " + username);
		String password = properties.fetchProperty("facebook.pass");
		System.out.println("This is the password " + password);
		dIXMethods.driverIXSendKeys(email, username);
		dIXMethods.driverIXSendKeys(pass, password);
		dIXMethods.driverIXClick(loginButton);
		Thread.sleep(100);
	}
}
