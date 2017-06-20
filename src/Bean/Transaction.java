package Bean;

public class Transaction 
{
	private String failed_mail_count;
	private String lasttxnstatus;
	private String failed_mail_sent_date;
	private String customerId;
	private String expiryDate;
	private String expiryYear;
	private String expiryMonth;
	
	public String getFailed_mail_count() 
	{
		return failed_mail_count;
	}
	public void setFailed_mail_count(String failed_mail_count) 
	{
		this.failed_mail_count = failed_mail_count;
	}
	public String getLasttxnstatus() 
	{
		return lasttxnstatus;
	}
	public void setLasttxnstatus(String lasttxnstatus) 
	{
		this.lasttxnstatus = lasttxnstatus;
	}
	public String getFailed_mail_sent_date() 
	{
		return failed_mail_sent_date;
	}
	public void setFailed_mail_sent_date(String failed_mail_sent_date) 
	{
		this.failed_mail_sent_date = failed_mail_sent_date;
	}
	public String getCustomerId() 
	{
		return customerId;
	}
	public void setCustomerId(String customerId) 
	{
		this.customerId = customerId;
	}
	public String getExpiryDate() 
	{
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) 
	{
		this.expiryDate = expiryDate;
	}
	public String getExpiryYear() 
	{
		return expiryYear;
	}
	public void setExpiryYear(String expiryYear) 
	{
		this.expiryYear = expiryYear;
	}
	public String getExpiryMonth() 
	{
		return expiryMonth;
	}
	public void setExpiryMonth(String expiryMonth) 
	{
		this.expiryMonth = expiryMonth;
	}
}
