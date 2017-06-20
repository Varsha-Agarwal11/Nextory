package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import common.BasePage;

public class ForgotPasswordPage extends BasePage
{
	@FindBy(id="e-mail")
	private WebElement EpostAddress;
	
	@FindBy(xpath="//button[contains(text(),'Skicka')]")
	private WebElement Skicka;
	

	public ForgotPasswordPage(WebDriver driver) 
	{
		super(driver);
		PageFactory.initElements(driver, this);
		// TODO Auto-generated constructor stub
	}
	
	
	public void enterEmailId(String un)
	{
		waitTillElementIsVisible(EpostAddress);
		EpostAddress.sendKeys(un);
	}
	
	public void clearEmailTextbox()
	{
		waitTillElementIsVisible(EpostAddress);
		EpostAddress.clear();
	}
	
	public void clickSkickaButton()
	{
		waitTillElementIsVisible(Skicka);
		Skicka.click();
	}
	
	public boolean skickaButtonClickable()
	{
		waitTillElementIsVisible(Skicka);
		return Skicka.isEnabled();
		
		
	}

}
