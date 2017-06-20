package confirmationsRuleMailer;

import java.util.ArrayList;
import java.util.Date;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

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
import pages.HomePage;
import pages.LoginPage;
import pages.MyAccountPage;
import pages.PaymentCardDetailsPage;

public class CardUpdatedExpiry extends SuperTestScript
{
	public static String un;
	public static String pwd;
	public static String cardNumber;
	public static String cvc;
	public static String month;
	public static String year;
	public static String memTypeCode;
	public static String adminUn;
	public static String adminPwd;
	public static String adminUrl;
	public static String initSubs;
	public static String finalSubs;
	
	MongoDBUtilMorphia mongoutil = new MongoDBUtilMorphia();
	Datastore ds = mongoutil.getMorphiaDatastoreForNlob();
	Email email = new Email();

	public CardUpdatedExpiry()
	{
		loginRequired=false;
		logoutRequired=false;
	}
	
	
	@Test(enabled=true, priority=71,  groups={"ConfirmationsRuleMailerPositive" , "All"})
	public void cardUpdatedExpiry()
	{
		
		un=Excel.getCellValue(INPUT_PATH, "CardUpdated", 1, 0);
		pwd=PasswordFromAdmin.gettingPasswordFromAdmin(un);
		cardNumber=Excel.getCellValue(INPUT_PATH, "CardUpdated", 1, 1);
		cvc=Excel.getCellValue(INPUT_PATH, "CardUpdated", 1, 2);
		month=Excel.getCellValue(INPUT_PATH,"CardUpdated", 1, 3);
		year=Excel.getCellValue(INPUT_PATH,"CardUpdated", 1, 4);
		adminUn=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINUN");
		adminPwd=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINPWD");
		adminUrl=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINURL");
		
		memTypeCode= Database.executeQuery("select member_type_code from customerinfo where email='" +un+ "'");
		
		log.info("Navigating To Login Page");
		HomePage home=new HomePage(driver);
		home.clickLoginLink();
		
		log.info("Entering login details with username as : '" +un+ "' and password as : '" +pwd+ "'" );
		LoginPage login=new LoginPage(driver);
		login.setEmailId(un);
		login.setPassword(pwd);
		login.clickLoginButton();
		
		MyAccountPage account=new MyAccountPage(driver);
		
		if(!"201002".equalsIgnoreCase(memTypeCode))
		{
		initSubs= account.getMyOrder();
		}
		
		String initRunDate= account.getRunDate();
		
		
		log.info("Clicking on button to change Credit Card");
		
		account.clickToChangeCreditCard();
		
		log.info("Filling the New Credit Card Details");
		PaymentCardDetailsPage card=new PaymentCardDetailsPage(driver);
		card.enterCardNumber(cardNumber);
		card.clickExpiryMonthDropdown();
		card.selectExpiryMonth(month);
		card.clickExpiryYearDropdown();
		card.selectExpiryYear(year);
		card.enterCvcNumber(cvc);
		card.clickToSaveCreditCard();
		
		log.info("Your Credit Card Details are Updated / Dina kreditkortsuppgifter har blivit uppdaterade.");
		
		//Excel.shiftingRowsUp(INPUT_PATH, "CardUpdated", 1);
		
		home.clickNextoryLogo();
		home.clickAccountLink();
		
		try
		{
		finalSubs= account.getMyOrder();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.info("Subscription not found");
		}
		
		String finalRunDate= account.getRunDate();
		
		if(!"201002".equalsIgnoreCase(memTypeCode))
		{
		Assert.assertEquals(finalSubs, initSubs);
		log.info("Subscription before and after the card changes are unaffected: " +initSubs);
		}
		
		Assert.assertEquals(initRunDate, finalRunDate);
		log.info("Subscription Run Date is unaffected after and before the card changes: " +initRunDate);
		
		log.info("logging out");
		account.clickLogOut();
		
		new WebDriverWait(driver,30).until(ExpectedConditions.titleContains("Ljudböcker & E-böcker - Lyssna & läs gratis i mobilen"));
		
		log.info("VALIDATING IN ADMIN SITE");
		
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
		
		if(memTypeCode.equalsIgnoreCase("201002"))									//<---------- card update for FREE_GIFTCARD_NOCARDINFO 
		{
			Assert.assertEquals(memberStatus, "FREE_GIFTCARD_MEMBER");
			log.info("FREE_GIFTCARD_NOCARDINFO changed to FREE_GIFTCARD_MEMBER");
		}
		
		else if(memTypeCode.equalsIgnoreCase("305006"))							//<---------- card update for MEMBER_CARD_EXPIRYDUE  
		{
			Assert.assertEquals(memberStatus, "MEMBER_PAYING");
			log.info("MEMBER_CARD_EXPIRYDUE changed to MEMBER_PAYING");
		}
		
		else if(memTypeCode.equalsIgnoreCase("205006"))							//<---------- card update for FREE_CARD_EXPRIYDUE 
		{
			Assert.assertEquals(memberStatus, "FREE_CAMPAIGN_MEMBER");
			log.info("FREE_CARD_EXPRIYDUE changed to FREE_CAMPAIGN_MEMBER");
		}
		
		else if(memTypeCode.equalsIgnoreCase("203002"))							//<---------- card update for FREE_TRIAL_MEMBER 
		{
			Assert.assertEquals(memberStatus, "FREE_TRIAL_MEMBER");
			log.info("FREE_TRIAL_MEMBER remains as FREE_TRIAL_MEMBER after card updated");
		}
		
		else if(memTypeCode.equalsIgnoreCase("304001"))							//<---------- card update for MEMBER_PAYING 
		{
			Assert.assertEquals(memberStatus, "MEMBER_PAYING");
			log.info("MEMBER_PAYING remains as MEMBER_PAYING after card updated");
		}
		
		else
		{
			log.info("card updated for member Type Code: ' " +memTypeCode+ " ' and after the card update the MemberType is: '" +memberStatus+"'");
		}
		
		admin.clickLogout();
		driver.get(url);
		
	
		
	
		
		
		//-------------------------------VALIDATING THE MAIL TRIGGERING-------------------------------------//	  
		
		
		log.info("Fetching data from databse MySQL");
		InformationFromBackend info=new InformationFromBackend();
		info.getDataForCustomerInfo("CardUpdatedExpiry");  
		  
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
			
			 //log.info("Email list="+emailList);
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
				String expectedSubject="Dina uppgifter har uppdaterats" ;	
		        String actualSubject=email.getSubject();
		        Assert.assertEquals(actualSubject, expectedSubject);
				log.info("Subject verified successfully");
		     
		   	    log.info("Response verification");
		   	    String expectedResponse="Success";
				String actualResponse=email.getReason();
				Assert.assertEquals(actualResponse,expectedResponse);
				log.info("Response is verified successfully");
				
				log.info("Triggered message verification");
				String expectedTriggerName = "Card updated (Expiry)";
				String actualTriggerName = email.getTriggerName();
				Assert.assertEquals(actualTriggerName, expectedTriggerName);
				log.info("Message Triggered successfully");

				log.info("Mail sent Date verification");
				String expectedupdateddate = AddDate.currentDate();
				Date actualupdateddate = email.getMailsentdate();
				Assert.assertEquals(actualupdateddate, expectedupdateddate);
				log.info("Mail sent date verified successfully");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}
	

	
//-------------------------------------- NEGATIVE FLOWS -------------------------------------------------//

