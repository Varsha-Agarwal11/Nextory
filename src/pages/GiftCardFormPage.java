package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import common.BasePage;

public class GiftCardFormPage extends BasePage
{
	@FindBy(id="btn-one")
	private WebElement Kop1;
	
	@FindBy(id="btn-two")
	private WebElement Kop2;
	
	@FindBy(id="btn-six")
	private WebElement Kop3;
	
	@FindBy(xpath="//button[contains(text(),'Ny kund')]")
	private WebElement NewCustomer;
	
	@FindBy(xpath="//button[contains(text(),'Jag är redan kund')]")
	private WebElement ExistingCustomer;
	
	@FindBy(xpath="//a[@href='/bli-medlem']//preceding::span")
	private WebElement TryFree14days;
	
	@FindBy(xpath="//input[@id='presentKortQty1']")
	private WebElement SelectRadioBtn1;
	
	@FindBy(xpath="//input[@id='presentKortQty2']")
	private WebElement SelectRadioBtn2;
	
	@FindBy(xpath="//input[@id='presentKortQty3']")
	private WebElement SelectRadioBtn3;
	
	@FindBy(id="name")
	private WebElement FirstName; 
	
	@FindBy(id="lastname")
	private WebElement LastName; 
	
	@FindBy(id="email")
	private WebElement Email; 
	
	@FindBy(id="submit")
	private WebElement Forsatt;
	
	@FindBy(xpath="//form[@id='giftCardForm']/h5")
	private WebElement Stang;
	
	@FindBy(id="voucher")
	private WebElement VoucherCode; 
	
	@FindBy(xpath="//input[@id='redeemEmail']//preceding::form[@id='giftCardForm']")
	private WebElement Epost;
	
	@FindBy(id="verifyEmail")
	private WebElement verifyEpost;
	
	@FindBy(xpath="//input[@id='password']//preceding::form[@id='giftCardForm-CustomerNew']")
	private WebElement password;
	
	@FindBy(id="redeemcard")
	private WebElement redeemed;
	
	public GiftCardFormPage(WebDriver driver) 
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	public void clickKop1()
	{
		waitTillElementIsVisible(Kop1);
		Kop1.click();
	}
	
	public void clickKop2()
	{
		waitTillElementIsVisible(Kop2);
		Kop2.click();
	}
	
	public void clickKop3()
	{
		waitTillElementIsVisible(Kop3);
		Kop3.click();
	}
	
	public void clickNyKund()
	{
		waitTillElementIsVisible(NewCustomer);
		NewCustomer.click();
	}
	
	public void clickExistingCustomer()
	{
		waitTillElementIsVisible(ExistingCustomer);
		ExistingCustomer.click();
	}
	
	public void clickTryFree14days()
	{
		waitTillElementIsVisible(TryFree14days);
		TryFree14days.click();
	}
	
	public void enterFirstName(String fn)
	{
		waitTillElementIsVisible(FirstName);
		FirstName.sendKeys(fn);
	}
	
	public void enterLastName(String ln)
	{
		waitTillElementIsVisible(LastName);
		LastName.sendKeys(ln);
	}
	
	public void enterEmail(String email)
	{
		waitTillElementIsVisible(Email);
		LastName.sendKeys(email);
	}
	
	public void clickForsattBtn()
	{
		waitTillElementIsVisible(Forsatt);
		ExistingCustomer.click();
	}
	
	public void clickStangBtn()
	{
		waitTillElementIsVisible(Stang);
		Stang.click();
	}
	
	public void enterVoucherCode(String voucher)
	{
		waitTillElementIsVisible(VoucherCode);
		VoucherCode.sendKeys(voucher);
	}
	
	public void enterEpost(String email)
	{
		waitTillElementIsVisible(Epost);
        Epost.sendKeys(email);
	}
	
	public void enterVerifyEpost(String email)
	{
		waitTillElementIsVisible(verifyEpost);
		verifyEpost.sendKeys(email);
	}
	
	public void enterPassword(String pwd)
	{
		waitTillElementIsVisible(password);
		password.sendKeys(pwd);
	}
	
	public void clickRedeemBtn()
	{
		waitTillElementIsVisible(redeemed);
		redeemed.click();
	}
	
}
