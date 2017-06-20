package confirmationsRuleMailer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import Mongo.Email_logs.Email;
import common.PasswordFromAdmin;
import common.SuperTestScript;
import generics.AddDate;
import generics.Database;
import generics.Excel;
import generics.MongoDBUtilMorphia;
import generics.Property;
import interfaceForApiOrDB.InformationFromBackend;
import pages.AdminPage;
import pages.ChangingSubscriptionPage;
import pages.HomePage;
import pages.LoginPage;
import pages.MyAccountPage;

public class SubscriptionUpgrade extends SuperTestScript
{
	public static String un;
	public static String pwd;
	public static String sub;
	public static String member;
	public static String adminUn;
	public static String adminPwd;
	public static String adminUrl;
	
	
	MongoDBUtilMorphia mongoutil = new MongoDBUtilMorphia();
	 Datastore ds = mongoutil.getMorphiaDatastoreForNlob();
	 Email email=new Email();
	 SoftAssert soft=new SoftAssert();
	 
	 
	public SubscriptionUpgrade()
	{
		loginRequired=false;
		logoutRequired=false;
	}
	
	@Test(enabled=true, priority=21, groups={"ConfirmationsRuleMailerPositive" , "All"})
	public void existingMemberSubscriptionUpgrade() throws EncryptedDocumentException, InvalidFormatException, IOException
	{
		
		log.info("SUBSCRIPTION UPDATE FOR MEMBER PAYING.");
		
		un=Excel.getCellValue(INPUT_PATH, "SubsUpgrade", 1, 0);					//<------------Using Bas Member as Input
		pwd=PasswordFromAdmin.gettingPasswordFromAdmin(un);
		//sub=Excel.getCellValue(INPUT_PATH, "SubsUpgrade", 1, 1);
		//member=Excel.getCellValue(INPUT_PATH, "SubsUpgrade", 1, 2);
		adminUn=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINUN");
		adminPwd=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINPWD");
		adminUrl=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINURL");

		
		
	//------------------------------------LOGIN AS A MEMBER_PAYING EXISTING USER--------------------------------------------//
		
		log.info("Clicking on Login Button");
		HomePage home=new HomePage(driver);
		home.clickLoginLink();
		log.info("Navigating to Login Page");
		
		log.info("Entering login details with username as : '" +un+ "' and password as : '" +pwd+ "'" );
		LoginPage login=new LoginPage(driver);
		login.setEmailId(un);
		login.setPassword(pwd);
		login.clickLoginButton();
		log.info("Navigating to My Account Page");
		
		MyAccountPage account=new MyAccountPage(driver);
		
		String initSubDate = account.getRunDate();
		String customerId= Database.executeQuery("select customerid from customerinfo where email='" +un+ "'");
		String runDate= Database.executeQuery("select next_subscription_run_date from customer2subscriptionmap where customerid=" +customerId);
		String memTypeCode= Database.executeQuery("select member_type_code from customerinfo  where email='" +un+ "'");
		
	//-------------------------------------VALIDATING FOR THE SUBSCRIPTION TYPE---------------------------------------------//
		
		
		String initText = account.getMyOrder();
		if(initText.contains("BAS")|| initText.contains("STANDARD"))
		{
			log.info("PRESENT SUBSCRIPTION TYPE AND PRICE PER MONTH IS : "+initText);
			
			log.info("Clicking on Subscription Upgrade Button");
			
			account.clickToChangeSubscription();
			log.info("Navigating to Subscription Page");
			
			log.info("UpGrading to higher subscriptions");
			ChangingSubscriptionPage change=new ChangingSubscriptionPage(driver);
			change.changeToPremium();
			change.clickToContinue();
			change.clickToAccept();
			log.info("Navigating back to My Account page and LogOuts");
			

			Excel.shiftingRowsUp(INPUT_PATH, "SubsUpgrade", 1);
			
			
			home.clickNextoryLogo();
			home.clickAccountLink();
			
			String text=account.getMySubscription();
			String subs=text.substring(22);
			
			String lastSubDate = account.getRunDate();
			String finalText= account.getMyOrder();
			String finalTextTrim= finalText.replaceAll("\\s+", "");
			
//				log.info("Validating the Subscription Run Date from Database");
//				String exp= runDate;
//				String act=driver.findElement(By.xpath("//div[@class='my-account-wrapper clearfix']//li[@class='left']")).getText();
//				Assert.assertEquals(act, exp);	
//				log.info("Previous to upgrading, Subscription Run Date was :" +exp);
//				log.info("Post-Upgrading, Subscription Run Date is : "+act);
			
				
			log.info("Validating the Subscription Run Date from WebSite");	
			Assert.assertEquals(initSubDate, lastSubDate);
			log.info("Previous to upgrading, Subscription Run Date was :" +initSubDate);
			log.info("Post-Upgrading, Subscription Run Date is : "+lastSubDate);
			
			Assert.assertEquals(finalTextTrim , "PREMIUM:249kr/månad");
			log.info(finalText);
			log.info("SUCCESSFULLY UPGRADED TO PREMIUM");
			
			log.info("logging out");
			MyAccountPage acc=new MyAccountPage(driver);
			acc.clickLogOut();
			
			new WebDriverWait(driver,30).until(ExpectedConditions.titleContains("Ljudböcker & E-böcker - Lyssna & läs gratis i mobilen"));
			
			
			//----------------------------------------VALIDATING INTO THE ADMIN SITE---------------------------------------------------//
		
			//CODE TO VALIDATE PREVIOUS SUBSCRIPTION TYPE CHANGES TO PREMIUM IMMEDIATELY
			log.info("VALIDATING INTO ADMIN SITE");

			driver.manage().deleteAllCookies();
			driver.get(adminUrl);
			
			AdminPage admin=new AdminPage(driver);
			admin.setUserName(adminUn);
			admin.setPassword(adminPwd);
			admin.clickLogin();
			admin.clickCustMgmt();
			admin.setEPost(un);
			admin.clickSearch();
			String memberStatus = admin.getMemberType();
			String subsType = admin.getSubsType();
			
			
			
			if(memTypeCode.equalsIgnoreCase("304001"))   		// if the email id picked belongs to Member Paying Type
			{

				Assert.assertEquals(memberStatus, "MEMBER_PAYING");
				log.info("Membership Status is: " +memberStatus + " in Admin Site");
				
				if(subs.equalsIgnoreCase("BAS"))
				{
					subs= "BASE";
				}
				
				Assert.assertEquals(subsType, subs);
				log.info("Subscription Type is: " +subsType+ " in Admin Site");
			}
			
			
			
			else if(memTypeCode.equalsIgnoreCase("203002"))			// if the email id picked belongs to Free Trial Member Type
			{
				Assert.assertEquals(memberStatus, "FREE_TRIAL_MEMBER");
				log.info("Membership Status is: " +memberStatus + " in Admin Site");
				
				if(subs.equalsIgnoreCase("BAS"))
				{
					subs= "BASE";
				}
				
				Assert.assertEquals(subsType, subs);
				log.info("Subscription Type is: " +subsType+ " in Admin Site");
			}
			
			
			
			
			else if(memTypeCode.equalsIgnoreCase("202002"))			// if the email id picked belongs to Free Campaign Member Type
			{
				Assert.assertEquals(memberStatus, "FREE_CAMPAIGN_MEMBER");
				log.info("Membership Status is: " +memberStatus + " in Admin Site");
				
				if(subs.equalsIgnoreCase("BAS"))
				{
					subs= "BASE";
				}
				
				Assert.assertEquals(subsType, subs);
				log.info("Subscription Type is: " +subsType+ " in Admin Site");
			}
			
			
			
			else if(memTypeCode.equalsIgnoreCase("302002"))				// if the email id picked belongs to MEMBER_CAMPAIGN_EXISTING Type
			{
				Assert.assertEquals(memberStatus, "MEMBER_CAMPAIGN_EXISTING");
				log.info("Membership Status is: " +memberStatus + " in Admin Site");
				
				if(subs.equalsIgnoreCase("BAS"))
				{
					subs= "BASE";
				}
				
				Assert.assertEquals(subsType, subs);
				log.info("Subscription Type is: " +subsType+ " in Admin Site");
			}
			
			
			else if(memTypeCode.equalsIgnoreCase("301002"))				// if the email id picked belongs to MEMBER_GIFTCARD_EXISTING Type
			{
				Assert.assertEquals(memberStatus, "MEMBER_GIFTCARD_EXISTING");
				log.info("Membership Status is: " +memberStatus + " in Admin Site");
				
				if(subs.equalsIgnoreCase("BAS"))
				{
					subs= "BASE";
				}
				
				Assert.assertEquals(subsType, subs);
				log.info("Subscription Type is: " +subsType+ " in Admin Site");
			}
			
			else if(memTypeCode.equalsIgnoreCase("305006"))				// if the email id picked belongs to MEMBER_CARD_EXPIRYDUE Type
			{
				Assert.assertEquals(memberStatus, "MEMBER_CARD_EXPIRYDUE");
				log.info("Membership Status is: " +memberStatus + " in Admin Site");
				
				if(subs.equalsIgnoreCase("BAS"))
				{
					subs= "BASE";
				}
				
				Assert.assertEquals(subsType, subs);
				log.info("Subscription Type is: " +subsType+ " in Admin Site");
			}
			
			
			admin.clickLogout();
			driver.get(url);
		
		
		}
		
		
		else
		{
			log.info("Subscription Type is already Premium and It can't be upgraded further");
			
			//log.info(un + pwd + sub + member);
			
//			Excel.shiftingRowsDown(INPUT_PATH, "SubsDowngrade", 1);
//			Excel.setExcelData(INPUT_PATH, "SubsDowngrade", 1, 0, un);
//			Excel.setExcelData(INPUT_PATH, "SubsDowngrade", 1, 1, pwd);
//			Excel.setExcelData(INPUT_PATH, "SubsDowngrade", 1, 2, sub);
//			Excel.setExcelData(INPUT_PATH, "SubsDowngrade", 1, 3, member );
			
			Excel.shiftingRowsUp(INPUT_PATH, "SubsUpgrade", 1);
			log.info("logging out");
			
			account.clickLogOut();
			
			new WebDriverWait(driver,30).until(ExpectedConditions.titleContains("Ljudböcker & E-böcker - Lyssna & läs gratis i mobilen"));
			
			existingMemberSubscriptionUpgrade();
			
			
		}
		
		
		//-------------------------------VALIDATING THE MAIL TRIGGERING-------------------------------------//	  		

		
		
		
		log.info("Fetching data from databse MySQL");
		 InformationFromBackend info=new InformationFromBackend();
		 info.getDataForCustomerInfo("SubscriptionUpgrade");
		 
		String customerid =  InformationFromBackend.result;
		log.info("Customerid selected from Sql is="+customerid);
		
		 log.info("Fetching data from MongoDB");
		 
		Query query=ds.createQuery(Email.class);
		query.filter("customerid",customerid);
		/*query1.field("from").equals("kundservice@nextory.se");*/
		//query.field("customerid").equals("");
		/*query1.field("mobilenumber").equals("");
		query1.field("firstname").equals("");
		query1.field("lastname").equals("");
		query1.field("to").equals("");
		query1.field("subject").equals("");
		query1.field("mailsentdate").equals("");
		query1.field("reason").equals("");*/
		try
		{
			//custList = (Membership) query.get();
		     // if(custList != null)
			//Email emailList=(Email) query1.get();
			ArrayList<Email> emailList=(ArrayList<Email>)query.asList();
			
			 log.info("Email list="+emailList);
			 for(Email email:emailList)
			 {
				log.info("class="+email.getClass());
				log.info( "CustomerID=" + customerid );
				log.info("Firstname="+email.getFirstname());
				email.getLastname();
				email.getFrom();
				email.getMobilenumber();
				log.info(email.getReason());
				log.info(email.getTriggerName());
				log.info(email.getSubject());
				
				log.info("Mail-Subject verification");
				String expectedSubject="Ditt konto shar blivit uppgraderat" ;	
		        String actualSubject=email.getSubject();
		        Assert.assertEquals(actualSubject, expectedSubject);
				log.info("Subject verified successfully");
		     
		   	    log.info("Response verification");
		   	    String expectedResponse="Success";
				String actualResponse=email.getReason();
				Assert.assertEquals(actualResponse,expectedResponse);
				log.info("Response is verified successfully");
				
			log.info("Triggered message verification");
			 String expectedTriggerName="Subscription upgrade" ;	
		     String actualTriggerName=email.getTriggerName();
		    Assert.assertEquals(actualTriggerName, expectedTriggerName);
			log.info("Message Triggered successfully"); 
			
			log.info("Mail sent Date verification");
			String expectedupdateddate=AddDate.currentDate();
		   	Date actualupdateddate=email.getMailsentdate();
		   	Assert.assertEquals(actualupdateddate, expectedupdateddate);
		   	log.info("Mail sent date verified successfully");
		   	 
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}
		


	//----------------------------------------- NEGATIVE FLOWS ------------------------------------------------------//

	@Test(enabled=true, priority=22, groups={"ConfirmationsRuleMailerNegative" , "All"})
	public void selectingSameSubscriptionToUpgrade() throws EncryptedDocumentException, InvalidFormatException, IOException
	{
		
		log.info("NEGATIVE FLOW: SELECTING THE SAME SUBSCRIPTION WHILE UPGRADING");
		
		un=Excel.getCellValue(INPUT_PATH, "SubsUpgrade", 1, 0);					//<------------Using Bas Member as Input
		pwd=PasswordFromAdmin.gettingPasswordFromAdmin(un);
		//sub=Excel.getCellValue(INPUT_PATH, "SubsUpgrade", 1, 1);
		//member=Excel.getCellValue(INPUT_PATH, "SubsUpgrade", 1, 2);
		adminUn=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINUN");
		adminPwd=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINPWD");
		adminUrl=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINURL");

		
		
	//------------------------------------LOGIN AS A MEMBER_PAYING EXISTING USER--------------------------------------------//
		
		log.info("Clicking on Login Button");
		HomePage home=new HomePage(driver);
		home.clickLoginLink();
		log.info("Navigating to Login Page");
		
		log.info("Entering login details with username as : '" +un+ "' and password as : '" +pwd+ "'" );
		LoginPage login=new LoginPage(driver);
		login.setEmailId(un);
		login.setPassword(pwd);
		login.clickLoginButton();
		log.info("Navigating to My Account Page");
		
		MyAccountPage account=new MyAccountPage(driver);
		
		String initSubDate = account.getRunDate();
		String customerId= Database.executeQuery("select customerid from customerinfo where email='" +un+ "'");
		String runDate= Database.executeQuery("select next_subscription_run_date from customer2subscriptionmap where customerid=" +customerId);
		String memTypeCode= Database.executeQuery("select member_type_code from customerinfo  where email='" +un+ "'");
		
	//-------------------------------------VALIDATING FOR THE SUBSCRIPTION TYPE---------------------------------------------//
		
		
		String initText = account.getMyOrder();

			log.info("PRESENT SUBSCRIPTION TYPE AND PRICE PER MONTH IS : "+initText);
			
			log.info("Clicking on Subscription Upgrade Button");
			
			account.clickToChangeSubscription();
			log.info("Navigating to Subscription Page");
			
			log.info("Clicking Same Subscription for alert pop up");
			ChangingSubscriptionPage change=new ChangingSubscriptionPage(driver);
			
			if(initText.contains("BAS"))
			{
			change.changeToBas();
			change.clickToContinue();
			}
			
			else if(initText.contains("STANDARD"))
			{
			change.changeToStandard();
			change.clickToContinue();
			}
			
			else if(initText.contains("PREMIUM"))
			{
			change.changeToPremium();
			change.clickToContinue();
			}
			
			String actMsg=driver.findElement(By.xpath("//p[@class='dynamic']")).getText();
			String actMsgTrim= actMsg.replaceAll("\\s+", "");
			String expMsg= "Väljdetabonnemangduvillbytatill";
			
			soft.assertEquals(actMsgTrim, expMsg, "Välj det abonnemang du vill byta Assertion Failed");
			
			driver.findElement(By.xpath("//button[@class='new blueButton nedgradering closePopUp closePopUp-button ']")).click();
			
			log.info("UpGrading to higher subscriptions");
			
			if(initText.contains("BAS")|| initText.contains("STANDARD"))
			{
			
			change.changeToPremium();
			change.clickToContinue();
			change.clickToAccept();
			log.info("Navigating back to My Account page and LogOuts");
			
//			Excel.shiftingRowsDown(INPUT_PATH, "SubsDowngrade", 1);
//			Excel.setExcelData(INPUT_PATH, "SubsDowngrade", 1, 0, un);
//			Excel.setExcelData(INPUT_PATH, "SubsDowngrade", 1, 1, pwd);
//			Excel.setExcelData(INPUT_PATH, "SubsDowngrade", 1, 2, sub);
//			Excel.setExcelData(INPUT_PATH, "SubsDowngrade", 1, 3, member );
			
		
			Excel.shiftingRowsUp(INPUT_PATH, "SubsUpgrade", 1);
			
			home.clickNextoryLogo();
			home.clickAccountLink();
			
			String text= account.getMySubscription();
			String subs=text.substring(22);
			
			String lastSubDate = account.getRunDate();
			String finalText= account.getMyOrder();
			String finalTextTrim= finalText.replaceAll("\\s+", "");
			
//				log.info("Validating the Subscription Run Date from Database");
//				String exp= runDate;
//				String act=driver.findElement(By.xpath("//div[@class='my-account-wrapper clearfix']//li[@class='left']")).getText();
//				Assert.assertEquals(act, exp);	
//				log.info("Previous to upgrading, Subscription Run Date was :" +exp);
//				log.info("Post-Upgrading, Subscription Run Date is : "+act);
			
				
			log.info("Validating the Subscription Run Date from WebSite");	
			Assert.assertEquals(initSubDate, lastSubDate);
			log.info("Previous to upgrading, Subscription Run Date was :" +initSubDate);
			log.info("Post-Upgrading, Subscription Run Date is : "+lastSubDate);
			
			Assert.assertEquals(finalTextTrim , "PREMIUM:249kr/månad");
			log.info(finalText);
			log.info("SUCCESSFULLY UPGRADED TO PREMIUM");
			
			log.info("logging out");
			MyAccountPage acc=new MyAccountPage(driver);
			acc.clickLogOut();
			
			new WebDriverWait(driver,30).until(ExpectedConditions.titleContains("Ljudböcker & E-böcker - Lyssna & läs gratis i mobilen"));
			
			
			//----------------------------------------VALIDATING INTO THE ADMIN SITE---------------------------------------------------//
		
			//CODE TO VALIDATE PREVIOUS SUBSCRIPTION TYPE CHANGES TO PREMIUM IMMEDIATELY
			log.info("VALIDATING INTO ADMIN SITE");

			driver.manage().deleteAllCookies();
			driver.get(adminUrl);
			
			AdminPage admin=new AdminPage(driver);
			admin.setUserName(adminUn);
			admin.setPassword(adminPwd);
			admin.clickLogin();
			admin.clickCustMgmt();
			admin.setEPost(un);
			admin.clickSearch();
			String memberStatus = admin.getMemberType();
			String subsType = admin.getSubsType();
			
			
			
			if(memTypeCode.equalsIgnoreCase("304001"))   		// if the email id picked belongs to Member Paying Type
			{

				Assert.assertEquals(memberStatus, "MEMBER_PAYING");
				log.info("Membership Status is: " +memberStatus + " in Admin Site");
				
				if(subs.equalsIgnoreCase("BAS"))
				{
					subs= "BASE";
				}
				
				Assert.assertEquals(subsType, subs);
				log.info("Subscription Type is: " +subsType+ " in Admin Site");
			}
			
			
			
			else if(memTypeCode.equalsIgnoreCase("203002"))			// if the email id picked belongs to Free Trial Member Type
			{
				Assert.assertEquals(memberStatus, "FREE_TRIAL_MEMBER");
				log.info("Membership Status is: " +memberStatus + " in Admin Site");
				
				if(subs.equalsIgnoreCase("BAS"))
				{
					subs= "BASE";
				}
				
				Assert.assertEquals(subsType, subs);
				log.info("Subscription Type is: " +subsType+ " in Admin Site");
			}
			
			
			
			
			else if(memTypeCode.equalsIgnoreCase("202002"))			// if the email id picked belongs to Free Campaign Member Type
			{
				Assert.assertEquals(memberStatus, "FREE_CAMPAIGN_MEMBER");
				log.info("Membership Status is: " +memberStatus + " in Admin Site");
				
				if(subs.equalsIgnoreCase("BAS"))
				{
					subs= "BASE";
				}
				
				Assert.assertEquals(subsType, subs);
				log.info("Subscription Type is: " +subsType+ " in Admin Site");
			}
			
			
			
			else if(memTypeCode.equalsIgnoreCase("302002"))				// if the email id picked belongs to MEMBER_CAMPAIGN_EXISTING Type
			{
				Assert.assertEquals(memberStatus, "MEMBER_CAMPAIGN_EXISTING");
				log.info("Membership Status is: " +memberStatus + " in Admin Site");
				
				if(subs.equalsIgnoreCase("BAS"))
				{
					subs= "BASE";
				}
				
				Assert.assertEquals(subsType, subs);
				log.info("Subscription Type is: " +subsType+ " in Admin Site");
			}
			
			
			else if(memTypeCode.equalsIgnoreCase("301002"))				// if the email id picked belongs to MEMBER_GIFTCARD_EXISTING Type
			{
				Assert.assertEquals(memberStatus, "MEMBER_GIFTCARD_EXISTING");
				log.info("Membership Status is: " +memberStatus + " in Admin Site");
				
				if(subs.equalsIgnoreCase("BAS"))
				{
					subs= "BASE";
				}
				
				Assert.assertEquals(subsType, subs);
				log.info("Subscription Type is: " +subsType+ " in Admin Site");
			}
			
			else if(memTypeCode.equalsIgnoreCase("305006"))				// if the email id picked belongs to MEMBER_CARD_EXPIRYDUE Type
			{
				Assert.assertEquals(memberStatus, "MEMBER_CARD_EXPIRYDUE");
				log.info("Membership Status is: " +memberStatus + " in Admin Site");
				
				if(subs.equalsIgnoreCase("BAS"))
				{
					subs= "BASE";
				}
				
				Assert.assertEquals(subsType, subs);
				log.info("Subscription Type is: " +subsType+ " in Admin Site");
			}
			
			
			admin.clickLogout();
			driver.get(url);
		
		
		}
		
		
		else
		{
			log.info("Subscription Type is already Premium and It can't be upgraded further");
			

			
			Excel.shiftingRowsUp(INPUT_PATH, "SubsUpgrade", 1);
			log.info("logging out");
			
			account.clickLogOut();
			
			new WebDriverWait(driver,30).until(ExpectedConditions.titleContains("Ljudböcker & E-böcker - Lyssna & läs gratis i mobilen"));
			
			selectingSameSubscriptionToUpgrade();
			
			
		}
			
			
			
			//-------------------------------VALIDATING THE MAIL TRIGGERING-------------------------------------//	  		

			
			
			
			log.info("Fetching data from databse MySQL");
			 InformationFromBackend info=new InformationFromBackend();
			 info.getDataForCustomerInfo("SubscriptionUpgrade");
			 
			String customerid =  InformationFromBackend.result;
			log.info("Customerid selected from Sql is="+customerid);
			
			 log.info("Fetching data from MongoDB");
			 
			Query query=ds.createQuery(Email.class);
			query.filter("customerid",customerid);
			/*query1.field("from").equals("kundservice@nextory.se");*/
			//query.field("customerid").equals("");
			/*query1.field("mobilenumber").equals("");
			query1.field("firstname").equals("");
			query1.field("lastname").equals("");
			query1.field("to").equals("");
			query1.field("subject").equals("");
			query1.field("mailsentdate").equals("");
			query1.field("reason").equals("");*/
			try
			{
				//custList = (Membership) query.get();
			     // if(custList != null)
				//Email emailList=(Email) query1.get();
				ArrayList<Email> emailList=(ArrayList<Email>)query.asList();
				
				 log.info("Email list="+emailList);
				 for(Email email:emailList)
				 {
					log.info("class="+email.getClass());
					log.info( "CustomerID=" + customerid );
					log.info("Firstname="+email.getFirstname());
					email.getLastname();
					email.getFrom();
					email.getMobilenumber();
					log.info(email.getReason());
					log.info(email.getTriggerName());
					log.info(email.getSubject());
					
					log.info("Mail-Subject verification");
					String expectedSubject="Ditt konto shar blivit uppgraderat" ;	
			        String actualSubject=email.getSubject();
			        Assert.assertEquals(actualSubject, expectedSubject);
					log.info("Subject verified successfully");
			     
			   	    log.info("Response verification");
			   	    String expectedResponse="Success";
					String actualResponse=email.getReason();
					Assert.assertEquals(actualResponse,expectedResponse);
					log.info("Response is verified successfully");
					
				log.info("Triggered message verification");
				 String expectedTriggerName="Subscription upgrade" ;	
			     String actualTriggerName=email.getTriggerName();
			    Assert.assertEquals(actualTriggerName, expectedTriggerName);
				log.info("Message Triggered successfully"); 
				
				log.info("Mail sent Date verification");
				String expectedupdateddate=AddDate.currentDate();
			   	Date actualupdateddate=email.getMailsentdate();
			   	Assert.assertEquals(actualupdateddate, expectedupdateddate);
			   	log.info("Mail sent date verified successfully");
			   	 
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}		
		}


	//--------------------------------CHECKING CANCEL BUTTONS------------------------------------------------------//

	@Test(enabled=true, priority=23, groups={"ConfirmationsRuleMailerNegative" , "All"})
	public void checkingCancelButtons()
	{
		log.info("SUBSCRIPTION UPDATE FOR MEMBER PAYING: CANCEL BUTTONS");
		
		un=Excel.getCellValue(INPUT_PATH, "SubsUpgrade", 1, 0);					//<------------Using Bas Member as Input
		pwd=PasswordFromAdmin.gettingPasswordFromAdmin(un);
		//sub=Excel.getCellValue(INPUT_PATH, "SubsUpgrade", 1, 1);
		//member=Excel.getCellValue(INPUT_PATH, "SubsUpgrade", 1, 2);
		adminUn=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINUN");
		adminPwd=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINPWD");
		adminUrl=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINURL");

		
		
	//------------------------------------LOGIN AS A MEMBER_PAYING EXISTING USER--------------------------------------------//
		
		log.info("Clicking on Login Button");
		HomePage home=new HomePage(driver);
		home.clickLoginLink();
		log.info("Navigating to Login Page");
		
		log.info("Entering login details with username as : '" +un+ "' and password as : '" +pwd+ "'" );
		LoginPage login=new LoginPage(driver);
		login.setEmailId(un);
		login.setPassword(pwd);
		login.clickLoginButton();
		log.info("Navigating to My Account Page");
		
		MyAccountPage account=new MyAccountPage(driver);
		
		String initSubDate = account.getRunDate();
		String customerId= Database.executeQuery("select customerid from customerinfo where email='" +un+ "'");
		String runDate= Database.executeQuery("select next_subscription_run_date from customer2subscriptionmap where customerid=" +customerId);
		String memTypeCode= Database.executeQuery("select member_type_code from customerinfo  where email='" +un+ "'");
		
	//-------------------------------------VALIDATING FOR THE SUBSCRIPTION TYPE---------------------------------------------//
		
		
		String initText = account.getMyOrder();
		
			log.info("PRESENT SUBSCRIPTION TYPE AND PRICE PER MONTH IS : "+initText);
			
			log.info("Clicking on Subscription Upgrade Button");
			
			account.clickToChangeSubscription();
			log.info("Navigating to Subscription Page");
			
			ChangingSubscriptionPage change=new ChangingSubscriptionPage(driver);
			change.changeToStandard();							
			change.changeToPremium();							
			change.clickToGoBack();											//going back to Account Page.
			
			account.clickToChangeSubscription();
			
			if(initText.contains("BAS"))
			{
			change.changeToBas();
			change.clickToContinue();
			}
			
			else if(initText.contains("STANDARD"))
			{
			change.changeToStandard();
			change.clickToContinue();
			}
			
			else if(initText.contains("PREMIUM"))
			{
			change.changeToPremium();
			change.clickToContinue();
			}
			
			String actMsg=driver.findElement(By.xpath("//p[@class='dynamic']")).getText();
			String actMsgTrim= actMsg.replaceAll("\\s+", "");
			String expMsg= "Väljdetabonnemangduvillbytatill";
			
			soft.assertEquals(actMsgTrim, expMsg, "Välj det abonnemang du vill byta Assertion Failed");
			driver.findElement(By.xpath("//div[@class='closepop closePopUp']")).click();
			
			
			if(initText.contains("BAS")|| initText.contains("STANDARD"))
			{
			
			change.changeToPremium();
			change.clickToContinue();
			change.clickToAccept();
			log.info("Navigating back to My Account page and LogOuts");
			
//			Excel.shiftingRowsDown(INPUT_PATH, "SubsDowngrade", 1);
//			Excel.setExcelData(INPUT_PATH, "SubsDowngrade", 1, 0, un);
//			Excel.setExcelData(INPUT_PATH, "SubsDowngrade", 1, 1, pwd);
//			Excel.setExcelData(INPUT_PATH, "SubsDowngrade", 1, 2, sub);
//			Excel.setExcelData(INPUT_PATH, "SubsDowngrade", 1, 3, member );
			
		
			Excel.shiftingRowsUp(INPUT_PATH, "SubsUpgrade", 1);
			
			
			home.clickNextoryLogo();
			home.clickAccountLink();
			
			String text= account.getMySubscription();
			String subs=text.substring(22);
			
			String lastSubDate = account.getRunDate();
			String finalText= account.getMyOrder();
			String finalTextTrim= finalText.replaceAll("\\s+", "");
			
//				log.info("Validating the Subscription Run Date from Database");
//				String exp= runDate;
//				String act=driver.findElement(By.xpath("//div[@class='my-account-wrapper clearfix']//li[@class='left']")).getText();
//				Assert.assertEquals(act, exp);	
//				log.info("Previous to upgrading, Subscription Run Date was :" +exp);
//				log.info("Post-Upgrading, Subscription Run Date is : "+act);
			
				
			log.info("Validating the Subscription Run Date from WebSite");	
			Assert.assertEquals(initSubDate, lastSubDate);
			log.info("Previous to upgrading, Subscription Run Date was :" +initSubDate);
			log.info("Post-Upgrading, Subscription Run Date is : "+lastSubDate);
			
			Assert.assertEquals(finalTextTrim , "PREMIUM:249kr/månad");
			log.info(finalText);
			log.info("SUCCESSFULLY UPGRADED TO PREMIUM");
			
			log.info("logging out");
			
			account.clickLogOut();
			
			new WebDriverWait(driver,30).until(ExpectedConditions.titleContains("Ljudböcker & E-böcker - Lyssna & läs gratis i mobilen"));
			
			
			//----------------------------------------VALIDATING INTO THE ADMIN SITE---------------------------------------------------//
		
			//CODE TO VALIDATE PREVIOUS SUBSCRIPTION TYPE CHANGES TO PREMIUM IMMEDIATELY
			log.info("VALIDATING INTO ADMIN SITE");

			driver.manage().deleteAllCookies();
			driver.get(adminUrl);
			
			AdminPage admin=new AdminPage(driver);
			admin.setUserName(adminUn);
			admin.setPassword(adminPwd);
			admin.clickLogin();
			admin.clickCustMgmt();
			admin.setEPost(un);
			admin.clickSearch();
			String memberStatus = admin.getMemberType();
			String subsType = admin.getSubsType();
			
			
			
			if(memTypeCode.equalsIgnoreCase("304001"))   		// if the email id picked belongs to Member Paying Type
			{

				Assert.assertEquals(memberStatus, "MEMBER_PAYING");
				log.info("Membership Status is: " +memberStatus + " in Admin Site");
				
				if(subs.equalsIgnoreCase("BAS"))
				{
					subs= "BASE";
				}
				
				Assert.assertEquals(subsType, subs);
				log.info("Subscription Type is: " +subsType+ " in Admin Site");
			}
			
			
			
			else if(memTypeCode.equalsIgnoreCase("203002"))			// if the email id picked belongs to Free Trial Member Type
			{
				Assert.assertEquals(memberStatus, "FREE_TRIAL_MEMBER");
				log.info("Membership Status is: " +memberStatus + " in Admin Site");
				
				if(subs.equalsIgnoreCase("BAS"))
				{
					subs= "BASE";
				}
				
				Assert.assertEquals(subsType, subs);
				log.info("Subscription Type is: " +subsType+ " in Admin Site");
			}
			
			
			
			
			else if(memTypeCode.equalsIgnoreCase("202002"))			// if the email id picked belongs to Free Campaign Member Type
			{
				Assert.assertEquals(memberStatus, "FREE_CAMPAIGN_MEMBER");
				log.info("Membership Status is: " +memberStatus + " in Admin Site");
				
				if(subs.equalsIgnoreCase("BAS"))
				{
					subs= "BASE";
				}
				
				Assert.assertEquals(subsType, subs);
				log.info("Subscription Type is: " +subsType+ " in Admin Site");
			}
			
			
			
			else if(memTypeCode.equalsIgnoreCase("302002"))				// if the email id picked belongs to MEMBER_CAMPAIGN_EXISTING Type
			{
				Assert.assertEquals(memberStatus, "MEMBER_CAMPAIGN_EXISTING");
				log.info("Membership Status is: " +memberStatus + " in Admin Site");
				
				if(subs.equalsIgnoreCase("BAS"))
				{
					subs= "BASE";
				}
				
				Assert.assertEquals(subsType, subs);
				log.info("Subscription Type is: " +subsType+ " in Admin Site");
			}
			
			
			else if(memTypeCode.equalsIgnoreCase("301002"))				// if the email id picked belongs to MEMBER_GIFTCARD_EXISTING Type
			{
				Assert.assertEquals(memberStatus, "MEMBER_GIFTCARD_EXISTING");
				log.info("Membership Status is: " +memberStatus + " in Admin Site");
				
				if(subs.equalsIgnoreCase("BAS"))
				{
					subs= "BASE";
				}
				
				Assert.assertEquals(subsType, subs);
				log.info("Subscription Type is: " +subsType+ " in Admin Site");
			}
			
			else if(memTypeCode.equalsIgnoreCase("305006"))				// if the email id picked belongs to MEMBER_CARD_EXPIRYDUE Type
			{
				Assert.assertEquals(memberStatus, "MEMBER_CARD_EXPIRYDUE");
				log.info("Membership Status is: " +memberStatus + " in Admin Site");
				
				if(subs.equalsIgnoreCase("BAS"))
				{
					subs= "BASE";
				}
				
				Assert.assertEquals(subsType, subs);
				log.info("Subscription Type is: " +subsType+ " in Admin Site");
			}
			
			
			admin.clickLogout();
			driver.get(url);
		
		
		}
		
		
		else
		{
			log.info("Subscription Type is already Premium and It can't be upgraded further");
			
			//log.info(un + pwd + sub + member);
			
//			Excel.shiftingRowsDown(INPUT_PATH, "SubsDowngrade", 1);
//			Excel.setExcelData(INPUT_PATH, "SubsDowngrade", 1, 0, un);
//			Excel.setExcelData(INPUT_PATH, "SubsDowngrade", 1, 1, pwd);
//			Excel.setExcelData(INPUT_PATH, "SubsDowngrade", 1, 2, sub);
//			Excel.setExcelData(INPUT_PATH, "SubsDowngrade", 1, 3, member );
			
			Excel.shiftingRowsUp(INPUT_PATH, "SubsUpgrade", 1);
			log.info("logging out");
			
			account.clickLogOut();
			
			new WebDriverWait(driver,30).until(ExpectedConditions.titleContains("Ljudböcker & E-böcker - Lyssna & läs gratis i mobilen"));
			
			checkingCancelButtons();
			
			
		}
			
			
			//-------------------------------VALIDATING THE MAIL TRIGGERING-------------------------------------//	  		

			
			
			
			log.info("Fetching data from databse MySQL");
			 InformationFromBackend info=new InformationFromBackend();
			 info.getDataForCustomerInfo("SubscriptionUpgrade");
			 
			String customerid =  InformationFromBackend.result;
			log.info("Customerid selected from Sql is="+customerid);
			
			 log.info("Fetching data from MongoDB");
			 
			Query query=ds.createQuery(Email.class);
			query.filter("customerid",customerid);
			/*query1.field("from").equals("kundservice@nextory.se");*/
			//query.field("customerid").equals("");
			/*query1.field("mobilenumber").equals("");
			query1.field("firstname").equals("");
			query1.field("lastname").equals("");
			query1.field("to").equals("");
			query1.field("subject").equals("");
			query1.field("mailsentdate").equals("");
			query1.field("reason").equals("");*/
			try
			{
				//custList = (Membership) query.get();
			     // if(custList != null)
				//Email emailList=(Email) query1.get();
				ArrayList<Email> emailList=(ArrayList<Email>)query.asList();
				
				 log.info("Email list="+emailList);
				 for(Email email:emailList)
				 {
					log.info("class="+email.getClass());
					log.info( "CustomerID=" + customerid );
					log.info("Firstname="+email.getFirstname());
					email.getLastname();
					email.getFrom();
					email.getMobilenumber();
					log.info(email.getReason());
					log.info(email.getTriggerName());
					log.info(email.getSubject());
					
					log.info("Mail-Subject verification");
					String expectedSubject="Ditt konto shar blivit uppgraderat" ;	
			        String actualSubject=email.getSubject();
			        Assert.assertEquals(actualSubject, expectedSubject);
					log.info("Subject verified successfully");
			     
			   	    log.info("Response verification");
			   	    String expectedResponse="Success";
					String actualResponse=email.getReason();
					Assert.assertEquals(actualResponse,expectedResponse);
					log.info("Response is verified successfully");
					
				log.info("Triggered message verification");
				 String expectedTriggerName="Subscription upgrade" ;	
			     String actualTriggerName=email.getTriggerName();
			    Assert.assertEquals(actualTriggerName, expectedTriggerName);
				log.info("Message Triggered successfully"); 
				
				log.info("Mail sent Date verification");
				String expectedupdateddate=AddDate.currentDate();
			   	Date actualupdateddate=email.getMailsentdate();
			   	Assert.assertEquals(actualupdateddate, expectedupdateddate);
			   	log.info("Mail sent date verified successfully");
			   	 
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}		
			
		}
}
	


