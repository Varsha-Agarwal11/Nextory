package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import common.BasePage;

public class GiftCardPaymentDetailsForm extends BasePage
{
	@FindBy(xpath="//input[@class='creditcardnumber']")
	private WebElement KortNummerTextBox;
	
	@FindBy(xpath="//select[@data-encrypted-name='expiryMonth']")
	private WebElement ExpiryMonthDropdown;
	
	@FindBy(xpath="//option[@value='1']")
	private WebElement FirstMonthValue;
	
	@FindBy(xpath="//option[@value='2']")
	private WebElement SecondMonthValue;
	
	@FindBy(xpath="//option[@value='3']")
	private WebElement ThirdMonthValue;
	
	@FindBy(xpath="//option[@value='4']")
	private WebElement FourthMonthValue;
	
	@FindBy(xpath="//option[@value='5']")
	private WebElement FifthMonthValue;
	
	@FindBy(xpath="//option[@value='6']")
	private WebElement SixthMonthValue;
	
	@FindBy(xpath="//option[@value='7']")
	private WebElement SeventhMonthValue;
	
	@FindBy(xpath="//option[@value='8']")
	private WebElement EightMonthValue;
	
	@FindBy(xpath="//option[@value='9']")
	private WebElement NinthMonthValue;
	
	@FindBy(xpath="//option[@value='10']")
	private WebElement TenthMonthValue;
	
	@FindBy(xpath="//option[@value=11']")
	private WebElement EleventhMonthValue;
	
	@FindBy(xpath="//option[@value='12']")
	private WebElement TwelvthMonthValue;
	
	@FindBy(xpath="//select[@data-encrypted-name='expiryYear']")
	private WebElement ExpiryYearDropdown;
	
	@FindBy(xpath="//option[@value='2016']")
	private WebElement SixteenthYearValue;
	
	@FindBy(xpath="//option[@value='2017']")
	private WebElement SeventeenthYearValue;
	
	@FindBy(xpath="//option[@value='2018']")
	private WebElement EighteenYearValue;
	
	@FindBy(xpath="//option[@value='2019']")
	private WebElement NineteenthYearValue;
	
	@FindBy(xpath="//option[@value='2020']")
	private WebElement TwentythYearValue;
	
	@FindBy(xpath="//option[@value='2021']")
	private WebElement TwentyOneYearValue;
	
	@FindBy(xpath="//option[@value='2022']")
	private WebElement TwentyTwoYearValue;
	
	@FindBy(xpath="//option[@value='2023']")
	private WebElement TwentyThreeYearValue;
	
	@FindBy(xpath="//option[@value='2024']")
	private WebElement TwentyFourYearValue;
	
	@FindBy(xpath="//option[@value='2025']")
	private WebElement TwentyFiveYearValue;
	
	@FindBy(xpath="//option[@value='2026']")
	private WebElement TwentySixYearValue;
	
	@FindBy(xpath="//option[@value='2027']")
	private WebElement TwentySevenYearValue;
	
	@FindBy(xpath="//option[@value='2028']")
	private WebElement TwentyEightYearValue;
	
	@FindBy(xpath="//option[@value='2029']")
	private WebElement TwentyNineYearValue;
	
	@FindBy(xpath="//option[@value='2030']")
	private WebElement ThirtyYearValue;
	
	@FindBy(xpath="//option[@value='2031']")
	private WebElement ThirtyOneYearValue;
	
	@FindBy(xpath="//option[@value='2032']")
	private WebElement ThirtyTwoYearValue;
	
	@FindBy(xpath="//option[@value='2033']")
	private WebElement ThirtyThreeYearValue;
	
	@FindBy(xpath="//option[@value='2034']")
	private WebElement ThirtyFourYearValue;
	
	@FindBy(xpath="//option[@value='2035']")
	private WebElement ThirtyFiveYearValue;
	
	@FindBy(xpath="//option[@value='2036']")
	private WebElement ThirtySixYearValue;
	
	@FindBy(xpath="//option[@value='2037']")
	private WebElement ThirtySevenYearValue;
	
	@FindBy(xpath="//option[@value='2038']")
	private WebElement ThirtyEightYearValue;
	
	@FindBy(xpath="//option[@value='2039']")
	private WebElement ThirtyNineYearValue;
	
	@FindBy(xpath="//option[@value='2040']")
	private WebElement FourtyYearValue;
	
