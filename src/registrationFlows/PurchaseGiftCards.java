package registrationFlows;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import common.PasswordFromAdmin;
import common.RandomEmail;
import common.SuperTestScript;
import generics.Database;
import generics.Excel;
import generics.Property;
import pages.AdminPage;
import pages.GiftcardPage;
import pages.HomePage;
import pages.LoginPage;
import pages.MyAccountPage;
import pages.PaymentCardDetailsPage;

public class PurchaseGiftCards extends SuperTestScript
{
	
	public static String fornamn;
	public static String efternamn;
	public static String email;
	public static String cardNumber;
	public static String cvc;
	public static String un;
	public static String pwd;
	public static String month;
	public static String year;
	public static String adminUn;
	public static String adminPwd;
	public static String adminUrl;
	
	SoftAssert soft=new SoftAssert();

	public PurchaseGiftCards()
	{
		loginRequired=false;
		logoutRequired=false;
	}
	
	
//------------------------------------------PURCHASING GIFTCARD AS A NEW USER For One Month------------------------------------//	

@Test(enabled=true, priority=81, groups={"RegistrationsPositive" , "All"})
public void giftCardPurchaseNewUserOneManad() throws InterruptedException
{
	
	log.info("PURCHASING GIFTCARD AS A NEW USER/VISITOR");
	
	fornamn=Excel.getCellValue(INPUT_PATH, "GiftcardOrderConfirmation", 1, 0);
	efternamn=Excel.getCellValue(INPUT_PATH, "GiftcardOrderConfirmation", 1, 1);
	email=RandomEmail.email();
	cardNumber=Excel.getCellValue(INPUT_PATH, "GiftcardOrderConfirmation", 1, 3);
	cvc=Excel.getCellValue(INPUT_PATH, "GiftcardOrderConfirmation", 1, 4);
	month=Excel.getCellValue(INPUT_PATH,"GiftcardOrderConfirmation", 1, 5);
	year=Excel.getCellValue(INPUT_PATH,"GiftcardOrderConfirmation", 1, 6);
	adminUn=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINUN");
	adminPwd=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINPWD");
	adminUrl=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINURL");
	
	log.info("Clicking on Presentkort Link at HomePage");		
	HomePage home=new HomePage(driver);
	home.clickGiftCard();
	
	log.info("Choosing the Gift card to purchase & Entering the Buyer details in customer form");
	GiftcardPage gift=new GiftcardPage(driver);
	gift.clickKopForOneMonth();
	Thread.sleep(2000);
	gift.enterFirstName(fornamn);
	gift.enterLastName(efternamn);
	gift.enterEmailToReceiveCode(email);
	gift.clickFortsattToGetCode();
	
	log.info("Entering the card details as final step");
	
	PaymentCardDetailsPage card=new PaymentCardDetailsPage(driver);
	card.enterCardNumber(cardNumber);
	card.clickExpiryMonthDropdown();
	card.selectExpiryMonth(month);
	card.clickExpiryYearDropdown();
	card.selectExpiryYear(year);
	card.enterCvcNumber(cvc);
	card.clickToGetGiftCard();
	
	String act1= driver.findElement(By.xpath("//h1[@class='category-h1']")).getText();
	String actTrim1= act1.replaceAll("\\s+", "");
	String exp1= "Tackfördittköp";
	Assert.assertEquals(actTrim1, exp1);
	log.info(act1);
	
	String act2= driver.findElement(By.xpath("//h5[@class='orderNumber']")).getText();
	Assert.assertTrue(act2.contains("Ditt ordernummer"));
	log.info(act2);
	
	log.info("Thankyou For Your Purchase / Tack för ditt köp");
	
	home.clickNextoryLogo();
	
	//Excel.shiftingRowsUp(INPUT_PATH, "GiftcardOrderConfirmation", 1);
	
	log.info("VALIDATING INTO ADMIN SITE");

	
	driver.manage().deleteAllCookies();
	driver.get(adminUrl);
	
	AdminPage admin=new AdminPage(driver);
	admin.setUserName(adminUn);
	admin.setPassword(adminPwd);
	admin.clickLogin();
	admin.clickCustMgmt();
	admin.setEPost(email);
	admin.clickSearch();
	String memberStatus = admin.getMemberType();
	

		Assert.assertEquals(memberStatus, "VISITOR_GIFTCARD_BUYER");
		log.info("Membership Status is: " +memberStatus + " in Admin Site");

	admin.clickLogout();
	driver.get(url);
	
}


//------------------------------------------PURCHASING GIFTCARD AS A NEW USER For Three Month------------------------------------//


@Test(enabled=true, priority=82, groups={"RegistrationsPositive" , "All"})
public void giftCardPurchaseNewUserThreeManad() throws InterruptedException
{
	
	log.info("PURCHASING GIFTCARD AS A NEW USER/VISITOR");
	
	fornamn=Excel.getCellValue(INPUT_PATH, "GiftcardOrderConfirmation", 1, 0);
	efternamn=Excel.getCellValue(INPUT_PATH, "GiftcardOrderConfirmation", 1, 1);
	email=RandomEmail.email();
	cardNumber=Excel.getCellValue(INPUT_PATH, "GiftcardOrderConfirmation", 1, 3);
	cvc=Excel.getCellValue(INPUT_PATH, "GiftcardOrderConfirmation", 1, 4);
	month=Excel.getCellValue(INPUT_PATH,"GiftcardOrderConfirmation", 1, 5);
	year=Excel.getCellValue(INPUT_PATH,"GiftcardOrderConfirmation", 1, 6);
	adminUn=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINUN");
	adminPwd=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINPWD");
	adminUrl=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINURL");
	
	log.info("Clicking on Presentkort Link at HomePage");		
	HomePage home=new HomePage(driver);
	home.clickGiftCard();
	
	log.info("Choosing the Gift card to purchase & Entering the Buyer details in customer form");
	GiftcardPage gift=new GiftcardPage(driver);
	gift.clickKopForThreeMonth();
	Thread.sleep(2000);
	gift.enterFirstName(fornamn);
	gift.enterLastName(efternamn);
	gift.enterEmailToReceiveCode(email);
	gift.clickFortsattToGetCode();
	
	log.info("Entering the card details as final step");
	
	PaymentCardDetailsPage card=new PaymentCardDetailsPage(driver);
	card.enterCardNumber(cardNumber);
	card.clickExpiryMonthDropdown();
	card.selectExpiryMonth(month);
	card.clickExpiryYearDropdown();
	card.selectExpiryYear(year);
	card.enterCvcNumber(cvc);
	card.clickToGetGiftCard();
	
	String act1= driver.findElement(By.xpath("//h1[@class='category-h1']")).getText();
	String actTrim1= act1.replaceAll("\\s+", "");
	String exp1= "Tackfördittköp";
	Assert.assertEquals(actTrim1, exp1);
	log.info(act1);
	
	String act2= driver.findElement(By.xpath("//h5[@class='orderNumber']")).getText();
	Assert.assertTrue(act2.contains("Ditt ordernummer"));
	log.info(act2);
	
	log.info("Thankyou For Your Purchase / Tack för ditt köp");
	
	home.clickNextoryLogo();
	
	//Excel.shiftingRowsUp(INPUT_PATH, "GiftcardOrderConfirmation", 1);
	
	log.info("VALIDATING INTO ADMIN SITE");

	
	driver.manage().deleteAllCookies();
	driver.get(adminUrl);
	
	AdminPage admin=new AdminPage(driver);
	admin.setUserName(adminUn);
	admin.setPassword(adminPwd);
	admin.clickLogin();
	admin.clickCustMgmt();
	admin.setEPost(email);
	admin.clickSearch();
	String memberStatus = admin.getMemberType();
	

		Assert.assertEquals(memberStatus, "VISITOR_GIFTCARD_BUYER");
		log.info("Membership Status is: " +memberStatus + " in Admin Site");

	admin.clickLogout();
	driver.get(url);
	
}


//------------------------------------------PURCHASING GIFTCARD AS A NEW USER For Six Month------------------------------------//

@Test(enabled=true, priority=83, groups={"RegistrationsPositive" , "All"})
public void giftCardPurchaseNewUserSixManad() throws InterruptedException
{
	
	log.info("PURCHASING GIFTCARD AS A NEW USER/VISITOR");
	
	fornamn=Excel.getCellValue(INPUT_PATH, "GiftcardOrderConfirmation", 1, 0);
	efternamn=Excel.getCellValue(INPUT_PATH, "GiftcardOrderConfirmation", 1, 1);
	email=RandomEmail.email();
	cardNumber=Excel.getCellValue(INPUT_PATH, "GiftcardOrderConfirmation", 1, 3);
	cvc=Excel.getCellValue(INPUT_PATH, "GiftcardOrderConfirmation", 1, 4);
	month=Excel.getCellValue(INPUT_PATH,"GiftcardOrderConfirmation", 1, 5);
	year=Excel.getCellValue(INPUT_PATH,"GiftcardOrderConfirmation", 1, 6);
	adminUn=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINUN");
	adminPwd=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINPWD");
	adminUrl=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINURL");
	
	log.info("Clicking on Presentkort Link at HomePage");		
	HomePage home=new HomePage(driver);
	home.clickGiftCard();
	
	log.info("Choosing the Gift card to purchase & Entering the Buyer details in customer form");
	GiftcardPage gift=new GiftcardPage(driver);
	gift.clickKopForSixMonth();
	Thread.sleep(2000);
	gift.enterFirstName(fornamn);
	gift.enterLastName(efternamn);
	gift.enterEmailToReceiveCode(email);
	gift.clickFortsattToGetCode();
	
	log.info("Entering the card details as final step");
	
	PaymentCardDetailsPage card=new PaymentCardDetailsPage(driver);
	card.enterCardNumber(cardNumber);
	card.clickExpiryMonthDropdown();
	card.selectExpiryMonth(month);
	card.clickExpiryYearDropdown();
	card.selectExpiryYear(year);
	card.enterCvcNumber(cvc);
	card.clickToGetGiftCard();
	
	String act1= driver.findElement(By.xpath("//h1[@class='category-h1']")).getText();
	String actTrim1= act1.replaceAll("\\s+", "");
	String exp1= "Tackfördittköp";
	Assert.assertEquals(actTrim1, exp1);
	log.info(act1);
	
	String act2= driver.findElement(By.xpath("//h5[@class='orderNumber']")).getText();
	Assert.assertTrue(act2.contains("Ditt ordernummer"));
	log.info(act2);
	
	log.info("Thankyou For Your Purchase / Tack för ditt köp");
	
	home.clickNextoryLogo();
	
	//Excel.shiftingRowsUp(INPUT_PATH, "GiftcardOrderConfirmation", 1);
	
	log.info("VALIDATING INTO ADMIN SITE");

	
	driver.manage().deleteAllCookies();
	driver.get(adminUrl);
	
	AdminPage admin=new AdminPage(driver);
	admin.setUserName(adminUn);
	admin.setPassword(adminPwd);
	admin.clickLogin();
	admin.clickCustMgmt();
	admin.setEPost(email);
	admin.clickSearch();
	String memberStatus = admin.getMemberType();
	

		Assert.assertEquals(memberStatus, "VISITOR_GIFTCARD_BUYER");
		log.info("Membership Status is: " +memberStatus + " in Admin Site");

	admin.clickLogout();
	driver.get(url);
	
}


//----------------------------------PURCHASING GIFTCARD OF ONE MONTH AS A EXISTING USER------------------------------------------//


@Test(enabled=true, priority=84 , groups={"RegistrationsPositive" , "All"})
public void oneMonthGiftCardPurchaseExistingUser() throws InterruptedException
{
	
	log.info("PURCHASING GIFT CARD AS A EXISTING USER");
	
	un=Excel.getCellValue(INPUT_PATH, "PurchaseGiftCard", 1, 0);
	//Database.executeUpdate("update customerinfo set password='76C3123DBEDEC1E13F920DB346B9C5BC' where email='" +un+ "'");
	pwd=PasswordFromAdmin.gettingPasswordFromAdmin(un);
	cardNumber=Excel.getCellValue(INPUT_PATH, "PurchaseGiftCard", 1, 1);
	cvc=Excel.getCellValue(INPUT_PATH, "PurchaseGiftCard", 1, 2);
	month=Excel.getCellValue(INPUT_PATH, "PurchaseGiftCard", 1, 3);
	year=Excel.getCellValue(INPUT_PATH, "PurchaseGiftCard", 1, 4);
	adminUn=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINUN");
	adminPwd=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINPWD");
	adminUrl=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINURL");
	
	log.info("Navigating to Login Page");
	HomePage home=new HomePage(driver);
	home.clickLoginLink();
	
	log.info("Entering Login Details");
	LoginPage login=new LoginPage(driver);
	login.setEmailId(un);
	login.setPassword(pwd);
	login.clickLoginButton();
	
	String initSubs = driver.findElement(By.xpath("//div[@class='my-account-wrapper clearfix']//h3[@class='category-h1 dynamic']")).getText();
	String subs=initSubs.substring(22);
	
	log.info("Clicking on Presentkort Link");
	home.clickGiftCard();
	
	log.info("Choosing the gift card to purchase");
	GiftcardPage gift=new GiftcardPage(driver);
	gift.clickKopForOneMonth();
	Thread.sleep(2000);
	gift.clickFortsattToGetCode();
	
	log.info("Filling Payment Card Details as Final Step");
	PaymentCardDetailsPage card=new PaymentCardDetailsPage(driver);
	card.enterCardNumber(cardNumber);
	card.clickExpiryMonthDropdown();
	card.selectExpiryMonth(month);
	card.clickExpiryYearDropdown();
	card.selectExpiryYear(year);
	card.enterCvcNumber(cvc);
	card.clickToGetGiftCard();
	
	String act1= driver.findElement(By.xpath("//h1[@class='category-h1']")).getText();
	String actTrim1= act1.replaceAll("\\s+", "");
	String exp1= "Tackfördittköp";
	Assert.assertEquals(actTrim1, exp1);
	log.info(act1);
	
	String act2= driver.findElement(By.xpath("//h5[@class='orderNumber']")).getText();
	Assert.assertTrue(act2.contains("Ditt ordernummer"));
	log.info(act2);
	
	
	log.info("Thankyou For Your Purchase / Tack för ditt köp");
	
	home.clickNextoryLogo();
	home.clickAccountLink();
	
	log.info("logging out");
	MyAccountPage account=new MyAccountPage(driver);
	account.clickLogOut();
	
	Excel.shiftingRowsUp(INPUT_PATH, "PurchaseGiftCard", 1);
	
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
	
	String memTypeCode= Database.executeQuery("select member_type_code from customerinfo  where email='" +un+ "'");
	
	if(memTypeCode.equalsIgnoreCase("304001"))                 //<------------------------------- Purchased giftcard by Member Paying     
	{
		
		Assert.assertEquals(memberStatus, "MEMBER_PAYING");
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
		
	}
	
	else if(memTypeCode.equalsIgnoreCase("203002"))				//<------------------------------- Purchased giftcard by Free Trial Member     
	{
		Assert.assertEquals(memberStatus, "FREE_TRIAL_MEMBER");
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
		
	}
	
	else if(memTypeCode.equalsIgnoreCase("202002"))				//<------------------------------- Purchased giftcard by Free Campaign Member     
	{
		Assert.assertEquals(memberStatus, "FREE_CAMPAIGN_MEMBER");
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
		
	}
	

	admin.clickLogout();
	driver.get(url);
	
}


//----------------------------------PURCHASING GIFTCARD OF THREE MONTH AS A EXISTING USER------------------------------------------//

@Test(enabled=true, priority=86 , groups={"RegistrationsPositive" , "All"})
public void threeMonthGiftCardPurchaseExistingUser() throws InterruptedException
{
	
	log.info("PURCHASING GIFT CARD AS A EXISTING USER");
	
	un=Excel.getCellValue(INPUT_PATH, "PurchaseGiftCard", 1, 0);
	//Database.executeUpdate("update customerinfo set password='76C3123DBEDEC1E13F920DB346B9C5BC' where email='" +un+ "'");
	pwd=PasswordFromAdmin.gettingPasswordFromAdmin(un);
	cardNumber=Excel.getCellValue(INPUT_PATH, "PurchaseGiftCard", 1, 1);
	cvc=Excel.getCellValue(INPUT_PATH, "PurchaseGiftCard", 1, 2);
	month=Excel.getCellValue(INPUT_PATH, "PurchaseGiftCard", 1, 3);
	year=Excel.getCellValue(INPUT_PATH, "PurchaseGiftCard", 1, 4);
	adminUn=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINUN");
	adminPwd=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINPWD");
	adminUrl=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINURL");
	
	log.info("Navigating to Login Page");
	HomePage home=new HomePage(driver);
	home.clickLoginLink();
	
	log.info("Entering Login Details");
	LoginPage login=new LoginPage(driver);
	login.setEmailId(un);
	login.setPassword(pwd);
	login.clickLoginButton();
	
	String initSubs = driver.findElement(By.xpath("//div[@class='my-account-wrapper clearfix']//h3[@class='category-h1 dynamic']")).getText();
	String subs=initSubs.substring(22);
	
	log.info("Clicking on Presentkort Link");
	home.clickGiftCard();
	
	log.info("Choosing the gift card to purchase");
	GiftcardPage gift=new GiftcardPage(driver);
	gift.clickKopForThreeMonth();
	Thread.sleep(2000);
	gift.clickFortsattToGetCode();
	
	log.info("Filling Payment Card Details as Final Step");
	PaymentCardDetailsPage card=new PaymentCardDetailsPage(driver);
	card.enterCardNumber(cardNumber);
	card.clickExpiryMonthDropdown();
	card.selectExpiryMonth(month);
	card.clickExpiryYearDropdown();
	card.selectExpiryYear(year);
	card.enterCvcNumber(cvc);
	card.clickToGetGiftCard();
	
	String act1= driver.findElement(By.xpath("//h1[@class='category-h1']")).getText();
	String actTrim1= act1.replaceAll("\\s+", "");
	String exp1= "Tackfördittköp";
	Assert.assertEquals(actTrim1, exp1);
	log.info(act1);
	
	String act2= driver.findElement(By.xpath("//h5[@class='orderNumber']")).getText();
	Assert.assertTrue(act2.contains("Ditt ordernummer"));
	log.info(act2);
	
	
	log.info("Thankyou For Your Purchase / Tack för ditt köp");
	
	home.clickNextoryLogo();
	home.clickAccountLink();
	
	log.info("logging out");
	MyAccountPage account=new MyAccountPage(driver);
	account.clickLogOut();
	
	Excel.shiftingRowsUp(INPUT_PATH, "PurchaseGiftCard", 1);
	
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
	
	String memTypeCode= Database.executeQuery("select member_type_code from customerinfo  where email='" +un+ "'");
	
	if(memTypeCode.equalsIgnoreCase("304001"))                 //<------------------------------- Purchased giftcard by Member Paying     
	{
		
		Assert.assertEquals(memberStatus, "MEMBER_PAYING");
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
		
	}
	
	else if(memTypeCode.equalsIgnoreCase("203002"))				//<------------------------------- Purchased giftcard by Free Trial Member     
	{
		Assert.assertEquals(memberStatus, "FREE_TRIAL_MEMBER");
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
		
	}
	
	else if(memTypeCode.equalsIgnoreCase("202002"))				//<------------------------------- Purchased giftcard by Free Campaign Member     
	{
		Assert.assertEquals(memberStatus, "FREE_CAMPAIGN_MEMBER");
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
		
	}
	

	admin.clickLogout();
	driver.get(url);
	
}


//----------------------------------PURCHASING GIFTCARD OF SIX MONTH AS A EXISTING USER------------------------------------------//

@Test(enabled=true, priority=87 , groups={"RegistrationsPositive" , "All"})
public void sixMonthGiftCardPurchaseExistingUser() throws InterruptedException
{
	
	log.info("PURCHASING GIFT CARD AS A EXISTING USER");
	
	un=Excel.getCellValue(INPUT_PATH, "PurchaseGiftCard", 1, 0);
	//Database.executeUpdate("update customerinfo set password='76C3123DBEDEC1E13F920DB346B9C5BC' where email='" +un+ "'");
	pwd=PasswordFromAdmin.gettingPasswordFromAdmin(un);
	cardNumber=Excel.getCellValue(INPUT_PATH, "PurchaseGiftCard", 1, 1);
	cvc=Excel.getCellValue(INPUT_PATH, "PurchaseGiftCard", 1, 2);
	month=Excel.getCellValue(INPUT_PATH, "PurchaseGiftCard", 1, 3);
	year=Excel.getCellValue(INPUT_PATH, "PurchaseGiftCard", 1, 4);
	adminUn=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINUN");
	adminPwd=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINPWD");
	adminUrl=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINURL");
	
	log.info("Navigating to Login Page");
	HomePage home=new HomePage(driver);
	home.clickLoginLink();
	
	log.info("Entering Login Details");
	LoginPage login=new LoginPage(driver);
	login.setEmailId(un);
	login.setPassword(pwd);
	login.clickLoginButton();
	
	String initSubs = driver.findElement(By.xpath("//div[@class='my-account-wrapper clearfix']//h3[@class='category-h1 dynamic']")).getText();
	String subs=initSubs.substring(22);
	
	log.info("Clicking on Presentkort Link");
	home.clickGiftCard();
	
	log.info("Choosing the gift card to purchase");
	GiftcardPage gift=new GiftcardPage(driver);
	gift.clickKopForSixMonth();
	Thread.sleep(2000);
	gift.clickFortsattToGetCode();
	
	log.info("Filling Payment Card Details as Final Step");
	PaymentCardDetailsPage card=new PaymentCardDetailsPage(driver);
	card.enterCardNumber(cardNumber);
	card.clickExpiryMonthDropdown();
	card.selectExpiryMonth(month);
	card.clickExpiryYearDropdown();
	card.selectExpiryYear(year);
	card.enterCvcNumber(cvc);
	card.clickToGetGiftCard();
	
	String act1= driver.findElement(By.xpath("//h1[@class='category-h1']")).getText();
	String actTrim1= act1.replaceAll("\\s+", "");
	String exp1= "Tackfördittköp";
	Assert.assertEquals(actTrim1, exp1);
	log.info(act1);
	
	String act2= driver.findElement(By.xpath("//h5[@class='orderNumber']")).getText();
	Assert.assertTrue(act2.contains("Ditt ordernummer"));
	log.info(act2);
	
	
	log.info("Thankyou For Your Purchase / Tack för ditt köp");
	
	home.clickNextoryLogo();
	home.clickAccountLink();
	
	log.info("logging out");
	MyAccountPage account=new MyAccountPage(driver);
	account.clickLogOut();
	
	Excel.shiftingRowsUp(INPUT_PATH, "PurchaseGiftCard", 1);
	
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
	
	String memTypeCode= Database.executeQuery("select member_type_code from customerinfo  where email='" +un+ "'");
	
	if(memTypeCode.equalsIgnoreCase("304001"))                 //<------------------------------- Purchased giftcard by Member Paying     
	{
		
		Assert.assertEquals(memberStatus, "MEMBER_PAYING");
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
		
	}
	
	else if(memTypeCode.equalsIgnoreCase("203002"))				//<------------------------------- Purchased giftcard by Free Trial Member     
	{
		Assert.assertEquals(memberStatus, "FREE_TRIAL_MEMBER");
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
		
	}
	
	else if(memTypeCode.equalsIgnoreCase("202002"))				//<------------------------------- Purchased giftcard by Free Campaign Member     
	{
		Assert.assertEquals(memberStatus, "FREE_CAMPAIGN_MEMBER");
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
		
	}
	

	admin.clickLogout();
	driver.get(url);
	
}


//----------------------------------------------- NEGATIVE FLOWS --------------------------------------------------------//


@Test(enabled=true, priority=88, groups={"RegistrationsNegative" , "All"})
public void invalidInputsNewUserPurchase() throws InterruptedException
{
	log.info("PURCHASING GIFTCARD AS A NEW USER/VISITOR: INVALID INPUTS (NEGATIVE)");
	
	fornamn=Excel.getCellValue(INPUT_PATH, "GiftcardOrderConfirmation", 1, 0);
	efternamn=Excel.getCellValue(INPUT_PATH, "GiftcardOrderConfirmation", 1, 1);
	email=RandomEmail.email();
	cardNumber=Excel.getCellValue(INPUT_PATH, "GiftcardOrderConfirmation", 1, 3);
	cvc=Excel.getCellValue(INPUT_PATH, "GiftcardOrderConfirmation", 1, 4);
	month=Excel.getCellValue(INPUT_PATH,"GiftcardOrderConfirmation", 1, 5);
	year=Excel.getCellValue(INPUT_PATH,"GiftcardOrderConfirmation", 1, 6);
	adminUn=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINUN");
	adminPwd=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINPWD");
	adminUrl=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINURL");
	
	log.info("Clicking on Presentkort Link at HomePage");		
	HomePage home=new HomePage(driver);
	home.clickGiftCard();
	
	log.info("Choosing the Gift card to purchase & Entering the Buyer details in customer form");
	GiftcardPage gift=new GiftcardPage(driver);
	gift.clickKopForOneMonth();
	Thread.sleep(2000);
	gift.clickFortsattToGetCode();
	
//	String fornamnErrMsg= driver.findElement(By.xpath("")).getText();
//	String fornamnErrMsgTrim= fornamnErrMsg.replaceAll("\\s+", "");
	
	String actErrMsg= "Vänligenfyllidettafält.";
	
//	soft.assertEquals(actErrMsg, fornamnErrMsgTrim, "ERROR MESSAGE DOESNOT MATCH IN THE FORNAMN TEXTBOX WHILE PROVIDING GIFTCARD BUYER INFO");
	
	String efterErrMsg=driver.findElement(By.xpath("//label[@for='lastname']")).getText();
	String efterErrMsgTrim= efterErrMsg.replaceAll("\\s+", "");
	
	soft.assertEquals(actErrMsg, efterErrMsgTrim, "ERROR MESSAGE DOESNOT MATCH IN THE EFTERNAMN TEXTBOX WHILE PROVIDING GIFTCARD BUYER INFO");
	log.info("ERROR MESSAGE AT EFTERNAMN TEXTBOX: "+efterErrMsg);
	
	
	String emailErrMsg=driver.findElement(By.xpath("//label[@for='email']")).getText();
	String emailErrMsgTrim= emailErrMsg.replaceAll("\\s+", "");
	
	soft.assertEquals(actErrMsg, emailErrMsgTrim, "ERROR MESSAGE DOESNOT MATCH IN THE EPOSTADDRESS TEXTBOX WHILE PROVIDING GIFTCARD BUYER INFO");
	log.info("ERROR MESSAGE AT E-POSTADRESS TEXTBOX: "+ emailErrMsg);
	
	
	gift.enterFirstName(fornamn);
	gift.enterLastName("se");
	gift.enterEmailToReceiveCode(email);
	gift.clickFortsattToGetCode();
	
//	String actCharMsg= driver.findElement(By.xpath("//label[@for='lastname']")).getText();
//	String actCharMsgTrim= actCharMsg.replaceAll("\\s+", "");
//	
//	String expCharMsg= "Pleaseenteratleast3characters.";
//	
//	soft.assertEquals(actCharMsgTrim, expCharMsg, "Please enter at least 3 characters. ERROR MSG NOT MATCHED");
	
	driver.findElement(By.xpath("//input[@placeholder='Efternamn']")).clear();
	
	gift.enterLastName(efternamn);
	gift.clickFortsattToGetCode();
	
	
	log.info("ENTERING THE WRONG CARD DETAILS");
	
	PaymentCardDetailsPage card=new PaymentCardDetailsPage(driver);
	card.enterCardNumber("5235323021701737");
	card.clickExpiryMonthDropdown();
	card.selectExpiryMonth("01");
	card.clickExpiryYearDropdown();
	card.selectExpiryYear("21");
	card.enterCvcNumber("000");
	card.clickToGetGiftCard();
	
	card.clickProvaIgen();
	
	log.info("ENTERING THE RIGHT CARD DETAILS");
	card.enterCardNumber(cardNumber);
	card.clickExpiryMonthDropdown();
	card.selectExpiryMonth(month);
	card.clickExpiryYearDropdown();
	card.selectExpiryYear(year);
	card.enterCvcNumber(cvc);
	card.clickToGetGiftCard();
	
	String act1= driver.findElement(By.xpath("//h1[@class='category-h1']")).getText();
	String actTrim1= act1.replaceAll("\\s+", "");
	String exp1= "Tackfördittköp";
	Assert.assertEquals(actTrim1, exp1);
	log.info(act1);
	
	String act2= driver.findElement(By.xpath("//h5[@class='orderNumber']")).getText();
	Assert.assertTrue(act2.contains("Ditt ordernummer"));
	log.info(act2);
	
	log.info("Thankyou For Your Purchase / Tack för ditt köp");
	
	home.clickNextoryLogo();
	
	//Excel.shiftingRowsUp(INPUT_PATH, "GiftcardOrderConfirmation", 1);
	
	log.info("VALIDATING INTO ADMIN SITE");

	
	driver.manage().deleteAllCookies();
	driver.get(adminUrl);
	
	AdminPage admin=new AdminPage(driver);
	admin.setUserName(adminUn);
	admin.setPassword(adminPwd);
	admin.clickLogin();
	admin.clickCustMgmt();
	admin.setEPost(email);
	admin.clickSearch();
	String memberStatus = admin.getMemberType();
	

		Assert.assertEquals(memberStatus, "VISITOR_GIFTCARD_BUYER");
		log.info("Membership Status is: " +memberStatus + " in Admin Site");

	admin.clickLogout();
	driver.get(url);	
}



//----------------------------------CHECKING CANCEL BUTTONS--------------------------------------------------//
@Test(enabled=true, priority=89, groups={"RegistrationsNegative" , "All"})
public void checkingCancelButtons() throws InterruptedException
{
log.info("PURCHASING GIFTCARD AS A NEW USER/VISITOR");
	
	fornamn=Excel.getCellValue(INPUT_PATH, "GiftcardOrderConfirmation", 1, 0);
	efternamn=Excel.getCellValue(INPUT_PATH, "GiftcardOrderConfirmation", 1, 1);
	email=RandomEmail.email();
	cardNumber=Excel.getCellValue(INPUT_PATH, "GiftcardOrderConfirmation", 1, 3);
	cvc=Excel.getCellValue(INPUT_PATH, "GiftcardOrderConfirmation", 1, 4);
	month=Excel.getCellValue(INPUT_PATH,"GiftcardOrderConfirmation", 1, 5);
	year=Excel.getCellValue(INPUT_PATH,"GiftcardOrderConfirmation", 1, 6);
	adminUn=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINUN");
	adminPwd=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINPWD");
	adminUrl=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINURL");
	
	log.info("Clicking on Presentkort Link at HomePage");		
	HomePage home=new HomePage(driver);
	home.clickGiftCard();
	
	log.info("Choosing the Gift card to purchase & Entering the Buyer details in customer form");
	
	GiftcardPage gift=new GiftcardPage(driver);
	gift.clickKopForOneMonth();
	
	Thread.sleep(2000);
	try{
	gift.clickStangToCloseKopForm();
	
	
	Thread.sleep(2000);
	//new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("btn-one"))));
	gift.clickKopForOneMonth();

	
	Thread.sleep(2000);
	//new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//input[@value='3']"))));
	
	
	gift.clickRadioForThreeMonth();
	gift.enterFirstName(fornamn);
	gift.enterLastName(efternamn);
	gift.enterEmailToReceiveCode(email);
	gift.clickFortsattToGetCode();
	}
	
	catch(Exception e)
	{
		e.printStackTrace();
	}
	driver.navigate().back();                                     //navigating back to presentkort page from the payment page
	
	String actTitle= driver.getTitle();
	String expTitle= "Presentkort för ljudböcker och e-böcker på nätet";
	soft.assertEquals(actTitle, expTitle, "TITLE DOES NOT MATCH ON PRESENTKORT PAGE");
	
	gift.clickKopForOneMonth();
	Thread.sleep(2000);
	gift.clickRadioForSixMonth();
	gift.enterFirstName(fornamn);
	gift.enterLastName(efternamn);
	gift.enterEmailToReceiveCode(email);
	gift.clickFortsattToGetCode();
	
	log.info("Entering the card details as final step");
	
	PaymentCardDetailsPage card=new PaymentCardDetailsPage(driver);
	card.enterCardNumber(cardNumber);
	card.clickExpiryMonthDropdown();
	card.selectExpiryMonth(month);
	card.clickExpiryYearDropdown();
	card.selectExpiryYear(year);
	card.enterCvcNumber(cvc);
	card.clickToGetGiftCard();
	
	String act1= driver.findElement(By.xpath("//h1[@class='category-h1']")).getText();
	String actTrim1= act1.replaceAll("\\s+", "");
	String exp1= "Tackfördittköp";
	Assert.assertEquals(actTrim1, exp1);
	log.info(act1);
	
	String act2= driver.findElement(By.xpath("//h5[@class='orderNumber']")).getText();
	Assert.assertTrue(act2.contains("Ditt ordernummer"));
	log.info(act2);
	
	log.info("Thankyou For Your Purchase / Tack för ditt köp");
	
	driver.navigate().back();
	
	String actText=driver.findElement(By.xpath("//p//strong")).getText();
	String actTextTrim=actText.replaceAll("\\s+", "");
	String expText="Gåtillbakatillvårförstasidagenomatt.";
	soft.assertEquals(actTextTrim, expText, "Gå tillbaka till vår första sida genom att. TEXT DOES NOT MATCH");
	
	driver.findElement(By.xpath("//p//strong//a")).click();
	
	String actHomeTitle= driver.getTitle();
	String expHomeTitle= "Ljudböcker & E-böcker - Lyssna & läs gratis i mobilen";
	soft.assertEquals(actHomeTitle, expHomeTitle, "TITLE DOES NOT MATCH ON HOME PAGE ");
	
	//checking if the 	Prova Gratis i 14 dagar   button is enabled or not
	driver.findElement(By.xpath("//header[@class='welcomeText']/a[@class='blueButton cta']")).isEnabled();
	
log.info("VALIDATING INTO ADMIN SITE");

	
	driver.manage().deleteAllCookies();
	driver.get(adminUrl);
	
	AdminPage admin=new AdminPage(driver);
	admin.setUserName(adminUn);
	admin.setPassword(adminPwd);
	admin.clickLogin();
	admin.clickCustMgmt();
	admin.setEPost(email);
	admin.clickSearch();
	String memberStatus = admin.getMemberType();
	

		Assert.assertEquals(memberStatus, "VISITOR_GIFTCARD_BUYER");
		log.info("Membership Status is: " +memberStatus + " in Admin Site");

	admin.clickLogout();
	driver.get(url);
	
}



@Test(enabled=true, priority=90, groups={"RegistrationsNegative" , "All"})
public void invalidInputsExisitingUserPurchase() throws InterruptedException
{
	log.info("PURCHASING GIFT CARD AS A EXISTING USER");
 
	un=Excel.getCellValue(INPUT_PATH, "PurchaseGiftCard", 1, 0);
	//Database.executeUpdate("update customerinfo set password='76C3123DBEDEC1E13F920DB346B9C5BC' where email='" +un+ "'");
	pwd=PasswordFromAdmin.gettingPasswordFromAdmin(un);
	cardNumber=Excel.getCellValue(INPUT_PATH, "PurchaseGiftCard", 1, 1);
	cvc=Excel.getCellValue(INPUT_PATH, "PurchaseGiftCard", 1, 2);
	month=Excel.getCellValue(INPUT_PATH, "PurchaseGiftCard", 1, 3);
	year=Excel.getCellValue(INPUT_PATH, "PurchaseGiftCard", 1, 4);
	adminUn=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINUN");
	adminPwd=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINPWD");
	adminUrl=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINURL");
 
 MyAccountPage acc = new MyAccountPage(driver);
 
 log.info("Navigating to Login Page");
 HomePage home=new HomePage(driver);
 home.clickLoginLink();
 
 log.info("Entering Login Details");
 LoginPage login=new LoginPage(driver);
 login.setEmailId(un);
 login.setPassword(pwd);
 login.clickLoginButton();
 
 String initSubs = acc.getMySubscription();
 String subs=initSubs.substring(22);
 
 log.info("Clicking on Presentkort Link");
 home.clickGiftCard();
 
 log.info("Choosing the gift card to purchase");
 GiftcardPage gift=new GiftcardPage(driver);
 gift.clickKopForOneMonth();
 Thread.sleep(2000);
 gift.clickStangToCloseKopForm();
 Thread.sleep(3000);
 gift.clickKopForOneMonth();
 Thread.sleep(2000);
 gift.clickFortsattToGetCode();
 
 log.info("ENTERING THE WRONG CARD DETAILS");
 
 PaymentCardDetailsPage card=new PaymentCardDetailsPage(driver);
 card.enterCardNumber("5235323021701737");
 card.clickExpiryMonthDropdown();
 card.selectExpiryMonth("01");
 card.clickExpiryYearDropdown();
 card.selectExpiryYear("21");
 card.enterCvcNumber("000");
 card.clickToGetGiftCard();
 
 card.clickProvaIgen();
 
 log.info("ENTERING THE RIGHT CARD DETAILS");
 card.enterCardNumber(cardNumber);
 card.clickExpiryMonthDropdown();
 card.selectExpiryMonth(month);
 card.clickExpiryYearDropdown();
 card.selectExpiryYear(year);
 card.enterCvcNumber(cvc);
 card.clickToGetGiftCard();
 
 String act1= driver.findElement(By.xpath("//h1[@class='category-h1']")).getText();
 String actTrim1= act1.replaceAll("\\s+", "");
 String exp1= "Tackfördittköp";
 Assert.assertEquals(actTrim1, exp1);
 log.info(act1);
 
 String act2= driver.findElement(By.xpath("//h5[@class='orderNumber']")).getText();
 Assert.assertTrue(act2.contains("Ditt ordernummer"));
 log.info(act2);
 
 
 log.info("Thankyou For Your Purchase / Tack för ditt köp");
 
 home.clickNextoryLogo();
 home.clickAccountLink();
 
 log.info("logging out");
 MyAccountPage account=new MyAccountPage(driver);
 account.clickLogOut();
 
 Excel.shiftingRowsUp(INPUT_PATH, "PurchaseGiftCard", 1);
 
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
 
 String memTypeCode= Database.executeQuery("select member_type_code from customerinfo  where email='" +un+ "'");
 
 if(memTypeCode.equalsIgnoreCase("304001"))                 //<------------------------------- Purchased giftcard by Member Paying     
 {
  
  Assert.assertEquals(memberStatus, "MEMBER_PAYING");
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
  
 }
 
 else if(memTypeCode.equalsIgnoreCase("203002"))    //<------------------------------- Purchased giftcard by Free Trial Member     
 {
  Assert.assertEquals(memberStatus, "FREE_TRIAL_MEMBER");
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
  
 }
 
 else if(memTypeCode.equalsIgnoreCase("202002"))    //<------------------------------- Purchased giftcard by Free Campaign Member     
 {
  Assert.assertEquals(memberStatus, "FREE_CAMPAIGN_MEMBER");
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
  
 }
 

 admin.clickLogout();
 driver.get(url);
 
}
}
