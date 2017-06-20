package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import common.BasePage;

public class AdminPage extends BasePage
{
	@FindBy(id="e-mail")
	private WebElement UserName;
	
	@FindBy(id="password")
	private WebElement Password;
	
	@FindBy(xpath="//input[@value='Logga in']")
	private WebElement LoggaIn;
	
	@FindBy(xpath="//b[contains(text(),'Customer Mgmt')]")
	private WebElement CustMgmt;
	
	@FindBy(xpath="//input[@id='email']")
	private WebElement Epost;
	
	@FindBy(xpath="//input[@class='CssButton searchCustomer']")
	private WebElement Search;

	@FindBy(xpath="//a[contains(text(),'Generate Password')]")
	private WebElement GeneratePassword;
	
	@FindBy(xpath="//input[@name='realPassword']")
	private WebElement PasswordValue;
	
	@FindBy(xpath="//input[@id='membertype']")
	private WebElement MemberType;
	
	@FindBy(xpath="//input[@id='subscriptionType']")
	private WebElement SubsType;
	
	@FindBy(xpath="//a[contains(text(),'Logout')]")
	private WebElement LogOut;

	

	public AdminPage(WebDriver driver) 
	{
		super(driver);
		PageFactory.initElements(driver, this);
		// TODO Auto-generated constructor stub
	}
	
	public void setUserName(String admUn)
	{
		waitTillElementIsVisible(UserName);
		UserName.sendKeys(admUn);
	}
	
	public void setPassword(String admPwd)
	{
		waitTillElementIsVisible(Password);
		Password.sendKeys(admPwd);
	}
	
	public void clickLogin()
	{
		waitTillElementIsVisible(LoggaIn);
		LoggaIn.click();
	}
	
	public void clickCustMgmt()
	{
		waitTillElementIsVisible(CustMgmt);
		CustMgmt.click();
	}
	
	public void setEPost(String epost)
	{
		waitTillElementIsVisible(Epost);
		Epost.sendKeys(epost);
	}
	
	public void clickGeneratePassword()
	{
		waitTillElementIsVisible(GeneratePassword);
		GeneratePassword.click();
	}
	
	public String getPasswordValue()
	{
		waitTillElementIsVisible(PasswordValue);
		return PasswordValue.getAttribute("value");
	}
	
	public void clickSearch()
	{
		waitTillElementIsVisible(Search);
		Search.click();
	}
	
	public String getMemberType()
	{
		waitTillElementIsVisible(MemberType);
		return MemberType.getAttribute("value");
	}
	
	public String getSubsType()
	{
		waitTillElementIsVisible(SubsType);
		return SubsType.getAttribute("value");
	}
	
	public void clickLogout()
	{
		waitTillElementIsVisible(LogOut);
		LogOut.click();
	}

}
