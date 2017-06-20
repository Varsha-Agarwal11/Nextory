package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import common.BasePage;

public class EndSubsFeedbackPage extends BasePage
{
	@FindBy(name="cancelReason")
	private WebElement FeedbackDropdown;
	
	@FindBy(xpath="//option[@value='Jag har inte tid att utnyttja tjänsten']")
	private WebElement NoTimeToUse;
	
	@FindBy(xpath="//option[@value='Tjänsten är inte tillräckligt prisvärd']")
	private WebElement NotAffordable;
	
	@FindBy(xpath="//option[@value='Jag hittar inte de böcker jag vill läsa']")
	private WebElement UnableToFindBook;
	
	@FindBy(xpath="//input[@id='cancelReasonExtend']")
	private WebElement BookNameToFind;
	
	@FindBy(xpath="//option[@value='Appen har inte fungerat som den ska']")
	private WebElement AppNotWorked;
	
	@FindBy(xpath="//option[@value='Jag har valt en annan tjänst']")
	private WebElement ChosenDifferent;
	
	@FindBy(xpath="//option[@value='Annat']")
	private WebElement Other;
	
	@FindBy(xpath="//input[@placeholder='Vänligen skriv din synpunkt']")
	private WebElement Writeview;
	
	@FindBy(xpath="//button[@class='frameButton responsive']")
	private WebElement AngraUndo;
	
	@FindBy(xpath="//button[@class='blueButton responsive']")
	private WebElement Avsluta;
	
	@FindBy(xpath="//button[@class='new blueButton ']")
	private WebElement Klar;

	public EndSubsFeedbackPage(WebDriver driver) 
	{
		super(driver);
		PageFactory.initElements(driver, this);
		
	}
	
	public void clickFeedbackDropdown()
	{
		waitTillElementIsVisible(FeedbackDropdown);
		FeedbackDropdown.click();
	}
	
	public void selectNoTimeToUse()
	{
		Select reason=new Select(FeedbackDropdown);
		reason.selectByValue("Jag har inte tid att utnyttja tjänsten");
	}
	
	public void selectNotAffordable()
	{
		Select reason=new Select(FeedbackDropdown);
		reason.deselectByValue("Tjänsten är inte tillräckligt prisvärd");
	}
	
	public void selectUnableToFindBook()
	{
		Select reason=new Select(FeedbackDropdown);
		reason.selectByValue("Jag hittar inte de böcker jag vill läsa");
	}
	
	public void enterBookNameToFind(String book)
	{
		waitTillElementIsVisible(BookNameToFind);
		BookNameToFind.sendKeys(book);
	}
	
	public void selectAppNotWorked()
	{
		Select reason=new Select(FeedbackDropdown);
		reason.selectByValue("Appen har inte fungerat som den ska");
	}
	
	public void selectChosenDiffService()
	{
		Select reason=new Select(FeedbackDropdown);
		reason.selectByValue("Jag har valt en annan tjänst");
	}
	
	public void selectOther()
	{
		Select reason=new Select(FeedbackDropdown);
		reason.selectByValue("Annat");
	}
	
	public void writeOtherView(String view)
	{
		waitTillElementIsVisible(Writeview);
		Writeview.sendKeys(view);
	}
	
	public void clickToUndoButton()
	{
		waitTillElementIsVisible(AngraUndo);
		AngraUndo.click();
	}
	
	public void clickToEndButton()
	{
		waitTillElementIsVisible(Avsluta);
		Avsluta.click();
	}
	
	public void clickClearButton()
	{
		waitTillElementIsVisible(Klar);
		Klar.click();
	}
	

}
