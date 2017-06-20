package Bean;

public class CustomerInfo 
{
	private int customerid;
	private String firstname;
	private String lastname;
	private String member_type_code;
	private String email;
	private String mobile;
	private String first_app_loggedin;
	private String createdOn;
	private String mail_sent_once;
	private String is_90per_mailsent;
	private String is_50per_mailsent;

	public String getLastname() 
	{
		return lastname;
	}

	public void setLastname(String lastname)
	{
		this.lastname = lastname;
	}

	public String getMember_type_code() 
	{
		return member_type_code;
	}

	public void setMember_type_code(String member_type_code) 
	{
		this.member_type_code = member_type_code;
	}

	public String getEmail() 
	{
		return email;
	}

	public void setEmail(String email) 
	{
		this.email = email;
	}

	public String getMobile() 
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	public String getFirst_app_loggedin() 
	{
		return first_app_loggedin;
	}

	public void setFirst_app_loggedin(String first_app_loggedin) 
	{
		this.first_app_loggedin = first_app_loggedin;
	}

	public String getCreatedOn() 
	{
		return createdOn;
	}

	public void setCreatedOn(String createdOn) 
	{
		this.createdOn = createdOn;
	}

	public String getMail_sent_once() 
	{
		return mail_sent_once;
	}

	public void setMail_sent_once(String mail_sent_once) 
	{
		this.mail_sent_once = mail_sent_once;
	}

	public String getIs_90per_mailsent() 
	{
		return is_90per_mailsent;
	}

	public void setIs_90per_mailsent(String is_90per_mailsent) 
	{
		this.is_90per_mailsent = is_90per_mailsent;
	}

	public String getIs_50per_mailsent() 
	{
		return is_50per_mailsent;
	}

	public void setIs_50per_mailsent(String is_50per_mailsent) 
	{
		this.is_50per_mailsent = is_50per_mailsent;
	}

	public int getCustomerid() 
	{
		return customerid;
	}

	public String getFirstname() 
	{
		return firstname;
	}

	public void setFirstname(String firstname) 
	{
		this.firstname = firstname;
	}
}
