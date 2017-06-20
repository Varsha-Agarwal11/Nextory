package common;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import generics.Utility;

public class CustomListener implements ITestListener
{
	@Override
	public void onTestFailure(ITestResult result) 
	{
		String folder=AutomationConstants.SNAP_PATH;
		Utility.getDesktopScreenshot(folder);
	}

	@Override
	public void onTestStart(ITestResult result) 
	{
		
	}

	@Override
	public void onTestSuccess(ITestResult result) 
	{
	
	}
	
	@Override
	public void onTestSkipped(ITestResult result) 
	{
		
	}
	
	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) 
	{
		
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

}
