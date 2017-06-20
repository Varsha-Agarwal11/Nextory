package antiChurnRuleMailer;

import java.util.ArrayList;
import java.util.Date;

import org.joda.time.DateTime;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.testng.Assert;
import org.testng.annotations.Test;

import Mongo.Email_logs.Email;
import Mongo.Membership_change_log.Membership;
import common.SuperTestScript;
import generics.AddDate;
import generics.MongoDBUtilMorphia;
import interfaceForApiOrDB.InformationFromBackend;
import interfaceForApiOrDB.InformationFromBackendNegative;

public class PostChurnWinback extends SuperTestScript
{
	public static String un;
	public static String pwd;
	
	MongoDBUtilMorphia mongoutil = new MongoDBUtilMorphia();
	Datastore ds = mongoutil.getMorphiaDatastoreForNlob();
	Membership member = new Membership();
	Email email = new Email();
	InformationFromBackend inf = new InformationFromBackend();

	public PostChurnWinback() 
	{
		loginRequired = false;
		logoutRequired = false;
	}

	@Test(enabled=true, priority=240,groups = {"AntiChurnRuleMailerPositive", "All"})
	public void postChurnWinbackTC110401() 
	{
		log.info("------------------------In post churn winback script Positive---------------------------");

		log.info("Fetching data from databse MySQL");
		InformationFromBackend info = new InformationFromBackend();
		info.getDataForCustomerInfo("PostChurnWinback");

		String customerid = InformationFromBackend.result;
		log.info("Customerid selected from Sql is=" + customerid);

		log.info("Inside membership change log");
		Membership custList = null;
	 
	 try
	 {
			Query query = ds.createQuery(Membership.class);
			// query.criteria("customerid").equal("7672");
			query.criteria("mem_type_code_old").exists();
			query.criteria("mem_type_code_old").notEqual(null);
			query.where("this.mem_type_code_old != this.mem_type_code_new");
			query.order("-_id");
			query.limit(1);

			custList = (Membership) query.get();
			if (custList != null) 
			{
				log.info("Id is=" + customerid);
				log.info("Mem type code old=" + custList.getMem_type_code_old());
				log.info("Mem type code new=" + custList.getMem_type_code_new());
            }   
      }
	 catch(Exception e)
	 {
		 e.printStackTrace();
	 }
		DateTime d = new DateTime(custList.getUpdateddate());
		Date thirtyday = d.minusDays(30).toDate();

		log.info("Date=" + thirtyday);

		// db.nx_membership_change_log.update({"_id": customerid},{$set :
		// {"updateddate" : -30day }});
		UpdateOperations<Membership> ops = ds.createUpdateOperations(Membership.class).set("updateddate", thirtyday);
		ds.update(ds.createQuery(Membership.class).field("customerid").equal(customerid), ops, true);

		log.info("Batch execution");
		driver.get("http://130.211.74.42:8082/nextory_batch/jobs/antichurn-winback-offer");

		log.info("Checking in the email that message is triggered or not");
		Query query1 = ds.createQuery(Email.class);
		// query1.filter("customerid",custList.getCustomerid());
		query1.filter("customerid", customerid);
		/* query1.field("from").equals("kundservice@nextory.se"); */
		// query.field("customerid").equals("");
		/*
		 * query1.field("mobilenumber").equals("");
		 * query1.field("firstname").equals("");
		 * query1.field("lastname").equals(""); query1.field("to").equals("");
		 * query1.field("subject").equals("");
		 * query1.field("mailsentdate").equals("");
		 * query1.field("reason").equals("");
		 */
		try
		{
			//custList = (Membership) query.get();
		     // if(custList != null)
			//Email emailList=(Email) query1.get();
			ArrayList<Email> emailList=(ArrayList<Email>)query1.asList();
			
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
				log.info("TriggerName="+email.getTriggerName());
				log.info(email.getSubject());
				
				log.info("Mail-Subject verification");
				String expectedSubject="Missa inte nyheterna hos Nextory. 1 månad för 9 kr" ;	
		        String actualSubject=email.getSubject();
		        Assert.assertEquals(actualSubject, expectedSubject);
				log.info("Subject verified successfully");
		     
		   	    log.info("Response verification");
		   	    String expectedResponse="Success";
				String actualResponse=email.getReason();
				Assert.assertEquals(actualResponse,expectedResponse);
				log.info("Response is success");
				
				log.info("Triggered message verification");
				String expectedTriggerName = "Post-churn: Winback";
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
	@Test(enabled=true, priority=241,groups = {"AntiChurnRuleMailerNegative", "All"})
	public void postChurnWinback() 
	{
		log.info("---------------------------In post churn winback script Negative-------------------------------");

		log.info("Fetching data from databse MySQL");
		InformationFromBackendNegative info = new InformationFromBackendNegative();
		info.getDataForCustomerInfo("PostChurnWinback");

		String customerid = InformationFromBackendNegative.result;
		log.info("Customerid selected from Sql is=" + customerid);

		log.info("Inside membership change log");
		Membership custList = null;
	 
	 try
	 {
			Query query = ds.createQuery(Membership.class);
			// query.criteria("customerid").equal("7672");
			query.criteria("mem_type_code_old").exists();
			query.criteria("mem_type_code_old").notEqual(null);
			query.where("this.mem_type_code_old != this.mem_type_code_new");
			query.order("-_id");
			query.limit(1);

			custList = (Membership) query.get();
			if (custList != null) 
			{
				log.info("Id is=" + customerid);
				log.info("Mem type code old=" + custList.getMem_type_code_old());
				log.info("Mem type code new=" + custList.getMem_type_code_new());
            }   
      }
	 catch(Exception e)
	 {
		 e.printStackTrace();
	 }
		DateTime d = new DateTime(custList.getUpdateddate());
		Date thirtyOneday = d.minusDays(31).toDate();

		log.info("Date=" + thirtyOneday);

		// db.nx_membership_change_log.update({"_id": customerid},{$set :
		// {"updateddate" : -31day }});
		UpdateOperations<Membership> ops = ds.createUpdateOperations(Membership.class).set("updateddate", thirtyOneday);
		ds.update(ds.createQuery(Membership.class).field("customerid").equal(customerid), ops, true);

		log.info("Batch execution");
		driver.get("http://130.211.74.42:8082/nextory_batch/jobs/antichurn-winback-offer");

		log.info("Checking in the email that message is triggered or not");
		Query query1 = ds.createQuery(Email.class);
		query1.filter("customerid", customerid);
		
		try
		{
			ArrayList<Email> emailList=(ArrayList<Email>)query1.asList();
			
			 for(Email email:emailList)
			 {
				log.info("class="+email.getClass());
				log.info( "CustomerID=" + customerid );
				log.info("Firstname="+email.getFirstname());
				email.getLastname();
				email.getFrom();
				email.getMobilenumber();
				log.info(email.getReason());
				log.info("TriggerName="+email.getTriggerName());
				log.info(email.getSubject());
				
				log.info("Mail-Subject verification");
				String expectedSubject="Missa inte nyheterna hos Nextory. 1 månad för 9 kr" ;	
		        String actualSubject=email.getSubject();
		        Assert.assertEquals(actualSubject, expectedSubject);
				log.info("Subject verified successfully");
		     
		   	    log.info("Response verification");
		   	    String expectedResponse="Success";
				String actualResponse=email.getReason();
				Assert.assertEquals(actualResponse,expectedResponse);
				log.info("Response is success");
				
				log.info("Triggered message verification");
				String expectedTriggerName = "Post-churn: Winback";
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
	@Test(enabled=true, priority=242,groups = {"AntiChurnRuleMailerNegative", "All"})
	public void postChurnWinbackLowerLimit() 
	{
		log.info("In post churn winback script");

		log.info("Fetching data from databse MySQL");
		InformationFromBackendNegative info = new InformationFromBackendNegative();
		info.getDataForCustomerInfo("PostChurnWinback");

		String customerid = InformationFromBackendNegative.result;
		log.info("Customerid selected from Sql is=" + customerid);

		log.info("Inside membership change log");
		Membership custList = null;
	 
	 try
	 {
			Query query = ds.createQuery(Membership.class);
			// query.criteria("customerid").equal("7672");
			query.criteria("mem_type_code_old").exists();
			query.criteria("mem_type_code_old").notEqual(null);
			query.where("this.mem_type_code_old != this.mem_type_code_new");
			query.order("-_id");
			query.limit(1);

			custList = (Membership) query.get();
			if (custList != null) 
			{
				log.info("Id is=" + customerid);
				log.info("Mem type code old=" + custList.getMem_type_code_old());
				log.info("Mem type code new=" + custList.getMem_type_code_new());
            }   
      }
	 catch(Exception e)
	 {
		 e.printStackTrace();
	 }
		DateTime d = new DateTime(custList.getUpdateddate());
		Date twentyNineday = d.minusDays(29).toDate();

		log.info("Date=" + twentyNineday);

		// db.nx_membership_change_log.update({"_id": customerid},{$set :
		// {"updateddate" : -29day }});
		UpdateOperations<Membership> ops = ds.createUpdateOperations(Membership.class).set("updateddate", twentyNineday);
		ds.update(ds.createQuery(Membership.class).field("customerid").equal(customerid), ops, true);

		log.info("Batch execution");
		driver.get("http://130.211.74.42:8082/nextory_batch/jobs/antichurn-winback-offer");

		log.info("Checking in the email that message is triggered or not");
		Query query1 = ds.createQuery(Email.class);
		query1.filter("customerid", customerid);
		
		try
		{
			ArrayList<Email> emailList=(ArrayList<Email>)query1.asList();
			
			 for(Email email:emailList)
			 {
				log.info("class="+email.getClass());
				log.info( "CustomerID=" + customerid );
				log.info("Firstname="+email.getFirstname());
				email.getLastname();
				email.getFrom();
				email.getMobilenumber();
				log.info(email.getReason());
				log.info("TriggerName="+email.getTriggerName());
				log.info(email.getSubject());
				
				log.info("Mail-Subject verification");
				String expectedSubject="Missa inte nyheterna hos Nextory. 1 månad för 9 kr" ;	
		        String actualSubject=email.getSubject();
		        Assert.assertEquals(actualSubject, expectedSubject);
				log.info("Subject verified successfully");
		     
		   	    log.info("Response verification");
		   	    String expectedResponse="Success";
				String actualResponse=email.getReason();
				Assert.assertEquals(actualResponse,expectedResponse);
				log.info("Response is success");
				
				log.info("Triggered message verification");
				String expectedTriggerName = "Post-churn: Winback";
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
