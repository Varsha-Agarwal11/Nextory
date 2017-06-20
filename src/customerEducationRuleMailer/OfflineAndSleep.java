package customerEducationRuleMailer;

import java.util.ArrayList;
import java.util.Date;

import org.joda.time.DateTime;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Criteria;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import Mongo.Email_logs.Email;
import Mongo.Library.CustomerLibrary;
import Mongo.Membership_change_log.Membership;
import generics.Api;
import generics.Database;
import generics.Excel;
import generics.MongoDBUtilMorphia;
import generics.Property;
import interfaceForApiOrDB.InformationFromBackend;
import interfaceForApiOrDB.InformationFromBackendNegative;
import pages.CampaignPage;
import pages.CustomerFormPage;
import pages.GiftCardFormPage;
import pages.HomePage;
import pages.NewSubscriptionPage;
import pages.PaymentCardDetailsPage;
import pages.RegistrationPage;
import common.Batch;
import common.CheckEmail;
import common.SuperTestScript;

public class OfflineAndSleep extends SuperTestScript
{
	public static String newEmail;
	 public static String confirm;
	 public static String newPwd;
	 public static String cardNumber;
	 public static String cvc;
	 public static String fn;
	 public static String ln;
	 public static String cellNum;
	 
	 //private SoftAssert softAssert=new SoftAssert();
	 MongoDBUtilMorphia mongoutil = new MongoDBUtilMorphia();
	 Datastore ds = mongoutil.getMorphiaDatastoreForNlob();
     Membership member=new Membership();
     Email email=new Email();
     
     InformationFromBackend info=new InformationFromBackend();
     
	 String expectedSubject="";
	 String actualSubject="";
	 String result="";
	 
//	 String expectedEmail="";
//	 String actualEmail="";
	 
	 Date expectedupdateddate;
	 Date actualupdateddate;
	 
	 
	   public OfflineAndSleep()
		{
			loginRequired=false;
			logoutRequired=false;
		}
		
