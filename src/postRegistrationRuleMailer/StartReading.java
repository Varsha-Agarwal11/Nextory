package postRegistrationRuleMailer;

import java.util.ArrayList;
import java.util.Date;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import Mongo.Email_logs.Email;
import Mongo.Membership_change_log.Membership;
import common.SuperTestScript;
import generics.AddDate;
import generics.MongoDBUtilMorphia;
import interfaceForApiOrDB.InformationFromBackend;
import interfaceForApiOrDB.InformationFromBackendNegative;
import generics.AddDate;

public class StartReading  extends SuperTestScript
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
 
// private SoftAssert softAssert=new SoftAssert();
 MongoDBUtilMorphia mongoutil = new MongoDBUtilMorphia();
 Datastore ds = mongoutil.getMorphiaDatastoreForNlob();
 Membership member=new Membership();
 Email email=new Email();
 
 String result="";
 
 String expectedupdateddate;
 Date actualupdateddate;
 public StartReading()
 {
  loginRequired=false;
  logoutRequired=false;
 }
 
 @Test(enabled=true , priority=50, groups={"PostRegistrationRuleMailerPositive" , "All"})
 public void StartReadingTC080501() throws InterruptedException
 { 
    log.info("-----------------------------Start Reading script Positive-----------------------------------------");

    log.info("Fetching data from databse MySQL");
	InformationFromBackend info=new InformationFromBackend();
	info.getDataForCustomerInfo("StartReading");    
	
	log.info("batch execution");
	driver.get("http://130.211.74.42:8082/nextory_batch/jobs/get-user-tostartreading");
   
	log.info("Fetching data from MongoDB");
	Membership custList = null;
	String customerid = InformationFromBackend.result;
	
	//-------------------------------------------nx_customer_email_logs--------------------------------------
//	        Query memQuery=ds.createQuery(Membership.class);
//	        memQuery.filter("customerid", customerid);
//	        memQuery.order("-_id");
//	        memQuery.limit(1);
	       
		   Query query1=ds.createQuery(Email.class);
			query1.filter("customerid",customerid);
			query1.order("-_id");
			query1.limit(1);
			
			log.info("Inside Email.class");
			try
			{
			// log.info("Inside try");
			ArrayList<Email> emailList = (ArrayList<Email>) query1.asList();
			log.info(emailList);
			
			for (Email email : emailList) 
			{
				log.info("In for loop");

				email.getClass();
				log.info("CustomerID=" + customerid);
				email.getTriggerName();
				// log.info("Subject id="+email.getSubject());
				log.info("TriggerName=" + email.getTriggerName());
				log.info(email.getFirstname());
				email.getLastname();
				email.getFrom();
				email.getMobilenumber();
				log.info("Reason=" + email.getReason());
				log.info("Subject=" + email.getSubject());

				log.info("Mail-Subject verification");
				String actualSubject = email.getSubject();
				String expectedSubject = "Kan jag hjälpa dig att börja läsa?";
				Assert.assertEquals(actualSubject, expectedSubject);
				log.info("Subject verified successfully");

				log.info("Response verification");
				String expectedResponse = "Success";
				String actualResponse = email.getReason();
				Assert.assertEquals(actualResponse, expectedResponse);
				log.info("Response is success");

				log.info("Triggered message verification");
				String expectedTriggerName = "Post-reg 5: Start reading";
				String actualTriggerName = email.getTriggerName();
				Assert.assertEquals(actualTriggerName, expectedTriggerName);
				log.info("Message Triggered successfully");

				log.info("Mail sent Date verification");
				expectedupdateddate = AddDate.currentDate();
				actualupdateddate = email.getMailsentdate();
				Assert.assertEquals(actualupdateddate, expectedupdateddate);
				log.info("mail sent date verified successfully");
			}

			}
			catch(Exception e)
			{
				e.printStackTrace();
			}		
 }
 @Test(enabled=true ,priority=51, groups={"PostRegistrationRuleMailerNegative" , "All"})
 public void StartReading() throws InterruptedException
 { 
    log.info("-------------------------------Start Reading script Negative------------------------------------------");

    log.info("Fetching data from databse MySQL");
	InformationFromBackendNegative info=new InformationFromBackendNegative();
	info.getDataForCustomerInfo("StartReading");    
	
	log.info("batch execution");
	driver.get("http://130.211.74.42:8082/nextory_batch/jobs/get-user-tostartreading");
   
	log.info("Fetching data from MongoDB");
	Membership custList = null;
	String customerid = InformationFromBackendNegative.result;
	
	//-------------------------------------------nx_customer_email_logs--------------------------------------
//	        Query memQuery=ds.createQuery(Membership.class);
//	        memQuery.filter("customerid", customerid);
//	        memQuery.order("-_id");
//	        memQuery.limit(1);
	       
		   Query query1=ds.createQuery(Email.class);
			query1.filter("customerid",customerid);
			query1.order("-_id");
			query1.limit(1);
			
			log.info("Inside Email.class");
			try
			{
			// log.info("Inside try");
			ArrayList<Email> emailList = (ArrayList<Email>) query1.asList();
			log.info(emailList);
			
			for (Email email : emailList) 
			{
				log.info("In for loop");

				email.getClass();
				log.info("CustomerID=" + customerid);
				email.getTriggerName();
				// log.info("Subject id="+email.getSubject());
				log.info("TriggerName=" + email.getTriggerName());
				log.info(email.getFirstname());
				email.getLastname();
				email.getFrom();
				email.getMobilenumber();
				log.info("Reason=" + email.getReason());
				log.info("Subject=" + email.getSubject());

				log.info("Mail-Subject verification");
				String actualSubject = email.getSubject();
				String expectedSubject = "Kan jag hjälpa dig att börja läsa?";
				Assert.assertEquals(actualSubject, expectedSubject);
				log.info("Subject verified successfully");

				log.info("Response verification");
				String expectedResponse = "Success";
				String actualResponse = email.getReason();
				Assert.assertEquals(actualResponse, expectedResponse);
				log.info("Response is success");

				log.info("Triggered message verification");
				String expectedTriggerName = "Post-reg 5: Start reading";
				String actualTriggerName = email.getTriggerName();
				Assert.assertEquals(actualTriggerName, expectedTriggerName);
				log.info("Message Triggered successfully");

				log.info("Mail sent Date verification");
				expectedupdateddate = AddDate.currentDate();
				actualupdateddate = email.getMailsentdate();
				Assert.assertEquals(actualupdateddate, expectedupdateddate);
				log.info("mail sent date verified successfully");
			}

			}
			catch(Exception e)
			{
				e.printStackTrace();
			}		
 }
}