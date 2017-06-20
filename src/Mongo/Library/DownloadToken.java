package Mongo.Library;

import java.util.Date;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class DownloadToken
{	
	public String title;
	
	public Integer format;
	
	public Integer part;
	
	public String name;
	
	public String size;
	
	public String sizetype;
	
	public String token;
	
	public String filechecksum;
	
	public Date ordercreateddate;

	public String getTitle() 
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public Integer getFormat() 
	{
		return format;
	}

	public void setFormat(Integer format) 
	{
		this.format = format;
	}

	public Integer getPart() {
		return part;
	}

	public void setPart(Integer part) {
		this.part = part;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getSizetype() {
		return sizetype;
	}

	public void setSizetype(String sizetype) {
		this.sizetype = sizetype;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getFilechecksum() {
		return filechecksum;
	}

	public void setFilechecksum(String filechecksum) {
		this.filechecksum = filechecksum;
	}

	public Date getOrdercreateddate() {
		return ordercreateddate;
	}

	public void setOrdercreateddate(Date ordercreateddate) {
		this.ordercreateddate = ordercreateddate;
	}
	
	
	

}
