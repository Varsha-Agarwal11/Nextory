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
import common.Batch;
import common.SuperTestScript;
import generics.Api;
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

public class FindGoodBook extends SuperTestScript
{	
	public static String newEmail;
	 public static String confirm;
	 public static String newPwd;
	 public static String cardNumber;
	 public static String cvc;
	 public static String fn;
	 public static String ln;
	 public static String cellNum;
	 
	// private SoftAssert softAssert=new SoftAssert();
	 MongoDBUtilMorphia mongoutil = new MongoDBUtilMorphia();
	 Datastore ds = mongoutil.getMorphiaDatastoreForNlob();
     Membership member=new Membership();
     Email email=new Email();
     InformationFromBackend info=new InformationFromBackend();
     
	 String expectedSubject="";
	 String actualSubject="";
	 String result="";
	 
	 Date expectedupdateddate;
	 Date actualupdateddate;
	 
	 
	public FindGoodBook()
	{
		loginRequired=false;
		logoutRequired=false;
	}
	
	@Test(enabled=true, priority=70, groups={"CustomerEducationRuleMailerPositive", "All"})
	public void findGoodBookFreeTrial() throws InterruptedException
	  {
		 log.info("----------------Inside Find Good Book Free Trial script Positive-------------------");
		 
		 log.info("Fetching data from databse MySQL");
		  result=info.getDataForCustomerInfo("FindGoodBookFreeTrial"); 
		  log.info("Customer id fetched : " + result);
		 
		 log.info("Fetching the data from database Mongo");
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
		    		  ds.createQuery(Membership.class).criteria("mem_type_code_old") .notEqual("mem_type_code_new")});
			  */ 
			  query.order("-_id");
		      query.limit(1);
		     custList = (Membership)query.get();
		     
		     if(custList != null)
		      {
		    	 //log.info("Id is="+custList.getId());
		    	  log.info("Mem type code old="+custList.getMem_type_code_old());
		    	  log.info("Mem type code new="+custList.getMem_type_code_new());
		    	  //log.info("Customer id ="+custList.getCustomerid());
		      }
		
		     Date latestdate = null;            		    
		     } 
		     catch(Exception e)
		     {
		    	e.printStackTrace();
		     } 	
		 
		    DateTime d = new DateTime( custList.getUpdateddate());
		    Date threeday = d.minusDays( 3 ).toDate();
		    
			 //db.nx_membership_change_log.update({"_id":17},{$set : {"updateddate" : -3day }})
			UpdateOperations<Membership> ops = ds.createUpdateOperations(Membership.class).set("updateddate", threeday);
			ds.update(ds.createQuery(Membership.class).field("customerid").equal( customerid ), ops,true);
			//log.info("Updated date....."+threeday);
				
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
						email.getClass();
						log.info( "CustomerID....." + customerid );
						email.getFirstname();
						email.getLastname();
						email.getFrom();
						email.getMobilenumber();
						email.getReason();
						email.getTriggerName();
				
				        expectedSubject="Hitta guldkornen" ;	
                        actualSubject=email.getSubject();
						
						log.info("Email......"+email.getTo());
