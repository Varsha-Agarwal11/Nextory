package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import common.BasePage;

public class EndSubscriptionPage extends BasePage
{
	@FindBy(xpath="//button[@class='frameButton responsive']")
	private WebElement Avbryt;
	
	@FindBy(xpath="//button[@class='blueButton responsive']")
	private WebElement JaTack;
	
	@FindBy(xpath="//button[@class='new borderButton greybutton']")
	private WebElement AvslutaAbonnemang;
	
	@FindBy(xpath="//button[@class='new blueButton nedgradering closePopUp-button closePopUp']")
	private WebElement Acceptera;
	
	@FindBy(xpath="//button[contains(text(),'Avsluta')]")
	private WebElement Avsluta;
	
	@FindBy(xpath="//button[contains(text(),'Ångra')]")
	private WebElement Ångra;

	public EndSubscriptionPage(WebDriver driver) 
	{
		super(driver);
		PageFactory.initElements(driver, this);
		// TODO Auto-generated constructor stub
	}
	
	public void clickNotToEndSubscription()
	{
		waitTillElementIsVisible(Avbryt);
		Avbryt.click();
	}
	
	public void clickToGoForLowerSubs()
	{
		waitTillElementIsVisible(JaTack);
		JaTack.click();
	}
	
	public void clickToConfirmEndSubscription()
	{
		waitTillElementIsVisible(AvslutaAbonnemang);
		AvslutaAbonnemang.click();
	}
	
	public void clickToAcceptForBas()
	{
		waitTillElementIsVisible(Acceptera);
		Acceptera.click();
	}
	
	public void clickToEnd()
	{
		waitTillElementIsVisible(Avsluta);
		Avsluta.click();
	}
	
	public void clickToUndo()
	{
		waitTillElementIsVisible(Ångra);
		Ångra.click();
	}

}
