package helper;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;

import com.Jira.zephyr;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class CrazyLogger {
	private int counter;
	private String name;
	private String browser;
	private String state;
	private zephyr zph = new zephyr();
	private String tester;
	private WebDriver driver;
	private ExtentTest test;
	private ExtentReports report;
	private Commons commons = new Commons();
	private final String fontBlueLarge = "<font color=blue size= \"+1\" >";
	private final String fontRedLarge = "<font color=red size= \"+1\" >";
	private final String fontGreenLarge = "<font color=green size= \"+1\" >";
	private final String fontCloseTag = "</font>";

	public CrazyLogger(String name, WebDriver driver, String browser, String state, String tester) {
		this.name = name;
		this.driver = driver;
		this.browser = browser;
		this.state = state;
		this.tester = tester;
		System.out.println("####### SETUP METHOD INTITIALIZED SUCCESSFULLY #######");
	}

	public void step(String info) {
		counter++;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss:SSS");
		LocalDateTime now = LocalDateTime.now();
		String timenow = dtf.format(now);
		test.log(LogStatus.INFO,
				fontBlueLarge + timenow + " - " + name + " - " + state + " - Browser: " + browser + " - Tester: " + tester + " - STEP-" + counter + ": " + info + fontCloseTag);
		TesterPresent.test.log(LogStatus.INFO,
				fontBlueLarge + timenow + " - " + name + " - " + state + " - Browser: " + browser + " - Tester: " + tester + " - STEP-" + counter + ": " + info + fontCloseTag);
		System.out.println(timenow + " - " + name + " - " + state + " - Browser: " + browser + " - Tester: " + tester + " - STEP-" + counter + ": " + info);
	}

	public void info(String info) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss:SSS");
		LocalDateTime now = LocalDateTime.now();
		String timenow = dtf.format(now);
		test.log(LogStatus.INFO, timenow + " - " + name + " - " + state + " - Browser: " + browser + " - Tester: " + tester + " - [INFO]: " + info);
		TesterPresent.test.log(LogStatus.INFO, timenow + " - " + name + " - " + state + " - Browser: " + browser + " - Tester: " + tester + " - [INFO]: " + info);
		System.out.println(timenow + " - " + name + " - " + state + " - Browser: " + browser + " - Tester: " + tester + " - [INFO]: " + info);
	}

	public void failedStep(String info, Exception e) {
		counter++;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		String timenow = dtf.format(now);
		test.log(LogStatus.INFO,
				timenow + " - " + name + " - " + state + " - Browser: " + browser + " - Tester: " + tester + " - [STEP-INFO]: STEP-" + counter + ": " + info + "\n" + e);
		TesterPresent.test.log(LogStatus.INFO,
				timenow + " - " + name + " - " + state + " - Browser: " + browser + " - Tester: " + tester + " - [STEP-INFO]: STEP-" + counter + ": " + info + "\n" + e);
		System.out.println(timenow + " - " + name + " - " + state + " - Browser: " + browser + " - Tester: " + tester + " - [STEP-INFO]: STEP-" + counter + ": " + info + "\n" + e);
		Assert.fail();
	}

	public void logResult(ITestResult testResult, String className, String executionCycle, AssertionError assertionMessage,
			Exception exceptionMessage, String browser) throws Exception {
		System.out.println("####### CLEANUP METHOD EXECUTION #######");
		if (testResult.getStatus() == ITestResult.FAILURE) {
			System.out.println("####### EXECUTION FAILED! #######");
			if(state.equals("online")){
				switch (browser){
				case "chrome": 
					if(exceptionMessage == null){
						zph.postExecutionResult(className.replace("_", "-"), executionCycle, "marwan", "Fail", counter, assertionMessage.toString(), "Microsoft Windows 10 - Chrome (Latest Version)");
					}
					else if(assertionMessage == null){
						zph.postExecutionResult(className.replace("_", "-"), executionCycle, "marwan", "Fail", counter, exceptionMessage.toString(), "Microsoft Windows 10 - Chrome (Latest Version)");
					}
					break;
				case "firefox":
					if(exceptionMessage == null){
						zph.postExecutionResult(className.replace("_", "-"), executionCycle, "marwan", "Fail", counter, assertionMessage.toString(), "Microsoft Windows 10 - Firefox (Latest Version)");
					}
					else if(assertionMessage == null){
						zph.postExecutionResult(className.replace("_", "-"), executionCycle, "marwan", "Fail", counter, exceptionMessage.toString(), "Microsoft Windows 10 - Firefox (Latest Version)");
					}
					break;
				case "edge":
					if(exceptionMessage == null){
						zph.postExecutionResult(className.replace("_", "-"), executionCycle, "marwan", "Fail", counter, assertionMessage.toString(), "Microsoft Windows 10 - Edge (Latest Version)");
					}
					else if(assertionMessage == null){
						zph.postExecutionResult(className.replace("_", "-"), executionCycle, "marwan", "Fail", counter, exceptionMessage.toString(), "Microsoft Windows 10 - Edge (Latest Version)");
					}
					break;
				case "safari":
					if(exceptionMessage == null){
						zph.postExecutionResult(className.replace("_", "-"), executionCycle, "marwan", "Fail", counter, assertionMessage.toString(), "Apple Mac OS (Virtual) - Safari (Latest Version)");
					}
					else if(assertionMessage == null){
						zph.postExecutionResult(className.replace("_", "-"), executionCycle, "marwan", "Fail", counter, exceptionMessage.toString(), "Apple Mac OS (Virtual) - Safari (Latest Version)");
					}
					break;
					default:
						break;
				}
			}
			String filename = className + "_" + commons.getRandomString(5) + ".png";
			String directory = new File(System.getProperty("user.dir")).getParent() + "\\screenshots\\";
			File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(sourceFile, new File(directory + filename));
			String imagepath = test.addScreenCapture(directory + filename);
			test.log(LogStatus.FAIL, testResult.getMethod().getMethodName(), imagepath);
			if (assertionMessage != null) {
				test.log(LogStatus.FAIL, fontRedLarge + assertionMessage.getMessage() + fontCloseTag);
			}
			if (exceptionMessage != null) {
				test.log(LogStatus.FAIL, fontRedLarge + exceptionMessage.getMessage() + fontCloseTag);
			}
			if (exceptionMessage != null) {
				TesterPresent.test.log(LogStatus.FAIL, fontRedLarge + exceptionMessage.getMessage() + fontCloseTag);
			}
			if (assertionMessage != null) {
				TesterPresent.test.log(LogStatus.FAIL, fontRedLarge + assertionMessage.getMessage() + fontCloseTag);
			}
			TesterPresent.test.log(LogStatus.FAIL, testResult.getMethod().getMethodName(), imagepath);
		} else {
			System.out.println("####### EXECUTION PASSED! #######");
			if(state.equals("online")){
				switch (browser){
				case "chrome": 
					zph.postExecutionResult(className.replace("_", "-"), executionCycle, tester, "Pass", 0, "", "Microsoft Windows 10 - Chrome (Latest Version)");
					break;
				case "firefox":
					zph.postExecutionResult(className.replace("_", "-"), executionCycle, tester, "Pass", 0, "", "Microsoft Windows 10 - Firefox (Latest Version)");
					break;
				case "edge":
					zph.postExecutionResult(className.replace("_", "-"), executionCycle, tester, "Pass", 0, "", "Microsoft Windows 10 - Edge (Latest Version)");
					break;
				case "safari":
					zph.postExecutionResult(className.replace("_", "-"), executionCycle, tester, "Pass", 0, "", "Apple Mac OS (Virtual) - Safari (Latest Version)");
					break;
					default:
						break;
				}
			}
			test.log(LogStatus.PASS, fontGreenLarge + testResult.getMethod().getMethodName() + fontCloseTag);
			TesterPresent.test.log(LogStatus.PASS,
					fontGreenLarge + testResult.getMethod().getMethodName() + fontCloseTag);
		}
		report.endTest(test);
		TesterPresent.report.endTest(test);
		report.flush();
	}

	public void prepareTest(String className) {
		report = new ExtentReports(new File(System.getProperty("user.dir")).getParent() + "\\reports\\" + className
				+ "-" + "_" + commons.getRandomString(5) + ".html");
		test = report.startTest(className);
		if (TesterPresent.report != null) {
			TesterPresent.test = TesterPresent.report.startTest(className);
		} else {
			TesterPresent.report = new ExtentReports(
					new File(System.getProperty("user.dir")).getParent() + "\\reports\\" + "PlanReport" + ".html");
			TesterPresent.test = TesterPresent.report.startTest(className);
		}
	}
}