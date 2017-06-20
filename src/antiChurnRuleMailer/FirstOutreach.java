package antiChurnRuleMailer;

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

public class FirstOutreach extends SuperTestScript 
{
	MongoDBUtilMorphia mongoutil = new MongoDBUtilMorphia();
	Datastore ds = mongoutil.getMorphiaDatastoreForNlob();
	Email email = new Email();

    public FirstOutreach()
	{
	   loginRequired=false;
	   logoutRequired=false;
	}
		
   @Test(enabled=true, priority=210,groups = {"AntiChurnRuleMailerPositive", "All"})
   public void firstOutreachTC110101()
   {
		log.info("-------------------------------Running First Outreach Anti churn Positive--------------------------");
		log.info("Fetching data from databse MySQL");
		InformationFromBackend info = new InformationFromBackend();
		info.getDataForCustomerInfo("FirstOutreach");

		String customerid = InformationFromBackend.result;
		log.info("Customerid selected from Sql is=" + customerid);

		log.info("Batch Execution");
		driver.get("http://130.211.74.42:8082/nextory_batch/jobs/get-visitors-tobecome-members");

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
				String expectedSubject = "Prova nu – gratis i 14 dagar";
				String actualSubject = email.getSubject();
				Assert.assertEquals(actualSubject, expectedSubject);
				log.info("Subject verified successfully");

				log.info("Response verification");
				String expectedResponse = "Success";
				String actualResponse = email.getReason();
				Assert.assertEquals(actualResponse, expectedResponse);
				log.info("Response is verified successfully");

				log.info("Triggered message verification");
				String expectedTriggerName = "Visitors: First outreach";
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
   @Test(enabled=true, priority=211,groups = {"AntiChurnRuleMailerNegative", "All"})
   public void firstOutreach()
   {
		log.info("----------------------------Running First Outreach Anti churn Neghative------------------------");
		log.info("Fetching data from databse MySQL");
		InformationFromBackendNegative info = new InformationFromBackendNegative();
		info.getDataForCustomerInfo("FirstOutreach");

		String customerid = InformationFromBackendNegative.result;
		log.info("Customerid selected from Sql is=" + customerid);

		log.info("Batch Execution");
		driver.get("http://130.211.74.42:8082/nextory_batch/jobs/get-visitors-tobecome-members");

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
				String expectedSubject = "Prova nu – gratis i 14 dagar";
				String actualSubject = email.getSubject();
				Assert.assertEquals(actualSubject, expectedSubject);
				log.info("Subject verified successfully");

				log.info("Response verification");
				String expectedResponse = "Success";
				String actualResponse = email.getReason();
				Assert.assertEquals(actualResponse, expectedResponse);
				log.info("Response is verified successfully");

				log.info("Triggered message verification");
				String expectedTriggerName = "Visitors: First outreach";
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