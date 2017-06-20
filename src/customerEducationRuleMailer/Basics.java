package customerEducationRuleMailer;

import java.sql.SQLException;
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

public class Basics extends SuperTestScript
{
	 public static String newEmail;
	 public static String confirm;
	 public static String newPwd;
	 public static String cardNumber;
	 public static String cvc;
	 public static String fn;
	 public static String ln;
	 public static String cellNum;

	 MongoDBUtilMorphia mongoutil = new MongoDBUtilMorphia();
	 Datastore ds = mongoutil.getMorphiaDatastoreForNlob();
     Membership member=new Membership();
     Email email=new Email();
     InformationFromBackend inf=new InformationFromBackend();

	 String expectedSubject;
	 String actualSubject;
	 String result;
	 String CustomerId;
	 
//	 String expectedEmail="";
//	 String actualEmail="";
	 
	 String expectedupdateddate;
	 Date actualupdateddate;
	 
	 public Basics() 
	 {
	  loginRequired=false;
	  logoutRequired=false;
	 }
	 
  @Test(enabled=true, priority=60, groups={"CustomerEducationRuleMailerPositive", "All"})
  public void basicsFreeTrial() throws InterruptedException, SQLException
  {
		log.info("--------------In Basics FreeTrialMember Script Positive flow--------------------");

		log.info("Fetching data from databse MySQL");
		result = inf.getDataForCustomerInfo("BasicsFreeTrial");
		log.info("Customerid fetched is=" + result);

		log.info("Fetching data from MongoDB");
		Membership custList = null;
		String customerid = InformationFromBackend.result;

		log.info("Customerid selected from Sql is=" + customerid);
		 
	//--------------------------------------------nx_membership_change_log---------------------------------
	 try
	 {
		 //db.nx_membership_change_log.find({customerid:'$edu'},{_id:1}).sort({_id:-1}).limit(1)
	  Query query = ds.createQuery(Membership.class);
	  //query.criteria("customerid").equal("7672");
	  query.criteria("mem_type_code_old").exists();
	  query.criteria("mem_type_code_old").notEqual(null);
	  query. where("this.mem_type_code_old != this.mem_type_code_new");
	  
      /*query.and(new Criteria []{ ds.createQuery(Membership.class).criteria("customerid").equal(7672),
    		  ds.createQuery(Membership.class).criteria("mem_type_code_old").exists(),
    		  ds.createQuery(Membership.class).criteria("mem_type_code_old").notEqual(null),
    		  ds.createQuery(Membership.class).criteria("mem_type_code_old") .notEqual("mem_type_code_new")});
      */
      
    
      //query.retrievedFields(true, "_id");
      query.order("-_id");
      query.limit(1);
      
      custList = (Membership) query.get();
      if(custList != null)
      {
				//log.info("Id is=" + custList.getId());
				log.info("Mem type code old=" + custList.getMem_type_code_old());
				log.info("Mem type code new=" + custList.getMem_type_code_new());
				CustomerId = custList.getCustomerid();
				log.info("Customer id selected from mongo=" + CustomerId);
      }
       
     //custList = (Membership)query.asList();   //One value: query.key() (or) query.get()           
     //log.info( custList.getMem_type_code_previous());
     Date latestdate = null;            		    
    }
	 catch(Exception e)
	 {
    	e.printStackTrace();
    } 	
	 
	 //log.info("Before Date");
    DateTime d = new DateTime( custList.getUpdateddate());
    Date oneday = d.minusDays( 1 ).toDate();
    
    log.info("Date="+oneday);
    
	 //db.nx_membership_change_log.update({"_id":17},{$set : {"updateddate" : -1day }});
	UpdateOperations<Membership> ops = ds.createUpdateOperations(Membership.class).set("updateddate", oneday);
	ds.update(ds.createQuery(Membership.class).field("customerid").equal( CustomerId), ops,true);
		
	//-------------------------------------------nx_customer_email_logs--------------------------------------
		Query query1=ds.createQuery(Email.class);
		query1.filter("customerid",CustomerId);
		
		try
		{
			//custList = (Membership) query.get();
		     // if(custList != null)
			//Email emailList=(Email) query1.get();
			ArrayList<Email> emailList=(ArrayList<Email>)query1.asList();
			
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
				expectedSubject = "i ett nötskal";
				actualSubject = email.getSubject();
				Assert.assertEquals(actualSubject, expectedSubject);
				log.info("Subject verified successfully");

				log.info("Response verification");
				String expectedResponse = "Success";
				String actualResponse = email.getReason();
				Assert.assertEquals(actualResponse, expectedResponse);
				log.info("Response is success");

				log.info("Triggered message verification");
				String expectedTriggerName = "Education 1 - The basics";
				String actualTriggerName = email.getTriggerName();
				Assert.assertEquals(actualTriggerName, expectedTriggerName);
				log.info("Message Triggered successfully");

				log.info("Mail sent Date verification");
				expectedupdateddate = AddDate.currentDate();
				actualupdateddate = email.getMailsentdate();
				Assert.assertEquals(actualupdateddate, expectedupdateddate);
				log.info("Mail sent date verified successfully");

			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		
		log.info("Batch Execution");
		driver.get("http://130.211.74.42:8082/nextory_batch/jobs/job-tip1-nextory-bascis");
		
    }
  
	 @Test(enabled=true, priority=61 ,groups={"CustomerEducationRuleMailerPositive", "All"})
	 public void basicsFreeGiftCard() throws InterruptedException
	 {
		 log.info("----------------In Basics FreeGiftCardMember Script Positive------------------------");
		 
		 log.info("Fetching data from databse MySQL");
			result = inf.getDataForCustomerInfo("BasicsFreeGiftCard");
			log.info("Customerid fetched is=" + result);

			log.info("Fetching data from MongoDB");
			Membership custList = null;
			String customerid = InformationFromBackend.result;

			log.info("Customerid selected from Sql is=" + customerid);
			 
		//--------------------------------------------nx_membership_change_log---------------------------------
		 try
		 {
		  //db.nx_membership_change_log.find({customerid:'$edu'},{_id:1}).sort({_id:-1}).limit(1)
		  Query query = ds.createQuery(Membership.class);
		  //query.criteria("customerid").equal("7672");
		  query.criteria("mem_type_code_old").exists();
		  query.criteria("mem_type_code_old").notEqual(null);
		  query. where("this.mem_type_code_old != this.mem_type_code_new");
		  
	      //query.retrievedFields(true, "_id");
	      query.order("-_id");
	      query.limit(1);
	      
	      custList = (Membership) query.get();
	      if(custList != null)
	      {
					//log.info("Id is=" + custList.getId());
					log.info("Mem type code old=" + custList.getMem_type_code_old());
					log.info("Mem type code new=" + custList.getMem_type_code_new());
					CustomerId = custList.getCustomerid();
					log.info("Customer id selected from mongo=" + CustomerId);
	      }
	       
	     //custList = (Membership)query.asList();   //One value: query.key() (or) query.get()           
	     //log.info( custList.getMem_type_code_previous());
	     Date latestdate = null;            		    
	    }
		 catch(Exception e)
		 {
	    	e.printStackTrace();
	    } 	
		 
		 //log.info("Before Date");
	    DateTime d = new DateTime( custList.getUpdateddate());
	    Date oneday = d.minusDays( 1 ).toDate();
	    
	    log.info("Date="+oneday);
	    
		 //db.nx_membership_change_log.update({"_id":17},{$set : {"updateddate" : -1day }});
		UpdateOperations<Membership> ops = ds.createUpdateOperations(Membership.class).set("updateddate", oneday);
		ds.update(ds.createQuery(Membership.class).field("customerid").equal( CustomerId), ops,true);
			
		//-------------------------------------------nx_customer_email_logs--------------------------------------
			Query query1=ds.createQuery(Email.class);
			query1.filter("customerid",CustomerId);
			
			try
			{
				//custList = (Membership) query.get();
			     // if(custList != null)
				//Email emailList=(Email) query1.get();
				ArrayList<Email> emailList=(ArrayList<Email>)query1.asList();
				
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
					expectedSubject = "i ett nötskal";
					actualSubject = email.getSubject();
					Assert.assertEquals(actualSubject, expectedSubject);
					log.info("Subject verified successfully");

					log.info("Response verification");
					String expectedResponse = "Success";
					String actualResponse = email.getReason();
					Assert.assertEquals(actualResponse, expectedResponse);
					log.info("Response is success");

					log.info("Triggered message verification");
					String expectedTriggerName = "Education 1 - The basics";
					String actualTriggerName = email.getTriggerName();
					Assert.assertEquals(actualTriggerName, expectedTriggerName);
					log.info("Message Triggered successfully");

					log.info("Mail sent Date verification");
					expectedupdateddate = AddDate.currentDate();
					actualupdateddate = email.getMailsentdate();
					Assert.assertEquals(actualupdateddate, expectedupdateddate);
					log.info("Mail sent date verified successfully");

				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}		
			
			log.info("Batch Execution");
			driver.get("http://130.211.74.42:8082/nextory_batch/jobs/job-tip1-nextory-bascis");
		 

    }
	 
	 @Test(enabled=true, priority=62 , groups={"CustomerEducationRuleMailerPositive", "All"})
	 public void basicsFreeCampaign() throws InterruptedException
	 {
		log.info("------------------In Free Campaign script Positive------------------------------------");

		log.info("Fetching data from databse MySQL");
		result = inf.getDataForCustomerInfo("BasicsFreeCampaign");
		log.info("Customerid fetched is=" + result);

		log.info("Fetching data from MongoDB");
		Membership custList = null;
		String customerid = InformationFromBackend.result;

		log.info("Customerid selected from Sql is=" + customerid);

		//--------------------------------------------nx_membership_change_log---------------------------------
		 try
		 {
		  //db.nx_membership_change_log.find({customerid:'$edu'},{_id:1}).sort({_id:-1}).limit(1)
		  Query query = ds.createQuery(Membership.class);
		  //query.criteria("customerid").equal("7672");
		  query.criteria("mem_type_code_old").exists();
		  query.criteria("mem_type_code_old").notEqual(null);
		  query. where("this.mem_type_code_old != this.mem_type_code_new");
		  
	      //query.retrievedFields(true, "_id");
	      query.order("-_id");
	      query.limit(1);
	      
	      custList = (Membership) query.get();
	      if(custList != null)
	      {
					//log.info("Id is=" + custList.getId());
					log.info("Mem type code old=" + custList.getMem_type_code_old());
					log.info("Mem type code new=" + custList.getMem_type_code_new());
					CustomerId = custList.getCustomerid();
					log.info("Customer id selected from mongo=" + CustomerId);
	      }
	       
	     //custList = (Membership)query.asList();   //One value: query.key() (or) query.get()           
	     //log.info( custList.getMem_type_code_previous());
	     Date latestdate = null;            		    
	    }
		 catch(Exception e)
		 {
	    	e.printStackTrace();
	    } 	
		 
		 //log.info("Before Date");
	    DateTime d = new DateTime( custList.getUpdateddate());
	    Date oneday = d.minusDays( 1 ).toDate();
	    
	    log.info("Date="+oneday);
	    
		 //db.nx_membership_change_log.update({"_id":17},{$set : {"updateddate" : -1day }});
		UpdateOperations<Membership> ops = ds.createUpdateOperations(Membership.class).set("updateddate", oneday);
		ds.update(ds.createQuery(Membership.class).field("customerid").equal( CustomerId), ops,true);
			
		//-------------------------------------------nx_customer_email_logs--------------------------------------
			Query query1=ds.createQuery(Email.class);
			query1.filter("customerid",CustomerId);
			
			try
			{
				ArrayList<Email> emailList=(ArrayList<Email>)query1.asList();
				
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
					expectedSubject = "i ett nötskal";
					actualSubject = email.getSubject();
					Assert.assertEquals(actualSubject, expectedSubject);
					log.info("Subject verified successfully");

					log.info("Response verification");
					String expectedResponse = "Success";
					String actualResponse = email.getReason();
					Assert.assertEquals(actualResponse, expectedResponse);
					log.info("Response is success");

					log.info("Triggered message verification");
					String expectedTriggerName = "Education 1 - The basics";
					String actualTriggerName = email.getTriggerName();
					Assert.assertEquals(actualTriggerName, expectedTriggerName);
					log.info("Message Triggered successfully");

					log.info("Mail sent Date verification");
					expectedupdateddate = AddDate.currentDate();
					actualupdateddate = email.getMailsentdate();
					Assert.assertEquals(actualupdateddate, expectedupdateddate);
					log.info("Mail sent date verified successfully");

				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}		
			
			log.info("Batch Execution");
			driver.get("http://130.211.74.42:8082/nextory_batch/jobs/job-tip1-nextory-bascis");
     }
	//-------------------------------NEGATIVE FLOWS-------------------------------------------------------------------
	 
	 @Test(enabled=true, priority=63, groups={"CustomerEducationRuleMailerNegative", "All"})
	  public void basicsFreeTrialNegative() throws InterruptedException, SQLException
	  {
		  log.info("------------------------In Basics FreeTrialMember Script negative----------------------------");
		  
		  log.info("Fetching data from databse MySQL");
		  result=inf.getDataForCustomerInfo("BasicsFreeTrial");   
		  log.info("Id fetched is="+result);
		  
		 log.info("Fetching data from MongoDB");
		 Membership custList = null;
		 String customerid =  InformationFromBackendNegative.result;
		 
		 log.info("Customerid selected from Sql is="+customerid);
			 
		//--------------------------------------------nx_membership_change_log---------------------------------
		 try
		 {
			 //db.nx_membership_change_log.find({customerid:'$edu'},{_id:1}).sort({_id:-1}).limit(1)
		  Query query = ds.createQuery(Membership.class);
		  //query.criteria("customerid").equal("7672");
		  query.criteria("mem_type_code_old").exists();
		  query.criteria("mem_type_code_old").notEqual(null);
		  query. where("this.mem_type_code_old != this.mem_type_code_new");
		  
	      /*query.and(new Criteria []{ ds.createQuery(Membership.class).criteria("customerid").equal(7672),
	    		  ds.createQuery(Membership.class).criteria("mem_type_code_old").exists(),
	    		  ds.createQuery(Membership.class).criteria("mem_type_code_old").notEqual(null),
	    		  ds.createQuery(Membership.class).criteria("mem_type_code_old") .notEqual("mem_type_code_new")});
	      */
	      
	    
	      //query.retrievedFields(true, "_id");
	      query.order("-_id");
	      query.limit(1);
	      
	      custList = (Membership) query.get();
	      if(custList != null)
	      {
					//log.info("Id is=" + custList.getId());
					log.info("Mem type code old=" + custList.getMem_type_code_old());
					log.info("Mem type code new=" + custList.getMem_type_code_new());
					CustomerId = custList.getCustomerid();
					log.info("Customer id selected from mongo=" + CustomerId);
	      }
	       
	     //custList = (Membership)query.asList();   //One value: query.key() (or) query.get()           
	     //log.info( custList.getMem_type_code_previous());
	     Date latestdate = null;            		    
	    }
		 catch(Exception e)
		 {
	    	e.printStackTrace();
	    } 	
		 
		 //log.info("Before Date");
	    DateTime d = new DateTime( custList.getUpdateddate());
	    Date twoday = d.minusDays( 2 ).toDate();
	    
	    log.info("Date="+twoday);
	    
		 //db.nx_membership_change_log.update({"_id":17},{$set : {"updateddate" : -2day }});
		UpdateOperations<Membership> ops = ds.createUpdateOperations(Membership.class).set("updateddate", twoday);
		ds.update(ds.createQuery(Membership.class).field("customerid").equal( CustomerId), ops,true);
			
		//-------------------------------------------nx_customer_email_logs--------------------------------------
			Query query1=ds.createQuery(Email.class);
			query1.filter("customerid",CustomerId);
			
			try
			{
				//custList = (Membership) query.get();
			     // if(custList != null)
				//Email emailList=(Email) query1.get();
				ArrayList<Email> emailList=(ArrayList<Email>)query1.asList();
				
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
					expectedSubject = "i ett nötskal";
					actualSubject = email.getSubject();
					Assert.assertEquals(actualSubject, expectedSubject);
					log.info("Subject verified successfully");

					log.info("Response verification");
					String expectedResponse = "Success";
					String actualResponse = email.getReason();
					Assert.assertEquals(actualResponse, expectedResponse);
					log.info("Response is success");

					log.info("Triggered message verification");
					String expectedTriggerName = "Education 1 - The basics";
					String actualTriggerName = email.getTriggerName();
					Assert.assertEquals(actualTriggerName, expectedTriggerName);
					log.info("Message Triggered successfully");

					log.info("Mail sent Date verification");
					expectedupdateddate = AddDate.currentDate();
					actualupdateddate = email.getMailsentdate();
					Assert.assertEquals(actualupdateddate, expectedupdateddate);
					log.info("Mail sent date verified successfully");

				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}		
			
			log.info("Batch Execution");
			driver.get("http://130.211.74.42:8082/nextory_batch/jobs/job-tip1-nextory-bascis");
			
	    }
	 
	 @Test(enabled=true, priority=64, groups={"CustomerEducationRuleMailerNegative", "All"})
	  public void basicsFreeCampaignNegative() throws InterruptedException, SQLException
	  {
		  log.info("------------------------In Basics FreeCampaignMember Script negative----------------------------");
		  
		  log.info("Fetching data from databse MySQL");
		  result=inf.getDataForCustomerInfo("BasicsFreeCampaign");   
		  log.info("Id fetched is="+result);
		  
		 log.info("Fetching data from MongoDB");
		 Membership custList = null;
		 String customerid =  InformationFromBackendNegative.result;
		 
		 log.info("Customerid selected from Sql is="+customerid);
			 
		//--------------------------------------------nx_membership_change_log---------------------------------
		 try
		 {
			 //db.nx_membership_change_log.find({customerid:'$edu'},{_id:1}).sort({_id:-1}).limit(1)
		  Query query = ds.createQuery(Membership.class);
		  //query.criteria("customerid").equal("7672");
		  query.criteria("mem_type_code_old").exists();
		  query.criteria("mem_type_code_old").notEqual(null);
		  query. where("this.mem_type_code_old != this.mem_type_code_new");
		  
	      /*query.and(new Criteria []{ ds.createQuery(Membership.class).criteria("customerid").equal(7672),
	    		  ds.createQuery(Membership.class).criteria("mem_type_code_old").exists(),
	    		  ds.createQuery(Membership.class).criteria("mem_type_code_old").notEqual(null),
	    		  ds.createQuery(Membership.class).criteria("mem_type_code_old") .notEqual("mem_type_code_new")});
	      */
	      
	    
	      //query.retrievedFields(true, "_id");
	      query.order("-_id");
	      query.limit(1);
	      
	      custList = (Membership) query.get();
	      if(custList != null)
	      {
				//log.info("Id is=" + custList.getId());
				log.info("Mem type code old=" + custList.getMem_type_code_old());
				log.info("Mem type code new=" + custList.getMem_type_code_new());
				CustomerId = custList.getCustomerid();
				log.info("Customer id selected from mongo=" + CustomerId);
			}
	     Date latestdate = null;            		    
	    }
		 catch(Exception e)
		 {
	    	e.printStackTrace();
	    } 	
		 
		 //log.info("Before Date");
	    DateTime d = new DateTime( custList.getUpdateddate());
	    Date twoday = d.minusDays( 2 ).toDate();
	    
	    log.info("Date="+twoday);
	    
		 //db.nx_membership_change_log.update({"_id":17},{$set : {"updateddate" : -2day }});
		UpdateOperations<Membership> ops = ds.createUpdateOperations(Membership.class).set("updateddate", twoday);
		ds.update(ds.createQuery(Membership.class).field("customerid").equal( CustomerId), ops,true);
			
		//-------------------------------------------nx_customer_email_logs--------------------------------------
		Query query1 = ds.createQuery(Email.class);
		query1.filter("customerid", CustomerId);

			try
			{
				ArrayList<Email> emailList=(ArrayList<Email>)query1.asList();
				
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
					expectedSubject = "i ett nötskal";
					actualSubject = email.getSubject();
					Assert.assertEquals(actualSubject, expectedSubject);
					log.info("Subject verified successfully");

					log.info("Response verification");
					String expectedResponse = "Success";
					String actualResponse = email.getReason();
					Assert.assertEquals(actualResponse, expectedResponse);
					log.info("Response is success");

					log.info("Triggered message verification");
					String expectedTriggerName = "Education 1 - The basics";
					String actualTriggerName = email.getTriggerName();
					Assert.assertEquals(actualTriggerName, expectedTriggerName);
					log.info("Message Triggered successfully");

					log.info("Mail sent Date verification");
					expectedupdateddate = AddDate.currentDate();
					actualupdateddate = email.getMailsentdate();
					Assert.assertEquals(actualupdateddate, expectedupdateddate);
					log.info("Mail sent date verified successfully");

				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}		
			
			log.info("Batch Execution");
			driver.get("http://130.211.74.42:8082/nextory_batch/jobs/job-tip1-nextory-bascis");
			
	    }
	 @Test(enabled=true, priority=65, groups={"CustomerEducationRuleMailerNegative", "All"})
	  public void basicsFreeGiftCardNegative() throws InterruptedException, SQLException
	  {
		  log.info("------------------------In Basics FreeGiftCardMember Script negative----------------------------");
		  
		  log.info("Fetching data from databse MySQL");
		  result=inf.getDataForCustomerInfo("BasicsFreeGiftCard");   
		  log.info("Id fetched is="+result);
		  
		 log.info("Fetching data from MongoDB");
		 Membership custList = null;
		 String customerid =  InformationFromBackendNegative.result;
		 
		 log.info("Customerid selected from Sql is="+customerid);
			 
		//--------------------------------------------nx_membership_change_log---------------------------------
		 try
		 {
			 //db.nx_membership_change_log.find({customerid:'$edu'},{_id:1}).sort({_id:-1}).limit(1)
		  Query query = ds.createQuery(Membership.class);
		  //query.criteria("customerid").equal("7672");
		  query.criteria("mem_type_code_old").exists();
		  query.criteria("mem_type_code_old").notEqual(null);
		  query. where("this.mem_type_code_old != this.mem_type_code_new");
	    
	      //query.retrievedFields(true, "_id");
	      query.order("-_id");
	      query.limit(1);
	      
	      custList = (Membership) query.get();
	      if(custList != null)
	      {
					//log.info("Id is=" + custList.getId());
					log.info("Mem type code old=" + custList.getMem_type_code_old());
					log.info("Mem type code new=" + custList.getMem_type_code_new());
					CustomerId = custList.getCustomerid();
					log.info("Customer id selected from mongo=" + CustomerId);
	      }
	     Date latestdate = null;            		    
	    }
		 catch(Exception e)
		 {
	    	e.printStackTrace();
	    } 	
		 
		 //log.info("Before Date");
	    DateTime d = new DateTime( custList.getUpdateddate());
	    Date twoday = d.minusDays( 2 ).toDate();
	    
	    log.info("Date="+twoday);
	    
		 //db.nx_membership_change_log.update({"_id":17},{$set : {"updateddate" : -2day }});
		UpdateOperations<Membership> ops = ds.createUpdateOperations(Membership.class).set("updateddate", twoday);
		ds.update(ds.createQuery(Membership.class).field("customerid").equal( CustomerId), ops,true);
			
		//-------------------------------------------nx_customer_email_logs--------------------------------------
			Query query1=ds.createQuery(Email.class);
			query1.filter("customerid",CustomerId);
			
			try
			{
				//custList = (Membership) query.get();
			     // if(custList != null)
				//Email emailList=(Email) query1.get();
				ArrayList<Email> emailList=(ArrayList<Email>)query1.asList();
				
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
					expectedSubject = "i ett nötskal";
					actualSubject = email.getSubject();
					Assert.assertEquals(actualSubject, expectedSubject);
					log.info("Subject verified successfully");

					log.info("Response verification");
					String expectedResponse = "Success";
					String actualResponse = email.getReason();
					Assert.assertEquals(actualResponse, expectedResponse);
					log.info("Response is success");

					log.info("Triggered message verification");
					String expectedTriggerName = "Education 1 - The basics";
					String actualTriggerName = email.getTriggerName();
					Assert.assertEquals(actualTriggerName, expectedTriggerName);
					log.info("Message Triggered successfully");

					log.info("Mail sent Date verification");
					expectedupdateddate = AddDate.currentDate();
					actualupdateddate = email.getMailsentdate();
					Assert.assertEquals(actualupdateddate, expectedupdateddate);
					log.info("Mail sent date verified successfully");

				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}		
			
			log.info("Batch Execution");
			driver.get("http://130.211.74.42:8082/nextory_batch/jobs/job-tip1-nextory-bascis");
			
	    }
	  
}

