# Selenium Test Advance Plus - NG Compatible Workspace Using Selenium

![N|Solid](https://www.selenium.dev/images/selenium_logo_large.png)

## The latest web testing technology every built!

This is a fully functional E2E and API workspace for testing and reuse.

# Features!

  - Selenium + Java
  - NG Web Driver (Paul Hammant)
  - Data driven testing using excel sheet for inputs
  - TestNG framework
  - Results uploaded to Jira automatically using Zypher Scale (UI tests only)
  - SureFire plugin
  - Customized reports (see webtesting\reports)
  - Screenshot on failure and attaching the screenshot to the report
  - Page factory
  - Maven framework
  - CI ready (run maven commands)
  
# Environment Setup!
  
	1.      Install Java environment from: https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html
	2.      Install eclipse from: https://www.eclipse.org/downloads/download.php?file=/technology/epp/downloads/release/mars/R/eclipse-java-mars-R-win32-x86_64.zip
	3.      Add environment variable JAVA_HOME with value: C:\Program Files\Java\jdk1.8.0_261
	4.      Add to the environment variable Path this value: %JAVA_HOME%\bin
	5.      Install Maven from: https://downloads.apache.org/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.zip
	6.      Extract the contents from the zip file and copy them in this location: C:\Program Files\Apache\apache-maven-3.6.3
	7.      Add environment variable M2_HOME with this value: C:\Program Files\Apache\apache-maven-3.6.3
	8.      Add environment variable M2 with this value: %M2_HOME%\bin
	9.      Add to the environment variable Path this value: %M2%
	10.     Add the environment variable MAVEN_OPTS with this value: -Xms256m -Xms512m
	11.     Open CMD and type:
			    mvn --version 
			        it should respond with the current installed maven version, if not then check that your environment variables are configured correctly
	12.     Open eclipse and click on the Help menu and select “Install New Software…” then click “Add…” and pass these parameters:
                i. Name: TestNG
                ii. Location: https://dl.bintray.com/testng-team/testng-eclipse-release/
	13.    When prompted which version to install; please select version 6.9.10 for TestNG, version 6.9.11 for TestNG M2E Integration and version 7.1.0 for TestNG P2 Feature
	14.    Restart your PC
	15.    Open Eclipse and create a workspace on the parent directory for the project folder (InstabugTask) so your project path should be: (~\<Workspace>\InstabugTask)
	16.    Import the project InstabugTask
	17.    You can now start testing!

### Test cases for UI

1) TST_T1, TST_T2 and TST_T3 are UI E2E test cases
    TST_T1 will test the login happy scenario for facebook
    TST_T2 will test the invalid email (data driven from excel sheet)
    TST_T3 will test the invalid password (data driver from excel sheet)
2) APITST_T1 -> APITST_T8 are API test cases
    APITST_T1 -> APITST_T6 will test the product fetch using query parameters (BVA and EP)
    APITST_T7 and APITST_T8 will test adding and deleting products
    
### API Bugs found

1) In APITST_T7 after deleting the product in the clean up, the swagger documentation states that the json body will return the data deleted not in an array, just a single entry of data; however, it was found that the returned json is an array

2) In APITST_T8 after deleting the product and then trying to get the same product by ID again; the swagger documentation states that the API will return error code (404); however, response code (200) was obtained with a data[] array empty.
	      

### Installation

Everything you need is in the pom.xml no prerequisites needed

Install the dependencies using this command

```sh
$ mvn dependency:sources
```

For javadoc run this command (optional)

```sh
$ mvn dependency:resolve -DClassifier=javadoc
```

### Plugins

SureFire is added in the pom file


### Running test cases

First clean the /target directory using this command
```sh
$ mvn clean
```

Use this command for the Regression suite
```sh
$ mvn test -PRegression
```

Use this command for the Sanity suite
```sh
$ mvn test -PSanity
```

Use this command for the API Regression suite
```sh
$ mvn test -PAPIRegression
```

### Reports
Report can be found in "..\reports"

### API log files
Log files acan be found in "..\APITestLogs"