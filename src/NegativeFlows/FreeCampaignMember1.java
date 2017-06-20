package NegativeFlows;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import common.SuperTestScript;
import generics.AddDate;
import generics.Excel;
import generics.Property;
import pages.AdminPage;
import pages.CampaignPage;
import pages.CustomerFormPage;
import pages.HomePage;
import pages.MyAccountPage;
import pages.NewSubscriptionPage;
import pages.PaymentCardDetailsPage;

public class FreeCampaignMember1 extends SuperTestScript
{
	public static String campCode;
	public static String newId;
	public static String confirm;
	public static String newPswd;
	public static String cardNumber;
	public static String cvc;
	public static String fn;
	public static String ln;
	public static String cellNum;
	public static String existingId;
	public static String existingPswd;
	public static String month;
	public static String year;
	public static String adminUn;
	public static String adminPwd;
	public static String adminUrl;
	
	
	public FreeCampaignMember1()
	{
	loginRequired=false;
	logoutRequired=false;
	}
	
	@Test(enabled=true, priority=1 ,groups={"NegativeFlows" ,"All"})
	public void freeCampaignMemberPositiveFlow() throws EncryptedDocumentException, InvalidFormatException, IOException
	{
		log.info("REDEEMING CAMPAIGN AS A NEW USER");
		
		campCode=Excel.getCellValue(INPUT_PATH,"CampaignCodes", 1, 0);
		newId=Excel.getCellValue(INPUT_PATH, "NewEmail", 1, 1);
		confirm=Excel.getCellValue(INPUT_PATH, "NewEmail", 1, 1);
		newPswd=Excel.getCellValue(INPUT_PATH, "NewEmail", 1, 2);
		cardNumber=Excel.getCellValue(INPUT_PATH, "NewEmail", 1, 3);
		cvc=Excel.getCellValue(INPUT_PATH, "NewEmail", 1, 4);
		month=Excel.getCellValue(INPUT_PATH,"NewEmail", 1, 5);
		year=Excel.getCellValue(INPUT_PATH,"NewEmail", 1, 6);
		adminUn=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINUN");
		adminPwd=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINPWD");
		adminUrl=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINURL");
		
		fn="automation";
		ln="test";
		cellNum="1234567890";
		
		
		log.info("Clicking on Kampanjkod link in home page");
		HomePage home=new HomePage(driver);
		home.clickCampaign();  
		log.info("Navigating to the Campaign Page");
		
		log.info("Filling the campaign code and user details");
		log.info("USERNAME: '" +newId+ "' PASSWORD: '" +newPswd+ "' CampCode: '" +campCode+ "'");
		CampaignPage camp=new CampaignPage(driver);
		camp.enterCampaignCode(campCode);
		camp.enterEmailId(newId);
		camp.enterConfirmMailId(confirm);
		camp.enterPassword(newPswd);
		camp.clickToContinue();
		log.info("Navigating to Subscription Page");
		
		log.info("Choosing the Subscription");
		NewSubscriptionPage chose=new NewSubscriptionPage(driver);
		chose.clickBasSub();
		chose.clickContinue();
		log.info("Navigating to Payment page");
		
		log.info("Entering Payment Details");
		PaymentCardDetailsPage card=new PaymentCardDetailsPage(driver);
		
	
			String act1 = driver.findElement(By.xpath("//div[@class='col-xs-12 col-sm-6 col-lg-5 usp__headers']//h3[1]//span[@class='samewidth']")).getText();
			String actTrim1= act1.replaceAll("\\s+", "");
			Assert.assertEquals(actTrim1, "30dagar");
			
			String act2= driver.findElement(By.xpath("//div[@class='col-xs-12 col-sm-6 col-lg-5 usp__headers']//h3[2]//span[@class='samewidth']")).getText();
			String actTrim2= act2.replaceAll("\\s+", "");
			Assert.assertEquals(actTrim2, "99kr");
			log.info("Making payment where Your Offer / Ditt erbjudande: Gratis i " +act1+ " and Pris/mån efter gratisperioden: " +act2);
	
		
		card.enterCardNumber(cardNumber);
		card.clickExpiryMonthDropdown();
		card.selectExpiryMonth(month);
		card.clickExpiryYearDropdown();
		card.selectExpiryYear(year);
		card.enterCvcNumber(cvc);
		card.clickPaymentSubmit();
		
		log.info("Filling the customer details");
		CustomerFormPage cust= new CustomerFormPage(driver);
		cust.enterFirstName(fn);
		cust.enterLastName(ln);
		cust.enterMobileNumber(cellNum);
		cust.clickContinue();
		log.info("Campaign code Successfully redeemed for BAS");
		
		log.info("Navigating to customer info page");
		Excel.shiftingRowsDown(INPUT_PATH, "ExistingEmail", 2);
		Excel.setExcelData(INPUT_PATH, "ExistingEmail", 2, 1, newId);
		Excel.setExcelData(INPUT_PATH, "ExistingEmail", 2, 2, newPswd);
		Excel.setExcelData(INPUT_PATH, "ExistingEmail", 2, 3, "BAS");
		Excel.setExcelData(INPUT_PATH, "ExistingEmail", 2, 4, "FREE CAMPAIGN MEMBER");
		Excel.setExcelData(INPUT_PATH, "ExistingEmail", 2, 5, campCode);
		Excel.setExcelData(INPUT_PATH, "ExistingEmail", 2, 6, cardNumber);
		Excel.setExcelData(INPUT_PATH, "ExistingEmail", 2, 7, month);
		Excel.setExcelData(INPUT_PATH, "ExistingEmail", 2, 8, year);
		Excel.setExcelData(INPUT_PATH, "ExistingEmail", 2, 9, cvc);
		
		//Excel.ShiftingRowsUp(INPUT_PATH, "Email", 2);
		//Excel.shiftingRowsUp(INPUT_PATH, "CampaignCodes",1);
		Excel.shiftingRowsUp(INPUT_PATH, "NewEmail", 1);
		
		home.clickNextoryLogo();
		home.clickAccountLink();
		
		String text=driver.findElement(By.xpath("//h3[@class='category-h1 dynamic']")).getText();
		String subs=text.substring(22);
		
		
			String actualSub = driver.findElement(By.xpath("//div[@class='my-account-wrapper clearfix']//li[@class='right']")).getText();
			String actualSubtrim= actualSub.replaceAll("\\s+", "");
			String exp1 = "BAS:99kr/månad";
			Assert.assertEquals(actualSubtrim, exp1);
			log.info("Order is " +actualSub);
			
			String currentDate= AddDate.currentDate();
			log.info("Current Date is : " +currentDate);
			
			String expected= AddDate.addingDays(30);
			String actual= driver.findElement(By.xpath("//div[@class='my-account-wrapper clearfix']//li[@class='left']")).getText();
			
			Assert.assertEquals(actual, expected.trim());
			log.info("Next Payment Date is: " +actual);
			
			String act=driver.findElement(By.xpath("//div[@class='my-account-wrapper']//li[@class='right']")).getText();
			String actTrim= act.replaceAll("\\s+", "");
			Assert.assertTrue(actTrim.contains("0.0kr"));	
			
			log.info("Recent Payment: " +act);
		
		
		MyAccountPage acc = new MyAccountPage(driver);
		acc.clickLogOut();

		new WebDriverWait(driver, 30).until(
				ExpectedConditions.titleContains("Ljudböcker & E-böcker - Ladda ner Gratis Ljudbok/E-bok Online"));

		log.info("VALIDATING INTO ADMIN SITE");

		driver.manage().deleteAllCookies();
		driver.get(adminUrl);

		AdminPage admin = new AdminPage(driver);
		admin.setUserName(adminUn);
		admin.setPassword(adminPwd);
		admin.clickLogin();
		admin.clickCustMgmt();
		admin.setEPost(newId);
		admin.clickSearch();
		String memberStatus = admin.getMemberType();
		String subsType = admin.getSubsType();
	
			Assert.assertEquals(memberStatus, "FREE_CAMPAIGN_MEMBER");
			log.info("Membership Status is: " +memberStatus + " in Admin Site");
			
			if(subs.equalsIgnoreCase("BAS"))
			{
				subs= "BASE";
			}
			Assert.assertEquals(subsType, subs);
			log.info("Subscription Type is: " +subsType+ " in Admin Site");
		
		admin.clickLogout();
		driver.get(url);
	
	}
	