//						String expectedEmail = "michaela.lilja@hotmail.com";
//						String actualEmail = email.getTo() ;
						expectedupdateddate=member.getUpdateddate();
				   	    actualupdateddate=email.getMailsentdate();
						email.getMailsentdate();
						
						  String expectedResponse="Success";
						  String actualResponse=email.getReason();
							Assert.assertEquals(actualResponse,expectedResponse);
							
						   String expectedTriggerName="Education 2 - Find a good book" ;	
					       String actualTriggerName=email.getTriggerName();
					     
					    log.info("Triggered message verification");
					    Assert.assertEquals(actualTriggerName, expectedTriggerName);
						log.info("Message Triggered successfully"); 
						
						log.info("Mail sent Date verification");
					     expectedupdateddate=member.getUpdateddate();
					   	 actualupdateddate=email.getMailsentdate();
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
				  driver.get("http://130.211.74.42:8082/nextory_batch/jobs/job-tip2-how-tofind-good-book");
	 }
	
	@Test(enabled=true, priority=71, groups={"CustomerEducationRuleMailerPositive", "All"})
	public void findGoodBookFreeCampaign() throws InterruptedException
	  {
		 log.info("----------------Inside Find Good Book Free Trial script Positive-------------------");
		 
		 log.info("Fetching data from databse MySQL");
		  result=info.getDataForCustomerInfo("FindGoodBookFreeCampaign"); 
		  log.info("Customer id fetched : " + result);
		 
		 log.info("Fetching the data from database Mongo");
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
		    		  ds.createQuery(Membership.class).criteria("mem_type_code_old") .notEqual("mem_type_code_new")});
			  */ 
			  query.order("-_id");
		      query.limit(1);
		     custList = (Membership)query.get();
		     
		     if(custList != null)
		      {
		    	 //log.info("Id is="+custList.getId());
		    	  log.info("Mem type code old="+custList.getMem_type_code_old());
		    	  log.info("Mem type code new="+custList.getMem_type_code_new());
		    	  //log.info("Customer id ="+custList.getCustomerid());
		      }
		
		     Date latestdate = null;            		    
		     } 
		     catch(Exception e)
		     {
		    	e.printStackTrace();
		     } 	
		 
		    DateTime d = new DateTime( custList.getUpdateddate());
		    Date threeday = d.minusDays( 3 ).toDate();
		    
			 //db.nx_membership_change_log.update({"_id":17},{$set : {"updateddate" : -3day }})
			UpdateOperations<Membership> ops = ds.createUpdateOperations(Membership.class).set("updateddate", threeday);
			ds.update(ds.createQuery(Membership.class).field("customerid").equal( customerid ), ops,true);
			//log.info("Updated date....."+threeday);
				
			//-------------------------------------------nx_customer_email_logs--------------------------------------
				Query query1=ds.createQuery(Email.class);
				query1.filter("customerid",customerid);

				try
				{
					ArrayList<Email> emailList=(ArrayList<Email>)query1.asList();
					
					 for(Email email:emailList)
					 {
						email.getClass();
						log.info( "CustomerID....." + customerid );
						email.getFirstname();
						email.getLastname();
						email.getFrom();
						email.getMobilenumber();
						email.getReason();
						email.getTriggerName();
				
				        expectedSubject="Hitta guldkornen" ;	
                        actualSubject=email.getSubject();
						
						log.info("Email......"+email.getTo());
//						String expectedEmail = "michaela.lilja@hotmail.com";
//						String actualEmail = email.getTo() ;
						expectedupdateddate=member.getUpdateddate();
				   	    actualupdateddate=email.getMailsentdate();
						email.getMailsentdate();
						
						  String expectedResponse="Success";
						  String actualResponse=email.getReason();
							Assert.assertEquals(actualResponse,expectedResponse);
							
						   String expectedTriggerName="Education 2 - Find a good book" ;	
					       String actualTriggerName=email.getTriggerName();
					     
					    log.info("Triggered message verification");
					    Assert.assertEquals(actualTriggerName, expectedTriggerName);
						log.info("Message Triggered successfully"); 
						
						log.info("Mail sent Date verification");
					     expectedupdateddate=member.getUpdateddate();
					   	 actualupdateddate=email.getMailsentdate();
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
				  driver.get("http://130.211.74.42:8082/nextory_batch/jobs/job-tip2-how-tofind-good-book");
	}
	
	@Test(enabled=true, priority=72, groups={"CustomerEducationRuleMailerPositive", "All"})
	public void findGoodBookFreeGiftCard() throws InterruptedException
	  {
		 log.info("----------------Inside Find Good Book Free Trial script Positive-------------------");
		 
		 log.info("Fetching data from databse MySQL");
		  result=info.getDataForCustomerInfo("FindGoodBookFreeGiftCard"); 
		  log.info("Customer id fetched : " + result);
		 
		 log.info("Fetching the data from database Mongo");
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
		    		  ds.createQuery(Membership.class).criteria("mem_type_code_old") .notEqual("mem_type_code_new")});
			  */ 
			  query.order("-_id");
		      query.limit(1);
		     custList = (Membership)query.get();
		     
		     if(custList != null)
		      {
		    	 //log.info("Id is="+custList.getId());
		    	  log.info("Mem type code old="+custList.getMem_type_code_old());
		    	  log.info("Mem type code new="+custList.getMem_type_code_new());
		    	  //log.info("Customer id ="+custList.getCustomerid());
		      }
		
		     Date latestdate = null;            		    
		     } 
		     catch(Exception e)
		     {
		    	e.printStackTrace();
		     } 	
		 
		    DateTime d = new DateTime( custList.getUpdateddate());
		    Date threeday = d.minusDays( 3 ).toDate();
		    
			 //db.nx_membership_change_log.update({"_id":17},{$set : {"updateddate" : -3day }})
			UpdateOperations<Membership> ops = ds.createUpdateOperations(Membership.class).set("updateddate", threeday);
			ds.update(ds.createQuery(Membership.class).field("customerid").equal( customerid ), ops,true);
			//log.info("Updated date....."+threeday);
				
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
						email.getClass();
						log.info( "CustomerID....." + customerid );
						email.getFirstname();
						email.getLastname();
						email.getFrom();
						email.getMobilenumber();
						email.getReason();
						email.getTriggerName();
				
				        expectedSubject="Hitta guldkornen" ;	
                        actualSubject=email.getSubject();
						
						log.info("Email......"+email.getTo());
