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
import generics.AddDate;
import generics.MongoDBUtilMorphia;
import interfaceForApiOrDB.InformationFromBackend;
import interfaceForApiOrDB.InformationFromBackendNegative;

public class SurpriseDelight extends SuperTestScript
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
//	static Logger log = Logger.getLogger(SurpriseDelight.class);
	//private SoftAssert softAssert=new SoftAssert();
	MongoDBUtilMorphia mongoutil = new MongoDBUtilMorphia();
	Datastore ds = mongoutil.getMorphiaDatastoreForNlob();
    Membership member=new Membership();
    Email email=new Email();
	String result="";
	 
	String expectedupdateddate;
	Date actualupdateddate;
	 
	public SurpriseDelight()
	{
		loginRequired=false;
		logoutRequired=false;
	}
	
	@Test(enabled=true, priority=20, groups={"PostRegistrationRuleMailerPositive" , "All"})
	public void SurpriseDelightTC080201() throws InterruptedException
	{
		
		log.info("------------------------Running Surprise and Delight Positive----------------------------");

		log.info("Fetching data from databse MySQL");
		InformationFromBackend info=new InformationFromBackend();
		info.getDataForCustomerInfo("SurpriseDelight");                  //CustometerInfo and orders
		
		log.info("Batch execution");
		driver.get("http://130.211.74.42:8082/nextory_batch/jobs/surprise-delight-user");
		
		log.info("Fetching data from MongoDB");
		Membership custList = null;
		String customerid = InformationFromBackend.result;
		
		log.info("customerid="+customerid);
		//-------------------------------------------nx_customer_email_logs--------------------------------------
	    // db.nx_customer_email_logs.find({"customerid" :$a}).sort({"_id":-1}).limit(1).pretty();
		    Query query1=ds.createQuery(Email.class);
			query1.filter("customerid", customerid);
			query1.order("-_id");
			query1.limit(1);
			
			//log.info("Inside Email.class");
			
			try
			{	
			//log.info("entering inside try block");

			ArrayList<Email> emailList = (ArrayList<Email>) query1.asList();
			log.info(emailList);
			//log.info("In array");

			for (Email email : emailList) 
			{
				//log.info("in for loop");
				email.getClass();
				log.info("class=" + email.getClass());
				log.info("CustomerID=" + customerid);
				email.getFirstname();
				log.info("Firstname=" + email.getFirstname());
				email.getLastname();
				email.getFrom();
				log.info("Subject=" + email.getSubject());
				email.getMobilenumber();
				log.info("Triggered message=" + email.getTriggerName());

				log.info("Mail-Subject verification");
				String expectedSubject = "Behöver du någon hjälp att komma igång?";
				String actualSubject = email.getSubject();
				Assert.assertEquals(actualSubject, expectedSubject);
				log.info("Subject verified successfully");

				log.info("Response verification");
				String expectedResponse = "Success";
				String actualResponse = email.getReason();
				Assert.assertEquals(actualResponse, expectedResponse);
				log.info("Response is success");

				log.info("Triggered message verification");
				String expectedTriggerName = "Post-reg 2: Surprise & delight";
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
		
	}
	@Test(enabled=true,priority=21, groups={"PostRegistrationRuleMailerNegative" , "All"})
	public void SurpriseDelight() throws InterruptedException
	{
		log.info("------------------------------Running Surprise and Delight Negative---------------------------");

		log.info("Fetching data from databse MySQL");
		InformationFromBackendNegative info=new InformationFromBackendNegative();
		info.getDataForCustomerInfo("SurpriseDelight");                  //CustometerInfo and orders
		
		log.info("Batch execution");
		driver.get("http://130.211.74.42:8082/nextory_batch/jobs/surprise-delight-user");
		
		log.info("Fetching data from MongoDB");
		Membership custList = null;
		String customerid = InformationFromBackendNegative.result;
		
		log.info("customerid="+customerid);
		//-------------------------------------------nx_customer_email_logs--------------------------------------
	    // db.nx_customer_email_logs.find({"customerid" :$a}).sort({"_id":-1}).limit(1).pretty();
		    Query query1=ds.createQuery(Email.class);
			query1.filter("customerid", customerid);
			query1.order("-_id");
			query1.limit(1);
			
			//log.info("Inside Email.class");
			
			try
			{	
			//log.info("entering inside try block");

			ArrayList<Email> emailList = (ArrayList<Email>) query1.asList();
			log.info(emailList);
			//log.info("In array");

			for (Email email : emailList) 
			{
				//log.info("in for loop");
				email.getClass();
				log.info("class=" + email.getClass());
				log.info("CustomerID=" + customerid);
				email.getFirstname();
				log.info("Firstname=" + email.getFirstname());
				email.getLastname();
				email.getFrom();
				log.info("Subject=" + email.getSubject());
				email.getMobilenumber();
				log.info("Triggered message=" + email.getTriggerName());

				log.info("Mail-Subject verification");
				String expectedSubject = "Behöver du någon hjälp att komma igång?";
				String actualSubject = email.getSubject();
				Assert.assertEquals(actualSubject, expectedSubject);
				log.info("Subject verified successfully");

				log.info("Response verification");
				String expectedResponse = "Success";
				String actualResponse = email.getReason();
				Assert.assertEquals(actualResponse, expectedResponse);
				log.info("Response is success");

				log.info("Triggered message verification");
				String expectedTriggerName = "Post-reg 2: Surprise & delight";
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
	}

	
}
