package giftCardsRuleMailer;

import java.util.ArrayList;
import java.util.Date;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.testng.Assert;
import org.testng.annotations.Test;

import Mongo.Email_logs.Email;
import Mongo.Membership_change_log.Membership;
import common.SuperTestScript;
import generics.AddDate;
import generics.MongoDBUtilMorphia;
import interfaceForApiOrDB.InformationFromBackend;
import interfaceForApiOrDB.InformationFromBackendNegative;

public class GC2_AddCardDetails extends SuperTestScript  
{
	MongoDBUtilMorphia mongoutil = new MongoDBUtilMorphia();
	 Datastore ds = mongoutil.getMorphiaDatastoreForNlob();
	 Email email=new Email();
	 Membership member=new Membership();
	 
	public GC2_AddCardDetails()
	{
		loginRequired=false;
		logoutRequired=false;
	}
	@Test(enabled=true, priority=330, groups={"GiftCardsRuleMailerPositive" , "All"})
	public void gC2_AddCardDetailsTC100101() throws InterruptedException
	{
		log.info("Inside Gift card 2 script");
		
		log.info("Fetching data from databse MySQL");
		InformationFromBackend info = new InformationFromBackend();
		info.getDataForCustomerInfo("GC2_AddCardDetails");

		String customerid = InformationFromBackend.result;
		log.info("Customerid selected from Sql is=" + customerid);
		
		log.info("Batch Execution");
		driver.get("http://130.211.74.42:8082/nextory_batch/jobs/giftcard-add-paydetails7days");
		
		log.info("Fetching data from MongoDB");
		
		log.info("Membertype verification");
		String current_membertype =member.getMem_type_code_new();
		String initial_membertype =member.getMem_type_code_old();
		Assert.assertEquals(current_membertype, initial_membertype);
		log.info("Membertype verified successfully");
		
		Query query = ds.createQuery(Email.class);
		query.filter("customerid", customerid);
		
		try 
		{
			ArrayList<Email> emailList = (ArrayList<Email>) query.asList();
			//log.info("Email triggered="+emailList);
			
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
				String expectedSubject = "Ditt presentkort är snart slut";
				String actualSubject = email.getSubject();
				Assert.assertEquals(actualSubject, expectedSubject);
				log.info("Subject verified successfully");

				log.info("Response verification");
				String expectedResponse = "Success";
				String actualResponse = email.getReason();
				Assert.assertEquals(actualResponse, expectedResponse);
				log.info("Response is verified successfully");

				log.info("Triggered message verification");
				String expectedTriggerName = "Giftcard: 1 Add card details";
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
	@Test(enabled=true, priority=331, groups={"GiftCardsRuleMailerNegative" , "All"})
	public void gC2_AddCardDetails() throws InterruptedException
	{
		log.info("Inside Gift card 2 script");
		
		log.info("Fetching data from databse MySQL");
		InformationFromBackendNegative info = new InformationFromBackendNegative();
		info.getDataForCustomerInfo("GC2_AddCardDetails");

		String customerid = InformationFromBackendNegative.result;
		log.info("Customerid selected from Sql is=" + customerid);
		
		log.info("Batch Execution");
		driver.get("http://130.211.74.42:8082/nextory_batch/jobs/giftcard-add-paydetails7days");
		
		log.info("Fetching data from MongoDB");
		
		log.info("Membertype verification");
		String current_membertype =member.getMem_type_code_new();
		String initial_membertype =member.getMem_type_code_old();
		Assert.assertEquals(current_membertype, initial_membertype);
		log.info("Membertype verified successfully");
		
		Query query = ds.createQuery(Email.class);
		query.filter("customerid", customerid);
		
		try 
		{
			ArrayList<Email> emailList = (ArrayList<Email>) query.asList();
			//log.info("Email triggered="+emailList);
			
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
				String expectedSubject = "Ditt presentkort är snart slut";
				String actualSubject = email.getSubject();
				Assert.assertEquals(actualSubject, expectedSubject);
				log.info("Subject verified successfully");

				log.info("Response verification");
				String expectedResponse = "Success";
				String actualResponse = email.getReason();
				Assert.assertEquals(actualResponse, expectedResponse);
				log.info("Response is verified successfully");

				log.info("Triggered message verification");
				String expectedTriggerName = "Giftcard: 1 Add card details";
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
