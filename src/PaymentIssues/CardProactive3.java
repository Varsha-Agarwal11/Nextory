package PaymentIssues;

import java.util.ArrayList;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.testng.Assert;
import org.testng.annotations.Test;

import Mongo.Email_logs.Email;
import Mongo.Membership_change_log.Membership;
import common.SuperTestScript;
import generics.MongoDBUtilMorphia;
import interfaceForApiOrDB.InformationFromBackend;
import interfaceForApiOrDB.InformationFromBackendNegative;

public class CardProactive3 extends SuperTestScript
{
	MongoDBUtilMorphia mongoutil = new MongoDBUtilMorphia();
	Datastore ds = mongoutil.getMorphiaDatastoreForNlob();
	Membership member = new Membership();
	Email email = new Email();
	InformationFromBackend info = new InformationFromBackend();

	public CardProactive3()
	{
		 loginRequired=false;
		  logoutRequired=false;
	}
	 @Test(enabled=true,priority=290,groups={"PaymentIssuesRuleMailerPositive","All"})
	 public void cardProactive3TC120301()
	 {
      log.info("In card proactive 3 script");
      
      log.info("Fetching data from databse MySQL");
 	  InformationFromBackend info=new InformationFromBackend();
 	  info.getDataForCustomerInfo("CardProactive3");  
 	 
	  log.info("Batch execution");
	  driver.get("http://130.211.74.42:8082/nextory_batch/jobs/add-carddetails-before-7daysbefore");   
	  
	  log.info("Fetching data from MongoDB");
		 Membership custList = null;
		 String customerid = InformationFromBackend.result;
	   	 log.info("Customerid fetched="+customerid);

		//-------------------------------------------nx_customer_email_logs--------------------------------------
		 
		 //db.nx_customer_email_logs.find({"customerid" :$a}).sort({"_id":-1}).limit(1).pretty();
		 
				Query query1=ds.createQuery(Email.class);
				query1.filter("customerid",customerid);
				query1.order("-_id");
				query1.limit(1);
				
				try{
					ArrayList<Email> emailList=(ArrayList<Email>)query1.asList();
					
					   
					for(Email email:emailList)
					{
						//email.getClass();
						log.info( "CustomerID=" + customerid);
						log.info(email.getFirstname());
						email.getLastname();
						email.getFrom();
						email.getMobilenumber();
						log.info(email.getReason());
						log.info(email.getSubject());
						
						String expectedResponse="Success";
						String actualResponse=email.getReason();
						Assert.assertEquals(actualResponse,expectedResponse);
						
					   String expectedTriggerName="Card: Proactive 3" ;	
				       String actualTriggerName=email.getTriggerName();
				       
				       String expectedSubject="Viktig information om ditt abonnemang";
				       String actualSubject=email.getSubject();
				       
				    log.info("Triggered message verification");
				    Assert.assertEquals(actualTriggerName, expectedTriggerName);
					log.info("Message Triggered successfully"); 
					
//					log.info("Mail sent Date verification");
//				     expectedupdateddate=member.getUpdateddate();
//				   	 actualupdateddate=email.getMailsentdate();
//				   	 Assert.assertEquals(actualupdateddate, expectedupdateddate);
				   	 
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
     }
	 @Test(enabled=true,priority=291,groups={"PaymentIssuesRuleMailerNegative","All"})
	 public void cardProactive3()
	 {
      log.info("In card proactive 3 script");
      
      log.info("Fetching data from databse MySQL");
 	  InformationFromBackendNegative info=new InformationFromBackendNegative();
 	  info.getDataForCustomerInfo("CardProactive3");  
 	 
	  log.info("Batch execution");
	  driver.get("http://130.211.74.42:8082/nextory_batch/jobs/add-carddetails-before-7daysbefore");   
	  
	  log.info("Fetching data from MongoDB");
		 Membership custList = null;
		 String customerid = InformationFromBackendNegative.result;
	   	 log.info("Customerid fetched="+customerid);

		//-------------------------------------------nx_customer_email_logs--------------------------------------
		 
		 //db.nx_customer_email_logs.find({"customerid" :$a}).sort({"_id":-1}).limit(1).pretty();
		 
				Query query1=ds.createQuery(Email.class);
				query1.filter("customerid",customerid);
				query1.order("-_id");
				query1.limit(1);
				
				try{
					ArrayList<Email> emailList=(ArrayList<Email>)query1.asList();
					
					   
					for(Email email:emailList)
					{
						//email.getClass();
						log.info( "CustomerID=" + customerid);
						log.info(email.getFirstname());
						email.getLastname();
						email.getFrom();
						email.getMobilenumber();
						log.info(email.getReason());
						log.info(email.getSubject());
						
						String expectedResponse="Success";
						String actualResponse=email.getReason();
						Assert.assertEquals(actualResponse,expectedResponse);
						
					   String expectedTriggerName="Card: Proactive 3" ;	
				       String actualTriggerName=email.getTriggerName();
				       
				       String expectedSubject="Viktig information om ditt abonnemang";
				       String actualSubject=email.getSubject();
				       
				    log.info("Triggered message verification");
				    Assert.assertEquals(actualTriggerName, expectedTriggerName);
					log.info("Message Triggered successfully"); 
					
//					log.info("Mail sent Date verification");
//				     expectedupdateddate=member.getUpdateddate();
//				   	 actualupdateddate=email.getMailsentdate();
//				   	 Assert.assertEquals(actualupdateddate, expectedupdateddate);
				   	 
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
     }
}