	@Test(enabled=true, priority=72,  groups={"ConfirmationsRuleMailerNegative" , "All"})
	public void negativeFlows()
	{
		
			
			un=Excel.getCellValue(INPUT_PATH, "CardUpdated", 1, 0);
			pwd=PasswordFromAdmin.gettingPasswordFromAdmin(un);
			cardNumber=Excel.getCellValue(INPUT_PATH, "CardUpdated", 1, 1);
			cvc=Excel.getCellValue(INPUT_PATH, "CardUpdated", 1, 2);
			month=Excel.getCellValue(INPUT_PATH,"CardUpdated", 1, 3);
			year=Excel.getCellValue(INPUT_PATH,"CardUpdated", 1, 4);
			adminUn=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINUN");
			adminPwd=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINPWD");
			adminUrl=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINURL");
			
			memTypeCode= Database.executeQuery("select member_type_code from customerinfo where email='" +un+ "'");
			
			log.info("Navigating To Login Page");
			HomePage home=new HomePage(driver);
			home.clickLoginLink();
			
			log.info("Entering login details with username as : '" +un+ "' and password as : '" +pwd+ "'" );
			LoginPage login=new LoginPage(driver);
			login.setEmailId(un);
			login.setPassword(pwd);
			login.clickLoginButton();
			
			MyAccountPage account=new MyAccountPage(driver);
			
			if(!"201002".equalsIgnoreCase(memTypeCode))
			{
			initSubs=account.getMyOrder();
			}
			
			String initRunDate= account.getRunDate();
			
			log.info("Clicking on button to change Credit Card");
			
			account.clickToChangeCreditCard();
			
			log.info("Filling the New Credit Card Details");
			PaymentCardDetailsPage card=new PaymentCardDetailsPage(driver);
			card.enterCardNumber("1234567887654321");
			card.clickExpiryMonthDropdown();
			card.selectExpiryMonth("12");
			card.clickExpiryYearDropdown();
			card.selectExpiryYear("28");
			card.enterCvcNumber("888");
			card.clickToSaveCreditCard();
			
			card.clickProvaIgen();
			
			card.enterCardNumber(cardNumber);
			card.clickExpiryMonthDropdown();
			card.selectExpiryMonth(month);
			card.clickExpiryYearDropdown();
			card.selectExpiryYear(year);
			card.enterCvcNumber(cvc);
			card.clickToSaveCreditCard();
			
			log.info("Your Credit Card Details are Updated / Dina kreditkortsuppgifter har blivit uppdaterade.");
			
			//Excel.shiftingRowsUp(INPUT_PATH, "CardUpdated", 1);
			
			home.clickNextoryLogo();
			home.clickAccountLink();
			
			try
			{
			finalSubs= account.getMyOrder();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				log.info("Subscription not found");
			}
			
			String finalRunDate= account.getRunDate();
			
			if(!"201002".equalsIgnoreCase(memTypeCode))
			{
			Assert.assertEquals(finalSubs, initSubs);
			log.info("Subscription before and after the card changes are unaffected: " +initSubs);
			}
			
			Assert.assertEquals(initRunDate, finalRunDate);
			log.info("Subscription Run Date is unaffected after and before the card changes: " +initRunDate);
			
			log.info("logging out");
			account.clickLogOut();
			
			new WebDriverWait(driver,30).until(ExpectedConditions.titleContains("Ljudböcker & E-böcker - Lyssna & läs gratis i mobilen"));
			
			log.info("VALIDATING IN ADMIN SITE");
			
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
			
			if(memTypeCode.equalsIgnoreCase("201002"))									//<---------- card update for FREE_GIFTCARD_NOCARDINFO 
			{
				Assert.assertEquals(memberStatus, "FREE_GIFTCARD_MEMBER");
				log.info("FREE_GIFTCARD_NOCARDINFO changed to FREE_GIFTCARD_MEMBER");
			}
			
			else if(memTypeCode.equalsIgnoreCase("305006"))							//<---------- card update for MEMBER_CARD_EXPIRYDUE  
			{
				Assert.assertEquals(memberStatus, "MEMBER_PAYING");
				log.info("MEMBER_CARD_EXPIRYDUE changed to MEMBER_PAYING");
			}
			
			else if(memTypeCode.equalsIgnoreCase("205006"))							//<---------- card update for FREE_CARD_EXPRIYDUE 
			{
				Assert.assertEquals(memberStatus, "FREE_CAMPAIGN_MEMBER");
				log.info("FREE_CARD_EXPRIYDUE changed to FREE_CAMPAIGN_MEMBER");
			}
			
			else if(memTypeCode.equalsIgnoreCase("203002"))							//<---------- card update for FREE_TRIAL_MEMBER 
			{
				Assert.assertEquals(memberStatus, "FREE_TRIAL_MEMBER");
				log.info("FREE_TRIAL_MEMBER remains as FREE_TRIAL_MEMBER after card updated");
			}
			
			else if(memTypeCode.equalsIgnoreCase("304001"))							//<---------- card update for MEMBER_PAYING 
			{
				Assert.assertEquals(memberStatus, "MEMBER_PAYING");
				log.info("MEMBER_PAYING remains as MEMBER_PAYING after card updated");
			}
			
			else
			{
				log.info("card updated for member Type Code: ' " +memTypeCode+ " ' and after the card update the MemberType is: '" +memberStatus+"'");
			}
			
			admin.clickLogout();
			driver.get(url);
			
		
		

		
		
		//-------------------------------VALIDATING THE MAIL TRIGGERING-------------------------------------//	  
		
		
		log.info("Fetching data from databse MySQL");
		InformationFromBackend info=new InformationFromBackend();
		info.getDataForCustomerInfo("CardUpdatedExpiry");  
		  
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
			
			 //log.info("Email list="+emailList);
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
				String expectedSubject="Dina uppgifter har uppdaterats" ;	
		        String actualSubject=email.getSubject();
		        Assert.assertEquals(actualSubject, expectedSubject);
				log.info("Subject verified successfully");
		     
		   	    log.info("Response verification");
		   	    String expectedResponse="Success";
				String actualResponse=email.getReason();
				Assert.assertEquals(actualResponse,expectedResponse);
				log.info("Response is verified successfully");
				
				log.info("Triggered message verification");
				String expectedTriggerName = "Card updated (Expiry)";
				String actualTriggerName = email.getTriggerName();
				Assert.assertEquals(actualTriggerName, expectedTriggerName);
				log.info("Message Triggered successfully");

				log.info("Mail sent Date verification");
				String expectedupdateddate = AddDate.currentDate();
				Date actualupdateddate = email.getMailsentdate();
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
