package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import common.BasePage;

public class NewSubscriptionPage extends BasePage
{
	@FindBy(xpath="//button[contains(text(),'Bas')]")
	private WebElement BasSubscription;
	
	@FindBy(xpath="//button[contains(text(),'Standard')]")
	private WebElement StandardSubscription;
	
	@FindBy(xpath="//button[contains(text(),'Premium')]")
	private WebElement PremiumSubscription;
	
	@FindBy(xpath="//button[contains(text(),'Fortsätt')]")
	private WebElement Fortsatt;
	
	public NewSubscriptionPage(WebDriver driver) 
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	public void clickBasSub()
	{
		waitTillElementIsVisible(BasSubscription);
		BasSubscription.click();
	}
	
	public void clickStandardSub()
	{
		waitTillElementIsVisible(StandardSubscription);
		StandardSubscription.click();
	}
	
	public void clickPremiumSub()
	{
		waitTillElementIsVisible(PremiumSubscription);
		PremiumSubscription.click();
	}
	
	public void clickContinue()
	{
		waitTillElementIsVisible(Fortsatt);
		Fortsatt.click();
	}
}
