package interfaceForApiOrDB;

import java.sql.Connection;
import java.sql.Statement;

import org.testng.log4testng.Logger;

import Bean.Customer2SubscriptionMap;
import Bean.CustomerInfo;
import Bean.LibraryMongo;
import Bean.Membership_change_logMongo;
import Bean.NxCustomerEmailLogsMongo;
import Bean.Orders;
import Bean.SubscriptionMaster;
import Bean.Transaction;
import common.AutomationConstants;
import generics.Database;
import postRegistrationRuleMailer.Welcome;

public class InformationFromBackendNegative implements InformationFromBackendInterface,AutomationConstants
{
	Database db = new Database();
	Connection con = db.getConnection();	
	Logger log=Logger.getLogger(InformationFromBackend.class);
	CustomerInfo custInfo = new CustomerInfo();
	Customer2SubscriptionMap c2s=new Customer2SubscriptionMap();
	Orders order=new Orders();
	SubscriptionMaster subsMaster=new SubscriptionMaster();
	Transaction trans=new Transaction();
	LibraryMongo lib=new LibraryMongo();
	Membership_change_logMongo memShip=new Membership_change_logMongo();
	NxCustomerEmailLogsMongo custMail=new NxCustomerEmailLogsMongo();
	public static String query =" ";
	public static String query1=" ";
	public static String action=""; 
	public static Statement stmt=null;
	public static String result;
	public static String result1;
	public static String result2;
	public static String email="";
	