//						String expectedEmail = "michaela.lilja@hotmail.com";
//						String actualEmail = email.getTo() ;
						expectedupdateddate=member.getUpdateddate();
				   	    actualupdateddate=email.getMailsentdate();
						email.getMailsentdate();
						
						  String expectedResponse="Success";
						  String actualResponse=email.getReason();
							Assert.assertEquals(actualResponse,expectedResponse);
							
						   String expectedTriggerName="Education 2 - Find a good book" ;	
					       String actualTriggerName=email.getTriggerName();
					     
					    log.info("Triggered message verification");
					    Assert.assertEquals(actualTriggerName, expectedTriggerName);
						log.info("Message Triggered successfully"); 
						
						log.info("Mail sent Date verification");
					     expectedupdateddate=member.getUpdateddate();
					   	 actualupdateddate=email.getMailsentdate();
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
				  driver.get("http://130.211.74.42:8082/nextory_batch/jobs/job-tip2-how-tofind-good-book");
	}
//----------------------------------------NEGATIVE FLOWS-------------------------------------------------------------	
	
	@Test(enabled=true, priority=73, groups={"CustomerEducationRuleMailerNegative", "All"})
	public void findGoodBookFreeTrialNegative() throws InterruptedException
	  {
		 log.info("---------------------Inside Find Good Book Free Trial script Negative-------------------------");
		 
		 log.info("Fetching data from databse MySQL");
		  result=info.getDataForCustomerInfo("FindGoodBookFreeTrial"); 
		  log.info("mailsent_once : " + result);
		 
		 log.info("Fetching the data from database Mongo");
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
		    		  ds.createQuery(Membership.class).criteria("mem_type_code_old") .notEqual("mem_type_code_new")});
			  */ 
			  query.order("-_id");
		      query.limit(1);
		     custList = (Membership)query.get();
		     
		     if(custList != null)
		      {
		    	 log.info("Id is="+custList.getId());
		    	  log.info("Mem type code old="+custList.getMem_type_code_old());
		    	  log.info("Mem type code new="+custList.getMem_type_code_new());
		    	  log.info("Customer id ="+custList.getCustomerid());
		      }
		
		     Date latestdate = null;            		    
		     } 
		     catch(Exception e)
		     {
		    	e.printStackTrace();
		     } 	
		 
		    DateTime d = new DateTime( custList.getUpdateddate());
		    Date fourthday = d.minusDays( 4 ).toDate();
		    
			 //db.nx_membership_change_log.update({"_id":17},{$set : {"updateddate" : -4day }})
			UpdateOperations<Membership> ops = ds.createUpdateOperations(Membership.class).set("updateddate", fourthday);
			ds.update(ds.createQuery(Membership.class).field("customerid").equal( customerid ), ops,true);
			//log.info("Updated date....."+fourthday);
				
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
						email.getClass();
						//log.info( "CustomerID....." + customerid );
						email.getFirstname();
						email.getLastname();
						email.getFrom();
						email.getMobilenumber();
						email.getReason();
						email.getTriggerName();
				
				        expectedSubject="Hitta guldkornen" ;	
                        actualSubject=email.getSubject();
						
						log.info("Email......"+email.getTo());
