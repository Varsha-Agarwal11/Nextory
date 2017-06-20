package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import common.BasePage;

public class RegistrationPage extends BasePage
{
	@FindBy(id="email")
	private WebElement EPostAdress;
	
	@FindBy(id="confirmemail")
	private WebElement UpprepaEpost;
	
	@FindBy(id="examplePassword")
	private WebElement Losenord;
	
	@FindBy(xpath="//button[contains(text(),'Gå tillbaka')]")
	private WebElement GoBack;
	
	@FindBy(xpath="//button[contains(text(),'Fortsätt')]")
	private WebElement Fortsatt;
	
	public RegistrationPage(WebDriver driver) 
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}
		
	public void setNewEmail(String newEmail)
	{
		waitTillElementIsVisible(EPostAdress);
		EPostAdress.sendKeys(newEmail);
	}
	
	public void confirmNewEmail(String confirm)
	{
		waitTillElementIsVisible(UpprepaEpost);
		UpprepaEpost.sendKeys(confirm);
	}
	
	public void setNewPassword(String newPwd)
	{
		waitTillElementIsVisible(Losenord);
		Losenord.sendKeys(newPwd);
	}
	
	public void clickToContinue()
	{
		waitTillElementIsVisible(Fortsatt);
		Fortsatt.click();
	}
	
	public void clickToGoBack()
	{
		waitTillElementIsVisible(GoBack);
		GoBack.click();
	}
	public void clearEPostAddress()
	 {
	  waitTillElementIsVisible(EPostAdress);
	  EPostAdress.clear();
	 }
	 
	 public void clearConfirmNewEmail()
	 {
	  waitTillElementIsVisible(UpprepaEpost);
	  UpprepaEpost.clear();
	 }
	 
	 public void clearNewPassword()
	 {
	  waitTillElementIsVisible(Losenord);
	  Losenord.clear();
	 }
}
