package confirmationsRuleMailer;

import java.util.ArrayList;
import java.util.Date;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.testng.Assert;
import org.testng.annotations.Test;

import Mongo.Email_logs.Email;
import common.SuperTestScript;
import generics.AddDate;
import generics.MongoDBUtilMorphia;
import interfaceForApiOrDB.InformationFromBackend;
import pages.CustomerFormPage;

public class ChurnNonPayment extends SuperTestScript
{
	 MongoDBUtilMorphia mongoutil = new MongoDBUtilMorphia();
	 Datastore ds = mongoutil.getMorphiaDatastoreForNlob();
	 Email email=new Email();
	 
	public ChurnNonPayment()
	{
		loginRequired=false;
		logoutRequired=false;
	}
	
	@Test(enabled=true, priority=160, groups={"ConfirmationsRuleMailerPositive" , "All"})
	public void churnNonPaymentTC100701()
	{
		log.info("Running Churn Non Payment");
		/*
		log.info("Verifying the customer info");
		CustomerFormPage cust=new CustomerFormPage(driver);
		cust.clickContinue();
		log.info("Nonmember successfully converted to Active Member");
		*/
		  log.info("Fetching data from databse MySQL");
		  InformationFromBackend info=new InformationFromBackend();
		  info.getDataForCustomerInfo("ChurnNonPayment");  
		  
		String customerid =  InformationFromBackend.result;
		log.info("Customerid selected from Sql is="+customerid);
		
		log.info("Batch Execution");
		driver.get("http://130.211.74.42:8082/payment/testExtentedFailedTransaction?customerid="+customerid+" & provider=ADYEN");
		
		 log.info("Fetching data from MongoDB");
		 
		Query query=ds.createQuery(Email.class);
		query.filter("customerid",customerid);
		
		try
		{
			ArrayList<Email> emailList=(ArrayList<Email>)query.asList();
			
			 log.info("Email list="+emailList);
			 for(Email email:emailList)
			 {
				log.info("class="+email.getClass());
				log.info( "CustomerID=" + customerid );
				log.info("Firstname="+email.getFirstname());
				email.getLastname();
				email.getFrom();
				email.getMobilenumber();
				log.info(email.getReason());
				log.info(email.getTriggerName());
				log.info(email.getSubject());
				
				log.info("Mail-Subject verification");
				String expectedSubject="Ditt konto har pausats" ;	
		        String actualSubject=email.getSubject();
		        Assert.assertEquals(actualSubject, expectedSubject);
				log.info("Subject verified successfully");
		     
		   	    log.info("Response verification");
		   	    String expectedResponse="Success";
				String actualResponse=email.getReason();
				Assert.assertEquals(actualResponse,expectedResponse);
				log.info("Response is verified successfully");
				
			   log.info("Triggered message verification");
			   String expectedTriggerName="Churn: Non-payment" ;	
		       String actualTriggerName=email.getTriggerName();
		       Assert.assertEquals(actualTriggerName, expectedTriggerName);
			   log.info("Message Triggered successfully"); 
			
			log.info("Mail sent Date verification");
			String expectedupdateddate=AddDate.currentDate();
		   	Date actualupdateddate=email.getMailsentdate();
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
