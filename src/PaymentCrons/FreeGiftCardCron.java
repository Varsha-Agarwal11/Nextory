package PaymentCrons;

import org.testng.Assert;
import org.testng.annotations.Test;

import common.SuperTestScript;
import generics.AddDate;
import generics.Database;
import generics.Property;
import pages.HomePage;
import pages.LoginPage;
import pages.MyAccountPage;
import pages.PaymentCardDetailsPage;

public class FreeGiftCardCron extends SuperTestScript
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
	public static String un;
	public static String pwd;
	public static String cardNumber;
	public static String cvc;
	public static String month;
	public static String year;
	
	
	public FreeGiftCardCron()
	{
		loginRequired=false;
		logoutRequired=false;
	}
	
	//----------------------------------FREE GIFT CARD PAYMENT CRON SUCCESS AT DAY 1-------------------------------------//
	@Test
	public void runFreeGiftCardCronSuccess()
	{
		customerId = Database.executeQuery("select customerid from customerinfo where member_type_code=201007 order by rand() limit 1");
		log.info("Customer Id of the Free Gift Card Member is : " +customerId);
		
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
		log.info("CURRENT MEMBER_TYPE IS : MEMBER_PAYING");			//Validating if the current Member Type gets converted to MEMBER_PAYING or not.	
		
		Assert.assertEquals(previousMemTypeCode, "201007");
		log.info("PREVIOUS MEMBER_TYPE WAS: FREE_GIFTCARD_MEMBER");	//Validating if the previous Member Type was FREE_GIFTCARD_MEMBER or Not.
		
		Assert.assertEquals(lastTxnStatus, "SUCCESS");
		log.info("LAST TRANSACTION STATUS IS: " +lastTxnStatus);
		
		
	}
	
	
	//---------------------------------- FREE GIFT CARD PAYMENT CRON FAILS AT DAY 1, DAY 3 and DAY 7----------------------------//

	@Test
	public void runGiftCardCronFailure()
	{

// Failing at Day 1 //
		
		log.info("// Failing at Day 1 //");
		
		customerId = Database.executeQuery("select customerid from customerinfo where member_type_code=201007 order by rand() limit 1");
		log.info("Customer Id of the Free Gift Card Member is : " +customerId);
		
		Database.executeQuery("select next_subscription_run_date from customer2subscriptionmap where customerid =" +customerId);
		
		Database.executeUpdate("update customer2subscriptionmap set next_subscription_run_date=now() where customerid= " +customerId);
		
		Database.executeUpdate("update transaction set transactionid='change817' where customerid=" +customerId);
		
		log.info("Running the Batch after updating the next_subscription_run_date and transactionId");
		
		driver.navigate().to("http://130.211.74.42:8080/payment/testmonthlysubscription?customerid=" +customerId);
		driver.close();
		
		status = Database.executeQuery("select status from orders where customerid=" +customerId + " order by orderdate desc limit 1");
		presentMemTypeCode = Database.executeQuery("select member_type_code from customerinfo where customerid =" +customerId);
		previousMemTypeCode = Database.executeQuery("select prev_mem_code from customerinfo where customerid=" +customerId);
		lastTxnStatus = Database.executeQuery("select lasttxnstatus from transaction where customerid=" +customerId);
		subscriptionId = Database.executeQuery("select subscriptionid from customer2subscriptionmap where customerid=" +customerId);
		orderAmount = Database.executeQuery("select orderamount from orders where customerid= " +customerId+ " order by orderdate desc limit 1");
		failedCount = Database.executeQuery("select failed_mail_count from transaction where customerid=" +customerId);
		
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
		
		Assert.assertEquals(status, "FAILED");
		log.info("STATUS IS : " +status);    									//Validating if the status gets SUCCESS or FAILED.
		
		Assert.assertEquals(presentMemTypeCode, "203011");
		log.info("CURRENT MEMBER_TYPE IS : FREE_TRANSACTION_FAILED");			//Validating if the current Member Type gets converted to FREE_TRANSACTION_FAILED or not.	
		
		Assert.assertEquals(previousMemTypeCode, "201007");
		log.info("PREVIOUS MEMBER_TYPE WAS: FREE_GIFTCARD_MEMBER");				//Validating if the previous Member Type was FREE_GIFTCARD_MEMBER or Not.
		
		Assert.assertEquals(lastTxnStatus, "FAILED");
		log.info("LAST TRANSACTION STATUS IS: " +lastTxnStatus);
		
		Assert.assertFalse(failedCount.equalsIgnoreCase("0"));
		log.info("FAILED COUNT IS:" +failedCount);
		
		
//Failing at Day 3 //
		
		log.info("//Failing at Day 3 //");
		Database.executeUpdate("update customer2subscriptionmap set next_subscription_run_date=now() - interval 3 DAY where customerid= " +customerId);
		
		Database.executeUpdate("update transaction set transactionid='change817' where customerid=" +customerId);
		
		log.info("Running the Batch after updating the next_subscription_run_date and transactionId");
		
		driver.navigate().to("http://130.211.74.42:8080/payment/testExtentedFailedTransaction?customerid=" +customerId+ "&provider=ADYEN");
		driver.close();
		
		status = Database.executeQuery("select status from orders where customerid=" +customerId + " order by orderdate desc limit 1");
		presentMemTypeCode = Database.executeQuery("select member_type_code from customerinfo where customerid =" +customerId);
		previousMemTypeCode = Database.executeQuery("select prev_mem_code from customerinfo where customerid=" +customerId);
		lastTxnStatus = Database.executeQuery("select lasttxnstatus from transaction where customerid=" +customerId);
		subscriptionId = Database.executeQuery("select subscriptionid from customer2subscriptionmap where customerid=" +customerId);
		orderAmount = Database.executeQuery("select orderamount from orders where customerid= " +customerId+ " order by orderdate desc limit 1");
		failedCount = Database.executeQuery("select failed_mail_count from transaction where customerid=" +customerId);
		
		Assert.assertEquals(status, "FAILED");
		log.info("STATUS IS : " +status);    									//Validating if the status gets SUCCESS or FAILED.
		
		Assert.assertEquals(presentMemTypeCode, "203011");
		log.info("CURRENT MEMBER_TYPE IS : FREE_TRANSACTION_FAILED");			//Validating if the current Member Type gets converted to FREE_TRANSACTION_FAILED or not.	
		
		Assert.assertEquals(previousMemTypeCode, "201007");
		log.info("PREVIOUS MEMBER_TYPE WAS: FREE_GIFTCARD_MEMBER");				//Validating if the previous Member Type was FREE_GIFTCARD_MEMBER or Not.
		
		Assert.assertEquals(lastTxnStatus, "FAILED");
		log.info("LAST TRANSACTION STATUS IS: " +lastTxnStatus);
		
		Assert.assertFalse(failedCount.equalsIgnoreCase("0"));
		log.info("FAILED COUNT IS:" +failedCount);
		
		
//Failing at Day 7 //
		
		log.info("//Failing at Day 7 //");
		Database.executeUpdate("update customer2subscriptionmap set next_subscription_run_date=now() - interval 7 DAY where customerid= " +customerId);
				
		Database.executeUpdate("update transaction set transactionid='change817' where customerid=" +customerId);
			
		log.info("Running the Batch after updating the next_subscription_run_date and transactionId");
				
		driver.navigate().to("http://130.211.74.42:8080/payment/testExtentedFailedTransaction?customerid=" +customerId+ "&provider=ADYEN");
		driver.close();
				
		status = Database.executeQuery("select status from orders where customerid=" +customerId + " order by orderdate desc limit 1");
		presentMemTypeCode = Database.executeQuery("select member_type_code from customerinfo where customerid =" +customerId);
		previousMemTypeCode = Database.executeQuery("select prev_mem_code from customerinfo where customerid=" +customerId);
		lastTxnStatus = Database.executeQuery("select lasttxnstatus from transaction where customerid=" +customerId);
		subscriptionId = Database.executeQuery("select subscriptionid from customer2subscriptionmap where customerid=" +customerId);
		orderAmount = Database.executeQuery("select orderamount from orders where customerid= " +customerId+ " order by orderdate desc limit 1");
		failedCount = Database.executeQuery("select failed_mail_count from transaction where customerid=" +customerId);
				
		Assert.assertEquals(status, "FAILED");
		log.info("STATUS IS : " +status);    									//Validating if the status gets SUCCESS or FAILED.
			
		Assert.assertEquals(presentMemTypeCode, "401005");
		log.info("CURRENT MEMBER_TYPE IS : NONMEMBER_PREVIOUS_GIFTCARD");			//Validating if the current Member Type gets converted to NONMEMBER_PREVIOUS_GIFTCARD or not.	
		
		Assert.assertEquals(previousMemTypeCode, "203011");
		log.info("PREVIOUS MEMBER_TYPE WAS: FREE_TRANSACTION_FAILED");				//Validating if the previous Member Type was FREE_TRANSACTION_FAILED or Not.
		
		Assert.assertEquals(lastTxnStatus, "FAILED");
		log.info("LAST TRANSACTION STATUS IS: " +lastTxnStatus);
				
		Assert.assertFalse(failedCount.equalsIgnoreCase("0"));
		log.info("FAILED COUNT IS:" +failedCount);
				
		
	}
	
	
	//---------------------------------- FREE GIFT CARD PAYMENT CRON FAILS AT DAY 1 AND SUCCESS AT DAY 3 ----------------------------//
	
	@Test
	public void runFreeGiftCardCronSuccessAtThree()
	{
		// Failing at Day 1 //
		
				log.info("// Failing at Day 1 //");
				customerId = Database.executeQuery("select customerid from customerinfo where member_type_code=201007 order by rand() limit 1");
				log.info("Customer Id of the Free Gift Card Member is : " +customerId);
				
				Database.executeQuery("select next_subscription_run_date from customer2subscriptionmap where customerid =" +customerId);
				
				Database.executeUpdate("update customer2subscriptionmap set next_subscription_run_date=now() where customerid= " +customerId);
				
				Database.executeUpdate("update transaction set transactionid='change817' where customerid=" +customerId);
				
				log.info("Running the Batch after updating the next_subscription_run_date and transactionId");
				
				driver.navigate().to("http://130.211.74.42:8080/payment/testmonthlysubscription?customerid=" +customerId);
				driver.close();
				
				status = Database.executeQuery("select status from orders where customerid=" +customerId + " order by orderdate desc limit 1");
				presentMemTypeCode = Database.executeQuery("select member_type_code from customerinfo where customerid =" +customerId);
				previousMemTypeCode = Database.executeQuery("select prev_mem_code from customerinfo where customerid=" +customerId);
				lastTxnStatus = Database.executeQuery("select lasttxnstatus from transaction where customerid=" +customerId);
				subscriptionId = Database.executeQuery("select subscriptionid from customer2subscriptionmap where customerid=" +customerId);
				orderAmount = Database.executeQuery("select orderamount from orders where customerid= " +customerId+ " order by orderdate desc limit 1");
				failedCount = Database.executeQuery("select failed_mail_count from transaction where customerid=" +customerId);
				
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
				
				Assert.assertEquals(status, "FAILED");
				log.info("STATUS IS : " +status);    									//Validating if the status gets SUCCESS or FAILED.
				
				Assert.assertEquals(presentMemTypeCode, "203011");
				log.info("CURRENT MEMBER_TYPE IS : FREE_TRANSACTION_FAILED");			//Validating if the current Member Type gets converted to FREE_TRANSACTION_FAILED or not.	
				
				Assert.assertEquals(previousMemTypeCode, "201007");
				log.info("PREVIOUS MEMBER_TYPE WAS: FREE_GIFTCARD_MEMBER");				//Validating if the previous Member Type was FREE_GIFTCARD_MEMBER or Not.
				
				Assert.assertEquals(lastTxnStatus, "FAILED");
				log.info("LAST TRANSACTION STATUS IS: " +lastTxnStatus);
				
				Assert.assertFalse(failedCount.equalsIgnoreCase("0"));
				log.info("FAILED COUNT IS:" +failedCount);
				
		
		//SUCCESS AT DAY 3//
			
				Database.executeUpdate("update customer2subscriptionmap set next_subscription_run_date=now()-interval 3 DAY where customerid= " +customerId);
				
				un= Database.executeQuery("select email from customerinfo where customerid=" +customerId);						// getting the email id for the particular customer id
				Database.executeUpdate("update customerinfo set password='76C3123DBEDEC1E13F920DB346B9C5BC' where email='" +un+ "'");   // updating the password for the customer as "password"
				pwd="password";
				cardNumber=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "CARDNUM");
				cvc=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "CVC");
				month=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "MONTH");
				year=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "YEAR");
				
				log.info("Navigating To Login Page");
				HomePage home=new HomePage(driver);
				home.clickLoginLink();
				
				log.info("Entering login details with username as : '" +un+ "' and password as : '" +pwd+ "'" );
				LoginPage login=new LoginPage(driver);
				login.setEmailId(un);
				login.setPassword(pwd);
				login.clickLoginButton();
				
				log.info("Clicking on button to change Credit Card");
				MyAccountPage account=new MyAccountPage(driver);
				account.clickToChangeCreditCard();
				
				log.info("Filling the New Credit Card Details");
				PaymentCardDetailsPage card=new PaymentCardDetailsPage(driver);
				card.enterCardNumber(cardNumber);
				card.clickExpiryMonthDropdown();
				card.selectExpiryMonth(month);
				card.clickExpiryYearDropdown();
				card.selectExpiryYear(year);
				card.enterCvcNumber(cvc);
				card.clickToSaveCreditCard();
				
				log.info("Your Credit Card Details are Updated / Dina kreditkortsuppgifter har blivit uppdaterade.");
				
				
				
				home.clickNextoryLogo();
				home.clickAccountLink();
				
				
				
				log.info("Running the Batch after updating the Next Subscription Run Date");
				
				driver.navigate().to("http://130.211.74.42:8080/payment/testmonthlysubscription?customerid=" +customerId);
				driver.close();
				
				String currentDate= AddDate.currentDate();
				log.info("Current Date is : " +currentDate);

				String expected= AddDate.addingDays(27);
				
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
				log.info("CURRENT MEMBER_TYPE IS : MEMBER_PAYING");			//Validating if the current Member Type gets converted to MEMBER_PAYING or not.	
				
				Assert.assertEquals(previousMemTypeCode, "203011");
				log.info("PREVIOUS MEMBER_TYPE WAS: FREE_TRANSACTION_FAILED");	//Validating if the previous Member Type was FREE_TRANSACTION_FAILED or Not.
				
				Assert.assertEquals(lastTxnStatus, "SUCCESS");
				log.info("LAST TRANSACTION STATUS IS: " +lastTxnStatus);
	}
				
	
	//---------------------------------- FREE GIFT CARD PAYMENT CRON FAILS AT DAY 1, DAY 3 AND SUCCESS AT DAY 7 ----------------------------//
	
	@Test
	public void runFreeGiftCardCronSuccessAtSeven()
	{
		// Failing at Day 1 //
				
				log.info("// Failing at Day 1 //");
				customerId = Database.executeQuery("select customerid from customerinfo where member_type_code=201007 order by rand() limit 1");
				log.info("Customer Id of the Free Gift Card Member is : " +customerId);
					
				Database.executeQuery("select next_subscription_run_date from customer2subscriptionmap where customerid =" +customerId);
					
				Database.executeUpdate("update customer2subscriptionmap set next_subscription_run_date=now() where customerid= " +customerId);
					
				Database.executeUpdate("update transaction set transactionid='change817' where customerid=" +customerId);
					
				log.info("Running the Batch after updating the next_subscription_run_date and transactionId");
					
				driver.navigate().to("http://130.211.74.42:8080/payment/testmonthlysubscription?customerid=" +customerId);
				driver.close();
					
				status = Database.executeQuery("select status from orders where customerid=" +customerId + " order by orderdate desc limit 1");
				presentMemTypeCode = Database.executeQuery("select member_type_code from customerinfo where customerid =" +customerId);
				previousMemTypeCode = Database.executeQuery("select prev_mem_code from customerinfo where customerid=" +customerId);
				lastTxnStatus = Database.executeQuery("select lasttxnstatus from transaction where customerid=" +customerId);
				subscriptionId = Database.executeQuery("select subscriptionid from customer2subscriptionmap where customerid=" +customerId);
				orderAmount = Database.executeQuery("select orderamount from orders where customerid= " +customerId+ " order by orderdate desc limit 1");
				failedCount = Database.executeQuery("select failed_mail_count from transaction where customerid=" +customerId);
					
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
					
				Assert.assertEquals(status, "FAILED");
				log.info("STATUS IS : " +status);    									//Validating if the status gets SUCCESS or FAILED.
					
				Assert.assertEquals(presentMemTypeCode, "203011");
				log.info("CURRENT MEMBER_TYPE IS : FREE_TRANSACTION_FAILED");			//Validating if the current Member Type gets converted to FREE_TRANSACTION_FAILED or not.	
					
				Assert.assertEquals(previousMemTypeCode, "201007");
				log.info("PREVIOUS MEMBER_TYPE WAS: FREE_GIFTCARD_MEMBER");				//Validating if the previous Member Type was FREE_GIFTCARD_MEMBER or Not.
						
				Assert.assertEquals(lastTxnStatus, "FAILED");
				log.info("LAST TRANSACTION STATUS IS: " +lastTxnStatus);
					
				Assert.assertFalse(failedCount.equalsIgnoreCase("0"));
				log.info("FAILED COUNT IS:" +failedCount);
					
					
		//Failing at Day 3 //
					
				log.info("//Failing at Day 3 //");
				Database.executeUpdate("update customer2subscriptionmap set next_subscription_run_date=now() - interval 3 DAY where customerid= " +customerId);
					
				Database.executeUpdate("update transaction set transactionid='change817' where customerid=" +customerId);
				
				log.info("Running the Batch after updating the next_subscription_run_date and transactionId");
					
				driver.navigate().to("http://130.211.74.42:8080/payment/testExtentedFailedTransaction?customerid=" +customerId+ "&provider=ADYEN");
				driver.close();
					
				status = Database.executeQuery("select status from orders where customerid=" +customerId + " order by orderdate desc limit 1");
				presentMemTypeCode = Database.executeQuery("select member_type_code from customerinfo where customerid =" +customerId);
				previousMemTypeCode = Database.executeQuery("select prev_mem_code from customerinfo where customerid=" +customerId);
				lastTxnStatus = Database.executeQuery("select lasttxnstatus from transaction where customerid=" +customerId);
				subscriptionId = Database.executeQuery("select subscriptionid from customer2subscriptionmap where customerid=" +customerId);
				orderAmount = Database.executeQuery("select orderamount from orders where customerid= " +customerId+ " order by orderdate desc limit 1");
				failedCount = Database.executeQuery("select failed_mail_count from transaction where customerid=" +customerId);
					
				Assert.assertEquals(status, "FAILED");
				log.info("STATUS IS : " +status);    									//Validating if the status gets SUCCESS or FAILED.
					
				Assert.assertEquals(presentMemTypeCode, "203011");
				log.info("CURRENT MEMBER_TYPE IS : FREE_TRANSACTION_FAILED");			//Validating if the current Member Type gets converted to FREE_TRANSACTION_FAILED or not.	
					
				Assert.assertEquals(previousMemTypeCode, "201007");
				log.info("PREVIOUS MEMBER_TYPE WAS: FREE_GIFTCARD_MEMBER");				//Validating if the previous Member Type was FREE_GIFTCARD_MEMBER or Not.
					
				Assert.assertEquals(lastTxnStatus, "FAILED");
				log.info("LAST TRANSACTION STATUS IS: " +lastTxnStatus);
				
				Assert.assertFalse(failedCount.equalsIgnoreCase("0"));
				log.info("FAILED COUNT IS:" +failedCount);
				
		
	//SUCCESS AT DAY 7//		
				
				log.info("//SUCCESS AT DAY 7//");
				
				Database.executeUpdate("update customer2subscriptionmap set next_subscription_run_date=now() - interval 7 DAY where customerid= " +customerId);
				
				un= Database.executeQuery("select email from customerinfo where customerid=" +customerId);						// getting the email id for the particular customer id
				Database.executeUpdate("update customerinfo set password='76C3123DBEDEC1E13F920DB346B9C5BC' where email='" +un+ "'");   // updating the password for the customer as "password"
				pwd="password";
				cardNumber=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "CARDNUM");
				cvc=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "CVC");
				month=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "MONTH");
				year=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "YEAR");
				
				log.info("Navigating To Login Page");
				HomePage home=new HomePage(driver);
				home.clickLoginLink();
				
				log.info("Entering login details with username as : '" +un+ "' and password as : '" +pwd+ "'" );
				LoginPage login=new LoginPage(driver);
				login.setEmailId(un);
				login.setPassword(pwd);
				login.clickLoginButton();
				
				log.info("Clicking on button to change Credit Card");
				MyAccountPage account=new MyAccountPage(driver);
				account.clickToChangeCreditCard();
				
				log.info("Filling the New Credit Card Details");
				PaymentCardDetailsPage card=new PaymentCardDetailsPage(driver);
				card.enterCardNumber(cardNumber);
				card.clickExpiryMonthDropdown();
				card.selectExpiryMonth(month);
				card.clickExpiryYearDropdown();
				card.selectExpiryYear(year);
				card.enterCvcNumber(cvc);
				card.clickToSaveCreditCard();
				
				log.info("Your Credit Card Details are Updated / Dina kreditkortsuppgifter har blivit uppdaterade.");
				
				home.clickNextoryLogo();
				home.clickAccountLink();
				
				
				
				log.info("Running the Batch after updating the Next Subscription Run Date");
				
				driver.navigate().to("http://130.211.74.42:8080/payment/testmonthlysubscription?customerid=" +customerId);
				driver.close();
				
				String currentDate= AddDate.currentDate();
				log.info("Current Date is : " +currentDate);

				String expected= AddDate.addingDays(23);
				
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
				log.info("CURRENT MEMBER_TYPE IS : MEMBER_PAYING");			//Validating if the current Member Type gets converted to MEMBER_PAYING or not.	
				
				Assert.assertEquals(previousMemTypeCode, "203011");
				log.info("PREVIOUS MEMBER_TYPE WAS: FREE_TRANSACTION_FAILED");	//Validating if the previous Member Type was FREE_TRANSACTION_FAILED or Not.
				
				Assert.assertEquals(lastTxnStatus, "SUCCESS");
				log.info("LAST TRANSACTION STATUS IS: " +lastTxnStatus);
	}
}
