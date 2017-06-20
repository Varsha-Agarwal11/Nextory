package Mongo.Library;

import java.util.Date;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class LibraryTrackLog {

	public Integer percentage;
	
	public Date createdate;
	
	public Date updatedate;
	
}
