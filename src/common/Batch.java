package common;

import java.io.IOException;

public class Batch 
{

	public static void batchExecution() 
	{
		// TODO Auto-generated method stub
		try 
		{
            String[] command = {"cmd.exe", "/C", "Start", "test.bat"};
            Process p =  Runtime.getRuntime().exec(command);           
        } 
		catch (IOException ex) 
		{
        }
		
		
	}
	public static void main(String[] args)
	{
		batchExecution();
	}

}
