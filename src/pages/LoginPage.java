package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import common.BasePage;

public class LoginPage extends BasePage 
{
	@FindBy(xpath="//li[@id='menu-item-263']/a[contains(text(),'Logga in')]")
	private WebElement LoggaInLink;
	
	@FindBy(id="e-mail")
	private WebElement EPostAdress;
	
	@FindBy(id="password")
	private WebElement Losenord;
	
	@FindBy(xpath="//button[@class='blueButton']")
	private WebElement LoggaInButton;
	
	@FindBy(xpath="//a[@href='/glomt-losenord']")
	private WebElement ForgotPassword;
	
	
	public LoginPage(WebDriver driver) 
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}
	

	public void clickLoginLink()
	{
		waitTillElementIsVisible(LoggaInLink);
		LoggaInLink.click();
	}

	public void setEmailId(String un)
	{
		waitTillElementIsVisible(EPostAdress);
		EPostAdress.sendKeys(un);
	}
	
	public void setPassword(String pwd)
	{
		waitTillElementIsVisible(Losenord);
		Losenord.sendKeys(pwd);
	}
	
	public void clickLoginButton()
	{
		waitTillElementIsVisible(LoggaInButton);
		LoggaInButton.click();
	}
	
	public void clickForgotPassword()
	{
		waitTillElementIsVisible(ForgotPassword);
		ForgotPassword.click();
	}
	
}
