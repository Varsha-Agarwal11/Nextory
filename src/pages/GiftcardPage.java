package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import common.BasePage;

public class GiftcardPage extends BasePage
{
	@FindBy(id="btn-one")
	private WebElement OneManadButton;
	
	@FindBy(id="btn-two")
	private WebElement ThreeManadButton;
	
	@FindBy(id="btn-six")
	private WebElement SixManadButton;
	
	@FindBy(xpath="//input[@value='1']")
	private WebElement OneManadRadio;
	
	@FindBy(xpath="//input[@value='3']")
	private WebElement ThreeManadRadio;
	
	@FindBy(xpath="//input[@value='6']")
	private WebElement SixManadRadio;
	
	@FindBy(xpath="//input[@placeholder='Förnamn']")
	private WebElement Fornamn;
	
	@FindBy(xpath="//input[@placeholder='Efternamn']")
	private WebElement Efternamn;
	
	@FindBy(xpath="//input[@placeholder='E-post']")
	private WebElement Epost;
	
	@FindBy(xpath="//button[contains(text(),'Fortsätt')]")
	private WebElement Fortsatt;
	
	@FindBy(xpath="//form[@id='giftCardForm']/h5")
	private WebElement Stang;
	
	@FindBy(xpath="//button[contains(text(),'Ny kund')]")
	private WebElement NyKund;
	
	@FindBy(xpath="//button[contains(text(),'Jag är redan kund')]")
	private WebElement RedanKund;
	
	@FindBy(xpath="//div[@class='form-group margTop-20']//input[@placeholder='Fyll i din presentkod']")
	private WebElement NewFillPresentKod;
	
	@FindBy(xpath="//form[@id='giftCardForm-CustomerNew']//input[@placeholder='E-postadress']")
	private WebElement NewEPostAddress;
	
	@FindBy(xpath="//input[@placeholder='Verifiera e-postadress']")
	private WebElement VerifieraEpost;
	
	@FindBy(xpath="//form[@id='giftCardForm-CustomerNew']//input[@id='password']")
	private WebElement NewLosenord;
	
	@FindBy(xpath="//form[@id='giftCardForm-CustomerNew']//button[contains(text(),'Lös in presentkort')]")
	private WebElement NewLosInPresentkort;
	
	@FindBy(xpath="//form[@id='giftCardForm-CustomerNew']//h5")
	private WebElement NewStangForm;
	
	@FindBy(xpath="//div[@class='clearfix buttonRow buttonfloater']//button[contains(text(),'Fortsätt')]")
	private WebElement PopUpFortsatt;
	
	@FindBy(xpath="//div[@class='clearfix buttonRow buttonfloater']//button[contains(text(),'Gå tillbaka')]")
	private WebElement PopUpTillBaka;
	
	@FindBy(xpath="//form[@id='giftCardForm-Customer']//input[@placeholder='Fyll i din presentkod']")
	private WebElement ExistingFillPresentKod;
		
	@FindBy(xpath="//form[@id='giftCardForm-Customer']//input[@id='redeemEmail']")
	private WebElement ExistingEpost;
	
	@FindBy(xpath="//form[@id='giftCardForm-Customer']//input[@id='password']")
	private WebElement ExistingLosenord;
	
	@FindBy(xpath="//form[@id='giftCardForm-Customer']//button[@type='submit']")
	private WebElement ExistingLosInPresentkort;
	
	@FindBy(xpath="//form[@id='giftCardForm-Customer']//h5")
	private WebElement ExistingStangForm;
	
	@FindBy(xpath= "//label[@class='error' and @for='giftVoucher']")
	private WebElement PresentkodError;
	
	@FindBy(xpath = "//label[@class='error' and @for='redeemEmailNew']")
	private WebElement NewEPostAddressErr;
	
	@FindBy(xpath = "//label[@class='error' and @for='redeemEmail']")
	private WebElement ExistEPostAddressErr;
	
	@FindBy(xpath = "//label[@class='error' and @for='confirmemail']")
	private WebElement ConfEmailErr;
	
	@FindBy(xpath = "//label[@class='error' and @for='password']")
	private WebElement PasswordErr;
	
	@FindBy(xpath = "//div[@class='form-group margTop-20']//span[@class='error']")
	private WebElement WrongNewPwdErr;
	
	@FindBy(xpath = "//div[@class='form-group ']//span[@class='error']")
	private WebElement ExistWrongPwdErr;
	
	@FindBy(xpath = "//form[@id='giftCardForm-Customer']//span[@class='error']")
	private WebElement ExistWrongEmailErr;
	
	
	

	public GiftcardPage(WebDriver driver) 
	{
		super(driver);
		PageFactory.initElements(driver, this);
		
	}
	
	public void clickKopForOneMonth()
	{
		waitTillElementIsVisible(OneManadButton);
		OneManadButton.click();
	}
	
	public void clickKopForThreeMonth()
	{
		waitTillElementIsVisible(ThreeManadButton);
		ThreeManadButton.click();
	}
	
	public void clickKopForSixMonth()
	{
		waitTillElementIsVisible(SixManadButton);
		SixManadButton.click();
	}
	
	public void clickRadioForOneMonth()
	{
		waitTillElementIsVisible(OneManadRadio);
		OneManadRadio.click();
	}
	
	public void clickRadioForThreeMonth()
	{
		waitTillElementIsVisible(ThreeManadRadio);
		ThreeManadRadio.click();
	}
	
	public void clickRadioForSixMonth()
	{
		waitTillElementIsVisible(SixManadRadio);
		SixManadRadio.click();
	}
	
