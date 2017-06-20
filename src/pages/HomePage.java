package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import common.BasePage;

public class HomePage extends BasePage
{
	@FindBy(xpath="//img[@class='logotype-top']")
	private WebElement NextoryLogo;
	
	@FindBy(xpath="//header[@class='welcomeText']/a[@class='blueButton cta']")
	private WebElement Provagratisi14dagar;
	
	@FindBy(xpath="//nav[@class='topmenu-screen']//a[contains(text(),'Logga in')]")
	private WebElement LoggaInLink;
	
	@FindBy(xpath="//a[contains(text(),'Presentkort')]")
	private WebElement Presentkort;
	
	@FindBy(xpath="//a[contains(text(),'Kampanjkod')]")
	private WebElement Kampanjkod;
	
	@FindBy(xpath="//nav[@class='topmenu-screen']//a[contains(text(),'Konto')]")
	private WebElement KontoLink;
	
	public HomePage(WebDriver driver) 
	{
		super(driver);
		PageFactory.initElements(driver, this);
	
	}
	
	public void clickNextoryLogo()
	{
		waitTillElementIsVisible(NextoryLogo);
		NextoryLogo.click();
	}
	
	public void clickToRegister()
	{
		waitTillElementIsVisible(Provagratisi14dagar);
		Provagratisi14dagar.click();
	}
	
	public void clickLoginLink()
	{
		waitTillElementIsVisible(LoggaInLink);
		LoggaInLink.click();
	}
	
	public void clickAccountLink()
	{
		waitTillElementIsVisible(KontoLink);
		KontoLink.click();
	}
	
	
	public void clickGiftCard()
	{
		waitTillElementIsVisible(Presentkort);
		Presentkort.click();
	}
	
	public void clickCampaign()
	{
		waitTillElementIsVisible(Kampanjkod);
		Kampanjkod.click();
	}

}
