package NegativeFlows;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.PasswordFromAdmin;
import common.SuperTestScript;
import generics.AddDate;
import generics.Database;
import generics.Excel;
import generics.Property;
import pages.AdminPage;
import pages.GiftcardPage;
import pages.HomePage;
import pages.LoginPage;
import pages.MyAccountPage;

public class RedeemGCWithLogin extends SuperTestScript
{
	public static String un;
	public static String pwd;
	public static String kod;
	public static String codePrice;
	public static String adminUn;
	public static String adminPwd;
	public static String adminUrl;
	public static double pricePerDay;
	
	public RedeemGCWithLogin()
	{
		loginRequired=false;
		logoutRequired=false;
	}
	
	@Test(enabled=true, priority=1, groups={"NegativeFlows" , "All"})
	public void redeemGiftCardExistingUserWithLoginPositiveFlow() throws EncryptedDocumentException, InvalidFormatException, IOException
	{
		adminUn=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINUN");
		adminPwd=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINPWD");
		adminUrl=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINURL");
		un=Excel.getCellValue(INPUT_PATH, "MemberPaying" , 1, 0);
		pwd=PasswordFromAdmin.gettingPasswordFromAdmin(un);
		kod=Database.executeQuery("select a.voucher from giftcard_pur_details a left outer join giftcard b on a.giftcardid=b.id where giftcardname='PresenkortNew' and status='ACTIVE' and redeemedby is null and a.validupto>curdate() order by rand() limit 1");
		codePrice= Database.executeQuery("select a.price from giftcard_pur_details a left outer join giftcard b on a.giftcardid=b.id where voucher='" +kod+ "'");
		 
		log.info("Clicking on Login Link");
		HomePage home=new HomePage(driver);
		home.clickLoginLink();
		
		log.info("Login with the details Email as '" +un+ "' and password as '" +pwd+ "'");
		LoginPage login = new LoginPage(driver);
		login.setEmailId(un);
		login.setPassword(pwd);
		login.clickLoginButton();
		
		String initSubs=driver.findElement(By.xpath("//h3[@class='category-h1 dynamic']")).getText();
		String subs=initSubs.substring(22);
		
		home.clickGiftCard();
		
		log.info("Entering the giftcard Code");
		GiftcardPage gc=new GiftcardPage(driver);
		gc.clickRedanKund();
		gc.enterPresentKodExistingUser(kod);
		gc.clickToRedeemAsExisting();
		
		home.clickNextoryLogo();
		home.clickAccountLink();
		
		Excel.shiftingRowsUp(INPUT_PATH, "MemberPaying", 1);
		
		if(initSubs.contains("BAS"))
		{
			pricePerDay= 3.3;                      //<---------------- 99 kr/30 days = 3.3kr/day
			
			String actSubs=driver.findElement(By.xpath("//h3[@class='category-h1 dynamic']")).getText();
			String actSubsTrim=actSubs.replaceAll("\\s+", "");
			String expSubs="Duharabonnemanget:BAS";
			
			Assert.assertEquals(actSubsTrim, expSubs);
			log.info(actSubs);

			String actual = driver
					.findElement(By.xpath("//div[@class='my-account-wrapper clearfix']//li[@class='left']")).getText();

			double result = Double.parseDouble(codePrice);
			int days = (int) Math.round(result / pricePerDay);

			String currentDate = AddDate.currentDate();
			log.info("Current Date is : " + currentDate);

			String expected = AddDate.addingDays(days);

			Assert.assertEquals(actual, expected.trim());
			log.info("Next Payment Date is: " + actual);
		}
		
		else if(initSubs.contains("STANDARD"))
		{
			pricePerDay= 6.633333333333333;                      //<---------------- 199 kr/30 days = 6.633333333333333 kr/day			
			String actSubs=driver.findElement(By.xpath("//h3[@class='category-h1 dynamic']")).getText();
			String actSubsTrim=actSubs.replaceAll("\\s+", "");
			String expSubs="Duharabonnemanget:STANDARD";
			
				Assert.assertEquals(actSubsTrim, expSubs);
				log.info(actSubs);
			
			String actual= driver.findElement(By.xpath("//div[@class='my-account-wrapper clearfix']//li[@class='left']")).getText();
			
			double result=Double.parseDouble(codePrice);
			int days = (int) Math.round(result / pricePerDay);
			 
			String currentDate= AddDate.currentDate();
			log.info("Current Date is : " +currentDate);
				
			String expected= AddDate.addingDays(days);
			
			Assert.assertEquals(actual, expected.trim());
			log.info("Next Payment Date is: " + actual);
		}
		
		else if(initSubs.contains("PREMIUM PLUS"))
		{
			pricePerDay	= 6.6;                      //<---------------- 198 kr/30 days = 6.6kr/day
			
			String actSubs=driver.findElement(By.xpath("//h3[@class='category-h1 dynamic']")).getText();
			String actSubsTrim=actSubs.replaceAll("\\s+", "");
			String expSubs="Duharabonnemanget:PREMIUMPLUS";
			
				Assert.assertEquals(actSubsTrim, expSubs);
				log.info(actSubs);
			
			String actual= driver.findElement(By.xpath("//div[@class='my-account-wrapper clearfix']//li[@class='left']")).getText();
			
			double result=Double.parseDouble(codePrice);
			int days = (int) Math.round(result / pricePerDay);
			 
			String currentDate= AddDate.currentDate();
			log.info("Current Date is : " +currentDate);
				
			String expected= AddDate.addingDays(days);
			
			
				Assert.assertEquals(actual, expected.trim());
				log.info("Next Payment Date is: " +actual);
		}
		
		else if(initSubs.contains("PREMIUM"))
		{
			pricePerDay	= 8.3;                      //<---------------- 249 kr/30 days = 8.3kr/day
			
			String actSubs = driver.findElement(By.xpath("//h3[@class='category-h1 dynamic']")).getText();
			String actSubsTrim = actSubs.replaceAll("\\s+", "");
			String expSubs = "Duharabonnemanget:PREMIUM";

			Assert.assertEquals(actSubsTrim, expSubs);
			log.info(actSubs);

			String actual = driver.findElement(By.xpath("//div[@class='my-account-wrapper clearfix']//li[@class='left']")).getText();

			double result = Double.parseDouble(codePrice);
			int days = (int) Math.round(result / pricePerDay);

			String currentDate = AddDate.currentDate();
			log.info("Current Date is : " + currentDate);

			String expected = AddDate.addingDays(days);

			Assert.assertEquals(actual, expected.trim());
			log.info("Next Payment Date is: " + actual);
		}
		
		log.info("logging out");
		MyAccountPage acc=new MyAccountPage(driver);
		acc.clickLogOut();
		
		new WebDriverWait(driver,30).until(ExpectedConditions.titleContains("Ljudböcker & E-böcker - Ladda ner Gratis Ljudbok/E-bok Online"));
		
		log.info("Validating into Admin Site");
		
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
		
	
			Assert.assertEquals(memberStatus, "MEMBER_GIFTCARD_EXISTING");
			log.info("Membership Status is: " +memberStatus + " in Admin Site");
			
			
			if(subs.contains("BAS"))
			{
				subs= "BASE";
			}
			else if(subs.contains("PREMIUM PLUS"))
			{
				subs= "PREMIUM";
			}
		
			Assert.assertEquals(subsType, subs);
			log.info("Subscription Type is: " +subsType+ " in Admin Site");
		
		
			admin.clickLogout();
			driver.get(url);
	}
	
