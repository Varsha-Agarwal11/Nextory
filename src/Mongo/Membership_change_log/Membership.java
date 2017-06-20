package Mongo.Membership_change_log;

import java.util.Date;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity(value="nx_membership_change_log",noClassnameStored = true)
public class Membership 
{
  @Id
  private String id;
  
  private String customerid;
  private Integer mem_type_code_previous;
  private Date updateddate;
  private String mem_type_code_old;
  private String mem_type_code_new;
  
  public String getCustomerid()
  {
	return customerid;
  }

  public void setCustomerid(String customerid) 
  {
	this.customerid = customerid;
  }

public String getMem_type_code_old() 
{
	return mem_type_code_old;
}

public void setMem_type_code_old(String mem_type_code_old) 
{
	this.mem_type_code_old = mem_type_code_old;
}

public String getMem_type_code_new() 
{
	return mem_type_code_new;
}

public void setMem_type_code_new(String mem_type_code_new) 
{
	this.mem_type_code_new = mem_type_code_new;
}

public Date getUpdateddate() {
	return updateddate;
}

public void setUpdateddate(Date updateddate) {
	this.updateddate = updateddate;
}

public String getId() {
	return id;
}

public void setId(String id) {
	this.id = id;
}

public Integer getMem_type_code_previous() {
	return mem_type_code_previous;
}

public void setMem_type_code_previous(Integer mem_type_code_previous) {
	this.mem_type_code_previous = mem_type_code_previous;
}


   
}
