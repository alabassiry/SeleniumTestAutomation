		
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
	@Parameters({"executionCycle" , "browser"})
	public void cleanup(ITestResult testResult, String executionCycle, String browser) throws Exception {
		logger.logResult(testResult, this.getClass().getSimpleName(),executionCycle, assertionMessage, exceptionMessage, browser);
		driver.quit();
	}

}