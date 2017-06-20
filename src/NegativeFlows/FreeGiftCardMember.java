package NegativeFlows;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.SuperTestScript;
import generics.Excel;
import generics.Property;
import pages.AdminPage;
import pages.GiftcardPage;
import pages.HomePage;
import pages.PaymentCardDetailsPage;

public class FreeGiftCardMember extends SuperTestScript
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

	public FreeGiftCardMember()
	{
		loginRequired=false;
		logoutRequired=false;
	}
	
//------------------------------------------PURCHASING GIFTCARD AS A NEW USER------------------------------------//	

@Test(enabled=true, priority=1, groups={"NegativeFlows","All"})
public void freeGiftCardMemberPositiveFlow() throws InterruptedException
{
	
	log.info("PURCHASING GIFTCARD AS A 'NEW USER/VISITOR' ");
	
	fornamn=Excel.getCellValue(INPUT_PATH, "GiftcardOrderConfirmation", 1, 0);
	efternamn=Excel.getCellValue(INPUT_PATH, "GiftcardOrderConfirmation", 1, 1);
	email=Excel.getCellValue(INPUT_PATH, "GiftcardOrderConfirmation", 1, 2);
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
	
	Excel.shiftingRowsUp(INPUT_PATH, "GiftcardOrderConfirmation", 1);
	
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
  
@Test(enabled=true, priority=2, groups={"NegativeFlows","All"})
public void freeGiftCardIncorrectEntryInCustomerFormPage() throws InterruptedException
{
	log.info("PURCHASING GIFTCARD AS A NEW USER/VISITOR");
	
	fornamn=Excel.getCellValue(INPUT_PATH, "GiftcardOrderConfirmation", 1, 0);
	efternamn=Excel.getCellValue(INPUT_PATH, "GiftcardOrderConfirmation", 1, 1);
	email=Excel.getCellValue(INPUT_PATH, "GiftcardOrderConfirmation", 1, 2);
	
	log.info("Clicking on Presentkort Link at HomePage");		
	HomePage home=new HomePage(driver);
	home.clickGiftCard();
	
	log.info("Choosing the Gift card to purchase & Entering the Buyer details in customer form");
	GiftcardPage gift=new GiftcardPage(driver);
	gift.clickKopForOneMonth();
	Thread.sleep(2000);
	gift.enterFirstName(fornamn);
	gift.enterLastName("");
	gift.enterEmailToReceiveCode("nextory");
	gift.clickFortsattToGetCode();
	
	String actualLastNameMsg=driver.findElement(By.xpath("//label[@for='lastname' and text()='Vänligen fyll i detta fält.']")).getText();
	Assert.assertEquals(actualLastNameMsg,"Vänligen fyll i detta fält.");
	log.info("Cant leave the 'Efternamn' field blank and at least 3 characters need to be entered");
	
	String actualEmailMsg=driver.findElement(By.xpath("//label[@for='email' and text()='Vänligen fyll i en giltig e-postadress.']")).getText();
	Assert.assertEquals(actualEmailMsg,"Vänligen fyll i en giltig e-postadress.");
	log.info("Cant leave the 'Epost' field blank and it should be in standard format");

	home.clickNextoryLogo();
  }

@Test(enabled=true, priority=3, groups={"NegativeFlows","All"})
public void FreeGiftCardIncorrectEntryInPaymentPage() throws InterruptedException
{
	
	log.info("PURCHASING GIFTCARD AS A NEW USER/VISITOR");
	
	fornamn=Excel.getCellValue(INPUT_PATH, "GiftcardOrderConfirmation", 1, 0);
	efternamn=Excel.getCellValue(INPUT_PATH, "GiftcardOrderConfirmation", 1, 1);
	email=Excel.getCellValue(INPUT_PATH, "GiftcardOrderConfirmation", 1, 2);
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
	card.enterCvcNumber("563");
	card.clickToGetGiftCard();

	log.info("Failure page is displayed with message 'Oj, din kortregistrering avbröts!' ");
	driver.findElement(By.xpath("//button[text()='Prova igen']")).click();
	log.info("Navigates back to the PaymentCardDetailsPage");
	home.clickNextoryLogo();
  }
}