	public void enterFirstName(String fornamn)
	{
		waitTillElementIsVisible(Fornamn);
		Fornamn.sendKeys(fornamn);
	}
	
	public void enterLastName(String efternamn)
	{
		waitTillElementIsVisible(Efternamn);
		Efternamn.sendKeys(efternamn);
	}
	
	public void enterEmailToReceiveCode(String email)
	{
		waitTillElementIsVisible(Epost);
		Epost.sendKeys(email);
	}
	
	public void clickFortsattToGetCode()
	{
		waitTillElementIsVisible(Fortsatt);
		Fortsatt.click();
	}
	
	public void clickStangToCloseKopForm()
	{
		waitTillElementIsVisible(Stang);
		Stang.click();
	}
	
	public void clickNyKund()
	{
		waitTillElementIsVisible(NyKund);
		NyKund.click();
	}
	
	public void clickRedanKund()
	{
		waitTillElementIsVisible(RedanKund);
		RedanKund.click();
	}
	
	public void enterPresentkodNewUser(String kod)
	{
		waitTillElementIsVisible(NewFillPresentKod);
		NewFillPresentKod.sendKeys(kod);
	}
	
	public void enterNewEmail(String newEmail)
	{
		waitTillElementIsVisible(NewEPostAddress);
		NewEPostAddress.sendKeys(newEmail);
	}
	
	public void verifyNewEmail(String newEmail)
	{
		waitTillElementIsVisible(VerifieraEpost);
		VerifieraEpost.sendKeys(newEmail);
	}
	
	public void enterNewPassword(String newPwd)
	{
		waitTillElementIsVisible(NewLosenord);
		NewLosenord.sendKeys(newPwd);
	}
	
	public void clickToRedeemAsNew()
	{
		waitTillElementIsVisible(NewLosInPresentkort);
		NewLosInPresentkort.click();
	}
	
	public void closeRedeemFormNewUser()
	{
		waitTillElementIsVisible(NewStangForm);
		NewStangForm.click();
	}
	
	public void clickPopUpFortsatt()
	{
		waitTillElementIsVisible(PopUpFortsatt);
		PopUpFortsatt.click();
	}
	
	public void clickPopUpGoBack()
	{
		waitTillElementIsVisible(PopUpTillBaka);
		PopUpTillBaka.click();
	}
	
	public void enterPresentKodExistingUser(String kod)
	{
		waitTillElementIsVisible(ExistingFillPresentKod);
		ExistingFillPresentKod.sendKeys(kod);
	}
	
	public void enterExistingEmail(String existEmail)
	{
		waitTillElementIsVisible(ExistingEpost);
		ExistingEpost.sendKeys(existEmail);
	}
	
	public void enterExistingPassword(String existPwd)
	{
		waitTillElementIsVisible(ExistingLosenord);
		ExistingLosenord.sendKeys(existPwd);
	}
	
	public void clickToRedeemAsExisting()
	{
		waitTillElementIsVisible(ExistingLosInPresentkort);
		ExistingLosInPresentkort.click();
	}
	
	public void closeRedeemForExistingUser()
	{
		waitTillElementIsVisible(ExistingStangForm);
		ExistingStangForm.click();
	}
	
	public String getPresentKodErrMsg()
	{
		waitTillElementIsVisible(PresentkodError);
		return PresentkodError.getText();
	}
	
	public String getPostadressErrMsg()
	{
		waitTillElementIsVisible(NewEPostAddressErr);
		return NewEPostAddressErr.getText();
	}
	
	public String getConfEmailErr()
	{
		waitTillElementIsVisible(ConfEmailErr);
		return ConfEmailErr.getText();
	}
	
	public String getPasswordErr()
	{
		waitTillElementIsVisible(PasswordErr);
		return PasswordErr.getText();
	}
	
	public void clearNewPresentkod()
	{
		waitTillElementIsVisible(NewFillPresentKod);
		NewFillPresentKod.clear();
	}
	
	public void clearNewEmail()
	{
		waitTillElementIsVisible(NewEPostAddress);
		NewEPostAddress.clear();
	}
	
	public void clearNewConfEmail()
	{
		waitTillElementIsVisible(VerifieraEpost);
		VerifieraEpost.clear();
	}
	
	public void clearNewPswd()
	{
		waitTillElementIsVisible(NewLosenord);
		NewLosenord.clear();
	}
	
	public String getWrongNewPwdErr()
	{
		waitTillElementIsVisible(WrongNewPwdErr);
		return WrongNewPwdErr.getText();
	}
	
	public String getExistEmailErrMsg()
	{
		waitTillElementIsVisible(ExistEPostAddressErr);
		return ExistEPostAddressErr.getText();
	}
	
	public String getExistingWrongPwdErr()
	{
		waitTillElementIsVisible(ExistWrongPwdErr);
		return ExistWrongPwdErr.getText();
	}
	
	public String getExistingWrongEmailErr()
	{
		waitTillElementIsVisible(ExistWrongEmailErr);
		return ExistWrongEmailErr.getText();
	}
	
	public void clearExistingPresentKod()
	{
		waitTillElementIsVisible(ExistingFillPresentKod);
		ExistingFillPresentKod.clear();
	}
	
	public void clearExistingEPost()
	{
		waitTillElementIsVisible(ExistingEpost);
		ExistingEpost.clear();
	}
	
	public void clearExistingPassword()
	{
		waitTillElementIsVisible(ExistingLosenord);
		ExistingLosenord.clear();
	}
	
	

}
