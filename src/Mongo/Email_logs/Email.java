package Mongo.Email_logs;

import java.util.Date;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity(value="nx_customer_email_logs",noClassnameStored = true)
public class Email
{
  @Id
  private String id;
  
  private String from;
  
  private String customerid;
  
  private String mobilenumber;
  
  private String firstname;
  
  private String lastname;
  
  private String to;
  
  private String subject;
  
  private Date mailsentdate;
  
  private String reason;
  
  private String triggerName;

  public String getTriggerName()
  {
	  return triggerName;
  }
  
  public void setTriggerName()
  {
	  this.triggerName=triggerName;
  }
  
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

public String getCustomerid() 
{
	return customerid;
}

public void setCustomerid(String customerid) 
{
	this.customerid = customerid;
}

public String getMobilenumber() 
{
	return mobilenumber;
}

public void setMobilenumber(String mobilenumber) 
{
	this.mobilenumber = mobilenumber;
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

public Date getMailsentdate() 
{
	return mailsentdate;
}

public void setMailsentdate(Date mailsentdate) 
{
	this.mailsentdate = mailsentdate;
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