	@FindBy(xpath="//option[@value='2041']")
	private WebElement FourtyOneYearValue;
	
	@FindBy(xpath="//option[@value='2042']")
	private WebElement FourtyTwoYearValue;
	
	@FindBy(xpath="//option[@value='2043']")
	private WebElement FourtyThreeYearValue;
	
	@FindBy(xpath="//option[@value='2044']")
	private WebElement FourtyFourYearValue;
	
	@FindBy(xpath="//option[@value='2045']")
	private WebElement FourtyFiveYearValue;
	
	@FindBy(xpath="//input[@data-encrypted-name='cvc']")
	private WebElement CvcNumberTextbox;
	
	@FindBy(id="submit")
	private WebElement getGiftCardBtn;
	
	
	

			public GiftCardPaymentDetailsForm(WebDriver driver) 
				{
					super(driver);
					PageFactory.initElements(driver, this);
				}
	
	
			
	public void enterCardNumber(String cardNumber)
	{
		waitTillElementIsVisible(KortNummerTextBox);
		KortNummerTextBox.sendKeys(cardNumber);
	}
	
	public void clickExpiryMonthDropdown()
	{
		waitTillElementIsVisible(ExpiryMonthDropdown);
		ExpiryMonthDropdown.click();
	}
	
	public void selectExpiryMonth(String month)
		{
			if(month.equalsIgnoreCase("01"))
			{
			Select mon=new Select(ExpiryMonthDropdown);
			mon.selectByValue("1");
			}
			
			else if(month.equalsIgnoreCase("02"))
			{
			Select mon=new Select(ExpiryMonthDropdown);
			mon.selectByValue("2");
			}
			
			else if(month.equalsIgnoreCase("03"))
			{
			Select mon=new Select(ExpiryMonthDropdown);
			mon.selectByValue("3");
			}
			
			else if(month.equalsIgnoreCase("04"))
			{
			Select mon=new Select(ExpiryMonthDropdown);
			mon.selectByValue("4");
			}
			
			else if(month.equalsIgnoreCase("05"))
			{
			Select mon=new Select(ExpiryMonthDropdown);
			mon.selectByValue("5");
			}
			
			else if(month.equalsIgnoreCase("06"))
			{
			Select mon=new Select(ExpiryMonthDropdown);
			mon.selectByValue("6");
			}
			
			else if(month.equalsIgnoreCase("07"))
			{
			Select mon=new Select(ExpiryMonthDropdown);
			mon.selectByValue("7");
			}
			
			else if(month.equalsIgnoreCase("08"))
			{
			Select mon=new Select(ExpiryMonthDropdown);
			mon.selectByValue("8");
			}
			
			else if(month.equalsIgnoreCase("09"))
			{
			Select mon=new Select(ExpiryMonthDropdown);
			mon.selectByValue("9");
			}
			
			else if(month.equalsIgnoreCase("10"))
			{
			Select mon=new Select(ExpiryMonthDropdown);
			mon.selectByValue("10");
			}
			
			else if(month.equalsIgnoreCase("11"))
			{
			Select mon=new Select(ExpiryMonthDropdown);
			mon.selectByValue("11");
			}
			
			else if(month.equalsIgnoreCase("12"))
			{
			Select mon=new Select(ExpiryMonthDropdown);
			mon.selectByValue("12");
			}
	}
	
	public void clickExpiryYearDropdown()
	{
		waitTillElementIsVisible(ExpiryYearDropdown);
		ExpiryYearDropdown.click();
	}
	
