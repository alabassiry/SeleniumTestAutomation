	private WebDriver driver;
	private DriverIXSetup xDriver;
	private Actions action;
	private AssertionError assertionMessage;
	private Exception exceptionMessage;
	private CrazyLogger logger;
	private JavascriptExecutor jsDriver;
	private NgWebDriver ngDriver;
	private WebDriverWait wait;
	private Homepage homepage;
	private Loginpage loginpage;
	private DriverIXMethods dIXMethods;

	@BeforeMethod
	@Parameters({ "browser", "state", "URL" , "tester"})
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
		homepage = new Homepage(driver, wait, jsDriver, logger, action);
		loginpage = new Loginpage(driver, wait, jsDriver, logger, action);
		dIXMethods = new DriverIXMethods(driver, wait, jsDriver, logger, action);
	}

	@Test
	@Parameters({"URL","tester"})
	public void test(String URL, String tester) throws Exception {
		try {
			dIXMethods.getURL(URL);
			ngDriver.waitForAngularRequestsToFinish();
			loginpage.login(tester);