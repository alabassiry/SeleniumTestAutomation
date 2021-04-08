package com.AdvancePlus.Test;

import org.testng.annotations.Test;
import com.AdvancePlus.DriverPlus.DriverIXMethods;
import com.AdvancePlus.DriverPlus.DriverIXSetup;
import com.pagefactory.Loginpage;
import com.paulhammant.ngwebdriver.NgWebDriver;
import helper.CrazyLogger;
import helper.LoadXLSX;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;

public class TST_T2 {
	private WebDriver driver;
	private DriverIXSetup xDriver;
	private Actions action;
	private AssertionError assertionMessage;
	private Exception exceptionMessage;
	private CrazyLogger logger;
	private JavascriptExecutor jsDriver;
	private NgWebDriver ngDriver;
	private WebDriverWait wait;
	private Loginpage loginpage;
	private DriverIXMethods dIXMethods;
	private LoadXLSX excel;

	@DataProvider(name = "loginData")
	private Object[][] dataprovider() {
		Object[][] testData = excel.getTestData("Wrong_Email");
		return testData;
	}

	@BeforeTest
	public void preSetup() throws FileNotFoundException, IOException {
		excel = new LoadXLSX();
		excel.loadXlsx();
	}

	@BeforeMethod
	@Parameters({ "browser", "state", "URL", "tester" })
	public void setup(String browser, String state, String URL, String tester) throws Exception {
		xDriver = new DriverIXSetup(driver, browser, URL);
		driver = xDriver.setup(driver, browser);
		jsDriver = (JavascriptExecutor) driver;
		ngDriver = new NgWebDriver(jsDriver);
		wait = new WebDriverWait(driver, 10);
		driver.manage().window().maximize();
		logger = new CrazyLogger(this.getClass().getSimpleName(), driver, browser, state, tester);
		logger.prepareTest(this.getClass().getSimpleName());
		action = new Actions(driver);
		loginpage = new Loginpage(driver, wait, jsDriver, logger, action);
		dIXMethods = new DriverIXMethods(driver, wait, jsDriver, logger, action);
	}

	@Test(dataProvider = "loginData")
	public void test(String Email, String Password) throws Exception {
		try {
			logger.step("Navigate to www.facebook.com");
			dIXMethods.getURL("https://www.facebook.com");
			ngDriver.waitForAngularRequestsToFinish();

			logger.step("Try logging in using invalid email");
			dIXMethods.driverIXSendKeys(loginpage.email, Email);
			dIXMethods.driverIXSendKeys(loginpage.pass, Password);
			dIXMethods.driverIXClick(loginpage.loginButton);
			Assert.assertEquals(true, loginpage.wrongEmailLoginMessage.isDisplayed());
		}

		catch (AssertionError e) {
			assertionMessage = e;
			throw e;
		}

		catch (Exception e) {
			exceptionMessage = e;
			throw e;
		}

	}

	@AfterMethod(alwaysRun = true)
	@Parameters({ "executionCycle", "browser" })
	public void cleanup(ITestResult testResult, String executionCycle, String browser) throws Exception {
		logger.logResult(testResult, this.getClass().getSimpleName(), executionCycle, assertionMessage,
				exceptionMessage, browser);
		driver.quit();
	}

}