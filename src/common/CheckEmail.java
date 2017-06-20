package common;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import generics.Excel;

public class CheckEmail implements AutomationConstants 
{
	public static String mailUrl;
	public static String userName;
	public static String password;
	public static String subject;
	public static WebDriver driver;

	
	public static void validatingSubjectLine()
	{
		
		try
		{
		    mailUrl = Excel.getCellValue(INPUT_PATH, "Email", 1, 0);
			userName = Excel.getCellValue(INPUT_PATH, "Email", 1, 1);
			password = Excel.getCellValue(INPUT_PATH, "Email", 1, 2);
			
		System.setProperty("webdriver.chrome.driver","./Drivers/chromedriver.exe");  
		driver=new ChromeDriver();
		driver.get(mailUrl);
		driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
	    driver.findElement(By.name("Email")).sendKeys(userName);
	    driver.findElement(By.id("next")).click();
	    driver.findElement(By.id("Passwd")).sendKeys(password);
	    driver.findElement(By.id("signIn")).click();
	    driver.findElement(By.xpath("//div[text()='Promotions']")).click();
//	    String msg=driver.findElement(By.xpath("//tr[@tabindex='-1']")).getText();
//	    Assert.assertTrue(msg.contains(subject));
//	    System.out.println("Subject Line successfully matched: " +subject);
	    List<WebElement> list=driver.findElements(By.xpath("//tr[@tabindex='-1']"));
//	    for(int i=0;i<10; i++)
//	    {
	    	String msg=list.get(1).getText();
	    	System.out.println(msg);
	    	Assert.assertTrue(msg.contains(subject));
	    	System.out.println("Subject line successfully matched: " +subject);
//	    }
//	    
	}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Subject line failed to match");
		}
		driver.close();
}
	public static void main(String[] args)
	{
		validatingSubjectLine();
	}
}
