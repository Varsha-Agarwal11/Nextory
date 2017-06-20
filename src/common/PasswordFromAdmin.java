package common;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import generics.Property;

public class PasswordFromAdmin implements AutomationConstants
{
	public static String un;
	public static String adminUn;
	public static String adminPwd;
	public static String adminUrl;
	public static WebDriver driver;
	public static String pwd;
	

	public static String gettingPasswordFromAdmin(String un)
	{
		adminUn=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINUN");
		adminPwd=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINPWD");
		adminUrl=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "ADMINURL");
		
		System.setProperty(CHROME_KEY, DRIVER_PATH+CHROME_FILE);
		driver= new ChromeDriver();
		
		driver.manage().deleteAllCookies();
		driver.get(adminUrl);
		driver.findElement(By.id("e-mail")).sendKeys(adminUn);
		driver.findElement(By.id("password")).sendKeys(adminPwd);
		driver.findElement(By.xpath("//input[@value='Logga in']")).click();
		driver.findElement(By.xpath("//b[text()='Customer Mgmt']")).click();
		driver.findElement(By.xpath("//input[@id='email']")).sendKeys(un);
		driver.findElement(By.xpath("//input[@class='CssButton searchCustomer']")).click();
		WebElement ele1=driver.findElement(By.xpath("//label[contains(text(),'Tmp Password')]/../following-sibling::td[1]/*"));
		ele1.click();
		
		pwd = driver.findElement(By.xpath("//input[@name='realPassword']")).getAttribute("value");
		//System.out.println(pwd);
	
		driver.close();
	
		return pwd;

	
	}
	
	public static void main(String[] args)
	{
		gettingPasswordFromAdmin("test_62322@test.se");
	}

}
