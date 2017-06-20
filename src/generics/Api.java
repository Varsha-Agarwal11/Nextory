package generics;

import org.openqa.selenium.WebDriver;

import common.AutomationConstants;

public class Api implements AutomationConstants
{
	public static WebDriver driver;
	public static String apiun;
	public static String apipwd;

	public static String fetchData()
	{
      driver.get("");
      apiun = Excel.getCellValue(APIURL, "Api", 1, 1);
      apipwd = Excel.getCellValue(APIURL, "Api", 1, 2);
      return "";
	}
	public static void clickSurpriseDelight()
	{ }
	public static void clickStartReading()
	{ }
	public static void clickDownloadApp()
	{ }
	public static void clickFindBook()
	{ }
	
	public static void clickBasics()
	{ }
	public static void clickFindGoodBook()
	{ }
	public static void clickOfflineAndSleep()
	{ }
	public static void clickOurDifferentPlans()
	{ }
	public static void clickWhereToRead()
	{ }
	
	public static void clickWelBackNoOffer()
	{ }
	public static void clickSubsDowngrade()
	{ }
	public static void clickSubsUpgrade()
	{ }
	public static void clickChurnCmpgnMem()
	{ }
	public static void clickChurnGiftCardMem()
	{ }
	public static void clickChurnNonPayment()
	{ }
	public static void clickChurnPayingMem()
	{ }
	public static void clickExclusiveNonPayment()
	{ }
	public static void clickCardUpdated()
	{ }
	public static void clickForgotPassword()
	{ }
	
	public static void clickFirstOutreach()
	{ }
	public static void clickSecondOutreach()
	{ }
	public static void clickInactive7Days()
	{ }
	public static void clickWinback()
	{ }
	public static void clickEngagementFiftyBook()
	{ }
	public static void clickEngagementNintyBook()
	{ }
	
	public static void clickProactive1()
	{ }
	public static void clickProactive2()
	{ }
	public static void clickProactive3()
	{ }
	public static void clickCancelTx1()
	{ }
	public static void clickCancelTx2()
	{ }
	public static void clickCancelTx3()
	{ }
	
	public static void clickOrderConfirmations()
	{ }
	public static void clickGC1()
	{ }
	public static void clickGC2()
	{ }
} 