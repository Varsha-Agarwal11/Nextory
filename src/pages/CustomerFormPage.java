package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import common.BasePage;

public class CustomerFormPage extends BasePage 
{
	@FindBy(id="firstname")
	private WebElement fornamn;
	
	@FindBy(id="lastname")
	private WebElement Efternamn;
	
	@FindBy(id="cellphone")
	private WebElement Mobilnummer;
	
	@FindBy(xpath="//button[contains(text(),'Fortsätt')]")
	private WebElement Fortsatt;
	

	public CustomerFormPage(WebDriver driver) 
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}
	

	public void enterFirstName(String fn)
	{
		waitTillElementIsVisible(fornamn);
		fornamn.sendKeys(fn);
	}
	
	public void enterLastName(String ln)
	{
		waitTillElementIsVisible(Efternamn);
		Efternamn.sendKeys(ln);
	}
	
	public void enterMobileNumber(String cellNum)
	{
		waitTillElementIsVisible(Mobilnummer);
		Mobilnummer.sendKeys(cellNum);
	}
	
	public void clickContinue()
	{
		waitTillElementIsVisible(Fortsatt);
		Fortsatt.click();
	}
}
