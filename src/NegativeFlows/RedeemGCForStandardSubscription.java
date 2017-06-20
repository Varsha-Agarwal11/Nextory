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
import generics.Database;
import generics.Excel;
import generics.Property;
import pages.AdminPage;
import pages.GiftcardPage;
import pages.HomePage;
import pages.MyAccountPage;
import pages.NewSubscriptionPage;

public class RedeemGCForStandardSubscription extends SuperTestScript
{
	public static String un;
	public static String pwd;
	public static String kod;
	public static String codePrice;
	public static String adminUn;
	public static String adminPwd;
	public static String adminUrl;
	public static double pricePerDay;
	
	public RedeemGCForStandardSubscription()
	   {
		   loginRequired=false;
		   logoutRequired=false;
	   }
	
	//---------------------------------Redeeming GiftCard For Standard Subscription-----------------------------------//

		@Test(enabled=true, priority=1, groups={"NegativeFlows" , "All"})
		public void redeemGiftCardNewUserPositiveFlow() throws InterruptedException, EncryptedDocumentException, InvalidFormatException, IOException
		{
		adminUn = Property.getPropertyValue(CONFIG_PATH + CONFIG_FILE, "ADMINUN");
		adminPwd = Property.getPropertyValue(CONFIG_PATH + CONFIG_FILE, "ADMINPWD");
		adminUrl = Property.getPropertyValue(CONFIG_PATH + CONFIG_FILE, "ADMINURL");
		un = Excel.getCellValue(INPUT_PATH, "NewEmail", 1, 1);
		pwd = Excel.getCellValue(INPUT_PATH, "NewEmail", 1, 2);
		kod = Database.executeQuery(
				"select a.voucher from giftcard_pur_details a left outer join giftcard b on a.giftcardid=b.id where giftcardname='PresenkortNew' and status='ACTIVE' and redeemedby is null and a.validupto>curdate() order by rand() limit 1");
		codePrice = Database.executeQuery(
				"select a.price from giftcard_pur_details a left outer join giftcard b on a.giftcardid=b.id where voucher='"
						+ kod + "'");

		pricePerDay = 6.633333333333333; // <---------------- 199 kr/30 days =
											// 6.633333333333333 kr/day

		log.info("REDEEMING GIFTCARD FOR STANDARD SUBSCRIPTION");

		log.info("Clicking on Presentkort Link");
		HomePage home = new HomePage(driver);
		home.clickGiftCard();

		log.info("Clicking on 'Ny Kund' button");
		GiftcardPage gc = new GiftcardPage(driver);
		gc.clickNyKund();
		Thread.sleep(2000);

		log.info("Entering the Giftcard code and the Email as '" + un + "' and password as '" + pwd + "'");
		gc.enterPresentkodNewUser(kod);
		gc.enterNewEmail(un);
		gc.verifyNewEmail(un);
		gc.enterNewPassword(pwd);
		gc.clickToRedeemAsNew();

		log.info("Choosing Subscriptions");
		NewSubscriptionPage subs = new NewSubscriptionPage(driver);
		subs.clickStandardSub();
		subs.clickContinue();

		String act1 = driver.findElement(By.xpath("//div[@class='presentKortPopUp']/h2")).getText();
		String actTrim1 = act1.replaceAll("\\s+", "");
		String exp1 = "STANDARD";

		Assert.assertEquals(actTrim1, exp1);
		log.info("PresentKort Subscription :" + act1);

		String act2 = driver.findElement(By.xpath("//div[@class='presentKortPopUp']/h3")).getText();
		String actTrim2 = act2.replaceAll("\\s+", "");
		String exp2 = "199kr/mån";

		Assert.assertEquals(actTrim2, exp2);
		log.info("Presentkort Price : " + act2);

		gc.clickPopUpFortsatt();

		home.clickNextoryLogo();
		home.clickAccountLink();

		Excel.shiftingRowsDown(INPUT_PATH, "ExistingEmail", 2);
		Excel.setExcelData(INPUT_PATH, "ExistingEmail", 2, 1, un);
		Excel.setExcelData(INPUT_PATH, "ExistingEmail", 2, 2, pwd);
		Excel.setExcelData(INPUT_PATH, "ExistingEmail", 2, 3, "STANDARD");
		Excel.setExcelData(INPUT_PATH, "ExistingEmail", 2, 4, "FREE_GIFTCARD_NOCARDINFO");
		Excel.setExcelData(INPUT_PATH, "ExistingEmail", 2, 5, kod);

		Excel.shiftingRowsUp(INPUT_PATH, "NewEmail", 1);

		String actSubs = driver.findElement(By.xpath("//h3[@class='category-h1 dynamic']")).getText();
		String actSubsTrim = actSubs.replaceAll("\\s+", "");
		String expSubs = "Duharabonnemanget:STANDARD";

		Assert.assertEquals(actSubsTrim, expSubs);
		log.info(actSubs);

		String actual = driver.findElement(By.xpath("//div[@class='my-account-wrapper clearfix']//li[@class='left']"))
				.getText();

		double result = Double.parseDouble(codePrice);
		int days = (int) Math.round(result / pricePerDay);

		String currentDate = AddDate.currentDate();
		log.info("Current Date is : " + currentDate);

		String expected = AddDate.addingDays(days);

		Assert.assertEquals(actual, expected.trim());
		log.info("Next Payment Date is: " + actual);

		log.info("logging out");
		MyAccountPage acc = new MyAccountPage(driver);
		acc.clickLogOut();

		new WebDriverWait(driver, 30).until(
				ExpectedConditions.titleContains("Ljudböcker & E-böcker - Ladda ner Gratis Ljudbok/E-bok Online"));

		log.info("Validating into Admin Site");

		driver.manage().deleteAllCookies();
		driver.get(adminUrl);

		AdminPage admin = new AdminPage(driver);
		admin.setUserName(adminUn);
		admin.setPassword(adminPwd);
		admin.clickLogin();
		admin.clickCustMgmt();
		admin.setEPost(un);
		admin.clickSearch();
		String memberStatus = admin.getMemberType();
		String subsType = admin.getSubsType();

		Assert.assertEquals(memberStatus, "FREE_GIFTCARD_NOCARDINFO");
		log.info("Membership Status is: " + memberStatus + " in Admin Site");

		Assert.assertEquals(subsType, "STANDARD");
		log.info("Subscription Type is: " + subsType + " in Admin Site");

		admin.clickLogout();
		driver.get(url);
		}
		