	public String getDataForCustomerInfo(String action) 
	{
    //---------------------------------------------POST REGISTRATION QUERIES-------------------------------------------------------
		
		if(action.equalsIgnoreCase("SurpriseDelight"))
		{
			email=Welcome.newEmail;
		    query = "select customerid from customerinfo where member_type_code in (201007,201002,202002,203002) order by rand() limit 1";
			result = Database.executeQuery(query);
			System.out.println("Customer id picked for first customer: " + result);
			query = "select customerid from customerinfo where member_type_code in (201007,201002,202002,203002) order by rand() limit 1";
			result1= Database.executeQuery(query);
			System.out.println("Customer id picked for second customer: " + result1);
			updateOrders(action, result);
			updateOrders1(action,result1);
			query = "Select ebc.email as email, ebc.firstname as firstname,ebc.customerid as customerid, ebc.mobile, ebc.lastname from customerinfo ebc where ebc.customerid in ( (select cinfo.customerid from customerinfo cinfo, orders o where cinfo.customerid=o.customerid AND o.status='AUTHORIZED' AND (o.orderdate>(now() - interval 90 minute) and o.orderdate<(now() - interval 30 minute) and (cinfo.member_type_code>200000 and cinfo.member_type_code<300000)) GROUP  BY cinfo.customerid ORDER BY o.orderdate ASC))";
			Database.executeQuery(query);
		}
		else if(action.equalsIgnoreCase("DownloadApp"))
		{
			email = Welcome.newEmail;
			query = "select customerid from customerinfo where member_type_code in (201007,201002,202002,203002) order by rand() limit 1";
			result=Database.executeQuery(query);
			//query= "select customerid from customerinfo where (select ebc.email as Eemail,  ebc.firstname as firstname,ebc.customerid as customerid, ebc.mobile as mobile, ebc.lastname as lastname from customerinfo ebc,customer2subscriptionmap emap where ebc.customerid =emap.customerid and DATEDIFF(Date(now()),Date(subscribedon))=1 and ebc.first_app_loggedin is null)"; // and email=" + email;
			//query = "select customerid from customerinfo where email='" +email+ "'";
			//query="select customerid from (select ebc.email as email,  ebc.firstname as firstname,ebc.customerid as customerid, ebc.mobile as mobile, ebc.lastname as lastname from customerinfo ebc,customer2subscriptionmap emap where ebc.customerid=emap.customerid and DATEDIFF(Date(now()),Date(subscribedon))=1 and ebc.first_app_loggedin is null and ebc.member_type_code>200000 and ebc.member_type_code<400000 )A order by rand() limit 1;";
			query = "select customerid from customerinfo where member_type_code in (201007,201002,202002,203002) order by rand() limit 1";
			result1=Database.executeQuery(query);
			//System.out.println("QueryResult="+result);
			updateCustomer2SubscriptionMap(action,result);
			updateCustomer2SubscriptionMap1(action,result1);
		}
		else if(action.equalsIgnoreCase("FindBook"))
		{
			email = Welcome.newEmail;
			// query="select customerid from customerinfo email='" +email+ "'";
			query = "select customerid from customerinfo where member_type_code >200000  and member_type_code < 400000 and firstname is not null order by rand()  limit 1";
			result = Database.executeQuery(query);
		    updateCustomerInfo(action,result);
		    query = "select customerid from customerinfo where member_type_code >200000  and member_type_code < 400000 and firstname is not null order by rand()  limit 1";
			result1 = Database.executeQuery(query);
		    updateCustomerInfo1(action,result1);
		    //query="select info.firstname ,info.customerid, info.email, info.mobile,info.lastname  from customerinfo info where info.member_type_code >200000  and info.member_type_code < 400000 and  info.first_app_loggedin>now() - interval 120 minute  and  info.first_app_loggedin<now() - interval 60 minute;";
			query="select customerid from customerinfo where member_type_code >200000  and member_type_code < 400000 and  first_app_loggedin>now() - interval 120 minute  and  first_app_loggedin<now() - interval 60 minute";
			result1=Database.executeQuery(query);
		 }
		else if(action.equalsIgnoreCase("StartReading"))
		{
			email = Welcome.newEmail;
			query = "select customerid from customerinfo where member_type_code in (201007,201002,202002,203002) order by rand() limit 1";
			result=Database.executeQuery(query);
			//query="select customerid from customerinfo where email='" +email+ "'";
			//query="select info.firstname ,info.customerid, info.email, info.mobile, info.lastname from customerinfo info where info.member_type_code >200000  and info.member_type_code < 400000    and  info.first_app_loggedin is not null and  DATEDIFF(Date(now()),Date(info.first_app_loggedin))=1;";
			query = "select customerid from customerinfo where member_type_code in (201007,201002,202002,203002) order by rand() limit 1";
			result1=Database.executeQuery(query);
			updateCustomerInfo(action,result);
			updateCustomerInfo1(action,result1);
		}
				
      //-------------------------------------------CONFIRMATIONS QUERIES---------------------------------------------------
	    else if(action.equalsIgnoreCase("WelcomeBackNoOffer"))
	    {
			query = "select customerid from customerinfo where member_type_code=404005 order by rand() limit 1";
			result = Database.executeQuery(query);
			query = "select status from orders where customerid=" + result + " order by orderdate desc limit 1";
			result = Database.executeQuery(query);
		}
		else if(action.equalsIgnoreCase("SubscriptionDowngrade"))
		{
			query = "select customerid from customerinfo where member_type_code=304001 order by rand() limit 1";
			result = Database.executeQuery(query);
		}
		else if(action.equalsIgnoreCase("SubscriptionUpgrade"))
		{
			query = "select customerid from customerinfo where member_type_code in (304001,203002) order by rand() limit 1";
			result = Database.executeQuery(query);
		}
		else if(action.equalsIgnoreCase("ChurnFreeCampaignMember"))
		{
			query = "select customerid from customerinfo where member_type_code in (202002,203002) order by rand() limit 1";
			result = Database.executeQuery(query);
		}
		else if(action.equalsIgnoreCase("ChurnGiftCardMember"))
		{
			query = "select customerid from customerinfo where member_type_code=203011 order by rand() limit 1";
			result = Database.executeQuery(query);
			//updateCustomer2SubscriptionMap(action, result);
			//updateTransaction(action, result);
		}
		else if(action.equalsIgnoreCase("ChurnNonPayment"))
		{
			query = "select customerid from customerinfo where member_type_code=304011 order by rand() limit 1";
			result = Database.executeQuery(query);
			//updateCustomer2SubscriptionMap(action, result);
			//updateTransaction(action, result);
		}
		else if(action.equalsIgnoreCase("ChurnPayingMember"))
		{
			query = "select customerid from customerinfo where member_type_code=304011 order by rand() limit 1";
			result = Database.executeQuery(query);
		}
		else if(action.equalsIgnoreCase("AccountTerminationExcludingNonPayment"))
		{
			query = "select customerid from customerinfo where member_type_code=304003 order by rand() limit 1";
			result = Database.executeQuery(query);
			updateCustomer2SubscriptionMap(action, result);
		}
		else if(action.equalsIgnoreCase("CardUpdatedExpiry"))
		{
			query = "select customerid from customerinfo where member_type_code in (304001,205006) order by rand() limit 1";
			result = Database.executeQuery(query);
		}
		else if(action.equalsIgnoreCase("ForgotPassword"))
		{
			query = "select customerid from customerinfo where member_type_code=304001 order by rand() limit 1";
			result = Database.executeQuery(query);
		}
		//-------------------------------------------ANTI CHURN QUERIES--------------------------------------------------
		
		else if(action.equalsIgnoreCase("FirstOutreach"))
		{ 
			query = "select customerid from customerinfo where member_type_code=100002 order by rand() limit 1";
			result = Database.executeQuery(query);
			updateCustomerInfo(action, result);
		} 
		else if (action.equalsIgnoreCase("SecondOutreach"))
		{
			query = "select customerid from customerinfo where member_type_code=100002 order by rand() limit 1";
			result = Database.executeQuery(query);
			updateCustomerInfo(action, result);
			query = "select customerid from customerinfo where member_type_code=100002 order by rand() limit 1";
			result1 = Database.executeQuery(query);
			updateCustomerInfo1(action, result);
		} 
		else if (action.equalsIgnoreCase("Inactive7Days"))
		{
			query = "select customerid from nextory_new.api_auth_tokens a join customerinfo b on a.user_id=b.customerid where a.updateddate<now() - interval 8 day and member_type_code>200000 and member_type_code<400000 order by rand() limit 1";
			result = Database.executeQuery(query);
			query = "select mailsent_once from customerinfo where customerid=" + result;
			result1 = Database.executeQuery(query);
			updateCustomerInfo(action, result);
		}
		else if (action.equalsIgnoreCase("PostChurnWinback"))
		{
			query = "select customerid from customerinfo where member_type_code in (select member_type_code from membertype_master where membertype like '%NONMEMBER%' and member_type_code>=400000) order by rand() limit 1";
			result = Database.executeQuery(query);
			query = "select concat('ISODate(*',concat(curdate() - interval 30 day,'T03:00:00.160Z'),'*)')";
			System.out.println(Database.executeQuery(query));
			// ----updateMongo();---db.nx_membership_change_log.update({"_id":17},{$set
			// : {"updateddate" : -30}})
		}
		else if (action.equalsIgnoreCase("Engagement50Per"))
		{
			query = "select customerid from customerinfo where member_type_code in (201007,202002,203002) order by rand() limit 1";
			result = Database.executeQuery(query);
			updateCustomerInfo(action, result);

		}
		else if (action.equalsIgnoreCase("Engagement90per"))
		{
			query = "select customerid from customerinfo where member_type_code in (300000,404005,201007,202002,203002) order by rand() limit 1";
			result = Database.executeQuery(query);
			updateCustomerInfo(action, result);
		}

//------------------------------------CUSTOMER EDUCATION QUERIES---------------------------------------------------
				
		else if (action.equalsIgnoreCase("Basics")) 
		{
			query = "select customerid from customerinfo where member_type_code in (202002,201007,203002) order by rand() limit 1";
			result = Database.executeQuery(query);
			updateCustomerInfo(action, result);
			log.info(result);
		} 
		else if (action.equalsIgnoreCase("FindGoodBook"))
		{
			query = "select customerid from customerinfo where member_type_code in (202002,201007,203002) order by rand() limit 1";
			result = Database.executeQuery(query);
			updateCustomerInfo(action, result);
			log.info(result);
		} 
		else if (action.equalsIgnoreCase("WhereToRead"))
        {
			query = "select customerid from customerinfo where member_type_code in (202002,201007,203002) order by rand() limit 1";
			result = Database.executeQuery(query);
			updateCustomerInfo(action, result);
			log.info(result);
		} 
		else if (action.equalsIgnoreCase("OfflineAndSleep"))
		{
			query = "select customerid from customerinfo where member_type_code in (202002,201007,203002) order by rand() limit 1";
			result = Database.executeQuery(query);
			updateCustomerInfo(action, result);
			log.info(result);
		} 
		else if (action.equalsIgnoreCase("OurDifferentPlans")) 
		{
			query = "select customerid from customerinfo where member_type_code in (202002,201007,203002) order by rand() limit 1";
			result = Database.executeQuery(query);
			updateCustomerInfo(action, result);
			log.info(result);
		}
				
 //---------------------------------------PAYMENT ISSUES--------------------------------------------------------------
		else if (action.equalsIgnoreCase("CardProactive1"))
		{
			query = "select customerid from customerinfo where member_type_code in (304001,202002) order by rand() limit 1";
			result = Database.executeQuery(query);
			updateTransaction(action, result);
		} 
		else if (action.equalsIgnoreCase("CardProactive2"))
		{
			query = "select customerid from customerinfo where member_type_code in (304001,202002) order by rand() limit 1";
			result = Database.executeQuery(query);
			updateTransaction(action, result);
		}
		else if (action.equalsIgnoreCase("CardProactive3"))
		{
			query = "select customerid from customerinfo where member_type_code in (304001,202002) order by rand() limit 1";
			result = Database.executeQuery(query);
			updateTransaction(action, result);
		}
		else if (action.equalsIgnoreCase("CancelledTx1")) 
		{
			query = "select customerid from customerinfo where member_type_code in (304001,202002) order by rand() limit 1";
			result = Database.executeQuery(query);
			//updateCustomer2SubscriptionMap(action, result);
		} 
		else if (action.equalsIgnoreCase("CancelledTx2")) 
		{
			query = "select customerid from customerinfo where member_type_code in (304011,203011) order by rand() limit 1";
			result = Database.executeQuery(query);
			//updateTransaction(action, result);
		}
	//----------------------------------GIFT CARD-------------------------------------------------------
		else if (action.equalsIgnoreCase("GC1_AddCardDetails")) 
		{
			// 101001 --> Visitor GC Buyer
			query = "select customerid from customerinfo where member_type_code=101001 order by rand() limit 1";
			result = Database.executeQuery(query);
			updateCustomer2SubscriptionMap(action, result);
		} 
		else if (action.equalsIgnoreCase("GC2_AddCardDetails"))
		{
			// 201002 --> Free Gift Card NoCardInfo
			query = "select customerid from customerinfo where member_type_code=201002 order by rand() limit 1";
			result = Database.executeQuery(query);
			updateCustomer2SubscriptionMap(action, result);
		} 
		else if (action.equalsIgnoreCase("OrderConfirmation"))
		{
			query = "select customerid from customerinfo where member_type_code=201002 order by rand() limit 1";
			result = Database.executeQuery(query);
		}
		return result;
	}
//--------------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------------
	public String getDataForSubscriptionMaster(String action) 
	{
		return null;
	}
//--------------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------------	
	public String getDataForMembership_change_logMongo(String action)
	{
		return null;
	}
//--------------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------------
	public String getDataForLibraryMongo(String action) 
	{
		return null;
	}
//--------------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------------	
	public String getDataForNxCustomerEmailLogsMongo(String action) 
	{
		return null;
	}
//--------------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------------
	public String updateCustomerInfo(String action, String result) 
	{
		if(action.equalsIgnoreCase("FindBook"))
    	{
    	  	query="update customerinfo set first_app_loggedin= (now() - interval 60 minute) where customerid=" +result;
    	  	Database.executeUpdate(query);
    	}
    	else if(action.equalsIgnoreCase("DownloadApp"))
    	{ }
    	else if(action.equalsIgnoreCase("StartReading"))
    	{
    		query="update customerinfo set first_app_loggedin=now() - interval 23 hour where customerid=" +result;
    		Database.executeUpdate(query);
    	}
    	else if(action.equalsIgnoreCase("SurpriseAndDelight"))
    	{ }
    	
    	//---------------------------------------ANTI CHURN QUERIES---------------------------------------------------------
    	else if(action.equalsIgnoreCase("FirstOutreach"))
    	{
    		query="update customerinfo set createdon=now() - interval 60 minute where customerid=" +result;
    		Database.executeUpdate(query);
    	}
    	else if(action.equalsIgnoreCase("SecondOutreach"))
    	{
    		query="update customerinfo set createdon=now() - interval 47 minute where customerid=" +result;
    		Database.executeUpdate(query);
    	}
    	else if(action.equalsIgnoreCase("Inactive7Days"))
    	{
    		query="update nextory_new.api_auth_tokens set updateddate=now() - interval 8 day where user_id="+result;
    		Database.executeUpdate(query);
    	}
    	else if(action.equalsIgnoreCase("Engagement50Per"))
    	{
    		query="update customerinfo set member_type_code=304001,is50perc_mailsent=1,is90perc_mailsent=0 where customerid="+result;
    	    Database.executeUpdate(query);
    	}
    	else if(action.equalsIgnoreCase("Engagement90Per"))
    	{
    		query="update customerinfo set member_type_code=203002,is50perc_mailsent=1,is90perc_mailsent=1 where customerid="+result;
            Database.executeUpdate(query);
    	}
   //----------------------------------------CUSTOMER EDUCATION-----------------------------------------------
    	else if(action.equalsIgnoreCase("Basics"))
    	{
    	    query="update customerinfo set mailsent_once='|Education1|' where customerid= "+result;
    	    Database.executeUpdate(query);
    	}
    	else if(action.equalsIgnoreCase("FindGoodBook"))
    	{
    	    query="update customerinfo set mailsent_once='|Education2|' where customerid= "+result;
    	    Database.executeUpdate(query);
    	}
    	else if(action.equalsIgnoreCase("OfflineAndSleep"))
    	{
    	    query="update customerinfo set mailsent_once='|Education3|' where customerid= "+result;
    	    Database.executeUpdate(query);
    	}
    	else if(action.equalsIgnoreCase("OurDifferentPlans"))
    	{
    	    query="update customerinfo set mailsent_once='|Education4|' where customerid= "+result;
    	    Database.executeUpdate(query);
    	}
    	else if(action.equalsIgnoreCase("WhereToRead"))
    	{
    	    query="update customerinfo set mailsent_once='|Education5|' where customerid= "+result;
    	    Database.executeUpdate(query);
    	}
		return result;
	}
	//-----==========================================================================================================  
	public String updateCustomerInfo1(String action, String result) 
	{
		if(action.equalsIgnoreCase("FindBook"))
    	{
    	  	query="update customerinfo set first_app_loggedin= (now() - interval 120 minute) where customerid=" +result;
    	  	Database.executeUpdate(query);
    	}
    	else if(action.equalsIgnoreCase("StartReading"))
    	{
    		query="update customerinfo set first_app_loggedin=now() - interval 48 hour where customerid=" +result;
    		Database.executeUpdate(query);
    	}
    	else if(action.equalsIgnoreCase("SurpriseAndDelight"))
    	{ }
    	
    	//---------------------------------------ANTI CHURN QUERIES---------------------------------------------------------
    	/*else if(action.equalsIgnoreCase("FirstOutreach"))
    	{
    		query="update customerinfo set createdon=now() - interval 60 minute where customerid=" +result;
    		Database.executeUpdate(query);
    	}*/
    	else if(action.equalsIgnoreCase("SecondOutreach"))
    	{
    		query="update customerinfo set createdon=now() - interval 73 minute where customerid=" +result;
    		Database.executeUpdate(query);
    	}
    /*	else if(action.equalsIgnoreCase("Inactive7Days"))
    	{
    		query="update nextory_new.api_auth_tokens set updateddate=now() - interval 7 day where user_id="+result;
    		Database.executeUpdate(query);
    	}*/
    	else if(action.equalsIgnoreCase("Engagement50Per"))
    	{
    		query="update customerinfo set member_type_code=304001,is50perc_mailsent=0,is90perc_mailsent=0 where customerid="+result;
    	    Database.executeUpdate(query);
    	}
    	else if(action.equalsIgnoreCase("Engagement90Per"))
    	{
    		query="update customerinfo set member_type_code=203002,is50perc_mailsent=1,is90perc_mailsent=0 where customerid="+result;
            Database.executeUpdate(query);
    	}
   //----------------------------------------CUSTOMER EDUCATION-----------------------------------------------
    	/*else if(action.equalsIgnoreCase("Basics"))
    	{
    	    query="update customerinfo set mailsent_once='' where customerid= "+result;
    	    Database.executeUpdate(query);
    	}
    	else if(action.equalsIgnoreCase("FindGoodBook"))
    	{
    	    query="update customerinfo set mailsent_once='' where customerid= "+result;
    	    Database.executeUpdate(query);
    	}
    	else if(action.equalsIgnoreCase("OfflineAndSleep"))
    	{
    	    query="update customerinfo set mailsent_once='' where customerid= "+result;
    	    Database.executeUpdate(query);
    	}
    	else if(action.equalsIgnoreCase("OurDifferentPlans"))
    	{
    	    query="update customerinfo set mailsent_once='' where customerid= "+result;
    	    Database.executeUpdate(query);
    	}
    	else if(action.equalsIgnoreCase("WhereToRead"))
    	{
    	    query="update customerinfo set mailsent_once='' where customerid= "+result;
    	    Database.executeUpdate(query);
    	}*/
		return result;
	}
//--------------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------------	
	public String updateOrders(String action, String result) 
	{
		if (action.equalsIgnoreCase("SurpriseDelight")) 
		{
			query = "update orders set orderdate=now() - interval 20 minute where customerid=" + result;
			Database.executeUpdate(query);
			/*query = "update orders set orderdate=now() - interval 120 minute where customerid=" + result1;
			Database.executeUpdate(query);*/
		}
		return result;
	}
	public String updateOrders1(String action, String result1) 
	{
		if (action.equalsIgnoreCase("SurpriseDelight")) 
		{
			/*query = "update orders set orderdate=now() - interval 20 minute where customerid=" + result;
			Database.executeUpdate(query);*/
			query = "update orders set orderdate=now() - interval 120 minute where customerid=" + result1;
			Database.executeUpdate(query);
		}
		return result;
	}
	
//--------------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------------	
	public String updateCustomer2SubscriptionMap(String action, String result) 
	{
		if(action.equalsIgnoreCase("DownloadApp"))
		{
		query="update customer2subscriptionmap set subscribedon=date(now() - interval 23 hour) where customerid=" +result;
		Database.executeUpdate(query);
		}
		/*else if(action.equalsIgnoreCase("ChurnGiftCardMember"))
		{
			query="update customer2subscriptionmap set next_subscription_run_date=DATE_sub(curdate(), INTERVAL 7 DAY) where customerid=" +result;
			Database.executeUpdate(query);
		}
		else if(action.equalsIgnoreCase("ChurnNonPayment"))
		{
			query="update customer2subscriptionmap set next_subscription_run_date=(now() - interval 29 day) where customerid=" +result;
			Database.executeUpdate(query);
		}*/
		else if(action.equalsIgnoreCase("AccountTerminationExcludingNonPayment"))
		{
			query="update customer2subscriptionmap set next_subscription_run_date=now() + interval 1 day where customerid=" +result;
			Database.executeUpdate(query);
		}
		/*else if(action.equalsIgnoreCase("CancelledTx1"))
		{
			query="update customer2subscriptionmap set next_subscription_run_date=curdate() where customerid=" +result;
			Database.executeUpdate(query);
		}*/
		else if(action.equalsIgnoreCase("GC1AddCardDetails"))
		{
			query="update customer2subscriptionmap set next_subscription_run_date=curdate() - interval 8 day where customerid=" +result;
			Database.executeUpdate(query);
		}
		else if(action.equalsIgnoreCase("GC2AddCardDetails"))
		{
			query="update customer2subscriptionmap set next_subscription_run_date=curdate() - interval 3 day where customerid=" +result;
			Database.executeUpdate(query);
		}
		else if(action.equalsIgnoreCase("CancelledTx1"))
		{
		    query="update customer2subscriptionmap set next_subscription_run_date=currentdate where customerid="+result;
		    Database.executeQuery(query);
		}
		return action;
	}
	public String updateCustomer2SubscriptionMap1(String action, String result) 
	{
		if(action.equalsIgnoreCase("DownloadApp"))
		{
		query="update customer2subscriptionmap set subscribedon=date(now() - interval 48 hour) where customerid=" +result;
		Database.executeUpdate(query);
		}
		else if(action.equalsIgnoreCase("ChurnGiftCardMember"))
		{
			query="update customer2subscriptionmap set next_subscription_run_date=DATE_sub(curdate(), INTERVAL 7 DAY) where customerid=" +result;
			Database.executeUpdate(query);
		}
		else if(action.equalsIgnoreCase("ChurnNonPayment"))
		{
			query="update customer2subscriptionmap set next_subscription_run_date=(now() - interval 29 day) where customerid=" +result;
			Database.executeUpdate(query);
		}
		else if(action.equalsIgnoreCase("AccountTerminationExcludingNonPayment"))
		{
			query="update customer2subscriptionmap set next_subscription_run_date=now() - interval 1 day where customerid=" +result;
			Database.executeUpdate(query);
		}
		else if(action.equalsIgnoreCase("CancelledTx1"))
		{
			query="update customer2subscriptionmap set next_subscription_run_date=curdate() where customerid=" +result;
			Database.executeUpdate(query);
		}
		else if(action.equalsIgnoreCase("GC1AddCardDetails"))
		{
			query="update customer2subscriptionmap set next_subscription_run_date=curdate() - interval 7 day where customerid=" +result;
			Database.executeUpdate(query);
		}
		else if(action.equalsIgnoreCase("OrderConfirmation"))
		{
			query="update customer2subscriptionmap set next_subscription_run_date=curdate() - interval 2 day where customerid=" +result;
			Database.executeUpdate(query);
		}
		else if(action.equalsIgnoreCase("CancelledTx1"))
		{
		    query="update customer2subscriptionmap set next_subscription_run_date=currentdate where customerid="+result;
		    Database.executeQuery(query);
		}
		return action;
	}
//--------------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------------	
	public String updateTransaction(String action, String result) 
	{
		if(action.equalsIgnoreCase("ChurnGiftCardMember"))
		{
		 query="update transaction set failed_mail_count=2 where customerid=" +result;
		 Database.executeUpdate(query);
		}
		else if(action.equalsIgnoreCase("ChurnNonPayment"))
		{
			query="update transaction set failed_mail_count=6,lasttxnstatus='FAILED'  where customerid=" +result;
			Database.executeUpdate(query);
		}
		else if(action.equalsIgnoreCase("CardProactive1"))
		{
		   query="update transaction set expire_day=substr(date_add(now(), INTERVAL 31 DAY),9,2),expire_month=substr(date_add(now(), INTERVAL 31 DAY),6,2),expire_year=substr(date_add(now(), INTERVAL 31 DAY),1,4) where customerid=" +result;
		   Database.executeUpdate(query);
		}
		else if(action.equalsIgnoreCase("CardProactive2"))
		{
			query="update transaction set expire_day=substr(date_add(now(), INTERVAL 15 DAY),9,2),expire_month=substr(date_add(now(), INTERVAL 15 DAY),6,2),expire_year=substr(date_add(now(), INTERVAL 15 DAY),1,4) where customerid=" +result;
			Database.executeUpdate(query);
		}
		else if(action.equalsIgnoreCase("CardProactive3"))
		{
			query="update transaction set expire_day=substr(date_add(now(), INTERVAL 8 DAY),9,2),expire_month=substr(date_add(now(), INTERVAL 8 DAY),6,2),expire_year=substr(date_add(now(), INTERVAL 8 DAY),1,4) where customerid=" +result;
			Database.executeUpdate(query);
		}
		else if(action.equalsIgnoreCase("CancelledTx2"))
		{
			query="Update transaction set failed_mail_count=1,lasttxnstatus='FAILED',failed_mail_sent_date=DATE_sub(curdate(), INTERVAL 3 DAY)  where customerid=" +result;
			Database.executeUpdate(query);
			query="update customer2subscriptionmap set next_subscription_run_date=DATE_sub(curdate(), INTERVAL 3 DAY) where customerid="+result;
			Database.executeUpdate(query);
		}
		return action;
	}
//--------------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------------	
	public String updateMembership_change_LogMongo(String action, String result)
	{
		return null;
	}
//--------------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------------		
}
