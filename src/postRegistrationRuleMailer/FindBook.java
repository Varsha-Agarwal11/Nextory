package postRegistrationRuleMailer;

import java.util.ArrayList;
import java.util.Date;

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

public class FindBook extends SuperTestScript
{
 public static String newEmail;
 public static String confirm;
 public static String newPwd;
 public static String cardNumber;
 public static String cvc;
 public static String fn;
 public static String ln;
 public static String cellNum;
 public static String dbUrl;
 public static String username;
 public static String password;
 public static String query;
 
 //private SoftAssert softAssert=new SoftAssert();
 MongoDBUtilMorphia mongoutil = new MongoDBUtilMorphia();
 Datastore ds = mongoutil.getMorphiaDatastoreForNlob();
 Membership member=new Membership();
 Email email=new Email();
 
 String result="";
 
 Date expectedupdateddate;
 Date actualupdateddate;
 public FindBook()
 {
  loginRequired=false;
  logoutRequired=false;
 }
 
 @Test(enabled=true , priority=30, groups={"PostRegistrationRuleMailerPositive" , "All"})
 public void FindBookTC080401() throws InterruptedException
 { 
     log.info("-------------------------Running Find a Book Positive-------------------------------");
     log.info("Fetching data from databse MySQL");
	 InformationFromBackend info=new InformationFromBackend();
	 info.getDataForCustomerInfo("FindBook");    
	
	 log.info("batch execution");
	 driver.get("http://130.211.74.42:8082/nextory_batch/jobs/user-to-find-and-savebook");
	
	 log.info("Fetching data from MongoDB");
	 Membership custList = null;
	 String customerid = InformationFromBackend.result;
	//-------------------------------------------nx_customer_email_logs--------------------------------------
	 
	 //db.nx_customer_email_logs.find({"customerid" :$a}).sort({"_id":-1}).limit(1).pretty();
	 
			Query query1=ds.createQuery(Email.class);
			query1.filter("customerid",customerid);
			query1.order("-_id");
			query1.limit(1);
			
			try{
				ArrayList<Email> emailList=(ArrayList<Email>)query1.asList();
				log.info("EmailList="+emailList);
				   
				for(Email email:emailList)
				{
				// email.getClass();
				log.info("CustomerID=" + customerid);
				log.info(email.getFirstname());
				email.getLastname();
				email.getFrom();
				email.getMobilenumber();
				log.info(email.getReason());
				log.info(email.getSubject());

				String expectedResponse = "Success";
				String actualResponse = email.getReason();
				Assert.assertEquals(actualResponse, expectedResponse);

				String expectedTriggerName = "Post-reg 4: Find a book";
				String actualTriggerName = email.getTriggerName();

				String expectedSubject = "Hitta en magisk bok";
				String actualSubject = email.getSubject();

				log.info("Triggered message verification");
				Assert.assertEquals(actualTriggerName, expectedTriggerName);
				log.info("Message Triggered successfully");

				// log.info("Mail sent Date verification");
				// expectedupdateddate=member.getUpdateddate();
				// actualupdateddate=email.getMailsentdate();
				// Assert.assertEquals(actualupdateddate, expectedupdateddate);

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
 @Test(enabled=true ,priority=31, groups={"PostRegistrationRuleMailerNegative" , "All"})
 public void FindBook() throws InterruptedException
 { 
     log.info("--------------------------------Running Find a Book Negative--------------------------");
     log.info("Fetching data from databse MySQL");
	 InformationFromBackendNegative info=new InformationFromBackendNegative();
	 info.getDataForCustomerInfo("FindBook");    
	 
	 log.info("batch execution");
	 driver.get("http://130.211.74.42:8082/nextory_batch/jobs/user-to-find-and-savebook");
	
	 log.info("Fetching data from MongoDB");
	 Membership custList = null;
	 String customerid = InformationFromBackendNegative.result;
	//-------------------------------------------nx_customer_email_logs--------------------------------------
	 
	 //db.nx_customer_email_logs.find({"customerid" :$a}).sort({"_id":-1}).limit(1).pretty();
	 
			Query query1=ds.createQuery(Email.class);
			query1.filter("customerid",customerid);
			query1.order("-_id");
			query1.limit(1);
			
			try{
				ArrayList<Email> emailList=(ArrayList<Email>)query1.asList();
				log.info("EmailList="+emailList);
				   
				for(Email email:emailList)
				{
				// email.getClass();
				log.info("CustomerID=" + customerid);
				log.info(email.getFirstname());
				email.getLastname();
				email.getFrom();
				email.getMobilenumber();
				log.info(email.getReason());
				log.info(email.getSubject());

				String expectedResponse = "Success";
				String actualResponse = email.getReason();
				Assert.assertEquals(actualResponse, expectedResponse);

				String expectedTriggerName = "Post-reg 4: Find a book";
				String actualTriggerName = email.getTriggerName();

				String expectedSubject = "Hitta en magisk bok";
				String actualSubject = email.getSubject();

				log.info("Triggered message verification");
				Assert.assertEquals(actualTriggerName, expectedTriggerName);
				log.info("Message Triggered successfully");

				// log.info("Mail sent Date verification");
				// expectedupdateddate=member.getUpdateddate();
				// actualupdateddate=email.getMailsentdate();
				// Assert.assertEquals(actualupdateddate, expectedupdateddate);

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