	@Test(enabled = true, priority = 90, groups = { "CustomerEducationRuleMailerPositive", "All" })
	public void offlineAndSleepTC090401() throws InterruptedException 
	{
		log.info("---------------Inside Offline and sleep Free Trial script Positive------------------------");

		log.info("Fetching data from databse MySQL");
		result = info.getDataForCustomerInfo("OfflineAndSleepFreeTrial");
		log.info("Customerid from Sql : " + result);

		log.info("Fetching data from databse Mongo");
		String customerid = InformationFromBackend.result;
		Membership custList = null;
		log.info("Customerid=" + customerid);
		try
		{
				//--------------------------------------------nx_membership_change_log---------------------------------
			Query query = ds.createQuery(Membership.class);
			query.criteria("mem_type_code_old").exists();
			query.criteria("mem_type_code_old").notEqual(null);
			query.where("this.mem_type_code_old != this.mem_type_code_new");
			    
				/*  query.and(new Criteria []{ ds.createQuery(Membership.class).criteria("customerid").equal(customerid),
			    		  ds.createQuery(Membership.class).criteria("mem_type_code_old").exists(),
			    		  ds.createQuery(Membership.class).criteria("mem_type_code_old").notEqual(null),
			    		  ds.createQuery(Membership.class).criteria("mem_type_code_old") .notEqual("mem_type_code_new")}); */
			query.order("-_id");
			query.limit(1);

			custList = (Membership) query.get();
			if (custList != null) {
				//log.info("Id is=" + custList.getId());
				log.info("Mem type code old=" + custList.getMem_type_code_old());
				log.info("Mem type code new=" + custList.getMem_type_code_new());
				log.info("Customer id =" + InformationFromBackend.result);
			}
			log.info(custList.getMem_type_code_previous());
			Date latestdate = null;
		} catch (Exception e) {
			e.printStackTrace();
		}

		DateTime d = new DateTime(custList.getUpdateddate());
		Date sevenday = d.minusDays(7).toDate();

		//db.nx_membership_change_log.update({"_id":17},{$set : {"updateddate" : -7day }})
		UpdateOperations<Membership> ops = ds.createUpdateOperations(Membership.class).set("updateddate", sevenday);
		ds.update(ds.createQuery(Membership.class).field("customerid").equal(customerid), ops, true);
		//log.info("Updated date....."+sevenday);
					
		//-------------------------------------------nx_customer_email_logs--------------------------------------
		Query query1 = ds.createQuery(Email.class);
		query1.filter("customerid", customerid);
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
			ArrayList<Email> emailList = (ArrayList<Email>) query1.asList();

			for (Email email : emailList) 
			{
				// email.getClass();
				//log.info("CustomerID....." + customerid);
				email.getFirstname();
				email.getLastname();
				email.getFrom();
				email.getMobilenumber();
				email.getReason();
				email.getTriggerName();

				expectedSubject = "Läsa, resa, sova";
				actualSubject = email.getSubject();

				log.info("Email......" + email.getTo());
				// String expectedEmail = "michaela.lilja@hotmail.com";
				// String actualEmail = email.getTo() ;
				expectedupdateddate = member.getUpdateddate();
				actualupdateddate = email.getMailsentdate();
				email.getMailsentdate();

				String expectedResponse = "Success";
				String actualResponse = email.getReason();
				Assert.assertEquals(actualResponse, expectedResponse);

				String expectedTriggerName = "Education 4 - Offline and sleep";
				String actualTriggerName = email.getTriggerName();

				log.info("Triggered message verification");
				Assert.assertEquals(actualTriggerName, expectedTriggerName);
				log.info("Message Triggered successfully");

				log.info("Mail sent Date verification");
				expectedupdateddate = member.getUpdateddate();
				actualupdateddate = email.getMailsentdate();
				Assert.assertEquals(actualupdateddate, expectedupdateddate);

				log.info("Response verification");
				Assert.assertEquals(actualResponse, expectedResponse);
				log.info("Response is success");

				log.info("Mail-Subject verification");
				Assert.assertEquals(actualSubject, expectedSubject);
				log.info("Subject verified successfully");
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		log.info("Batch Execution");
		driver.get("http://130.211.74.42:8082/nextory_batch/jobs/job-tip4-offline-and-sleep");
			
  }
	
	@Test(enabled = true, priority = 91, groups = { "CustomerEducationRuleMailerPositive", "All" })
	public void offlineAndSleepFreeCampaign() throws InterruptedException 
	{
		log.info("---------------Inside Offline and sleep Free Campaign script Positive------------------------");

		log.info("Fetching data from databse MySQL");
		result = info.getDataForCustomerInfo("OfflineAndSleepFreeCampaign");
		log.info("Customerid from Sql : " + result);

		log.info("Fetching data from databse Mongo");
		String customerid = InformationFromBackend.result;
		Membership custList = null;
		log.info("Customerid=" + customerid);
		try
		{
				//--------------------------------------------nx_membership_change_log---------------------------------
			Query query = ds.createQuery(Membership.class);
			query.criteria("mem_type_code_old").exists();
			query.criteria("mem_type_code_old").notEqual(null);
			query.where("this.mem_type_code_old != this.mem_type_code_new");
			    
				/*  query.and(new Criteria []{ ds.createQuery(Membership.class).criteria("customerid").equal(customerid),
			    		  ds.createQuery(Membership.class).criteria("mem_type_code_old").exists(),
			    		  ds.createQuery(Membership.class).criteria("mem_type_code_old").notEqual(null),
			    		  ds.createQuery(Membership.class).criteria("mem_type_code_old") .notEqual("mem_type_code_new")}); */
			query.order("-_id");
			query.limit(1);

			custList = (Membership) query.get();
			if (custList != null) {
				//log.info("Id is=" + custList.getId());
				log.info("Mem type code old=" + custList.getMem_type_code_old());
				log.info("Mem type code new=" + custList.getMem_type_code_new());
				log.info("Customer id =" + InformationFromBackend.result);
			}
			log.info(custList.getMem_type_code_previous());
			Date latestdate = null;
		} catch (Exception e) {
			e.printStackTrace();
		}

		DateTime d = new DateTime(custList.getUpdateddate());
		Date sevenday = d.minusDays(7).toDate();

		//db.nx_membership_change_log.update({"_id":17},{$set : {"updateddate" : -7day }})
		UpdateOperations<Membership> ops = ds.createUpdateOperations(Membership.class).set("updateddate", sevenday);
		ds.update(ds.createQuery(Membership.class).field("customerid").equal(customerid), ops, true);
		//log.info("Updated date....."+sevenday);
					
		//-------------------------------------------nx_customer_email_logs--------------------------------------
		Query query1 = ds.createQuery(Email.class);
		query1.filter("customerid", customerid);
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
			ArrayList<Email> emailList = (ArrayList<Email>) query1.asList();

			for (Email email : emailList) 
			{
				// email.getClass();
				//log.info("CustomerID....." + customerid);
				email.getFirstname();
				email.getLastname();
				email.getFrom();
				email.getMobilenumber();
				email.getReason();
				email.getTriggerName();

				expectedSubject = "Läsa, resa, sova";
				actualSubject = email.getSubject();

				log.info("Email......" + email.getTo());
				// String expectedEmail = "michaela.lilja@hotmail.com";
				// String actualEmail = email.getTo() ;
				expectedupdateddate = member.getUpdateddate();
				actualupdateddate = email.getMailsentdate();
				email.getMailsentdate();

				String expectedResponse = "Success";
				String actualResponse = email.getReason();
				Assert.assertEquals(actualResponse, expectedResponse);

				String expectedTriggerName = "Education 4 - Offline and sleep";
				String actualTriggerName = email.getTriggerName();

				log.info("Triggered message verification");
				Assert.assertEquals(actualTriggerName, expectedTriggerName);
				log.info("Message Triggered successfully");

				log.info("Mail sent Date verification");
				expectedupdateddate = member.getUpdateddate();
				actualupdateddate = email.getMailsentdate();
				Assert.assertEquals(actualupdateddate, expectedupdateddate);

				log.info("Response verification");
				Assert.assertEquals(actualResponse, expectedResponse);
				log.info("Response is success");

				log.info("Mail-Subject verification");
				Assert.assertEquals(actualSubject, expectedSubject);
				log.info("Subject verified successfully");
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		log.info("Batch Execution");
		driver.get("http://130.211.74.42:8082/nextory_batch/jobs/job-tip4-offline-and-sleep");
			
  }
	@Test(enabled = true, priority = 92, groups = { "CustomerEducationRuleMailerPositive", "All" })
	public void offlineAndSleepFreeGiftCard() throws InterruptedException 
	{
		log.info("---------------Inside Offline and sleep Free Gift Card script Positive------------------------");

		log.info("Fetching data from databse MySQL");
		result = info.getDataForCustomerInfo("OfflineAndSleepFreeGiftCard");
		log.info("Customerid from Sql : " + result);

		log.info("Fetching data from databse Mongo");
		String customerid = InformationFromBackend.result;
		Membership custList = null;
		log.info("Customerid=" + customerid);
		try
		{
				//--------------------------------------------nx_membership_change_log---------------------------------
			Query query = ds.createQuery(Membership.class);
			query.criteria("mem_type_code_old").exists();
			query.criteria("mem_type_code_old").notEqual(null);
			query.where("this.mem_type_code_old != this.mem_type_code_new");
			    
				/*  query.and(new Criteria []{ ds.createQuery(Membership.class).criteria("customerid").equal(customerid),
			    		  ds.createQuery(Membership.class).criteria("mem_type_code_old").exists(),
			    		  ds.createQuery(Membership.class).criteria("mem_type_code_old").notEqual(null),
			    		  ds.createQuery(Membership.class).criteria("mem_type_code_old") .notEqual("mem_type_code_new")}); */
			query.order("-_id");
			query.limit(1);

			custList = (Membership) query.get();
			if (custList != null) {
				//log.info("Id is=" + custList.getId());
				log.info("Mem type code old=" + custList.getMem_type_code_old());
				log.info("Mem type code new=" + custList.getMem_type_code_new());
				log.info("Customer id =" + InformationFromBackend.result);
			}
			log.info(custList.getMem_type_code_previous());
			Date latestdate = null;
		} catch (Exception e) {
			e.printStackTrace();
		}

		DateTime d = new DateTime(custList.getUpdateddate());
		Date sevenday = d.minusDays(7).toDate();

		//db.nx_membership_change_log.update({"_id":17},{$set : {"updateddate" : -7day }})
		UpdateOperations<Membership> ops = ds.createUpdateOperations(Membership.class).set("updateddate", sevenday);
		ds.update(ds.createQuery(Membership.class).field("customerid").equal(customerid), ops, true);
		//log.info("Updated date....."+sevenday);
					
		//-------------------------------------------nx_customer_email_logs--------------------------------------
		Query query1 = ds.createQuery(Email.class);
		query1.filter("customerid", customerid);
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
			ArrayList<Email> emailList = (ArrayList<Email>) query1.asList();

			for (Email email : emailList) 
			{
				// email.getClass();
				//log.info("CustomerID....." + customerid);
				email.getFirstname();
				email.getLastname();
				email.getFrom();
				email.getMobilenumber();
				email.getReason();
				email.getTriggerName();

				expectedSubject = "Läsa, resa, sova";
				actualSubject = email.getSubject();

				log.info("Email......" + email.getTo());
				// String expectedEmail = "michaela.lilja@hotmail.com";
				// String actualEmail = email.getTo() ;
				expectedupdateddate = member.getUpdateddate();
				actualupdateddate = email.getMailsentdate();
				email.getMailsentdate();

				String expectedResponse = "Success";
				String actualResponse = email.getReason();
				Assert.assertEquals(actualResponse, expectedResponse);

				String expectedTriggerName = "Education 4 - Offline and sleep";
				String actualTriggerName = email.getTriggerName();

				log.info("Triggered message verification");
				Assert.assertEquals(actualTriggerName, expectedTriggerName);
				log.info("Message Triggered successfully");

				log.info("Mail sent Date verification");
				expectedupdateddate = member.getUpdateddate();
				actualupdateddate = email.getMailsentdate();
				Assert.assertEquals(actualupdateddate, expectedupdateddate);

				log.info("Response verification");
				Assert.assertEquals(actualResponse, expectedResponse);
				log.info("Response is success");

				log.info("Mail-Subject verification");
				Assert.assertEquals(actualSubject, expectedSubject);
				log.info("Subject verified successfully");
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		log.info("Batch Execution");
		driver.get("http://130.211.74.42:8082/nextory_batch/jobs/job-tip4-offline-and-sleep");
			
  }
	
	
	@Test(enabled = true, priority = 93, groups = { "CustomerEducationRuleMailerNegative", "All" })
	public void offlineAndSleepFreeTrialNegative() throws InterruptedException 
	{
		log.info("-----------------------------Inside Offline and sleep Free Trial script Negative------------------------");

		log.info("Fetching data from databse MySQL");
		result = info.getDataForCustomerInfo("OfflineAndSleepFreeTrial");
		log.info("Customer id from Sql: " + result);

		log.info("Fetching data from databse Mongo");
		String customerid = InformationFromBackend.result;
		Membership custList = null;
		log.info("Customerid=" + customerid);
		try
		{
				//--------------------------------------------nx_membership_change_log---------------------------------
			Query query = ds.createQuery(Membership.class);
			query.criteria("mem_type_code_old").exists();
			query.criteria("mem_type_code_old").notEqual(null);
			query.where("this.mem_type_code_old != this.mem_type_code_new");
			    
				/*  query.and(new Criteria []{ ds.createQuery(Membership.class).criteria("customerid").equal(customerid),
			    		  ds.createQuery(Membership.class).criteria("mem_type_code_old").exists(),
			    		  ds.createQuery(Membership.class).criteria("mem_type_code_old").notEqual(null),
			    		  ds.createQuery(Membership.class).criteria("mem_type_code_old") .notEqual("mem_type_code_new")}); */
			query.order("-_id");
			query.limit(1);

			custList = (Membership) query.get();
			if (custList != null)
			{
				//log.info("Id is=" + custList.getId());
				log.info("Mem type code old=" + custList.getMem_type_code_old());
				log.info("Mem type code new=" + custList.getMem_type_code_new());
				log.info("Customer id =" +InformationFromBackendNegative.result); 
				//custList.getCustomerid());
			}
			log.info(custList.getMem_type_code_previous());
			Date latestdate = null;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		DateTime d = new DateTime(custList.getUpdateddate());
		Date eighthday = d.minusDays(8).toDate();

		//db.nx_membership_change_log.update({"_id":17},{$set : {"updateddate" : -7day }})
		UpdateOperations<Membership> ops = ds.createUpdateOperations(Membership.class).set("updateddate", eighthday);
		ds.update(ds.createQuery(Membership.class).field("customerid").equal(customerid), ops, true);
		//log.info("Updated date....."+eighthday);
					
		//-------------------------------------------nx_customer_email_logs--------------------------------------
		Query query1 = ds.createQuery(Email.class);
		query1.filter("customerid", customerid);
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
			ArrayList<Email> emailList = (ArrayList<Email>) query1.asList();

			for (Email email : emailList) 
			{
				// email.getClass();
				log.info("CustomerID....." + customerid);
				email.getFirstname();
				email.getLastname();
				email.getFrom();
				email.getMobilenumber();
				email.getReason();
				email.getTriggerName();

				expectedSubject = "Läsa, resa, sova";
				actualSubject = email.getSubject();

				log.info("Email......" + email.getTo());
				// String expectedEmail = "michaela.lilja@hotmail.com";
				// String actualEmail = email.getTo() ;
				expectedupdateddate = member.getUpdateddate();
				actualupdateddate = email.getMailsentdate();
				email.getMailsentdate();

				String expectedResponse = "Success";
				String actualResponse = email.getReason();
				Assert.assertEquals(actualResponse, expectedResponse);

				String expectedTriggerName = "Education 4 - Offline and sleep";
				String actualTriggerName = email.getTriggerName();

				log.info("Triggered message verification");
				Assert.assertEquals(actualTriggerName, expectedTriggerName);
				log.info("Message Triggered successfully");

				log.info("Mail sent Date verification");
				expectedupdateddate = member.getUpdateddate();
				actualupdateddate = email.getMailsentdate();
				Assert.assertEquals(actualupdateddate, expectedupdateddate);

				log.info("Response verification");
				Assert.assertEquals(actualResponse, expectedResponse);
				log.info("Response is success");

				log.info("Mail-Subject verification");
				Assert.assertEquals(actualSubject, expectedSubject);
				log.info("Subject verified successfully");
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		log.info("Batch Execution");
		driver.get("http://130.211.74.42:8082/nextory_batch/jobs/job-tip4-offline-and-sleep");
			
  }
	@Test(enabled = true, priority = 94, groups = { "CustomerEducationRuleMailerNegative", "All" })
	public void offlineAndSleepFreeCampaignNegative() throws InterruptedException 
	{
		log.info("-----------------------------Inside Offline and sleep Free Campaign script Negative------------------------");

		log.info("Fetching data from databse MySQL");
		result = info.getDataForCustomerInfo("OfflineAndSleepFreeCampaign");
		log.info("Customer id from Sql: " + result);

		log.info("Fetching data from databse Mongo");
		String customerid = InformationFromBackend.result;
		Membership custList = null;
		log.info("Customerid=" + customerid);
		try
		{
				//--------------------------------------------nx_membership_change_log---------------------------------
			Query query = ds.createQuery(Membership.class);
			query.criteria("mem_type_code_old").exists();
			query.criteria("mem_type_code_old").notEqual(null);
			query.where("this.mem_type_code_old != this.mem_type_code_new");
			    
				/*  query.and(new Criteria []{ ds.createQuery(Membership.class).criteria("customerid").equal(customerid),
			    		  ds.createQuery(Membership.class).criteria("mem_type_code_old").exists(),
			    		  ds.createQuery(Membership.class).criteria("mem_type_code_old").notEqual(null),
			    		  ds.createQuery(Membership.class).criteria("mem_type_code_old") .notEqual("mem_type_code_new")}); */
			query.order("-_id");
			query.limit(1);

			custList = (Membership) query.get();
			if (custList != null)
			{
				//log.info("Id is=" + custList.getId());
				log.info("Mem type code old=" + custList.getMem_type_code_old());
				log.info("Mem type code new=" + custList.getMem_type_code_new());
				log.info("Customer id =" +InformationFromBackendNegative.result); 
				//custList.getCustomerid());
			}
			log.info(custList.getMem_type_code_previous());
			Date latestdate = null;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		DateTime d = new DateTime(custList.getUpdateddate());
		Date eighthday = d.minusDays(8).toDate();

		//db.nx_membership_change_log.update({"_id":17},{$set : {"updateddate" : -7day }})
		UpdateOperations<Membership> ops = ds.createUpdateOperations(Membership.class).set("updateddate", eighthday);
		ds.update(ds.createQuery(Membership.class).field("customerid").equal(customerid), ops, true);
		//log.info("Updated date....."+eighthday);
					
		//-------------------------------------------nx_customer_email_logs--------------------------------------
		Query query1 = ds.createQuery(Email.class);
		query1.filter("customerid", customerid);
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
			ArrayList<Email> emailList = (ArrayList<Email>) query1.asList();

			for (Email email : emailList) 
			{
				// email.getClass();
				log.info("CustomerID....." + customerid);
				email.getFirstname();
				email.getLastname();
				email.getFrom();
				email.getMobilenumber();
				email.getReason();
				email.getTriggerName();

				expectedSubject = "Läsa, resa, sova";
				actualSubject = email.getSubject();

				log.info("Email......" + email.getTo());
				// String expectedEmail = "michaela.lilja@hotmail.com";
				// String actualEmail = email.getTo() ;
				expectedupdateddate = member.getUpdateddate();
				actualupdateddate = email.getMailsentdate();
				email.getMailsentdate();

				String expectedResponse = "Success";
				String actualResponse = email.getReason();
				Assert.assertEquals(actualResponse, expectedResponse);

				String expectedTriggerName = "Education 4 - Offline and sleep";
				String actualTriggerName = email.getTriggerName();

				log.info("Triggered message verification");
				Assert.assertEquals(actualTriggerName, expectedTriggerName);
				log.info("Message Triggered successfully");

				log.info("Mail sent Date verification");
				expectedupdateddate = member.getUpdateddate();
				actualupdateddate = email.getMailsentdate();
				Assert.assertEquals(actualupdateddate, expectedupdateddate);

				log.info("Response verification");
				Assert.assertEquals(actualResponse, expectedResponse);
				log.info("Response is success");

				log.info("Mail-Subject verification");
				Assert.assertEquals(actualSubject, expectedSubject);
				log.info("Subject verified successfully");
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		log.info("Batch Execution");
		driver.get("http://130.211.74.42:8082/nextory_batch/jobs/job-tip4-offline-and-sleep");	
      }
	
	@Test(enabled = true, priority = 95, groups = { "CustomerEducationRuleMailerNegative", "All" })
	public void offlineAndSleepFreeGiftCardNegative() throws InterruptedException 
	{
		log.info("-----------------------------Inside Offline and sleep Free Gift Card script Negative------------------------");

		log.info("Fetching data from databse MySQL");
		result = info.getDataForCustomerInfo("OfflineAndSleepFreeGiftCard");
		log.info("Customer id from Sql: " + result);

		log.info("Fetching data from databse Mongo");
		String customerid = InformationFromBackend.result;
		Membership custList = null;
		log.info("Customerid=" + customerid);
		try
		{
			//--------------------------------------------nx_membership_change_log---------------------------------
			Query query = ds.createQuery(Membership.class);
			query.criteria("mem_type_code_old").exists();
			query.criteria("mem_type_code_old").notEqual(null);
			query.where("this.mem_type_code_old != this.mem_type_code_new");
			    
			query.order("-_id");
			query.limit(1);

			custList = (Membership) query.get();
			if (custList != null)
			{
				//log.info("Id is=" + custList.getId());
				log.info("Mem type code old=" + custList.getMem_type_code_old());
				log.info("Mem type code new=" + custList.getMem_type_code_new());
				log.info("Customer id =" +InformationFromBackendNegative.result); 
				//custList.getCustomerid());
			}
			log.info(custList.getMem_type_code_previous());
			Date latestdate = null;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		DateTime d = new DateTime(custList.getUpdateddate());
		Date eighthday = d.minusDays(8).toDate();

		//db.nx_membership_change_log.update({"_id":17},{$set : {"updateddate" : -7day }})
		UpdateOperations<Membership> ops = ds.createUpdateOperations(Membership.class).set("updateddate", eighthday);
		ds.update(ds.createQuery(Membership.class).field("customerid").equal(customerid), ops, true);
		//log.info("Updated date....."+eighthday);
					
		//-------------------------------------------nx_customer_email_logs--------------------------------------
		Query query1 = ds.createQuery(Email.class);
		query1.filter("customerid", customerid);

		try 
		{
			ArrayList<Email> emailList = (ArrayList<Email>) query1.asList();

			for (Email email : emailList) 
			{
				// email.getClass();
				log.info("CustomerID....." + customerid);
				email.getFirstname();
				email.getLastname();
				email.getFrom();
				email.getMobilenumber();
				email.getReason();
				email.getTriggerName();

				expectedSubject = "Läsa, resa, sova";
				actualSubject = email.getSubject();

				log.info("Email......" + email.getTo());
				// String expectedEmail = "michaela.lilja@hotmail.com";
				// String actualEmail = email.getTo() ;
				expectedupdateddate = member.getUpdateddate();
				actualupdateddate = email.getMailsentdate();
				email.getMailsentdate();

				String expectedResponse = "Success";
				String actualResponse = email.getReason();
				Assert.assertEquals(actualResponse, expectedResponse);

				String expectedTriggerName = "Education 4 - Offline and sleep";
				String actualTriggerName = email.getTriggerName();

				log.info("Triggered message verification");
				Assert.assertEquals(actualTriggerName, expectedTriggerName);
				log.info("Message Triggered successfully");

				log.info("Mail sent Date verification");
				expectedupdateddate = member.getUpdateddate();
				actualupdateddate = email.getMailsentdate();
				Assert.assertEquals(actualupdateddate, expectedupdateddate);

				log.info("Response verification");
				Assert.assertEquals(actualResponse, expectedResponse);
				log.info("Response is success");

				log.info("Mail-Subject verification");
				Assert.assertEquals(actualSubject, expectedSubject);
				log.info("Subject verified successfully");
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		log.info("Batch Execution");
		driver.get("http://130.211.74.42:8082/nextory_batch/jobs/job-tip4-offline-and-sleep");
			
  }
}	 
