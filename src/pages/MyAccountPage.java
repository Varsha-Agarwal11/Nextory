package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import common.BasePage;

public class MyAccountPage extends BasePage				
{

	@FindBy(xpath="//a[@class='custom-logga-utt']")
	private WebElement Loggaut;
	
	@FindBy(xpath="//button[@class='btn button big greyButton']")
	private WebElement Activera;
	
	@FindBy(xpath="//button[@class='blueButton flex upgrade']")
	private WebElement UpgradeButton;
	
	@FindBy(xpath="//button[@class='btn btn-default greyButton button big updateCardBtn']")
	private WebElement Gåvidare;
	
	@FindBy(xpath="//div[@class='buttonRow']//button[@class='btn btn-default greyButton button big']")
	private WebElement AvslutaAbonnemang;
	
	@FindBy(xpath="//div[@class='my-account-wrapper clearfix']//li[@class='right']")
	private WebElement MyOrder;
	
	@FindBy(xpath="//div[@class='my-account-wrapper clearfix']//li[@class='left']")
	private WebElement RunDate;
	
	@FindBy(xpath="//h3[@class='category-h1 dynamic']")
	private WebElement Subscription;
	
	@FindBy(xpath= "//button[@class='blueButton flex nedgradering popupLink']")
	private WebElement AvbrytNedgradering;
	
	
	public MyAccountPage(WebDriver driver) 
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	
	public void clickLogOut()
	{
		waitTillElementIsVisible(Loggaut);
		Loggaut.click();
	}
	
	public void clickOnActivate()
	{
		waitTillElementIsVisible(Activera);
		Activera.click();
	}
	
	public void clickToChangeSubscription()
	{
		waitTillElementIsVisible(UpgradeButton);
		UpgradeButton.click();
	}
	
	public void clickToChangeCreditCard()
	{
		waitTillElementIsVisible(Gåvidare);
		Gåvidare.click();
	}
	
	public void clickToEndSubscription()
	{
		waitTillElementIsVisible(AvslutaAbonnemang);
		AvslutaAbonnemang.click();
	}
	
	public String getMyOrder()
	{
		waitTillElementIsVisible(MyOrder);
		return MyOrder.getText();
	}
	
	public String getRunDate()
	{
		waitTillElementIsVisible(RunDate);
		return RunDate.getText();
	}
	
	public String getMySubscription()
	{
		waitTillElementIsVisible(Subscription);
		return Subscription.getText();
	}
	
	public boolean activeraIsClickable()
	{
		waitTillElementIsVisible(Activera);
		return Activera.isEnabled();
	}
	
	
	public boolean avslutaAbonnemangIsClickable()
	{
		waitTillElementIsVisible(AvslutaAbonnemang);
		return AvslutaAbonnemang.isEnabled();
	}
	
	public boolean avbrytIsClickable()
	{
		waitTillElementIsVisible(AvbrytNedgradering);
		return AvbrytNedgradering.isEnabled();
	}
	
	public boolean upgradeButtonIsClickable()
	{
		waitTillElementIsVisible(UpgradeButton);
		return UpgradeButton.isEnabled();
	}

}