		@Test(enabled=true, priority=2, groups={"NegativeFlows" , "All"})
		public void redeemGCNewUserEntryingIncorrectDetails() throws InterruptedException, EncryptedDocumentException, InvalidFormatException, IOException
		{
		adminUn = Property.getPropertyValue(CONFIG_PATH + CONFIG_FILE, "ADMINUN");
		adminPwd = Property.getPropertyValue(CONFIG_PATH + CONFIG_FILE, "ADMINPWD");
		adminUrl = Property.getPropertyValue(CONFIG_PATH + CONFIG_FILE, "ADMINURL");
		un = Excel.getCellValue(INPUT_PATH, "NewEmail", 1, 1);
		pwd = Excel.getCellValue(INPUT_PATH, "NewEmail", 1, 2);
		kod = Database.executeQuery(
				"select a.voucher from giftcard_pur_details a left outer join giftcard b on a.giftcardid=b.id where giftcardname='PresenkortNew' and status='ACTIVE' and redeemedby is null and a.validupto>curdate() order by rand() limit 1");
		codePrice = Database.executeQuery(
				"select a.price from giftcard_pur_details a left outer join giftcard b on a.giftcardid=b.id where voucher='"
						+ kod + "'");

		pricePerDay = 6.633333333333333; // <---------------- 199 kr/30 days =
											// 6.633333333333333 kr/day

		log.info("REDEEMING GIFTCARD FOR STANDARD SUBSCRIPTION");

		log.info("Clicking on Presentkort Link");
		HomePage home = new HomePage(driver);
		home.clickGiftCard();

		log.info("Clicking on 'Ny Kund' button");
		GiftcardPage gc = new GiftcardPage(driver);
		gc.clickNyKund();
		Thread.sleep(2000);

		log.info("Entering the Giftcard code and the Email as '" + un + "' and password as '" + pwd + "'");
		gc.enterPresentkodNewUser("");
		gc.enterNewEmail(un);
		gc.verifyNewEmail(un);
		gc.enterNewPassword("11");
		gc.clickToRedeemAsNew();

		log.info("Leaving the giftcard code blank");
		String actualGCMsg = driver.findElement(By.xpath("//label[text()='Vänligen fyll i din presentkod']")).getText();
		Assert.assertEquals(actualGCMsg, "Vänligen fyll i din presentkod");
		log.info("Actual GC Msg=" + actualGCMsg);

		log.info("Entering password less than the required digits");
		String actualPasswordMsg = driver
				.findElement(By.xpath("//label[text()='Ditt lösenord måste vara mellan 4 och 25 tecken.']")).getText();
		Assert.assertEquals(actualPasswordMsg, "Ditt lösenord måste vara mellan 4 och 25 tecken.");
		log.info("ActualMsg=" + actualPasswordMsg);
		}
	//-----------------------------------------Redeem Gc with different email id's-------------------------------	
		@Test(enabled=true, priority=3, groups={"NegativeFlows" , "All"})
		public void redeemGiftCardNewUserWithDifferentEmailIds() throws InterruptedException, EncryptedDocumentException, InvalidFormatException, IOException
		{
		adminUn = Property.getPropertyValue(CONFIG_PATH + CONFIG_FILE, "ADMINUN");
		adminPwd = Property.getPropertyValue(CONFIG_PATH + CONFIG_FILE, "ADMINPWD");
		adminUrl = Property.getPropertyValue(CONFIG_PATH + CONFIG_FILE, "ADMINURL");
		un = Excel.getCellValue(INPUT_PATH, "NewEmail", 1, 1);
		pwd = Excel.getCellValue(INPUT_PATH, "NewEmail", 1, 2);
		kod = Database.executeQuery(
				"select a.voucher from giftcard_pur_details a left outer join giftcard b on a.giftcardid=b.id where giftcardname='PresenkortNew' and status='ACTIVE' and redeemedby is null and a.validupto>curdate() order by rand() limit 1");
		codePrice = Database.executeQuery(
				"select a.price from giftcard_pur_details a left outer join giftcard b on a.giftcardid=b.id where voucher='"
						+ kod + "'");

		pricePerDay = 6.633333333333333; // <---------------- 199 kr/30 days =
											// 6.633333333333333 kr/day

		log.info("REDEEMING GIFTCARD FOR STANDARD SUBSCRIPTION");

		log.info("Clicking on Presentkort Link");
		HomePage home = new HomePage(driver);
		home.clickGiftCard();

		log.info("Clicking on 'Ny Kund' button");
		GiftcardPage gc = new GiftcardPage(driver);
		gc.clickNyKund();
		Thread.sleep(2000);

		log.info("Entering the Giftcard code and the Email as '" + un + "' and password as '" + pwd + "'");
		gc.enterPresentkodNewUser(kod);
		gc.enterNewEmail(un);
		gc.verifyNewEmail("nextory12345@frescano.se");
		gc.enterNewPassword(pwd);
		gc.clickToRedeemAsNew();
		
		log.info("Verifying the duplicate email id being typed");
		String actual = driver.findElement(By.xpath("//label[text()='E-post stämmer ej överens.']")).getText();
		Assert.assertEquals(actual, "E-post stämmer ej överens.");
		log.info("Verified the duplicate email id");
	 }
}
