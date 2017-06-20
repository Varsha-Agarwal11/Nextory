package giftCardsRuleMailer;

import java.util.ArrayList;
import java.util.Date;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.testng.Assert;
import org.testng.annotations.Test;

import Mongo.Email_logs.Email;
import common.PasswordFromAdmin;
import common.RandomEmail;
import common.SuperTestScript;
import generics.AddDate;
import generics.Excel;
import generics.MongoDBUtilMorphia;
import interfaceForApiOrDB.InformationFromBackend;
import pages.GiftcardPage;
import pages.HomePage;
import pages.LoginPage;
import pages.MyAccountPage;
import pages.PaymentCardDetailsPage;

public class OrderConfirmation extends SuperTestScript
{
	public static String fornamn;
	public static String efternamn;
	
	public static String cardNumber;
	public static String cvc;
	public static String un;
	public static String pwd;
	public static String month;
	public static String year;
	
	MongoDBUtilMorphia mongoutil = new MongoDBUtilMorphia();
	Datastore ds = mongoutil.getMorphiaDatastoreForNlob();
	Email email = new Email();
		
	public OrderConfirmation()
	{
	loginRequired=false;
	logoutRequired=false;
	}
	
	@Test(enabled=true, priority=320, groups={"GiftCardRuleMailerPositive" , "All"})
	public void GiftCardOrderConfirmationNewUser() throws InterruptedException
	{
		
		log.info("PURCHASING GIFTCARD AS A NEW USER/VISITOR");
		
		fornamn=Excel.getCellValue(INPUT_PATH, "GiftcardOrderConfirmation", 1, 0);
		efternamn=Excel.getCellValue(INPUT_PATH, "GiftcardOrderConfirmation", 1, 1);
		un=RandomEmail.email();
		cardNumber=un;
		cvc=Excel.getCellValue(INPUT_PATH, "NewEmail", 1, 4);
		month=Excel.getCellValue(INPUT_PATH,"NewEmail", 1, 5);
		year=Excel.getCellValue(INPUT_PATH,"NewEmail", 1, 6);
		
		log.info("Clicking on Presentkort Link at HomePage");		
		HomePage home=new HomePage(driver);
		home.clickGiftCard();
		
		log.info("Choosing the Gift card to purchase & Entering the Buyer details in customer form");
		GiftcardPage gift=new GiftcardPage(driver);
		gift.clickKopForOneMonth();
		Thread.sleep(2000);
		gift.enterFirstName(fornamn);
		gift.enterLastName(efternamn);
		gift.enterEmailToReceiveCode(un);
		gift.clickFortsattToGetCode();
		
		log.info("Entering the card details as final step");
		
		PaymentCardDetailsPage card=new PaymentCardDetailsPage(driver);
		card.enterCardNumber(cardNumber);
		card.clickExpiryMonthDropdown();
		card.selectExpiryMonth(month);
		card.clickExpiryYearDropdown();
		card.selectExpiryYear(year);
		card.enterCvcNumber(cvc);
		card.clickToGetGiftCard();
		
		log.info("Thankyou For Your Purchase / Tack för ditt köp");
		
		home.clickNextoryLogo();
		
		//Excel.shiftingRowsUp(INPUT_PATH, "GiftcardOrderConfirmation", 1);
	

		
//--------------------SQL MONGO------------------------------------------------------
	
		
		
		log.info("Fetching data from databse MySQL");
		InformationFromBackend info = new InformationFromBackend();
		info.getDataForCustomerInfo("OrderConfirmation");

		String customerid = InformationFromBackend.result;
		log.info("Customerid selected from Sql is=" + customerid);
		
		log.info("Batch Execution");
		driver.get("http://130.211.74.42:8082/nextory_batch/jobs/giftcard-add-paydetails2days");
		
		log.info("Fetching data from MongoDB");

		Query query = ds.createQuery(Email.class);
		query.filter("customerid", customerid);
		
		try 
		{
			ArrayList<Email> emailList = (ArrayList<Email>) query.asList();

			for (Email email : emailList) 
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
				String expectedSubject = "Dags att lägga in betalningsuppgifter";
				String actualSubject = email.getSubject();
				Assert.assertEquals(actualSubject, expectedSubject);
				log.info("Subject verified successfully");

				log.info("Response verification");
				String expectedResponse = "Success";
				String actualResponse = email.getReason();
				Assert.assertEquals(actualResponse, expectedResponse);
				log.info("Response is verified successfully");

				log.info("Triggered message verification");
				String expectedTriggerName = "Giftcard: 2 Add card details";
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
		
		driver.get(url);
		
	}
	
	@Test(enabled=true, priority=321 , groups={"GiftCardRuleMailerPositive" , "All"})
	public void GiftCardOrderConfirmationExistingUser() throws InterruptedException
	{
		
		log.info("PURCHASING GIFT CARD AS A EXISTING USER");
		
		un=Excel.getCellValue(INPUT_PATH, "ExistingEmail", 2, 1);
		pwd=PasswordFromAdmin.gettingPasswordFromAdmin(un);
		cardNumber=Excel.getCellValue(INPUT_PATH, "ExistingEmail", 2, 6);
		month=Excel.getCellValue(INPUT_PATH, "ExistingEmail", 2, 7);
		year=Excel.getCellValue(INPUT_PATH, "ExistingEmail", 2, 8);
		cvc=Excel.getCellValue(INPUT_PATH, "ExistingEmail", 2, 9);
		
		log.info("Navigating to Login Page");
		HomePage home=new HomePage(driver);
		home.clickLoginLink();
		
		log.info("Entering Login Details");
		LoginPage login=new LoginPage(driver);
		login.setEmailId(un);
		login.setPassword(pwd);
		login.clickLoginButton();
		
		log.info("Clicking on Presentkort Link");
		home.clickGiftCard();
		
		log.info("Choosing the gift card to purchase");
		GiftcardPage gift=new GiftcardPage(driver);
		gift.clickKopForThreeMonth();
		Thread.sleep(2000);
		gift.clickFortsattToGetCode();
		
		log.info("Filling Payment Card Details as Final Step");
		PaymentCardDetailsPage card=new PaymentCardDetailsPage(driver);
		card.enterCardNumber(cardNumber);
		card.clickExpiryMonthDropdown();
		card.selectExpiryMonth(month);
		card.clickExpiryYearDropdown();
		card.selectExpiryYear(year);
		card.enterCvcNumber(cvc);
		card.clickToGetGiftCard();
		
		log.info("Thankyou For Your Purchase / Tack för ditt köp");
		
		home.clickNextoryLogo();
		home.clickAccountLink();
		
		log.info("logging out");
		MyAccountPage account=new MyAccountPage(driver);
		account.clickLogOut();
		
		
		
//-----------------------------SQL MONGO---------------------------------------------------
	
		
		log.info("Fetching data from databse MySQL");
		InformationFromBackend info = new InformationFromBackend();
		info.getDataForCustomerInfo("OrderConfirmations");

		String customerid = InformationFromBackend.result;
		log.info("Customerid selected from Sql is=" + customerid);
		
		log.info("Batch Execution");
		driver.get("http://130.211.74.42:8082/nextory_batch/jobs/giftcard-add-paydetails2days");
		
		log.info("Fetching data from MongoDB");

		Query query = ds.createQuery(Email.class);
		query.filter("customerid", customerid);
		
		try 
		{
			ArrayList<Email> emailList = (ArrayList<Email>) query.asList();

			for (Email email : emailList) 
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
				String expectedSubject = "Dags att lägga in betalningsuppgifter";
				String actualSubject = email.getSubject();
				Assert.assertEquals(actualSubject, expectedSubject);
				log.info("Subject verified successfully");

				log.info("Response verification");
				String expectedResponse = "Success";
				String actualResponse = email.getReason();
				Assert.assertEquals(actualResponse, expectedResponse);
				log.info("Response is verified successfully");

				log.info("Triggered message verification");
				String expectedTriggerName = "Giftcard: 2 Add card details";
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
		
		driver.get(url);
		
	}

}
