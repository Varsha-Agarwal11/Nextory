package Bean;

public class NxCustomerEmailLogsMongo 
{
	private String id;
	private String from;
	private String customerId;
	private String mobileNum;
	private String firstname;
	private String lastname;
	private String to;
	private String subject;
	private String mailSentDate;
	private String reason;
	
	public String getId()
	{
		return id;
	}
	public void setId(String id) 
	{
		this.id = id;
	}
	public String getFrom()
    {
		return from;
	}
	public void setFrom(String from) 
	{
		this.from = from;
	}
	public String getCustomerId() 
	{
		return customerId;
	}
	public void setCustomerId(String customerId)
	{
		this.customerId = customerId;
	}
	public String getMobileNum() 
	{
		return mobileNum;
	}
	public void setMobileNum(String mobileNum)
	{
		this.mobileNum = mobileNum;
	}
	public String getFirstname() 
	{
		return firstname;
	}
	public void setFirstname(String firstname) 
	{
		this.firstname = firstname;
	}
	public String getLastname() 
	{
		return lastname;
	}
	public void setLastname(String lastname)
	{
		this.lastname = lastname;
	}
	public String getTo() 
	{
		return to;
	}
	public void setTo(String to)
	{
		this.to = to;
	}
	public String getSubject()
	{
		return subject;
	}
	public void setSubject(String subject) 
	{
		this.subject = subject;
	}
	public String getMailSentDate() 
	{
		return mailSentDate;
	}
	public void setMailSentDate(String mailSentDate) 
	{
		this.mailSentDate = mailSentDate;
	}
	public String getReason()
	{
		return reason;
	}
	public void setReason(String reason)
	{
		this.reason = reason;
	}
}
