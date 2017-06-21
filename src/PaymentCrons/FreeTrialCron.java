package PaymentCrons;

import org.testng.Assert;
import org.testng.annotations.Test;

import common.SuperTestScript;
import generics.AddDate;
import generics.Database;

public class FreeTrialCron extends SuperTestScript
{
	public static String customerId;
	public static String nextSubRunDate;
	public static String subscriptionId;
	public static String orderAmount;
	public static String status;
	public static String presentMemTypeCode;
	public static String previousMemTypeCode;
	public static String lastTxnStatus;
	public static String failedCount;
	
	public FreeTrialCron()
	{
		loginRequired=false;
		logoutRequired=false;
	}
	
	//--------------------------------------- FREE TRIAL PAYMENT CRON SUCCESS ------------------------------------------------//
	@Test
	public void freeTrialPaymentCronSuccess()
	{
		customerId = Database.executeQuery("select customerid from customerinfo where member_type_code=203002 order by rand() limit 1");
		log.info("Customer Id of the Free Trial Member is : " +customerId);
		
		Database.executeQuery("select next_subscription_run_date from customer2subscriptionmap where customerid =" +customerId);
		
		Database.executeUpdate("update customer2subscriptionmap set next_subscription_run_date=now() where customerid= " +customerId);
		
		log.info("Running the Batch after updating the Next Subscription Run Date");
		
		driver.navigate().to("http://130.211.74.42:8080/payment/testmonthlysubscription?customerid=" +customerId);
		driver.close();
		
		String currentDate= AddDate.currentDate();
		log.info("Current Date is : " +currentDate);

		String expected= AddDate.addingDays(30);
		
		nextSubRunDate= Database.executeQuery("select next_subscription_run_date from customer2subscriptionmap where customerid =" +customerId);
		log.info("Next Subscription Run Date is: " +nextSubRunDate);
		
		Assert.assertEquals(nextSubRunDate, expected);   // Validating the Next Subcription Run Date is 30 days after the payment cron batch is run
		
		subscriptionId = Database.executeQuery("select subscriptionid from customer2subscriptionmap where customerid=" +customerId);
		status = Database.executeQuery("select status from orders where customerid=" +customerId + " order by orderdate desc limit 1");
		orderAmount = Database.executeQuery("select orderamount from orders where customerid= " +customerId+ " order by orderdate desc limit 1");
		presentMemTypeCode = Database.executeQuery("select member_type_code from customerinfo where customerid =" +customerId);
		previousMemTypeCode = Database.executeQuery("select prev_mem_code from customerinfo where customerid=" +customerId);
		lastTxnStatus = Database.executeQuery("select lasttxnstatus from transaction where customerid=" +customerId);
		
		if(subscriptionId.equalsIgnoreCase("5"))
		{
			Assert.assertEquals(orderAmount, "99");
			log.info(" For BAS subscription Order Amount is : " +orderAmount);
		}
		
		else if(subscriptionId.equalsIgnoreCase("6"))
		{
			Assert.assertEquals(orderAmount, "199");
			log.info("For STANDARD subscription Order Amount is : " +orderAmount);
		}
		
		else if(subscriptionId.equalsIgnoreCase("7"))
		{
			Assert.assertEquals(orderAmount, "249");
			log.info("For PREMIUM subscription Order Amount is : " +orderAmount);
		}
		
		Assert.assertEquals(status, "SUCCESS");
		log.info("STATUS IS : " +status);    						//Validating if the status gets SUCCESS or FAILED.
		
		Assert.assertEquals(presentMemTypeCode, "304001");
		log.info("CURRENT MEMBER_TYPE IS : MEMBER_PAYING");			//Validating if the current Member Type gets converted to Member_Paying or not.	
		
		Assert.assertEquals(previousMemTypeCode, "203002");
		log.info("PREVIOUS MEMBER_TYPE WAS: FREE_TRIAL_MEMBER");	//Validating if the previous Member Type was FREE TRIAL MEMBER or Not.
		
		Assert.assertEquals(lastTxnStatus, "SUCCESS");
		log.info("LAST TRANSACTION STATUS IS: " +lastTxnStatus);
		
	}
	
	//-------------------------------------FREE TRIAL PAYMENT CRON FAILURE---------------------------------------------------//
	@Test
	public void freeTrialPaymentCronFailure()
	{
		customerId = Database.executeQuery("select customerid from customerinfo where member_type_code=203002 order by rand() limit 1");
		log.info("Customer Id of the Free Trial Member is : " +customerId);
		
		Database.executeQuery("select next_subscription_run_date from customer2subscriptionmap where customerid =" +customerId);
		
		Database.executeUpdate("update customer2subscriptionmap set next_subscription_run_date=now() where customerid= " +customerId);
		Database.executeUpdate("update transaction set transactionid='change817' where customerid=" +customerId);
		
		log.info("Running the Batch after updating the Next Subscription Run Date and transaction id to fail the cron");		
		driver.navigate().to("http://130.211.74.42:8080/payment/testmonthlysubscription?customerid=" +customerId);
		driver.close();
		
		lastTxnStatus = Database.executeQuery("select lasttxnstatus from transaction where customerid = " +customerId);
		
		failedCount = Database.executeQuery("select failed_mail_count from transaction where customerid=" +customerId);
		
		presentMemTypeCode = Database.executeQuery("select member_type_code from customerinfo where customerid =" +customerId);
		previousMemTypeCode = Database.executeQuery("select prev_mem_code from customerinfo where customerid=" +customerId);
		status = Database.executeQuery("select status from orders where customerid=" +customerId + " order by orderdate desc limit 1");
		
		Assert.assertEquals(status, "FAILED");
		log.info("STATUS IS : " +status);    						//Validating if the status gets SUCCESS or FAILED.
		
		Assert.assertEquals(presentMemTypeCode, "403005");
		log.info("CURRENT MEMBER_TYPE IS : NONMEMBER_PREVIOUS_TRIAL");			//Validating if the current Member Type gets converted to NONMEMBER_PREVIOUS_TRIAL or not.	
		
		Assert.assertEquals(previousMemTypeCode, "203002");
		log.info("PREVIOUS MEMBER_TYPE WAS: FREE_TRIAL_MEMBER");	//Validating if the previous Member Type was FREE TRIAL MEMBER or Not.
		
		Assert.assertEquals(lastTxnStatus, "FAILED");
		log.info("LAST TRANSACTION STATUS IS: " +lastTxnStatus);
		
		Assert.assertFalse(failedCount.equalsIgnoreCase("0"));
		log.info("FAILED COUNT IS:" +failedCount);
	}

}
