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

public class ChurnFreeTrialCampaignMember extends SuperTestScript  
{
	public static String un;
	public static String pwd;
	public static String adminUn;
	public static String adminPwd;
	public static String adminUrl;
	
	
	//Free Camp member=202002  
	//Free Trial member=203002  	
	 MongoDBUtilMorphia mongoutil = new MongoDBUtilMorphia();
	 Datastore ds = mongoutil.getMorphiaDatastoreForNlob();
	 Email email=new Email();
	 
	 
	public ChurnFreeTrialCampaignMember()
	{
		loginRequired=false;
		logoutRequired=false;
	}
	
	@Test(enabled=true, priority=61, groups={"ConfirmationsRuleMailerPositive" , "All"})
	public void churnFreeTrialCampaignMember()
	{
		log.info("ENDING THE SUBSCRIPTION");
		
		un=Excel.getCellValue(INPUT_PATH, "ChurnFreeTrialCampaign", 1, 0);    //using the Free Trial or Free Campaign Member email		
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
		
		String text=driver.findElement(By.xpath("//h3[@class='category-h1 dynamic']")).getText();
		log.info(text);
		String subs=text.substring(22);
		String memTypeCode= Database.executeQuery("select member_type_code from customerinfo where email='" +un+ "'");
		
		if(memTypeCode.equalsIgnoreCase("203002") || memTypeCode.equalsIgnoreCase("202002"))
		{
		
			log.info("Subscription is " +subs);
			log.info("Clicking on End Subscription button");
		  
			MyAccountPage acc=new MyAccountPage(driver);
			acc.clickToEndSubscription();
		
				if(subs.contains("PREMIUM") || subs.contains("STANDARD"))
				{
					EndSubscriptionPage end=new EndSubscriptionPage(driver);
					end.clickToEnd();
				}
		
			log.info("Feedback Page");
			EndSubsFeedbackPage feed=new EndSubsFeedbackPage(driver);
			feed.clickFeedbackDropdown();
			feed.selectNoTimeToUse();
			feed.clickToEndButton();
			feed.clickClearButton();
			
			Excel.shiftingRowsUp(INPUT_PATH, "ChurnFreeTrialCampaign", 1);
		
			log.info("Subscription Ended");
			home.clickNextoryLogo();
			home.clickAccountLink();
		
	
			Assert.assertTrue(acc.activeraIsClickable());
			log.info("'Aktivera' button is enabled and clickable");
			
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

			if(memTypeCode.equalsIgnoreCase("203002"))
			{
				Assert.assertEquals(memberStatus, "NONMEMBER_PREVIOUS_TRIAL");
				log.info("Membership Status is: " +memberStatus + " in Admin Site");
			
				if(subs.equalsIgnoreCase("BAS"))
				{
					subs= "BASE";
				}
				
				else if(subs.equalsIgnoreCase("PREMIUM PLUS"))
				{
					subs="PREMIUM";
				}
			
				Assert.assertEquals(subsType, subs);
				log.info("Subscription Type is: " +subsType+ " in Admin Site");
				
				admin.clickLogout();
				driver.get(url);
			}
		
			else if(memTypeCode.equalsIgnoreCase("202002"))
			{
				Assert.assertEquals(memberStatus, "NONMEMBER_PREVIOUS_CAMPAIGN");
				log.info("Membership Status is: " +memberStatus + " in Admin Site");
			
				if(subs.equalsIgnoreCase("BAS"))
				{
					subs= "BASE";
				}
				
				else if(subs.equalsIgnoreCase("PREMIUM PLUS"))
				{
					subs="PREMIUM";
				}
			
				Assert.assertEquals(subsType, subs);
				log.info("Subscription Type is: " +subsType+ " in Admin Site");
				
				admin.clickLogout();
				driver.get(url);
			}
		
		
		
		else
		{
			log.info("Member type is neither Free Trial Member nor Free Campaign Member");
			Excel.shiftingRowsUp(INPUT_PATH, "ChurnFreeTrialCampaign", 1);
			home.clickNextoryLogo();
			home.clickAccountLink();
			churnFreeTrialCampaignMember();
		}
	}

		log.info("Fetching data from databse MySQL");
		InformationFromBackend info = new InformationFromBackend();
		info.getDataForCustomerInfo("ChurnFreeCampaignMember");

	//-------------------------------VALIDATING THE MAIL TRIGGERING-------------------------------------//	  
		  
		  
		String customerid =  InformationFromBackend.result;
		log.info("Customerid selected from Sql is="+customerid);
		
		 log.info("Fetching data from MongoDB");
		 
		Query query=ds.createQuery(Email.class);
		query.filter("customerid",customerid);
		
		try
		{
			//custList = (Membership) query.get();
		     // if(custList != null)
			//Email emailList=(Email) query1.get();
			ArrayList<Email> emailList=(ArrayList<Email>)query.asList();
			
			 //log.info("Email list="+emailList);
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
				String expectedSubject="Ditt abonnemang har blivit uppsagt" ;	
		        String actualSubject=email.getSubject();
		        Assert.assertEquals(actualSubject, expectedSubject);
				log.info("Subject verified successfully");
		     
		   	    log.info("Response verification");
		   	    String expectedResponse="Success";
				String actualResponse=email.getReason();
				Assert.assertEquals(actualResponse,expectedResponse);
				log.info("Response is verified successfully");
				
				log.info("Triggered message verification");
				String expectedTriggerName = "Churn: Free/Campaign member";
				String actualTriggerName = email.getTriggerName();
				Assert.assertEquals(actualTriggerName, expectedTriggerName);
				log.info("Message Triggered successfully");

				log.info("Mail sent Date verification");
				String expectedupdateddate = AddDate.currentDate();
				Date actualupdateddate = email.getMailsentdate();
				Assert.assertEquals(actualupdateddate, expectedupdateddate);
				log.info("Mail sent date verified successfully");
		   	 
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
		
	}
	
	
	//--------------------------------------------------- NEGATIVE FLOWS ----------------------------------------------------------------------//	
	
		@Test(enabled=true, priority=62, groups={"ConfirmationsRuleMailerNegative" , "All"})
		public void churnFreeTrialCampaignMemberNoSelection()
		{
			log.info("CHURN FREE TRIAL CAMPAIGN MEMBER NO SELECTION AT FEEDBACK DROPDOWN: NEGATIVE FLOW");
			
			un=Excel.getCellValue(INPUT_PATH, "ChurnFreeTrialCampaign", 1, 0);    //using the Free Trial or Free Campaign Member email		
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
			String memTypeCode= Database.executeQuery("select member_type_code from customerinfo where email='" +un+ "'");
			
			if(memTypeCode.equalsIgnoreCase("203002") || memTypeCode.equalsIgnoreCase("202002"))
			{
			
				log.info("Subscription is " +subs);
				log.info("Clicking on End Subscription button");
			  
				
				acc.clickToEndSubscription();
			
				EndSubscriptionPage end=new EndSubscriptionPage(driver);
					if(subs.contains("PREMIUM") || subs.contains("STANDARD"))
					{
						
						end.clickToUndo();									//clicking on the Angra button to go back to account page
						
						acc.clickToEndSubscription();						//clicking on Avsluta abonnemang button again to end the subscription
						
						end.clickToEnd();									//clicking Avsluta button to confirm end subscription.
					}
			
				log.info("Feedback Page");
				
				EndSubsFeedbackPage feed=new EndSubsFeedbackPage(driver);
				
				feed.clickToUndoButton();								// clicking Angra Button on the Feedback Page, and Navigating back to Konto Page
				
				acc.clickToEndSubscription();							// Starts to End the Subscription again from Konto Page
				
				if(subs.contains("PREMIUM") || subs.contains("STANDARD"))
				{
					end.clickToEnd();									//clicking Avsluta button to confirm end subscription.
				}
				
				feed.clickToEndButton();                                             // Clicking on End Button without Input from Dropdown
				
				Alert a1 = driver.switchTo().alert();                                              
				String alertText=a1.getText();
				if(alertText.equalsIgnoreCase("Välj ett alternativ"))				// Validating for the Alert Box with the Suggestion message.
				{
					log.info("Alert box: " + alertText);
				}
				
				else 
				{
					log.info("No Alert Box found for no selection");
				}
				
				a1.accept();
				
				
				feed.clickFeedbackDropdown();										// Continuing with Churning Free Trial/Campaign Member
				feed.selectNoTimeToUse();
				feed.clickToEndButton();
				feed.clickClearButton();
				
				Excel.shiftingRowsUp(INPUT_PATH, "ChurnFreeTrialCampaign", 1);
			
				log.info("Subscription Ended");
				home.clickNextoryLogo();
				home.clickAccountLink();
			
				Assert.assertTrue(acc.activeraIsClickable());
				log.info("'Aktivera' button is enabled and clickable");
			
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

			
				if(memTypeCode.equalsIgnoreCase("203002"))
				{
					Assert.assertEquals(memberStatus, "NONMEMBER_PREVIOUS_TRIAL");
					log.info("Membership Status is: " +memberStatus + " in Admin Site");
				
					if(subs.equalsIgnoreCase("BAS"))
					{
						subs= "BASE";
					}
					
					else if(subs.equalsIgnoreCase("PREMIUM PLUS"))
					{
						subs="PREMIUM";
					}
				
					Assert.assertEquals(subsType, subs);
					log.info("Subscription Type is: " +subsType+ " in Admin Site");
					
					admin.clickLogout();
					driver.get(url);
				}
			
				else if(memTypeCode.equalsIgnoreCase("202002"))
				{
					Assert.assertEquals(memberStatus, "NONMEMBER_PREVIOUS_CAMPAIGN");
					log.info("Membership Status is: " +memberStatus + " in Admin Site");
				
					if(subs.equalsIgnoreCase("BAS"))
					{
						subs= "BASE";
					}
					
					else if(subs.equalsIgnoreCase("PREMIUM PLUS"))
					{
						subs="PREMIUM";
					}
				
					Assert.assertEquals(subsType, subs);
					log.info("Subscription Type is: " +subsType+ " in Admin Site");
					
					admin.clickLogout();
					driver.get(url);
				}
			
			
			
			else
			{
				log.info("Member type is neither Free Trial Member nor Free Campaign Member");
				Excel.shiftingRowsUp(INPUT_PATH, "ChurnFreeTrialCampaign", 1);
				home.clickNextoryLogo();
				home.clickAccountLink();
				churnFreeTrialCampaignMember();
			}
			}
			
			log.info("Fetching data from databse MySQL");
			InformationFromBackend info = new InformationFromBackend();
			info.getDataForCustomerInfo("ChurnFreeCampaignMember");

		//-------------------------------VALIDATING THE MAIL TRIGGERING-------------------------------------//	  
			  
			  
			String customerid =  InformationFromBackend.result;
			log.info("Customerid selected from Sql is="+customerid);
			
			 log.info("Fetching data from MongoDB");
			 
			Query query=ds.createQuery(Email.class);
			query.filter("customerid",customerid);
			
			try
			{
				//custList = (Membership) query.get();
			     // if(custList != null)
				//Email emailList=(Email) query1.get();
				ArrayList<Email> emailList=(ArrayList<Email>)query.asList();
				
				 //log.info("Email list="+emailList);
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
					String expectedSubject="Ditt abonnemang har blivit uppsagt" ;	
			        String actualSubject=email.getSubject();
			        Assert.assertEquals(actualSubject, expectedSubject);
					log.info("Subject verified successfully");
			     
			   	    log.info("Response verification");
			   	    String expectedResponse="Success";
					String actualResponse=email.getReason();
					Assert.assertEquals(actualResponse,expectedResponse);
					log.info("Response is verified successfully");
					
					log.info("Triggered message verification");
					String expectedTriggerName = "Churn: Free/Campaign member";
					String actualTriggerName = email.getTriggerName();
					Assert.assertEquals(actualTriggerName, expectedTriggerName);
					log.info("Message Triggered successfully");

					log.info("Mail sent Date verification");
					String expectedupdateddate = AddDate.currentDate();
					Date actualupdateddate = email.getMailsentdate();
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
	
	