//						String expectedEmail = "michaela.lilja@hotmail.com";
//						String actualEmail = email.getTo() ;
						expectedupdateddate=member.getUpdateddate();
				   	    actualupdateddate=email.getMailsentdate();
						email.getMailsentdate();
						
				       String expectedResponse = "Success";
				       String actualResponse = email.getReason();
				       Assert.assertEquals(actualResponse, expectedResponse);

				       String expectedTriggerName = "Education 2 - Find a good book";
				       String actualTriggerName = email.getTriggerName();

					    log.info("Triggered message verification");
					    Assert.assertEquals(actualTriggerName, expectedTriggerName);
						log.info("Message Triggered successfully"); 
						
						log.info("Mail sent Date verification");
					    expectedupdateddate=member.getUpdateddate();
					   	actualupdateddate=email.getMailsentdate();
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
				  driver.get("http://130.211.74.42:8082/nextory_batch/jobs/job-tip2-how-tofind-good-book");
	}
	@Test(enabled=true, priority=74, groups={"CustomerEducationRuleMailerNegative", "All"})
	public void findGoodBookFreeCampaignNegative() throws InterruptedException
	  {
		 log.info("---------------------Inside Find Good Book Free Campaign script Negative-------------------------");
		 
		 log.info("Fetching data from databse MySQL");
		  result=info.getDataForCustomerInfo("FindGoodBookFreeCampaign"); 
		  log.info("mailsent_once : " + result);
		 
		 log.info("Fetching the data from database Mongo");
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
		    	 log.info("Id is="+custList.getId());
		    	  log.info("Mem type code old="+custList.getMem_type_code_old());
		    	  log.info("Mem type code new="+custList.getMem_type_code_new());
		    	  log.info("Customer id ="+custList.getCustomerid());
		      }
		
		     Date latestdate = null;            		    
		     } 
		     catch(Exception e)
		     {
		    	e.printStackTrace();
		     } 	
		 
		    DateTime d = new DateTime( custList.getUpdateddate());
		    Date fourthday = d.minusDays( 4 ).toDate();
		    
			 //db.nx_membership_change_log.update({"_id":17},{$set : {"updateddate" : -4day }})
			UpdateOperations<Membership> ops = ds.createUpdateOperations(Membership.class).set("updateddate", fourthday);
			ds.update(ds.createQuery(Membership.class).field("customerid").equal( customerid ), ops,true);
			//log.info("Updated date....."+fourthday);
				
			//-------------------------------------------nx_customer_email_logs--------------------------------------
				Query query1=ds.createQuery(Email.class);
				query1.filter("customerid",customerid);
				
				try
				{
					ArrayList<Email> emailList=(ArrayList<Email>)query1.asList();
					
					 for(Email email:emailList)
					 {
						email.getClass();
						//log.info( "CustomerID....." + customerid );
						email.getFirstname();
						email.getLastname();
						email.getFrom();
						email.getMobilenumber();
						email.getReason();
						email.getTriggerName();
				
				        expectedSubject="Hitta guldkornen" ;	
                        actualSubject=email.getSubject();
						
						log.info("Email......"+email.getTo());
//						String expectedEmail = "michaela.lilja@hotmail.com";
//						String actualEmail = email.getTo() ;
						expectedupdateddate=member.getUpdateddate();
				   	    actualupdateddate=email.getMailsentdate();
						email.getMailsentdate();
						
				       String expectedResponse = "Success";
				       String actualResponse = email.getReason();
				       Assert.assertEquals(actualResponse, expectedResponse);

				       String expectedTriggerName = "Education 2 - Find a good book";
				       String actualTriggerName = email.getTriggerName();

					    log.info("Triggered message verification");
					    Assert.assertEquals(actualTriggerName, expectedTriggerName);
						log.info("Message Triggered successfully"); 
						
						log.info("Mail sent Date verification");
					    expectedupdateddate=member.getUpdateddate();
					   	actualupdateddate=email.getMailsentdate();
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
				  driver.get("http://130.211.74.42:8082/nextory_batch/jobs/job-tip2-how-tofind-good-book");
	}
	
	@Test(enabled=true, priority=75, groups={"CustomerEducationRuleMailerNegative", "All"})
	public void findGoodBookFreeGiftCardNegative() throws InterruptedException
	  {
		 log.info("---------------------Inside Find Good Book Free Gift Card script Negative-------------------------");
		 
		 log.info("Fetching data from databse MySQL");
		  result=info.getDataForCustomerInfo("FindGoodBookFreeGiftCard"); 
		  log.info("mailsent_once : " + result);
		 
		 log.info("Fetching the data from database Mongo");
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
		    	 log.info("Id is="+custList.getId());
		    	  log.info("Mem type code old="+custList.getMem_type_code_old());
		    	  log.info("Mem type code new="+custList.getMem_type_code_new());
		    	  log.info("Customer id ="+custList.getCustomerid());
		      }
		
		     Date latestdate = null;            		    
		     } 
		     catch(Exception e)
		     {
		    	e.printStackTrace();
		     } 	
		 
		    DateTime d = new DateTime( custList.getUpdateddate());
		    Date fourthday = d.minusDays( 4 ).toDate();
		    
			 //db.nx_membership_change_log.update({"_id":17},{$set : {"updateddate" : -4day }})
			UpdateOperations<Membership> ops = ds.createUpdateOperations(Membership.class).set("updateddate", fourthday);
			ds.update(ds.createQuery(Membership.class).field("customerid").equal( customerid ), ops,true);
			//log.info("Updated date....."+fourthday);
				
			//-------------------------------------------nx_customer_email_logs--------------------------------------
				Query query1=ds.createQuery(Email.class);
				query1.filter("customerid",customerid);
			
				try
				{
					ArrayList<Email> emailList=(ArrayList<Email>)query1.asList();
					
					 for(Email email:emailList)
					 {
						email.getClass();
						//log.info( "CustomerID....." + customerid );
						email.getFirstname();
						email.getLastname();
						email.getFrom();
						email.getMobilenumber();
						email.getReason();
						email.getTriggerName();
				
				        expectedSubject="Hitta guldkornen" ;	
                        actualSubject=email.getSubject();
						
						log.info("Email......"+email.getTo());
//						String expectedEmail = "michaela.lilja@hotmail.com";
//						String actualEmail = email.getTo() ;
						expectedupdateddate=member.getUpdateddate();
				   	    actualupdateddate=email.getMailsentdate();
						email.getMailsentdate();
						
				       String expectedResponse = "Success";
				       String actualResponse = email.getReason();
				       Assert.assertEquals(actualResponse, expectedResponse);

				       String expectedTriggerName = "Education 2 - Find a good book";
				       String actualTriggerName = email.getTriggerName();

					    log.info("Triggered message verification");
					    Assert.assertEquals(actualTriggerName, expectedTriggerName);
						log.info("Message Triggered successfully"); 
						
						log.info("Mail sent Date verification");
					    expectedupdateddate=member.getUpdateddate();
					   	actualupdateddate=email.getMailsentdate();
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
				  driver.get("http://130.211.74.42:8082/nextory_batch/jobs/job-tip2-how-tofind-good-book");
	}
	
}
