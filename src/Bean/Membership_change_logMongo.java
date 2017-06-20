package Bean;

public class Membership_change_logMongo 
{
	private String customerId;
	private String updatedDate;

	public String getUpdatedDate() 
	{
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) 
	{
		this.updatedDate = updatedDate;
	}

	public String getCustomerId() 
	{
		return customerId;
	}

	public void setCustomerId(String customerId) 
	{
		this.customerId = customerId;
	}
}
