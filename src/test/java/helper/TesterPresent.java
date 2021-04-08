package helper;

import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import java.io.File;

import org.testng.annotations.AfterSuite;

public class TesterPresent {
	public static ExtentTest test;
	public static ExtentReports report;
	private Commons commons = new Commons();
	public static String regressionSuite = null;

	@BeforeSuite
	public void beforeSuite() {
		regressionSuite = "yes";
		report = new ExtentReports(
				new File(System.getProperty("user.dir")).getParent() + "\\reports\\" + "PlanReport-_" + commons.getRandomString(3) + ".html");
	}

	@AfterSuite
	public void afterSuite() {
		report.flush();
	}

}