	@Test(enabled=true, priority=2, groups={"NegativeFlows" , "All"})
	public void redeemGiftCardExistingUserWithInvalidLogin() throws EncryptedDocumentException, InvalidFormatException, IOException
	{
		adminUn=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINUN");
		adminPwd=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINPWD");
		adminUrl=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINURL");
		un=Excel.getCellValue(INPUT_PATH, "MemberPaying" , 1, 0);
		pwd=PasswordFromAdmin.gettingPasswordFromAdmin(un);
		kod=Database.executeQuery("select a.voucher from giftcard_pur_details a left outer join giftcard b on a.giftcardid=b.id where giftcardname='PresenkortNew' and status='ACTIVE' and redeemedby is null and a.validupto>curdate() order by rand() limit 1");
		codePrice= Database.executeQuery("select a.price from giftcard_pur_details a left outer join giftcard b on a.giftcardid=b.id where voucher='" +kod+ "'");
		 
		log.info("Clicking on Login Link");
		HomePage home=new HomePage(driver);
		home.clickLoginLink();
		
		//log.info("Login with the details Email as '" +un+ "' and password as '" +pwd+ "'");
		LoginPage login = new LoginPage(driver);
		login.setEmailId("xyz");
		login.setPassword("123");
		login.clickLoginButton();
		
		log.info("verifying the E-post error message");
		String actEPostMsg=driver.findElement(By.xpath("//form[@id='loginForm']/div[1]/label")).getText();
		Assert.assertEquals(actEPostMsg, "Vänligen fyll i en giltig e-postadress");
		log.info("E-Post message is verified successfully");
		
		log.info("verifying the Password error message");
		String actPwdMsg=driver.findElement(By.xpath("//form[@id='loginForm']/div[2]/label")).getText();
		Assert.assertEquals(actPwdMsg, "Ditt lösenord måste vara mellan 4 och 25 tecken.");
		log.info("Password message is verified successfully");
	}
	
	@Test(enabled=true, priority=3, groups={"NegativeFlows" , "All"})
	public void redeemGiftCardExistingUserWithLoginUsingIncorrectGCcode() throws EncryptedDocumentException, InvalidFormatException, IOException
	{
		adminUn=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINUN");
		adminPwd=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINPWD");
		adminUrl=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINURL");
		un=Excel.getCellValue(INPUT_PATH, "MemberPaying" , 1, 0);
		pwd=PasswordFromAdmin.gettingPasswordFromAdmin(un);
		kod=Database.executeQuery("select a.voucher from giftcard_pur_details a left outer join giftcard b on a.giftcardid=b.id where giftcardname='PresenkortNew' and status='ACTIVE' and redeemedby is null and a.validupto>curdate() order by rand() limit 1");
		codePrice= Database.executeQuery("select a.price from giftcard_pur_details a left outer join giftcard b on a.giftcardid=b.id where voucher='" +kod+ "'");
		 
		log.info("Clicking on Login Link");
		HomePage home=new HomePage(driver);
		home.clickLoginLink();
		
		log.info("Login with the details Email as '" +un+ "' and password as '" +pwd+ "'");
		LoginPage login = new LoginPage(driver);
		login.setEmailId(un);
		login.setPassword(pwd);
		login.clickLoginButton();
		
		String initSubs=driver.findElement(By.xpath("//h3[@class='category-h1 dynamic']")).getText();
		String subs=initSubs.substring(22);
		
		home.clickGiftCard();
		
		log.info("Entering the giftcard Code");
		GiftcardPage gc=new GiftcardPage(driver);
		gc.clickRedanKund();
		gc.enterPresentKodExistingUser("Az");
		gc.clickToRedeemAsExisting();
		
		log.info("verifying the Presentkod error message");
		String actPresentkodMsg=driver.findElement(By.xpath("//div[@class='form-group ']//span[@class='error']")).getText();
		Assert.assertEquals(actPresentkodMsg, "Presentkort är ogiltig");
		log.info("Presentkod message is verified successfully");
	}
}