	@Test(enabled=true, priority=2 ,groups={"NegativeFlows" ,"All"})
	public void freeCampaignMemberUsingConsumedVoucherCode() throws EncryptedDocumentException, InvalidFormatException, IOException
	{
		log.info("REDEEMING CAMPAIGN AS A NEW USER");
		
		campCode="219AMW";
		newId=Excel.getCellValue(INPUT_PATH, "ExistingEmail", 1, 1);
		confirm=Excel.getCellValue(INPUT_PATH, "ExistingEmail", 1, 1);
		newPswd=Excel.getCellValue(INPUT_PATH, "ExistingEmail", 1, 2);
		
		log.info("Clicking on Kampanjkod link in home page");
		HomePage home=new HomePage(driver);
		home.clickCampaign();  
		log.info("Navigating to the Campaign Page");
		
		log.info("Filling the campaign code and user details");
		log.info("USERNAME: '" +newId+ "' PASSWORD: '" +newPswd+ "' CampCode: '" +campCode+ "'");
		CampaignPage camp=new CampaignPage(driver);
		camp.enterCampaignCode(campCode);
		camp.enterEmailId(newId);
		camp.enterConfirmMailId(confirm);
		camp.enterPassword(newPswd);
		camp.clickToContinue();
		
		String actualErrorMsg=driver.findElement(By.id("campcode.errors")).getText();
		Assert.assertEquals(actualErrorMsg, "Du har skrivit in fel kod i fältet.");
		log.info("Using already used voucher code of free campaign that is="+campCode);
	}
	
