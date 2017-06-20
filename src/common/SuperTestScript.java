package common;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import generics.Property;



@Listeners(CustomListener.class)
	public class SuperTestScript implements AutomationConstants
	{

		public Logger log;
		public WebDriver driver;
		public static String url;
		public static String un;
		public static String pwd;
		public static long timeout;
		public static boolean loginRequired=true;
		public static boolean logoutRequired=true;
		
		public SuperTestScript()
		{
			log=Logger.getLogger(this.getClass());
			Logger.getRootLogger().setLevel(org.apache.log4j.Level.INFO);
		}
		
		@BeforeSuite(groups={"PostRegistrationRuleMailerPositive" , "PostRegistrationRuleMailerNegative","CustomerEducationRuleMailerPositive", "GiftCardsRuleMailerPositive" ,"GiftCardsRuleMailerNegative", "PaymentIssuesRuleMailerPositive", "PaymentIssuesRuleMailerNegative", "AntiChurnRuleMailerPositive", "AntiChurnRuleMailerNegative", "ConfirmationsRuleMailerPositive","ConfirmationsRuleMailerNegative" , "CustomerEducationRuleMailerNegative", "RegistrationsPositive","RegistrationsNegative", "All"})
		public void initFramework()
		{
			log.info("Initializing Framework");
			url=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "URL");
			un=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "UN");
			pwd=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "PWD");
			timeout=Long.parseLong(Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "IMPLICIT"));
		}
		@AfterSuite(groups={"PostRegistrationRuleMailerPositive" , "PostRegistrationRuleMailerNegative","CustomerEducationRuleMailerPositive", "GiftCardsRuleMailerPositive" ,"GiftCardsRuleMailerNegative", "PaymentIssuesRuleMailerPositive", "PaymentIssuesRuleMailerNegative", "AntiChurnRuleMailerPositive", "AntiChurnRuleMailerNegative", "ConfirmationsRuleMailerPositive","ConfirmationsRuleMailerNegative" , "CustomerEducationRuleMailerNegative", "RegistrationsPositive","RegistrationsNegative", "All"})
		public void closeFramework()
		{
			log.info("Closing Framework");
		}
		
		@Parameters({"browser"})
		@BeforeTest(groups={"PostRegistrationRuleMailerPositive" , "PostRegistrationRuleMailerNegative","CustomerEducationRuleMailerPositive", "GiftCardsRuleMailerPositive" ,"GiftCardsRuleMailerNegative", "PaymentIssuesRuleMailerPositive", "PaymentIssuesRuleMailerNegative", "AntiChurnRuleMailerPositive", "AntiChurnRuleMailerNegative", "ConfirmationsRuleMailerPositive","ConfirmationsRuleMailerNegative" , "CustomerEducationRuleMailerNegative", "RegistrationsPositive","RegistrationsNegative", "All"})
		public void initBrowser(@Optional("chrome")String browser)
		{
			log.info("Execution Started on Browser: " +browser);
		}
		
		@Parameters({"browser"})
		@AfterTest(groups={"PostRegistrationRuleMailerPositive" , "PostRegistrationRuleMailerNegative","CustomerEducationRuleMailerPositive", "GiftCardsRuleMailerPositive" ,"GiftCardsRuleMailerNegative", "PaymentIssuesRuleMailerPositive", "PaymentIssuesRuleMailerNegative", "AntiChurnRuleMailerPositive", "AntiChurnRuleMailerNegative", "ConfirmationsRuleMailerPositive","ConfirmationsRuleMailerNegative" , "CustomerEducationRuleMailerNegative", "RegistrationsPositive","RegistrationsNegative", "All"})
		public void closeBrowser(@Optional("chrome")String browser)
		{
			log.info("Execution completed on browser: " +browser);
		}
		
		@Parameters({"browser"})
		//@BeforeClass(groups={"PostRegistrationRuleMailerPositive" , "PostRegistrationRuleMailerNegative", "GiftCardRuleMailer" , "ConfirmationsRuleMailerPositive", "ConfirmationsRuleMailerNegative" ,"CustomerEducationRuleMail", "Registrations", "All"})
		
		
	//	@AfterClass(groups={"PostRegistrationRuleMailerPositive" , "PostRegistrationRuleMailerNegative", "GiftCardRuleMailer" , "ConfirmationsRuleMailerPositive","ConfirmationsRuleMailerNegative" , "CustomerEducationRuleMail", "Registrations", "All"})
		
		
		@BeforeMethod(groups={"PostRegistrationRuleMailerPositive" , "PostRegistrationRuleMailerNegative","CustomerEducationRuleMailerPositive", "GiftCardsRuleMailerPositive" ,"GiftCardsRuleMailerNegative", "PaymentIssuesRuleMailerPositive", "PaymentIssuesRuleMailerNegative", "AntiChurnRuleMailerPositive", "AntiChurnRuleMailerNegative", "ConfirmationsRuleMailerPositive","ConfirmationsRuleMailerNegative" , "CustomerEducationRuleMailerNegative", "RegistrationsPositive","RegistrationsNegative", "All"})
		public void initApplication(@Optional("chrome") String browser)
		{
			log.info("Browser: " +browser);
			if(browser.equals("firefox"))
			{
				System.setProperty(GECKO_KEY, DRIVER_PATH+GECKO_FILE);
				driver= new FirefoxDriver();
				
			}
			
			else if(browser.equals("phantom"))
			{
				System.setProperty(PHANTOM_KEY, DRIVER_PATH+PHANTOM_FILE);
				driver= new PhantomJSDriver();
			}
			else
			{
				System.setProperty(CHROME_KEY, DRIVER_PATH+CHROME_FILE);
				driver= new ChromeDriver();
			}
			
			driver.manage().window().maximize();
			driver.get(url);
			driver.manage().deleteAllCookies();
			log.info("Timeout:" +timeout);
			driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
		}
		
		@AfterMethod(groups={"PostRegistrationRuleMailerPositive" , "PostRegistrationRuleMailerNegative","CustomerEducationRuleMailerPositive", "GiftCardsRuleMailerPositive" ,"GiftCardsRuleMailerNegative", "PaymentIssuesRuleMailerPositive", "PaymentIssuesRuleMailerNegative", "AntiChurnRuleMailerPositive", "AntiChurnRuleMailerNegative", "ConfirmationsRuleMailerPositive","ConfirmationsRuleMailerNegative" , "CustomerEducationRuleMailerNegative", "RegistrationsPositive","RegistrationsNegative", "All"})
		public void cleanApplication() throws InterruptedException
		{
			Thread.sleep(5000);
			driver.quit();
		}
		
		
		
	}