	public void selectExpiryYear(String year)
	{
		if(year.equalsIgnoreCase("16"))
		{
		Select yr= new Select(ExpiryYearDropdown);
		yr.selectByValue("2016");
		}
		
		else if(year.equalsIgnoreCase("17"))
		{
		Select yr= new Select(ExpiryYearDropdown);
		yr.selectByValue("2017");
		}
		
		else if(year.equalsIgnoreCase("18"))
		{
		Select yr= new Select(ExpiryYearDropdown);
		yr.selectByValue("2018");
		}
		
		else if(year.equalsIgnoreCase("19"))
		{
		Select yr= new Select(ExpiryYearDropdown);
		yr.selectByValue("2019");
		}
		
		else if(year.equalsIgnoreCase("20"))
		{
		Select yr= new Select(ExpiryYearDropdown);
		yr.selectByValue("2020");
		}
		
		else if(year.equalsIgnoreCase("21"))
		{
		Select yr= new Select(ExpiryYearDropdown);
		yr.selectByValue("2021");
		}
		
		else if(year.equalsIgnoreCase("22"))
		{
		Select yr= new Select(ExpiryYearDropdown);
		yr.selectByValue("2022");
		}
		
		else if(year.equalsIgnoreCase("23"))
		{
		Select yr= new Select(ExpiryYearDropdown);
		yr.selectByValue("2023");
		}
		
		else if(year.equalsIgnoreCase("24"))
		{
		Select yr= new Select(ExpiryYearDropdown);
		yr.selectByValue("2024");
		}
		
		else if(year.equalsIgnoreCase("25"))
		{
		Select yr= new Select(ExpiryYearDropdown);
		yr.selectByValue("2025");
		}
		
		else if(year.equalsIgnoreCase("26"))
		{
		Select yr= new Select(ExpiryYearDropdown);
		yr.selectByValue("2026");
		}
		
		else if(year.equalsIgnoreCase("27"))
		{
		Select yr= new Select(ExpiryYearDropdown);
		yr.selectByValue("2027");
		}
		
		else if(year.equalsIgnoreCase("28"))
		{
		Select yr= new Select(ExpiryYearDropdown);
		yr.selectByValue("2028");
		}
		
		else if(year.equalsIgnoreCase("29"))
		{
		Select yr= new Select(ExpiryYearDropdown);
		yr.selectByValue("2029");
		}
		
		else if(year.equalsIgnoreCase("30"))
		{
		Select yr= new Select(ExpiryYearDropdown);
		yr.selectByValue("2030");
		}
		
		else if(year.equalsIgnoreCase("31"))
		{
		Select yr= new Select(ExpiryYearDropdown);
		yr.selectByValue("2031");
		}
		
		else if(year.equalsIgnoreCase("32"))
		{
		Select yr= new Select(ExpiryYearDropdown);
		yr.selectByValue("2032");
		}
		
		else if(year.equalsIgnoreCase("33"))
		{
		Select yr= new Select(ExpiryYearDropdown);
		yr.selectByValue("2033");
		}
		
		else if(year.equalsIgnoreCase("34"))
		{
		Select yr= new Select(ExpiryYearDropdown);
		yr.selectByValue("2034");
		}
		
		else if(year.equalsIgnoreCase("35"))
		{
		Select yr= new Select(ExpiryYearDropdown);
		yr.selectByValue("2035");
		}
		
		else if(year.equalsIgnoreCase("36"))
		{
		Select yr= new Select(ExpiryYearDropdown);
		yr.selectByValue("2037");
		}
		
		else if(year.equalsIgnoreCase("37"))
		{
		Select yr= new Select(ExpiryYearDropdown);
		yr.selectByValue("2037");
		}
		
		else if(year.equalsIgnoreCase("38"))
		{
		Select yr= new Select(ExpiryYearDropdown);
		yr.selectByValue("2038");
		}
		
		else if(year.equalsIgnoreCase("39"))
		{
		Select yr= new Select(ExpiryYearDropdown);
		yr.selectByValue("2039");
		}
		
		else if(year.equalsIgnoreCase("40"))
		{
		Select yr= new Select(ExpiryYearDropdown);
		yr.selectByValue("2040");
		}
		
		else if(year.equalsIgnoreCase("41"))
		{
		Select yr= new Select(ExpiryYearDropdown);
		yr.selectByValue("2041");
		}
		
		else if(year.equalsIgnoreCase("42"))
		{
		Select yr= new Select(ExpiryYearDropdown);
		yr.selectByValue("2042");
		}
		
		else if(year.equalsIgnoreCase("43"))
		{
		Select yr= new Select(ExpiryYearDropdown);
		yr.selectByValue("2043");
		}
		
		else if(year.equalsIgnoreCase("44"))
		{
		Select yr= new Select(ExpiryYearDropdown);
		yr.selectByValue("2044");
		}
		
		else if(year.equalsIgnoreCase("45"))
		{
		Select yr= new Select(ExpiryYearDropdown);
		yr.selectByValue("2045");
		}
	}
	
	public void enterCvcNumber(String cvc)
	{
		waitTillElementIsVisible(CvcNumberTextbox);
		CvcNumberTextbox.sendKeys(cvc);
	}
	
	public void clickGetGiftCard()
	{
		waitTillElementIsVisible(getGiftCardBtn);
		getGiftCardBtn.click();
	}
	
}