	@Test(enabled=true, priority=3 ,groups={"NegativeFlows" ,"All"})
	public void freeCampaignMemberPaymentFailure() throws EncryptedDocumentException, InvalidFormatException, IOException
	{
		log.info("REDEEMING CAMPAIGN AS A NEW USER");
		
		campCode=Excel.getCellValue(INPUT_PATH,"CampaignCodes", 1, 0);
		newId=Excel.getCellValue(INPUT_PATH, "NewEmail", 1, 1);
		confirm=Excel.getCellValue(INPUT_PATH, "NewEmail", 1, 1);
		newPswd=Excel.getCellValue(INPUT_PATH, "NewEmail", 1, 2);
		cardNumber=Excel.getCellValue(INPUT_PATH, "NewEmail", 1, 3);
		cvc="455";
		month=Excel.getCellValue(INPUT_PATH,"NewEmail", 1, 5);
		year=Excel.getCellValue(INPUT_PATH,"NewEmail", 1, 6);
		adminUn=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINUN");
		adminPwd=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINPWD");
		adminUrl=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINURL");
		
		log.info("Clicking on Kampanjkod link in home page");
		HomePage home=new HomePage(driver);
		home.clickCampaign();  
		log.info("Navigating to the Campaign Page");
		
		log.info("Filling the campaign code and user details");
		log.info("USERNAME: '" +newId+ "' PASSWORD: '" +newPswd+ "' CampCode: '" +campCode+ "'");
		CampaignPage camp=new CampaignPage(driver);
		camp.enterCampaignCode(campCode);
		camp.enterEmailId(newId);
		camp.enterConfirmMailId(confirm);
		camp.enterPassword(newPswd);
		camp.clickToContinue();
		log.info("Navigating to Subscription Page");
		
		log.info("Choosing the Subscription");
		NewSubscriptionPage chose=new NewSubscriptionPage(driver);
		chose.clickBasSub();
		chose.clickContinue();
		log.info("Navigating to Payment page");
		
		log.info("Entering Payment Details");
		PaymentCardDetailsPage card=new PaymentCardDetailsPage(driver);
		
	
			String act1 = driver.findElement(By.xpath("//div[@class='col-xs-12 col-sm-6 col-lg-5 usp__headers']//h3[1]//span[@class='samewidth']")).getText();
			String actTrim1= act1.replaceAll("\\s+", "");
			Assert.assertEquals(actTrim1, "30dagar");
			
			String act2= driver.findElement(By.xpath("//div[@class='col-xs-12 col-sm-6 col-lg-5 usp__headers']//h3[2]//span[@class='samewidth']")).getText();
			String actTrim2= act2.replaceAll("\\s+", "");
			Assert.assertEquals(actTrim2, "99kr");
			log.info("Making payment where Your Offer / Ditt erbjudande: Gratis i " +act1+ " and Pris/mån efter gratisperioden: " +act2);
	
		card.enterCardNumber(cardNumber);
		card.clickExpiryMonthDropdown();
		card.selectExpiryMonth(month);
		card.clickExpiryYearDropdown();
		card.selectExpiryYear(year);
		card.enterCvcNumber(cvc);
		card.clickPaymentSubmit();
		
		home.clickNextoryLogo();
		
		Excel.shiftingRowsUp(INPUT_PATH, "NewEmail", 1);
	}
	//-------------------------Invalid Password ie less than minimum characters to be inputted---------------------------
	@Test(enabled=true, priority=4 ,groups={"NegativeFlows","All"})
	public void freeCampaignMemberEntryingPasswordLengthLessThanMin() throws EncryptedDocumentException, InvalidFormatException, IOException
	{
		log.info("REDEEMING CAMPAIGN AS A NEW USER");
		
		campCode=Excel.getCellValue(INPUT_PATH,"CampaignCodes", 1, 0);
		newId=Excel.getCellValue(INPUT_PATH, "NewEmail", 1, 1);
		confirm=Excel.getCellValue(INPUT_PATH, "NewEmail", 1, 1);
		newPswd="123";
		
		log.info("Clicking on Kampanjkod link in home page");
		HomePage home=new HomePage(driver);
		home.clickCampaign();  
		log.info("Navigating to the Campaign Page");
		
		log.info("Filling the campaign code and user details");
		log.info("USERNAME: '" +newId+ "' PASSWORD: '" +newPswd+ "' CampCode: '" +campCode+ "'");
		CampaignPage camp=new CampaignPage(driver);
		camp.enterCampaignCode(campCode);
		camp.enterEmailId(newId);
		camp.enterConfirmMailId(confirm);
		camp.enterPassword(newPswd);
		camp.clickToContinue();
		
		String actual = driver.findElement(By.xpath("//label[text()='Ditt lösenord måste vara mellan 4 och 25 tecken.']")).getText();
		Assert.assertEquals(actual, "Ditt lösenord måste vara mellan 4 och 25 tecken.");
		log.info("'Ditt lösenord måste vara mellan 4 och 25 tecken' message is being displayed while entering password less than the minimum of 4 characters");
 }
	
