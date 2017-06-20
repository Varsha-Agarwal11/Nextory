package confirmationsRuleMailer;

import java.util.ArrayList;
import java.util.Date;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import Mongo.Email_logs.Email;
import common.PasswordFromAdmin;
import common.SuperTestScript;
import generics.AddDate;
import generics.Database;
import generics.Excel;
import generics.MongoDBUtilMorphia;
import generics.Property;
import interfaceForApiOrDB.InformationFromBackend;
import pages.AdminPage;
import pages.EndSubsFeedbackPage;
import pages.EndSubscriptionPage;
import pages.HomePage;
import pages.LoginPage;
import pages.MyAccountPage;

public class ChurnPayingMember extends SuperTestScript
{
	public static String un;
	public static String pwd;
	public static String adminUn;
	public static String adminPwd;
	public static String adminUrl;
	
	 MongoDBUtilMorphia mongoutil = new MongoDBUtilMorphia();
	 Datastore ds = mongoutil.getMorphiaDatastoreForNlob();
	 Email email=new Email();
	 
	public ChurnPayingMember()
	{
		loginRequired=false;
		logoutRequired=false;
	}
	
	SoftAssert soft=new SoftAssert();
	//-----------------------------------------ENDING THE SUBSCRIPTION---------------------------------------------//
	

	
	@Test(enabled=true, priority=51, groups={"ConfirmationsRuleMailerPositive" , "All"})
	public void churnPayingMemberTC100601()
	{
		log.info("ENDING THE SUBSCRIPTION");
		
		un=Excel.getCellValue(INPUT_PATH, "ChurnPayingMember", 1, 0);    //  <-------------- using the member_paying email		
		pwd=PasswordFromAdmin.gettingPasswordFromAdmin(un);
		adminUn=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINUN");
		adminPwd=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINPWD");
		adminUrl=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINURL");
		
		log.info("Clicking on Login Button");
		HomePage home=new HomePage(driver);
		home.clickLoginLink();
		log.info("Navigating to Login Page");
		
		log.info("Entering login details with username as : '" +un+ "' and password as : '" +pwd+ "'" );
		LoginPage login=new LoginPage(driver);
		login.setEmailId(un);
		login.setPassword(pwd);
		login.clickLoginButton();
		log.info("Navigating to My Account Page");
		
		MyAccountPage acc=new MyAccountPage(driver);
		
		String text= acc.getMySubscription();
		log.info(text);
		String subs=text.substring(22);
		
	   try
	   {
		   
		   if(text.contains("PREMIUM") || text.contains("STANDARD"))
		   {
		   
		   log.info("Subscription is " +subs);
		   
		   log.info("Clicking on End Subscription button");
		   
		   acc.clickToEndSubscription();

		   log.info("Confirming");
		   String actual=driver.findElement(By.xpath("//p[@class='OmUnlimited-p']")).getText();
		   String expected="betala bara 99 kr/mån?";
		   
		   Assert.assertTrue(actual.contains(expected));
		   log.info("TIP: " +expected);
		   
		   EndSubscriptionPage end=new EndSubscriptionPage(driver);
		   end.clickToConfirmEndSubscription();
		   }
		   
		   else
		   {
			   log.info("Subscription is BAS");
			   
			   log.info("Clicking on End Subscription button");
			   acc.clickToEndSubscription();
		   }
		   
	   }
	  
	   catch(Exception e)
	   {
		   e.printStackTrace();
		  
	   }
	
	   
		log.info("Feedback Page");
		EndSubsFeedbackPage feed=new EndSubsFeedbackPage(driver);
		feed.clickFeedbackDropdown();
		feed.selectNoTimeToUse();
		feed.clickToEndButton();
		feed.clickClearButton();
		
		log.info("Subscription Ended");
		home.clickNextoryLogo();
		home.clickAccountLink();
		
		Excel.shiftingRowsUp(INPUT_PATH, "ChurnPayingMember", 1);
		
		if(text.contains("BAS"))
		{
			String act1=driver.findElement(By.xpath("//h3[contains(text(),'konto är avslutat')]")).getText();
			String actTrim1 = act1.replaceAll("\\s+", "");
			String exp1= "BASkontoäravslutat";
			
			Assert.assertEquals(actTrim1, exp1);
			log.info(act1);
		}
		
		else if(text.contains("STANDARD"))
		{
			String act1=driver.findElement(By.xpath("//h3[contains(text(),'konto är avslutat')]")).getText();
			String actTrim1 = act1.replaceAll("\\s+", "");
			String exp1= "STANDARDkontoäravslutat";
			
			Assert.assertEquals(actTrim1, exp1);
			log.info(act1);
		}
		
		else if(text.contains("PREMIUM"))
		{
			String act1=driver.findElement(By.xpath("//h3[contains(text(),'konto är avslutat')]")).getText();
			String actTrim1 = act1.replaceAll("\\s+", "");
			String exp1= "PREMIUMkontoäravslutat";
			
			Assert.assertEquals(actTrim1, exp1);
			log.info(act1);
		}
		
		String customerId= Database.executeQuery("select customerid from customerinfo where email='" +un+ "'"); 
		String runDate= Database.executeQuery("select next_subscription_run_date from customer2subscriptionmap where customerid=" +customerId);
		
		
			String exp= runDate;
			String act=driver.findElement(By.xpath("//h5[contains(text(),'konto är giltigt')]/../..//li[@class='left']")).getText();
			Assert.assertEquals(act, exp);	
			log.info("Expected date for Account access is until : " +exp);
			log.info("Your Default account is valid until : "+act);
	
		
		
		if(acc.activeraIsClickable())
		{
			log.info("Aktivera button is enabled and clickable");
		}
		else
		{
			log.info("Aktivera button is not enabled");
		}
		
		log.info("logging out");
		MyAccountPage account=new MyAccountPage(driver);
		account.clickLogOut();
		
		new WebDriverWait(driver,30).until(ExpectedConditions.titleContains("Ljudböcker & E-böcker - Lyssna & läs gratis i mobilen"));
		
		driver.manage().deleteAllCookies();
		driver.get(adminUrl);
		
		AdminPage admin=new AdminPage(driver);
		admin.setUserName(adminUn);
		admin.setPassword(adminPwd);
		admin.clickLogin();
		admin.clickCustMgmt();
		admin.setEPost(un);
		admin.clickSearch();
		String memberStatus = admin.getMemberType();
		String subsType = admin.getSubsType();
		
			Assert.assertEquals(memberStatus, "MEMBER_PAYING_CANCELLED");
			log.info("Membership Status is: " +memberStatus + " in Admin Site");
			
			if(subs.equalsIgnoreCase("BAS"))
			{
				subs= "BASE";
			}
			
			else if(subs.contains("PREMIUM PLUS"))
			{
				subs="PREMIUM";
			}
			
			Assert.assertEquals(subsType, subs);
			log.info("Subscription Type is: " +subsType+ " in Admin Site");
		
		
		
		admin.clickLogout();
		driver.get(url);
		
		//-------------------------------VALIDATING THE MAIL TRIGGERING-------------------------------------//	  		
		
				log.info("Fetching data from databse MySQL");
				InformationFromBackend info = new InformationFromBackend();
				info.getDataForCustomerInfo("ChurnPayingMember");

				String customerid = InformationFromBackend.result;
				log.info("Customerid selected from Sql is=" + customerid);

				log.info("Fetching data from MongoDB");

				Query query = ds.createQuery(Email.class);
				query.filter("customerid", customerid);
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
					//custList = (Membership) query.get();
				     // if(custList != null)
					//Email emailList=(Email) query1.get();
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
						String expectedSubject="Bekräftelse på uppsägning" ;	
				        String actualSubject=email.getSubject();
				        Assert.assertEquals(actualSubject, expectedSubject);
						log.info("Subject verified successfully");
				     
				   	    log.info("Response verification");
				   	    String expectedResponse="Success";
						String actualResponse=email.getReason();
						Assert.assertEquals(actualResponse,expectedResponse);
						log.info("Response is verified successfully");
						
					log.info("Triggered message verification");
					 String expectedTriggerName="Churn: Paying member" ;	
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
	
	
	
	//--------------------------------CHOOSING TO GO FOR LOWER SUBSCRIPTION RATHER THAN ENDING-------------------------//
	
	
	
	@Test(enabled=true, priority=52, groups={"ConfirmationsRuleMailerPositive" , "All"})
	public void churnPayingMemberTC100602()
	{
		log.info("CHOOSING TO GO FOR LOWER SUBSCRIPTION RATHER THAN ENDING");
		
		un=Excel.getCellValue(INPUT_PATH, "ChurnPayingMember", 1, 0);    //using the member_paying email		
		pwd=PasswordFromAdmin.gettingPasswordFromAdmin(un);
		adminUn=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINUN");
		adminPwd=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINPWD");
		adminUrl=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINURL");
		
		log.info("Clicking on Login Button");
		HomePage home=new HomePage(driver);
		home.clickLoginLink();
		log.info("Navigating to Login Page");
		
		log.info("Entering login details with username as : '" +un+ "' and password as : '" +pwd+ "'" );
		LoginPage login=new LoginPage(driver);
		login.setEmailId(un);
		login.setPassword(pwd);
		login.clickLoginButton();
		log.info("Navigating to My Account Page");
		
		MyAccountPage acc=new MyAccountPage(driver);
		
		String text= acc.getMySubscription();
		log.info(text);
		String subs=text.substring(22);
		
		try
		  {
			  
		   if(text.contains("PREMIUM") || text.contains("STANDARD"))
			   {
				   log.info("Subscription is " +subs);
			   
				   log.info("Clicking on End Subscription button");
				   
				   acc.clickToEndSubscription();

				   log.info("Clicking Ja Tack Button to go for Lower Subscription");
				   EndSubscriptionPage end=new EndSubscriptionPage(driver);
				   end.clickToGoForLowerSubs();
				   end.clickToAcceptForBas();
				   
				   Excel.shiftingRowsUp(INPUT_PATH, "ChurnPayingMember", 1);
				   System.out.println("text: "+text);
				  
				   
				   if(text.contains("PREMIUM PLUS"))
				   {
					   String act2= acc.getMySubscription();
					   String actTrim2= act2.replaceAll("\\s+", "");
					   String exp2= "Duharabonnemanget:PREMIUMPLUS";
					   
					   Assert.assertEquals(actTrim2, exp2);
					   
					   log.info("Subscription Till Next RunDate: " +act2);
				   }
				   
				   else if(text.contains("STANDARD"))
				   {
					   String act1= acc.getMySubscription();
					   String exp1= "Duharabonnemanget:STANDARD";
					   String actTrim1= act1.replaceAll("\\s+","");
					   
					   Assert.assertEquals(actTrim1, exp1);
					   
					   log.info("Subscription Till Next RunDate: " +act1);
				   }
				   
				   else if(text.contains("PREMIUM"))
				   {
					   String act1= acc.getMySubscription();
					   String exp1= "Duharabonnemanget:PREMIUM";
					   String actTrim1= act1.replaceAll("\\s+","");
					  
					   Assert.assertEquals(actTrim1, exp1);
					   
					   log.info("Subscription Till Next RunDate: " +act1);
				   }
				   
				  
				   String customerid= Database.executeQuery("select customerid from customerinfo where email='" +un+ "'"); 
				   String runDate= Database.executeQuery("select next_subscription_run_date from customer2subscriptionmap where customerid=" +customerid);
				   
				
						String exp= runDate;
						String act=acc.getRunDate();
						Assert.assertEquals(act, exp);	
						log.info("Expected date for Next Payment is : " +exp);
						log.info("Actual Next Payment date is : "+act);
						
						String expSub= "BAS:99kr/månad";
						String actSub= acc.getMyOrder();
						String actSubTrim= actSub.replaceAll("\\s+", "");
						Assert.assertEquals(actSubTrim, expSub);
						
						log.info("Next Subscription will be : " +actSub);
					
				   
				 
				   if(acc.avslutaAbonnemangIsClickable() && acc.avbrytIsClickable())
				   {
					   log.info("Cancel Downgrade button is Clickable");
					   log.info("End Subscription button is Clickable");
				   }
				   else
				   {
					   log.info("Cancel Downgrade button or End Subscription button is not clickable");
				   }
			   
			   
			   }
			   
			   else if(text.contains("BAS"))
			   {
				   	log.info("Subscription is BAS");
				   
				   	log.info("Clicking on End Subscription button");
				   
				   	acc.clickToEndSubscription();
				   
				   	log.info("Feedback Page");
					EndSubsFeedbackPage feed=new EndSubsFeedbackPage(driver);
					feed.clickFeedbackDropdown();
					feed.selectNoTimeToUse();
					feed.clickToEndButton();
					feed.clickClearButton();
					log.info("Subscription Ended");
					
					home.clickNextoryLogo();
					home.clickAccountLink();
					
					Excel.shiftingRowsUp(INPUT_PATH, "ChurnPayingMember", 1);
					
					String act1=driver.findElement(By.xpath("//h3[contains(text(),'konto är avslutat')]")).getText();
					String actTrim1= act1.replaceAll("\\s+", "");
					String exp1= "BASkontoäravslutat";
					
					Assert.assertEquals(actTrim1, exp1);
					log.info(act1);
					
					String customerid= Database.executeQuery("select customerid from customerinfo where email='" +un+ "'"); 
					String runDate= Database.executeQuery("select next_subscription_run_date from customer2subscriptionmap where customerid=" +customerid);
					
						String exp= runDate;
						String act=driver.findElement(By.xpath("//h5[contains(text(),'konto är giltigt')]/../..//li[@class='left']")).getText();
						Assert.assertEquals(act, exp);	
						log.info("Expected date for Account access is until : " +exp);
						log.info("Your Default account is valid until : "+act);
			
					
					if(acc.activeraIsClickable())
					{
						log.info("Aktivera button is enabled and clickable");
					}
					else
					{
						log.info("Aktivera button is not enabled");
					}
					
					home.clickNextoryLogo();
					home.clickAccountLink();
					
					log.info("logging out");
					MyAccountPage account=new MyAccountPage(driver);
					account.clickLogOut();
					
					new WebDriverWait(driver,30).until(ExpectedConditions.titleContains("Ljudböcker & E-böcker - Lyssna & läs gratis i mobilen"));
					
					driver.manage().deleteAllCookies();
					driver.get(adminUrl);
					
					AdminPage admin=new AdminPage(driver);
					admin.setUserName(adminUn);
					admin.setPassword(adminPwd);
					admin.clickLogin();
					admin.clickCustMgmt();
					admin.setEPost(un);
					admin.clickSearch();
					String memberStatus = admin.getMemberType();
					String subsType = admin.getSubsType();
					
						Assert.assertEquals(memberStatus, "MEMBER_PAYING_CANCELLED");
						log.info("Membership Status is: " +memberStatus + " in Admin Site");
						
						if(subs.equalsIgnoreCase("BAS"))
						{
							subs= "BASE";
						}
						
						if(subs.equalsIgnoreCase("PREMIUM PLUS"))
						{
							subs="PREMIUM";
						}
						
						Assert.assertEquals(subsType, subs);
						log.info("Subscription Type is: " +subsType+ " in Admin Site");
					
						admin.clickLogout();
						driver.get(url);
			   }
		   
		   }
		   
		 	catch(Exception e)
		   {
			   e.printStackTrace();
		   }
		
		
		//-------------------------------VALIDATING THE MAIL TRIGGERING-------------------------------------//	  		
		
				log.info("Fetching data from databse MySQL");
				InformationFromBackend info = new InformationFromBackend();
				info.getDataForCustomerInfo("ChurnPayingMember");

				String customerid = InformationFromBackend.result;
				log.info("Customerid selected from Sql is=" + customerid);

				log.info("Fetching data from MongoDB");

				Query query = ds.createQuery(Email.class);
				query.filter("customerid", customerid);
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
					//custList = (Membership) query.get();
				     // if(custList != null)
					//Email emailList=(Email) query1.get();
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
						String expectedSubject="Bekräftelse på uppsägning" ;	
				        String actualSubject=email.getSubject();
				        Assert.assertEquals(actualSubject, expectedSubject);
						log.info("Subject verified successfully");
				     
				   	    log.info("Response verification");
				   	    String expectedResponse="Success";
						String actualResponse=email.getReason();
						Assert.assertEquals(actualResponse,expectedResponse);
						log.info("Response is verified successfully");
						
					log.info("Triggered message verification");
					 String expectedTriggerName="Churn: Paying member" ;	
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
	
//---------------------------------------------- NEGATIVE FLOWS ----------------------------------------------//	
	
	@Test(enabled=true, priority=53, groups={"ConfirmationsRuleMailerNegative" , "All"})
	public void churnPayingMemberNoSelection()
	{
		log.info("NOT SELECTING THE REASON FROM DROPDOWN");
		
		un=Excel.getCellValue(INPUT_PATH, "ChurnPayingMember", 1, 0);    //  <-------------- using the member_paying email		
		pwd=PasswordFromAdmin.gettingPasswordFromAdmin(un);
		adminUn=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINUN");
		adminPwd=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINPWD");
		adminUrl=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINURL");
		
		log.info("Clicking on Login Button");
		HomePage home=new HomePage(driver);
		home.clickLoginLink();
		log.info("Navigating to Login Page");
		
		log.info("Entering login details with username as : '" +un+ "' and password as : '" +pwd+ "'" );
		LoginPage login=new LoginPage(driver);
		login.setEmailId(un);
		login.setPassword(pwd);
		login.clickLoginButton();
		log.info("Navigating to My Account Page");
		
		MyAccountPage acc=new MyAccountPage(driver);
		
		String text= acc.getMySubscription();
		log.info(text);
		String subs=text.substring(22);
		
		acc.clickToEndSubscription();
		
		EndSubscriptionPage end=new EndSubscriptionPage(driver);
		
	   try
	   {
		   
		   if(text.contains("PREMIUM") || text.contains("STANDARD"))
		   {
		   
			   log.info("Subscription is " +subs);
			   
			   log.info("Clicking on End Subscription button");
			   
			   
		   
			   
			   end.clickNotToEndSubscription();               					//goes back to Konto Page
			   
			   acc.clickToEndSubscription();									//navigates to End Subscription Page
			   end.clickToConfirmEndSubscription();								//navigates to Feedback Dropdown Page
			   
		   
		   }
		   
		   else
		   {
			   log.info("Subscription is BAS");
			   
			   log.info("Clicking on End Subscription button");
			  
		   }
		   
	   }
	  
	   catch(Exception e)
	   {
		   e.printStackTrace();
		  
	   }
	
	   
		log.info("Feedback Page");
		EndSubsFeedbackPage feed=new EndSubsFeedbackPage(driver);
		
		feed.clickToUndoButton();	
		
		acc.clickToEndSubscription();
		
		if(text.contains("PREMIUM") || text.contains("STANDARD"))
		   {
			end.clickToConfirmEndSubscription();	
		   }
		
		feed.clickToEndButton();
		//feed.clickClearButton();
		
		Alert a1 = driver.switchTo().alert();
		String alertText=a1.getText();
		if(alertText.equalsIgnoreCase("Välj ett alternativ"))
		{
			log.info("Alert box: " + alertText);
		}
		
		else 
		{
			log.info("No Alert Box found for no selection");
		}
		
		a1.accept();
		
		
		feed.clickFeedbackDropdown();
		feed.selectNoTimeToUse();
		feed.clickToEndButton();
		feed.clickClearButton();
		
		log.info("Subscription Ended");
		home.clickNextoryLogo();
		home.clickAccountLink();
		
		Excel.shiftingRowsUp(INPUT_PATH, "ChurnPayingMember", 1);
		
		if(text.contains("BAS"))
		{
			String act1=driver.findElement(By.xpath("//h3[contains(text(),'konto är avslutat')]")).getText();
			String actTrim1 = act1.replaceAll("\\s+", "");
			String exp1= "BASkontoäravslutat";
			
			Assert.assertEquals(actTrim1, exp1);
			log.info(act1);
		}
		
		else if(text.contains("STANDARD"))
		{
			String act1=driver.findElement(By.xpath("//h3[contains(text(),'konto är avslutat')]")).getText();
			String actTrim1 = act1.replaceAll("\\s+", "");
			String exp1= "STANDARDkontoäravslutat";
			
			Assert.assertEquals(actTrim1, exp1);
			log.info(act1);
		}
		
		else if(text.contains("PREMIUM"))
		{
			String act1=driver.findElement(By.xpath("//h3[contains(text(),'konto är avslutat')]")).getText();
			String actTrim1 = act1.replaceAll("\\s+", "");
			String exp1= "PREMIUMkontoäravslutat";
			
			Assert.assertEquals(actTrim1, exp1);
			log.info(act1);
		}
		
		String customerId= Database.executeQuery("select customerid from customerinfo where email='" +un+ "'"); 
		String runDate= Database.executeQuery("select next_subscription_run_date from customer2subscriptionmap where customerid=" +customerId);
		
		
			String exp= runDate;
			String act=driver.findElement(By.xpath("//h5[contains(text(),'konto är giltigt')]/../..//li[@class='left']")).getText();
			Assert.assertEquals(act, exp);	
			log.info("Expected date for Account access is until : " +exp);
			log.info("Your Default account is valid until : "+act);
	
		
		
		if(acc.activeraIsClickable())
		{
			log.info("Aktivera button is enabled and clickable");
		}
		else
		{
			log.info("Aktivera button is not enabled");
		}
		
		log.info("logging out");
		MyAccountPage account=new MyAccountPage(driver);
		account.clickLogOut();
		
		new WebDriverWait(driver,30).until(ExpectedConditions.titleContains("Ljudböcker & E-böcker - Lyssna & läs gratis i mobilen"));
		
		driver.manage().deleteAllCookies();
		driver.get(adminUrl);
		
		AdminPage admin=new AdminPage(driver);
		admin.setUserName(adminUn);
		admin.setPassword(adminPwd);
		admin.clickLogin();
		admin.clickCustMgmt();
		admin.setEPost(un);
		admin.clickSearch();
		String memberStatus = admin.getMemberType();
		String subsType = admin.getSubsType();
		
			Assert.assertEquals(memberStatus, "MEMBER_PAYING_CANCELLED");
			log.info("Membership Status is: " +memberStatus + " in Admin Site");
			
			if(subs.equalsIgnoreCase("BAS"))
			{
				subs= "BASE";
			}
			
			else if(subs.contains("PREMIUM PLUS"))
			{
				subs="PREMIUM";
			}
			
			Assert.assertEquals(subsType, subs);
			log.info("Subscription Type is: " +subsType+ " in Admin Site");
		
		
		
		admin.clickLogout();
		driver.get(url);
		
		
		//-------------------------------VALIDATING THE MAIL TRIGGERING-------------------------------------//	  		
		
				log.info("Fetching data from databse MySQL");
				InformationFromBackend info = new InformationFromBackend();
				info.getDataForCustomerInfo("ChurnPayingMember");

				String customerid = InformationFromBackend.result;
				log.info("Customerid selected from Sql is=" + customerid);

				log.info("Fetching data from MongoDB");

				Query query = ds.createQuery(Email.class);
				query.filter("customerid", customerid);
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
					//custList = (Membership) query.get();
				     // if(custList != null)
					//Email emailList=(Email) query1.get();
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
						String expectedSubject="Bekräftelse på uppsägning" ;	
				        String actualSubject=email.getSubject();
				        Assert.assertEquals(actualSubject, expectedSubject);
						log.info("Subject verified successfully");
				     
				   	    log.info("Response verification");
				   	    String expectedResponse="Success";
						String actualResponse=email.getReason();
						Assert.assertEquals(actualResponse,expectedResponse);
						log.info("Response is verified successfully");
						
					log.info("Triggered message verification");
					 String expectedTriggerName="Churn: Paying member" ;	
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
