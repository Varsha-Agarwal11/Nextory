package interfaceForApiOrDB;

public interface InformationFromBackendInterface 
{	
	public String getDataForCustomerInfo(String action);
	
	public String getDataForSubscriptionMaster(String action);
	
	public String getDataForMembership_change_logMongo(String action );
	
	public String getDataForLibraryMongo(String action);
	
	public String getDataForNxCustomerEmailLogsMongo(String action);
	
	public String updateCustomerInfo(String action,String result);
	
	public String updateOrders(String action,String result);
	
	public String updateCustomer2SubscriptionMap(String action,String result);
	
	public String updateTransaction(String action,String result);
	
	public String updateMembership_change_LogMongo(String action,String result);
}