	//------------------------------------------Invalid Confirm E-postadress--------------------------------------
	
	@Test(enabled=true, priority=5 ,groups={"NegativeFlows","All"})
	public void freeCampaignMemberEntryingDifferentEmailId() throws EncryptedDocumentException, InvalidFormatException, IOException
	{
		log.info("REDEEMING CAMPAIGN AS A NEW USER");
		
		campCode=Excel.getCellValue(INPUT_PATH,"CampaignCodes", 1, 0);
		newId=Excel.getCellValue(INPUT_PATH, "NewEmail", 1, 1);
		confirm=Excel.getCellValue(INPUT_PATH, "NewEmail", 1, 1);
		newPswd="123";
		
		log.info("Clicking on Kampanjkod link in home page");
		HomePage home=new HomePage(driver);
		home.clickCampaign();  
		log.info("Navigating to the Campaign Page");
		
		log.info("Filling the campaign code and user details");
		log.info("USERNAME: '" +newId+ "' PASSWORD: '" +newPswd+ "' CampCode: '" +campCode+ "'");
		CampaignPage camp=new CampaignPage(driver);
		camp.enterCampaignCode(campCode);
		camp.enterEmailId(newId);
		camp.enterConfirmMailId("nextory12345@frescano.se");
		camp.enterPassword(newPswd);
		camp.clickToContinue();
		
		log.info("Verifying the duplicate email id being typed");
		String actual = driver.findElement(By.xpath("//label[text()='E-post stämmer ej överens.']")).getText();
		Assert.assertEquals(actual, "E-post stämmer ej överens.");
		log.info("Verified the duplicate email id");
 }
}