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

public class Homepage {
	JavascriptExecutor jsDriver;
	WebDriver driver;
	WebDriverWait wait;
	String instance;
	CrazyLogger logger;
	Actions action;
	DriverIXMethods dIXMethods;

	@FindBy(xpath = "//span[contains(text(),'Welcome to Facebook, James')]")
	public WebElement WelcomeToFacebookText;
	
	@FindBy(xpath = "//div[@data-pagelet='LeftRail']//span[contains(text(),'James Alfeeiigbecbh Riceman')]")
	public WebElement LeftRailUserNameButton;

	@FindBy(xpath = "//div[@aria-label='Edit Profile' and @role='button']")
	public WebElement EditProfileButton;

	@FindBy(xpath = "//div[@aria-label='Edit profile' and @role='dialog']")
	public WebElement EditProfileDialog;

	/**
	 * Class constructor: takes a driver of type WebDriver, a wait of type
	 * WebDriverWait and a javascript executor of type JavascriptExecutor
	 * 
	 * @param driver
	 * @param wait
	 * @param jse
	 */
	public Homepage(WebDriver driver, WebDriverWait wait, JavascriptExecutor jsDriver, CrazyLogger logger,
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
	 * Clicks on the profile name on the left menu after successful login
	 * @throws InterruptedException 
	 */
	public void clickProfileNameOnLeftRail() throws InterruptedException {
		dIXMethods.driverIXClick(LeftRailUserNameButton);
		Thread.sleep(100);
	}

	/**
	 * Navigate through 1 elements sequentially
	 * 
	 * @param element1
	 * @throws InterruptedException
	 */
	public void navigateTo(WebElement element1) throws InterruptedException {
		dIXMethods.driverIXClick(element1);
		Thread.sleep(100);
	}
}
