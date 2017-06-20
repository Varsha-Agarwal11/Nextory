package confirmationsRuleMailer;

import java.util.ArrayList;
import java.util.Date;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.testng.Assert;
import org.testng.annotations.Test;

import Mongo.Email_logs.Email;
import common.SuperTestScript;
import generics.AddDate;
import generics.Api;
import generics.MongoDBUtilMorphia;
import interfaceForApiOrDB.InformationFromBackend;
import interfaceForApiOrDB.InformationFromBackendNegative;

public class AccountTerminationExcludingNonPayment extends SuperTestScript
{
	MongoDBUtilMorphia mongoutil = new MongoDBUtilMorphia();
	 Datastore ds = mongoutil.getMorphiaDatastoreForNlob();
	 Email email=new Email();
	 
	public AccountTerminationExcludingNonPayment()
	{
		
		loginRequired=false;
		logoutRequired=false;
	}
	
   @Test(enabled=true,priority=180, groups={"ConfirmationsRuleMailerPositive" , "All"})
   public void accountTerminationExcludingNonPaymentTC100801()
   {
		log.info("Running Account Termination Excluding Non Payment");

		log.info("Fetching data from databse MySQL");
		InformationFromBackend info = new InformationFromBackend();
		info.getDataForCustomerInfo("AccountTerminationExcludingNonPayment");

		String customerid = InformationFromBackend.result;
		log.info("Customerid selected from Sql is=" + customerid);

		log.info("Batch execution");
		driver.get("http://130.211.74.42:8082/payment/testcancelledmembercron?customerid=" + customerid);

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
				String expectedSubject = "Ditt abonnemang är nu avslutat";
				String actualSubject = email.getSubject();
				Assert.assertEquals(actualSubject, expectedSubject);
				log.info("Subject verified successfully");

				log.info("Response verification");
				String expectedResponse = "Success";
				String actualResponse = email.getReason();
				Assert.assertEquals(actualResponse, expectedResponse);
				log.info("Response is verified successfully");

				log.info("Triggered message verification");
				String expectedTriggerName = "Account termination: excl. non-payment";
				String actualTriggerName = email.getTriggerName();
				Assert.assertEquals(actualTriggerName, expectedTriggerName);
				log.info("Message Triggered successfully");

				log.info("Mail sent Date verification");
				String expectedupdateddate = AddDate.currentDate();
				Date actualupdateddate = email.getMailsentdate();
				Assert.assertEquals(actualupdateddate, expectedupdateddate);
				log.info("Mail sent date verified successfully");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
   }
   @Test(enabled=true,priority=181, groups={"ConfirmationsRuleMailerNegative" , "All"})
   public void accountTerminationExcludingNonPayment()
   {
	   log.info("Running Account Termination Excluding Non Payment");
	   
	   log.info("Fetching data from databse MySQL");
	   InformationFromBackendNegative info=new InformationFromBackendNegative();
	   info.getDataForCustomerInfo("AccountTerminationExcludingNonPayment");  
		  
		String customerid = InformationFromBackend.result;
		log.info("Customerid selected from Sql is=" + customerid);

		log.info("Batch execution");
		driver.get("http://130.211.74.42:8082/payment/testcancelledmembercron?customerid=" + customerid);

		log.info("Fetching data from MongoDB");

		Query query = ds.createQuery(Email.class);
		query.filter("customerid", customerid);
	
		try
		{
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
				String expectedSubject = "Ditt abonnemang är nu avslutat";
				String actualSubject = email.getSubject();
				Assert.assertEquals(actualSubject, expectedSubject);
				log.info("Subject verified successfully");

				log.info("Response verification");
				String expectedResponse = "Success";
				String actualResponse = email.getReason();
				Assert.assertEquals(actualResponse, expectedResponse);
				log.info("Response is verified successfully");

				log.info("Triggered message verification");
				String expectedTriggerName = "Account termination: excl. non-payment";
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
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	     
   }
}
