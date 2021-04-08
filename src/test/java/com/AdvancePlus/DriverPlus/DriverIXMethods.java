package com.AdvancePlus.DriverPlus;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.paulhammant.ngwebdriver.NgWebDriver;
import helper.CrazyLogger;

public class DriverIXMethods {
	JavascriptExecutor jsDriver;
	WebDriver driver;
	WebDriverWait wait;
	CrazyLogger logger;
	Actions action;
	NgWebDriver ngDriver;

	public DriverIXMethods(WebDriver driver, WebDriverWait wait, JavascriptExecutor jsDriver, CrazyLogger logger,
			Actions action) {
		this.jsDriver = jsDriver;
		this.wait = wait;
		this.driver = driver;
		this.logger = logger;
		this.action = action;
	}

	public void driverIXSendKeys(WebElement element, String text) throws InterruptedException {
		Thread.sleep(50);
		wait.until(ExpectedConditions.visibilityOf(element)).clear();
		Thread.sleep(50);
		wait.until(ExpectedConditions.visibilityOf(element)).sendKeys(text);
		String check1 = wait.until(ExpectedConditions.visibilityOf(element)).getAttribute("value");
		try {
			Assert.assertEquals(check1, text);
		} catch (AssertionError e) {
			wait.until(ExpectedConditions.visibilityOf(element)).clear();
			Thread.sleep(50);
			wait.until(ExpectedConditions.visibilityOf(element)).sendKeys(text);
		}
		logger.info("Typed: " + text + " in element: " + element.toString());
		Thread.sleep(50);
	}

	public void driverIXClick(WebElement element) throws InterruptedException {
		Thread.sleep(50);
		try {
			wait.until(ExpectedConditions.visibilityOf(element)).click();
		} catch (StaleElementReferenceException e) {
			Thread.sleep(1000);
			wait.until(ExpectedConditions.visibilityOf(element)).click();
		} catch (ElementClickInterceptedException e) {
			action.click().build().perform();
			Thread.sleep(100);
			wait.until(ExpectedConditions.visibilityOf(element)).click();
		}
		catch (NotFoundException e) {
			Thread.sleep(1000);
			wait.until(ExpectedConditions.visibilityOf(element)).click();
		}
		logger.info("Clicked on element: " + element.toString());
		Thread.sleep(50);
	}

	public void driverIXScroll(int pixels) throws InterruptedException {
		jsDriver.executeScript("window.scrollBy(0,"+pixels+")", "");
		Thread.sleep(50);
	}

	public String getAttributeValueOfWebElement(WebElement element) throws InterruptedException {
		Thread.sleep(50);
		return wait.until(ExpectedConditions.visibilityOf(element)).getAttribute("value");
	}

	public void getURL(String URL) throws Exception {
		driver.get(URL);
		Thread.sleep(1000);
	}

}
