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

public class WhereToRead extends SuperTestScript 
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
	 
	 
	 public WhereToRead()
		{
			loginRequired=false;
			logoutRequired=false;
		}
   @Test(enabled=true, priority=80, groups={"CustomerEducationRuleMailerPositive", "All"}) 
   public void whereToReadFreeTrial() throws InterruptedException
   {
	   log.info("-------------------Where to read Free Trial script positive---------------------------");
	   
	   log.info("Fetching data from databse MySQL");
	   result=info.getDataForCustomerInfo("WhereToReadFreeTrial");
	   log.info("Customerid : " + result);
	   
	   log.info("Fetching data from databse Mongo");
	   String customerid = InformationFromBackend.result;
		 Membership custList = null;
		 log.info("Customerid="+customerid);
		 try
		 {
			//--------------------------------------------nx_membership_change_log---------------------------------
			 Query query = ds.createQuery(Membership.class);	
			  query.criteria("mem_type_code_old").exists();
			  query.criteria("mem_type_code_old").notEqual(null);
			  query. where("this.mem_type_code_old != this.mem_type_code_new");
		       	  	     
			 /* query.and(new Criteria []{ ds.createQuery(Membership.class).criteria("customerid").equal(customerid),
		    		  ds.createQuery(Membership.class).criteria("mem_type_code_old").exists(),
		    		  ds.createQuery(Membership.class).criteria("mem_type_code_old").notEqual(null),
		    		  ds.createQuery(Membership.class).criteria("mem_type_code_old") .notEqual("mem_type_code_new")}); */
			  query.order("-_id");
		      query.limit(1);
		      
		     custList = (Membership)query.get();    
		     if(custList != null)
		      {
		    	 //System.out.println("Id is="+custList.getId());
		    	  System.out.println("Mem type code old="+custList.getMem_type_code_old());
		    	  System.out.println("Mem type code new="+custList.getMem_type_code_new());
		    	 // System.out.println("Customer id ="+custList.getCustomerid());
		      }
		    
		     Date latestdate = null;            		    
		     } 
		     catch(Exception e)
		     {
		    	e.printStackTrace();
		     } 	
		 
		    DateTime d = new DateTime( custList.getUpdateddate());
		    Date fiveday = d.minusDays( 5 ).toDate();
		    
			 //db.nx_membership_change_log.update({"_id":17},{$set : {"updateddate" : -5day }})
			UpdateOperations<Membership> ops = ds.createUpdateOperations(Membership.class).set("updateddate", fiveday);
			ds.update(ds.createQuery(Membership.class).field("customerid").equal( customerid ), ops,true);
			//System.out.println("Updated date....."+fiveday);
				
			//-------------------------------------------nx_customer_email_logs--------------------------------------
				Query query1=ds.createQuery(Email.class);
				query1.filter("customerid",customerid);
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
					ArrayList<Email> emailList=(ArrayList<Email>)query1.asList();
					
					   
					 for(Email email:emailList)
					 {
				//System.out.println("Class=" + email.getClass());
				// System.out.println( "CustomerID....." + customerid);
				System.out.println("Firstname=" + email.getFirstname());
				email.getLastname();
				email.getFrom();
				email.getMobilenumber();
				System.out.println("Reason=" + email.getReason());
				System.out.println("Triggername=" + email.getTriggerName());
				System.out.println("Subject name=" + email.getSubject());

				expectedSubject = "är perfekta för läsning";
				actualSubject = email.getSubject();

				// System.out.println("Email......"+email.getTo());
				// String expectedEmail = "michaela.lilja@hotmail.com";
				// String actualEmail = email.getTo() ;
				expectedupdateddate = member.getUpdateddate();
				actualupdateddate = email.getMailsentdate();
				email.getMailsentdate();
				String expectedResponse = "Success";
				String actualResponse = email.getReason();
				Assert.assertEquals(actualResponse, expectedResponse);

				String expectedTriggerName = "Education 3 - Where to read";
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
				catch(Exception e)
				{
					e.printStackTrace();
				}		
				
				log.info("Batch Execution");
				  driver.get("http://130.211.74.42:8082/nextory_batch/jobs/job-tip3-where-to-read");
   }
   
   @Test(enabled=true, priority=81, groups={"CustomerEducationRuleMailerPositive", "All"}) 
   public void whereToReadFreeCampaign() throws InterruptedException
   {
	   log.info("-------------------Where to read Free Camapign script positive---------------------------");
	   
	   log.info("Fetching data from databse MySQL");
	   result=info.getDataForCustomerInfo("WhereToReadFreeCampaign");
	   log.info("Customerid : " + result);
	   
	   log.info("Fetching data from databse Mongo");
	   String customerid = InformationFromBackend.result;
		 Membership custList = null;
		 log.info("Customerid="+customerid);
		 try
		 {
			//--------------------------------------------nx_membership_change_log---------------------------------
			 Query query = ds.createQuery(Membership.class);	
			  query.criteria("mem_type_code_old").exists();
			  query.criteria("mem_type_code_old").notEqual(null);
			  query. where("this.mem_type_code_old != this.mem_type_code_new");
		       	  	     
			 /* query.and(new Criteria []{ ds.createQuery(Membership.class).criteria("customerid").equal(customerid),
		    		  ds.createQuery(Membership.class).criteria("mem_type_code_old").exists(),
		    		  ds.createQuery(Membership.class).criteria("mem_type_code_old").notEqual(null),
		    		  ds.createQuery(Membership.class).criteria("mem_type_code_old") .notEqual("mem_type_code_new")}); */
			  query.order("-_id");
		      query.limit(1);
		      
		     custList = (Membership)query.get();    
		     if(custList != null)
		      {
		    	 //System.out.println("Id is="+custList.getId());
		    	  System.out.println("Mem type code old="+custList.getMem_type_code_old());
		    	  System.out.println("Mem type code new="+custList.getMem_type_code_new());
		    	 // System.out.println("Customer id ="+custList.getCustomerid());
		      }
		    
		     Date latestdate = null;            		    
		     } 
		     catch(Exception e)
		     {
		    	e.printStackTrace();
		     } 	
		 
		    DateTime d = new DateTime( custList.getUpdateddate());
		    Date fiveday = d.minusDays( 5 ).toDate();
		    
			 //db.nx_membership_change_log.update({"_id":17},{$set : {"updateddate" : -5day }})
			UpdateOperations<Membership> ops = ds.createUpdateOperations(Membership.class).set("updateddate", fiveday);
			ds.update(ds.createQuery(Membership.class).field("customerid").equal( customerid ), ops,true);
			//System.out.println("Updated date....."+fiveday);
				
			//-------------------------------------------nx_customer_email_logs--------------------------------------
				Query query1=ds.createQuery(Email.class);
				query1.filter("customerid",customerid);
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
					ArrayList<Email> emailList=(ArrayList<Email>)query1.asList();
					
					   
					 for(Email email:emailList)
					 {
				//System.out.println("Class=" + email.getClass());
				// System.out.println( "CustomerID....." + customerid);
				System.out.println("Firstname=" + email.getFirstname());
				email.getLastname();
				email.getFrom();
				email.getMobilenumber();
				System.out.println("Reason=" + email.getReason());
				System.out.println("Triggername=" + email.getTriggerName());
				System.out.println("Subject name=" + email.getSubject());

				expectedSubject = "är perfekta för läsning";
				actualSubject = email.getSubject();

				// System.out.println("Email......"+email.getTo());
				// String expectedEmail = "michaela.lilja@hotmail.com";
				// String actualEmail = email.getTo() ;
				expectedupdateddate = member.getUpdateddate();
				actualupdateddate = email.getMailsentdate();
				email.getMailsentdate();
				String expectedResponse = "Success";
				String actualResponse = email.getReason();
				Assert.assertEquals(actualResponse, expectedResponse);

				String expectedTriggerName = "Education 3 - Where to read";
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
				catch(Exception e)
				{
					e.printStackTrace();
				}		
				
				log.info("Batch Execution");
				  driver.get("http://130.211.74.42:8082/nextory_batch/jobs/job-tip3-where-to-read");
   }
   @Test(enabled=true, priority=82, groups={"CustomerEducationRuleMailerPositive", "All"}) 
   public void whereToReadFreeGiftCard() throws InterruptedException
   {
	   log.info("-------------------Where to read Free Gift Card script positive---------------------------");
	   
	   log.info("Fetching data from databse MySQL");
	   result=info.getDataForCustomerInfo("WhereToReadFreeGiftCard");
	   log.info("Customerid : " + result);
	   
	   log.info("Fetching data from databse Mongo");
	   String customerid = InformationFromBackend.result;
		 Membership custList = null;
		 log.info("Customerid="+customerid);
		 try
		 {
			//--------------------------------------------nx_membership_change_log---------------------------------
			 Query query = ds.createQuery(Membership.class);	
			  query.criteria("mem_type_code_old").exists();
			  query.criteria("mem_type_code_old").notEqual(null);
			  query. where("this.mem_type_code_old != this.mem_type_code_new");
		       	  	     
			 /* query.and(new Criteria []{ ds.createQuery(Membership.class).criteria("customerid").equal(customerid),
		    		  ds.createQuery(Membership.class).criteria("mem_type_code_old").exists(),
		    		  ds.createQuery(Membership.class).criteria("mem_type_code_old").notEqual(null),
		    		  ds.createQuery(Membership.class).criteria("mem_type_code_old") .notEqual("mem_type_code_new")}); */
			  query.order("-_id");
		      query.limit(1);
		      
		     custList = (Membership)query.get();    
		     if(custList != null)
		      {
		    	 //System.out.println("Id is="+custList.getId());
		    	  System.out.println("Mem type code old="+custList.getMem_type_code_old());
		    	  System.out.println("Mem type code new="+custList.getMem_type_code_new());
		    	 // System.out.println("Customer id ="+custList.getCustomerid());
		      }
		    
		     Date latestdate = null;            		    
		     } 
		     catch(Exception e)
		     {
		    	e.printStackTrace();
		     } 	
		 
		    DateTime d = new DateTime( custList.getUpdateddate());
		    Date fiveday = d.minusDays( 5 ).toDate();
		    
			 //db.nx_membership_change_log.update({"_id":17},{$set : {"updateddate" : -5day }})
			UpdateOperations<Membership> ops = ds.createUpdateOperations(Membership.class).set("updateddate", fiveday);
			ds.update(ds.createQuery(Membership.class).field("customerid").equal( customerid ), ops,true);
			//System.out.println("Updated date....."+fiveday);
				
			//-------------------------------------------nx_customer_email_logs--------------------------------------
				Query query1=ds.createQuery(Email.class);
				query1.filter("customerid",customerid);
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
					ArrayList<Email> emailList=(ArrayList<Email>)query1.asList();
					
					   
					 for(Email email:emailList)
					 {
				//System.out.println("Class=" + email.getClass());
				// System.out.println( "CustomerID....." + customerid);
				System.out.println("Firstname=" + email.getFirstname());
				email.getLastname();
				email.getFrom();
				email.getMobilenumber();
				System.out.println("Reason=" + email.getReason());
				System.out.println("Triggername=" + email.getTriggerName());
				System.out.println("Subject name=" + email.getSubject());

				expectedSubject = "är perfekta för läsning";
				actualSubject = email.getSubject();

				// System.out.println("Email......"+email.getTo());
				// String expectedEmail = "michaela.lilja@hotmail.com";
				// String actualEmail = email.getTo() ;
				expectedupdateddate = member.getUpdateddate();
				actualupdateddate = email.getMailsentdate();
				email.getMailsentdate();
				String expectedResponse = "Success";
				String actualResponse = email.getReason();
				Assert.assertEquals(actualResponse, expectedResponse);

				String expectedTriggerName = "Education 3 - Where to read";
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
				catch(Exception e)
				{
					e.printStackTrace();
				}		
				
				log.info("Batch Execution");
				  driver.get("http://130.211.74.42:8082/nextory_batch/jobs/job-tip3-where-to-read");
   }
   @Test(enabled=true, priority=83, groups={"CustomerEducationRuleMailerNegative", "All"}) 
   public void whereToReadFreeTrialNegative() throws InterruptedException
   {
	   log.info("-------------------------------Where to read Free Trial script Negative--------------------------------------");
	   
	   log.info("Fetching data from databse MySQL");
	   result=info.getDataForCustomerInfo("WhereToReadFreeTrial");
	   log.info("Customerid : " + result);
	   
	   log.info("Fetching data from databse Mongo");
	   String customerid = InformationFromBackend.result;
		 Membership custList = null;
		 log.info("Customerid="+customerid);
		 try
		 {
			//--------------------------------------------nx_membership_change_log---------------------------------
			 Query query = ds.createQuery(Membership.class);	
			  query.criteria("mem_type_code_old").exists();
			  query.criteria("mem_type_code_old").notEqual(null);
			  query. where("this.mem_type_code_old != this.mem_type_code_new");
		       	  	     
			 /* query.and(new Criteria []{ ds.createQuery(Membership.class).criteria("customerid").equal(customerid),
		    		  ds.createQuery(Membership.class).criteria("mem_type_code_old").exists(),
		    		  ds.createQuery(Membership.class).criteria("mem_type_code_old").notEqual(null),
		    		  ds.createQuery(Membership.class).criteria("mem_type_code_old") .notEqual("mem_type_code_new")}); */
			  query.order("-_id");
		      query.limit(1);
		      
		     custList = (Membership)query.get();    
		     if(custList != null)
		      {
		    	// System.out.println("Id is="+custList.getId());
		    	  System.out.println("Mem type code old="+custList.getMem_type_code_old());
		    	  System.out.println("Mem type code new="+custList.getMem_type_code_new());
		    	//  System.out.println("Customer id ="+custList.getCustomerid());
		      }
		    
		     Date latestdate = null;            		    
		     } 
		     catch(Exception e)
		     {
		    	e.printStackTrace();
		     } 	
		 
		    DateTime d = new DateTime( custList.getUpdateddate());
		    Date sixthday = d.minusDays( 6 ).toDate();
		    
			 //db.nx_membership_change_log.update({"_id":17},{$set : {"updateddate" : -5day }})
			UpdateOperations<Membership> ops = ds.createUpdateOperations(Membership.class).set("updateddate", sixthday);
			ds.update(ds.createQuery(Membership.class).field("customerid").equal( customerid ), ops,true);
			//System.out.println("Updated date....."+sixthday);
				
			//-------------------------------------------nx_customer_email_logs--------------------------------------
				Query query1=ds.createQuery(Email.class);
				query1.filter("customerid",customerid);
				try
				{
					ArrayList<Email> emailList=(ArrayList<Email>)query1.asList();
					
					   
			for(Email email:emailList)
			{
				System.out.println("Firstname=" + email.getFirstname());
				email.getLastname();
				email.getFrom();
				email.getMobilenumber();
				System.out.println("Reason=" + email.getReason());
				System.out.println("Triggername=" + email.getTriggerName());
				System.out.println("Subject name=" + email.getSubject());

				expectedSubject = "är perfekta för läsning";
				actualSubject = email.getSubject();

				// System.out.println("Email......"+email.getTo());
				// String expectedEmail = "michaela.lilja@hotmail.com";
				// String actualEmail = email.getTo() ;
				expectedupdateddate = member.getUpdateddate();
				actualupdateddate = email.getMailsentdate();
				email.getMailsentdate();
				String expectedResponse = "Success";
				String actualResponse = email.getReason();
				Assert.assertEquals(actualResponse, expectedResponse);

				String expectedTriggerName = "Education 3 - Where to read";
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
				catch(Exception e)
				{
					e.printStackTrace();
				}		
				
				log.info("Batch Execution");
				driver.get("http://130.211.74.42:8082/nextory_batch/jobs/job-tip3-where-to-read");
   }
   @Test(enabled=true, priority=84, groups={"CustomerEducationRuleMailerNegative", "All"}) 
   public void whereToReadFreeCampaignNegative() throws InterruptedException
   {
	   log.info("-------------------------------Where to read Free Campaign script Negative--------------------------------------");
	   
	   log.info("Fetching data from databse MySQL");
	   result=info.getDataForCustomerInfo("WhereToReadFreeCampaign");
	   log.info("Customerid : " + result);
	   
	   log.info("Fetching data from databse Mongo");
	   String customerid = InformationFromBackend.result;
		 Membership custList = null;
		 log.info("Customerid="+customerid);
		 try
		 {
			//--------------------------------------------nx_membership_change_log---------------------------------
			 Query query = ds.createQuery(Membership.class);	
			  query.criteria("mem_type_code_old").exists();
			  query.criteria("mem_type_code_old").notEqual(null);
			  query. where("this.mem_type_code_old != this.mem_type_code_new");
		       	  	     
			 /* query.and(new Criteria []{ ds.createQuery(Membership.class).criteria("customerid").equal(customerid),
		    		  ds.createQuery(Membership.class).criteria("mem_type_code_old").exists(),
		    		  ds.createQuery(Membership.class).criteria("mem_type_code_old").notEqual(null),
		    		  ds.createQuery(Membership.class).criteria("mem_type_code_old") .notEqual("mem_type_code_new")}); */
			  query.order("-_id");
		      query.limit(1);
		      
		     custList = (Membership)query.get();    
		     if(custList != null)
		      {
		    	// System.out.println("Id is="+custList.getId());
		    	  System.out.println("Mem type code old="+custList.getMem_type_code_old());
		    	  System.out.println("Mem type code new="+custList.getMem_type_code_new());
		    	//  System.out.println("Customer id ="+custList.getCustomerid());
		      }
		    
		     Date latestdate = null;            		    
		     } 
		     catch(Exception e)
		     {
		    	e.printStackTrace();
		     } 	
		 
		    DateTime d = new DateTime( custList.getUpdateddate());
		    Date sixthday = d.minusDays( 6 ).toDate();
		    
			 //db.nx_membership_change_log.update({"_id":17},{$set : {"updateddate" : -5day }})
			UpdateOperations<Membership> ops = ds.createUpdateOperations(Membership.class).set("updateddate", sixthday);
			ds.update(ds.createQuery(Membership.class).field("customerid").equal( customerid ), ops,true);
			//System.out.println("Updated date....."+sixthday);
				
			//-------------------------------------------nx_customer_email_logs--------------------------------------
				Query query1=ds.createQuery(Email.class);
				query1.filter("customerid",customerid);
				try
				{
					ArrayList<Email> emailList=(ArrayList<Email>)query1.asList();
					
					   
			for(Email email:emailList)
			{
				System.out.println("Firstname=" + email.getFirstname());
				email.getLastname();
				email.getFrom();
				email.getMobilenumber();
				System.out.println("Reason=" + email.getReason());
				System.out.println("Triggername=" + email.getTriggerName());
				System.out.println("Subject name=" + email.getSubject());

				expectedSubject = "är perfekta för läsning";
				actualSubject = email.getSubject();

				// System.out.println("Email......"+email.getTo());
				// String expectedEmail = "michaela.lilja@hotmail.com";
				// String actualEmail = email.getTo() ;
				expectedupdateddate = member.getUpdateddate();
				actualupdateddate = email.getMailsentdate();
				email.getMailsentdate();
				String expectedResponse = "Success";
				String actualResponse = email.getReason();
				Assert.assertEquals(actualResponse, expectedResponse);

				String expectedTriggerName = "Education 3 - Where to read";
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
				catch(Exception e)
				{
					e.printStackTrace();
				}		
				
				log.info("Batch Execution");
				driver.get("http://130.211.74.42:8082/nextory_batch/jobs/job-tip3-where-to-read");
   }
   @Test(enabled=true, priority=85, groups={"CustomerEducationRuleMailerNegative", "All"}) 
   public void whereToReadFreeGiftCardNegative() throws InterruptedException
   {
	   log.info("-------------------------------Where to read Free Gift Card script Negative--------------------------------------");
	   
	   log.info("Fetching data from databse MySQL");
	   result=info.getDataForCustomerInfo("WhereToReadFreeGiftCard");
	   log.info("Customerid : " + result);
	   
	   log.info("Fetching data from databse Mongo");
	   String customerid = InformationFromBackend.result;
		 Membership custList = null;
		 log.info("Customerid="+customerid);
		 try
		 {
			//--------------------------------------------nx_membership_change_log---------------------------------
			 Query query = ds.createQuery(Membership.class);	
			  query.criteria("mem_type_code_old").exists();
			  query.criteria("mem_type_code_old").notEqual(null);
			  query. where("this.mem_type_code_old != this.mem_type_code_new");
			  query.order("-_id");
		      query.limit(1);
		      
		     custList = (Membership)query.get();    
		     if(custList != null)
		      {
		    	// System.out.println("Id is="+custList.getId());
		    	  System.out.println("Mem type code old="+custList.getMem_type_code_old());
		    	  System.out.println("Mem type code new="+custList.getMem_type_code_new());
		    	//  System.out.println("Customer id ="+custList.getCustomerid());
		      }
		    
		     Date latestdate = null;            		    
		     } 
		     catch(Exception e)
		     {
		    	e.printStackTrace();
		     } 	
		 
		    DateTime d = new DateTime( custList.getUpdateddate());
		    Date sixthday = d.minusDays( 6 ).toDate();
		    
			 //db.nx_membership_change_log.update({"_id":17},{$set : {"updateddate" : -5day }})
			UpdateOperations<Membership> ops = ds.createUpdateOperations(Membership.class).set("updateddate", sixthday);
			ds.update(ds.createQuery(Membership.class).field("customerid").equal( customerid ), ops,true);
			//System.out.println("Updated date....."+sixthday);
				
			//-------------------------------------------nx_customer_email_logs--------------------------------------
				Query query1=ds.createQuery(Email.class);
				query1.filter("customerid",customerid);
				try
				{
					ArrayList<Email> emailList=(ArrayList<Email>)query1.asList();
					
					   
			for(Email email:emailList)
			{
				System.out.println("Firstname=" + email.getFirstname());
				email.getLastname();
				email.getFrom();
				email.getMobilenumber();
				System.out.println("Reason=" + email.getReason());
				System.out.println("Triggername=" + email.getTriggerName());
				System.out.println("Subject name=" + email.getSubject());

				expectedSubject = "är perfekta för läsning";
				actualSubject = email.getSubject();

				// System.out.println("Email......"+email.getTo());
				// String expectedEmail = "michaela.lilja@hotmail.com";
				// String actualEmail = email.getTo() ;
				expectedupdateddate = member.getUpdateddate();
				actualupdateddate = email.getMailsentdate();
				email.getMailsentdate();
				String expectedResponse = "Success";
				String actualResponse = email.getReason();
				Assert.assertEquals(actualResponse, expectedResponse);

				String expectedTriggerName = "Education 3 - Where to read";
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
				catch(Exception e)
				{
					e.printStackTrace();
				}		
				
				log.info("Batch Execution");
				driver.get("http://130.211.74.42:8082/nextory_batch/jobs/job-tip3-where-to-read");
   }
}
