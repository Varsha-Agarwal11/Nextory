package confirmationsRuleMailer;

import java.util.ArrayList;
import java.util.Date;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import Mongo.Email_logs.Email;
import common.SuperTestScript;
import generics.AddDate;
import generics.Excel;
import generics.MongoDBUtilMorphia;
import interfaceForApiOrDB.InformationFromBackend;
import pages.ForgotPasswordPage;
import pages.HomePage;
import pages.LoginPage;

public class ForgotPassword extends SuperTestScript
{
	public static String un;
	
	MongoDBUtilMorphia mongoutil = new MongoDBUtilMorphia();
	 Datastore ds = mongoutil.getMorphiaDatastoreForNlob();
	 Email email=new Email();
	SoftAssert soft= new SoftAssert();
	 
	public ForgotPassword()
	{
		loginRequired=false;
		logoutRequired=false;
	}
	
	@Test(enabled=true, priority=41,  groups={"ConfirmationsRuleMailerPositive" , "All"})
	public void forgotPasswordTC101001()
	{
		un=Excel.getCellValue(INPUT_PATH, "ForgotPassword", 1, 0);
		
		log.info("Clicking Login on HomePage");
		HomePage home=new HomePage(driver);
		home.clickLoginLink();
		
		log.info("Clicking on Forgot Password");
		LoginPage login=new LoginPage(driver);
		login.setEmailId(un);
		login.clickForgotPassword();
		
		log.info("Entering email id '" +un+ "' to send the new password link");
		ForgotPasswordPage forgot=new ForgotPasswordPage(driver);
		forgot.enterEmailId(un);
		Assert.assertTrue(forgot.skickaButtonClickable(), "SKICKA BUTTON IS NOT CLICKABLE");
		forgot.clickSkickaButton();
		
		boolean elem= driver.findElement(By.xpath("//h1[contains(text(),'Lösenordsåterställning')]")).isDisplayed();
		
		Assert.assertTrue(elem, "FORGOT PASSWORD LINK NOT SENT and FAILED");
		
		log.info("Password Link sent on the Email Id entered");
		
		//log.info("Refreshing the Excel Sheet");
		//Excel.shiftingRowsUp(INPUT_PATH, "ForgotPassword", 1);
		//log.info("Excel sheet refreshed");
		
		home.clickNextoryLogo();
		
		
		//-------------------------------VALIDATING THE MAIL TRIGGERING-------------------------------------//	  
		
		
			log.info("Fetching data from databse MySQL");
			InformationFromBackend info = new InformationFromBackend();
			info.getDataForCustomerInfo("ForgotPassword");

			String customerid = InformationFromBackend.result;
			log.info("Customerid selected from Sql is=" + customerid);

			log.info("Fetching data from MongoDB");

			Query query = ds.createQuery(Email.class);
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
					log.info("class=" + email.getClass());
					log.info("CustomerID=" + customerid);
					log.info("Firstname=" + email.getFirstname());
					email.getLastname();
					email.getFrom();
					email.getMobilenumber();
					log.info(email.getReason());
					log.info(email.getTriggerName());
					log.info(email.getSubject());

					log.info("Mail-Subject verification");
					String expectedSubject = "Har du glömt ditt lösenord?";
					String actualSubject = email.getSubject();
					Assert.assertEquals(actualSubject, expectedSubject);
					log.info("Subject verified successfully");

					log.info("Response verification");
					String expectedResponse = "Success";
					String actualResponse = email.getReason();
					Assert.assertEquals(actualResponse, expectedResponse);
					log.info("Response is verified successfully");

					log.info("Triggered message verification");
					String expectedTriggerName = "Forgot Password";
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
	
	
	//---------------------------------------------------------NEGATIVE FLOWS------------------------------------------------------------------//
	
	@Test(enabled=true, priority=42,  groups={"ConfirmationsRuleMailerNegative" , "All"})
	public void forgotPasswordBlankEmail()
	{
		
		log.info("LEAVING THE EMAIL BOX EMPTY: NEGATIVE FLOW");
		un=Excel.getCellValue(INPUT_PATH, "ForgotPassword", 1, 0);
		
		log.info("Clicking Login on HomePage");
		HomePage home=new HomePage(driver);
		home.clickLoginLink();
		
		log.info("Clicking on Forgot Password");
		LoginPage login=new LoginPage(driver);
		login.setEmailId(un);
		login.clickForgotPassword();
		
		log.info("Leaving email id to get the Error Message");
		ForgotPasswordPage forgot=new ForgotPasswordPage(driver);
		forgot.clickSkickaButton();
		
		String actErrMsg = driver.findElement(By.xpath("//label[@class='error']")).getText();
		String actErrMsgTrim = actErrMsg.replaceAll("\\s+", "");
		String expErrMsg = "Vänligenfylliengiltige-postadress.";
		
		Assert.assertEquals(actErrMsgTrim, expErrMsg, "ERROR MESSAGE AT FORGET PASSWORD PAGE DOES NOT MATCH");
		
		forgot.enterEmailId(un);
		Assert.assertTrue(forgot.skickaButtonClickable(), "SKICKA BUTTON IS NOT CLICKABLE");
		forgot.clickSkickaButton();
	
		boolean elem= driver.findElement(By.xpath("//h1[contains(text(),'Lösenordsåterställning')]")).isDisplayed();
		
		soft.assertTrue(driver.findElement(By.xpath("//h1[contains(text(),'Lösenordsåterställning')]")).isDisplayed(), "FORGOT PASSWORD LINK NOT SENT and FAILED");
		
		log.info("Password Link sent on the Email Id entered");
		
		home.clickNextoryLogo();
		
		
		
		//-------------------------------VALIDATING THE MAIL TRIGGERING-------------------------------------//	  
		
		
			log.info("Fetching data from databse MySQL");
			InformationFromBackend info = new InformationFromBackend();
			info.getDataForCustomerInfo("ForgotPassword");

			String customerid = InformationFromBackend.result;
			log.info("Customerid selected from Sql is=" + customerid);

			log.info("Fetching data from MongoDB");

			Query query = ds.createQuery(Email.class);
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
					log.info("class=" + email.getClass());
					log.info("CustomerID=" + customerid);
					log.info("Firstname=" + email.getFirstname());
					email.getLastname();
					email.getFrom();
					email.getMobilenumber();
					log.info(email.getReason());
					log.info(email.getTriggerName());
					log.info(email.getSubject());

					log.info("Mail-Subject verification");
					String expectedSubject = "Har du glömt ditt lösenord?";
					String actualSubject = email.getSubject();
					Assert.assertEquals(actualSubject, expectedSubject);
					log.info("Subject verified successfully");

					log.info("Response verification");
					String expectedResponse = "Success";
					String actualResponse = email.getReason();
					Assert.assertEquals(actualResponse, expectedResponse);
					log.info("Response is verified successfully");

					log.info("Triggered message verification");
					String expectedTriggerName = "Forgot Password";
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
	
	
	@Test(enabled=true, priority=43,  groups={"ConfirmationsRuleMailerNegative" , "All"})
	public void forgotPasswordWrongEmail()
	{
		
		log.info("ENTERING THE EMAIL BOX WITH NON_EXISTING EMAIL: NEGATIVE FLOW");
		un=Excel.getCellValue(INPUT_PATH, "ForgotPassword", 1, 0);
		
		log.info("Clicking Login on HomePage");
		HomePage home=new HomePage(driver);
		home.clickLoginLink();
		
		log.info("Clicking on Forgot Password");
		LoginPage login=new LoginPage(driver);
		login.clickForgotPassword();
		
		log.info("Entering Wrong Email Id to get the Error Message");
		ForgotPasswordPage forgot=new ForgotPasswordPage(driver);
		forgot.enterEmailId("non_existing_email@frescano.se");
		forgot.clickSkickaButton();
		
		String actErrMsg = driver.findElement(By.xpath("//span[@id='email.errors']")).getText();
		String actErrMsgTrim = actErrMsg.replaceAll("\\s+", "");
		String expErrMsg = "Denangivnae-postadressenärfelaktig";
		
		Assert.assertEquals(actErrMsgTrim, expErrMsg, "ERROR MESSAGE AT FORGET PASSWORD PAGE DOES NOT MATCH");
		
		forgot.clearEmailTextbox();
		forgot.enterEmailId(un);
		forgot.clickSkickaButton();
		
		boolean elem= driver.findElement(By.xpath("//h1[contains(text(),'Lösenordsåterställning')]")).isDisplayed();
		
		Assert.assertTrue(elem, "FORGOT PASSWORD LINK NOT SENT and FAILED");
		
		log.info("Password Link sent on the Email Id entered");
			
		home.clickNextoryLogo();
		
		
		
		//-------------------------------VALIDATING THE MAIL TRIGGERING-------------------------------------//	  
		
		
			log.info("Fetching data from databse MySQL");
			InformationFromBackend info = new InformationFromBackend();
			info.getDataForCustomerInfo("ForgotPassword");

			String customerid = InformationFromBackend.result;
			log.info("Customerid selected from Sql is=" + customerid);

			log.info("Fetching data from MongoDB");

			Query query = ds.createQuery(Email.class);
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
					log.info("class=" + email.getClass());
					log.info("CustomerID=" + customerid);
					log.info("Firstname=" + email.getFirstname());
					email.getLastname();
					email.getFrom();
					email.getMobilenumber();
					log.info(email.getReason());
					log.info(email.getTriggerName());
					log.info(email.getSubject());

					log.info("Mail-Subject verification");
					String expectedSubject = "Har du glömt ditt lösenord?";
					String actualSubject = email.getSubject();
					Assert.assertEquals(actualSubject, expectedSubject);
					log.info("Subject verified successfully");

					log.info("Response verification");
					String expectedResponse = "Success";
					String actualResponse = email.getReason();
					Assert.assertEquals(actualResponse, expectedResponse);
					log.info("Response is verified successfully");

					log.info("Triggered message verification");
					String expectedTriggerName = "Forgot Password";
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


