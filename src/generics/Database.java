package generics;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;

import common.AutomationConstants;

public class Database implements AutomationConstants
{
	public static String driver="com.mysql.jdbc.Driver";
	public static String dbUrl=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "DBURL"); //jdbc:mysql://localhost:3306/nextory_new
	public static String username=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "DBUSER"); //root
	public static String password=Property.getPropertyValue(CONFIG_PATH+CONFIG_FILE, "DBPWD");  //test
	static Logger log = Logger.getLogger(Database.class);
	
	
	
	public static String executeQuery(String query1)  
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet result = null;
		String account = null;
		try
		{
		//Accessing driver from the JAR file 
	    Class.forName(driver);
	    System.out.println("MySql driver loaded ok.");
	    
	    con=DriverManager.getConnection(dbUrl, username, password);

	    log.info("DB connected successfully");
	    
	    stmt = con.createStatement();

	    result = stmt.executeQuery(query1);

	    
	    while(result.next())
	    {
	        account = result.getString(1);
            log.info(account);    //Id fetched is
        }
	    con.close();
	    return account;
		}
		catch(Exception e)
		{
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
		finally
		{
			try{
			if(con!=null)
				con.close();
			if(stmt != null)
				stmt.close();
			if(result!= null)
			 result.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.out.println("Error while closing the connection");
			}
		}
		return account;
	}
	
	public static int executeUpdate(String query2)
	{
		Connection con = null;
		Statement stmt = null;
		try
		{
			Class.forName(driver);
			
			System.out.println("MySql driver loaded ok.");
		    
			con =DriverManager.getConnection(dbUrl, username, password);

		    log.info("DB connected successfully");
		    
		    stmt = con.createStatement();
			
		    stmt.executeUpdate(query2);
		    
		    System.out.println("Table updated.");
		   
		    
		    
		}
		catch(Exception e)
		{
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
		finally
		{
			try{
				if(con!=null)
					con.close();
			if(stmt != null)
				stmt.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.out.println("Error while closing the connection");
			}
		}
		return 0;
		
		
	}
	
	public Connection getConnection()
	 { 
	  Connection con = null;
	  try
	  {
	  //Accessing driver from the JAR file 
	     Class.forName(driver);
	     System.out.println("MySql driver loaded ok.");
	     
	     con=DriverManager.getConnection(dbUrl, username, password);

	     log.info("DB connected successfully");
	  }
	     catch(Exception e)
	  {
	   System.err.println("Got an exception! ");
	   System.err.println(e.getMessage());
	  }
	  
	  return con;
	 }
}
