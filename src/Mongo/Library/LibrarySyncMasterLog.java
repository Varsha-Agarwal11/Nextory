package Mongo.Library;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Transient;

@Embedded
public class LibrarySyncMasterLog {

	private Integer formattype;
	
	private String percentage;
	
	private String highpercentage;
	
	private String position;
	
	private Double lastcurperc;
	
	private Date createddate;
	
	private Date updatedate;
	
	private Date persyncdate;
	
	private Date detsyncdate;

	private Date syncdateforbookmarks;
	
	private Date syncdateforhighlights;
	
	private String cfi;
	private String idref;

	@Transient
	private String strpersyncdate;
	
	@Transient
	private String strdetsyncdate;
	@Transient
	private String strsyncdateforbookmarks;
	@Transient
	private String strsyncdateforhighlights;

	
	private Integer fixforveriosfour;

	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

	public String getHighpercentage() {
		return highpercentage;
	}

	public void setHighpercentage(String highpercentage) {
		this.highpercentage = highpercentage;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Double getLastcurperc() {
		return lastcurperc;
	}

	public void setLastcurperc(Double lastcurperc) {
		this.lastcurperc = lastcurperc;
	}

	public Date getPersyncdate() {
		return persyncdate;
	}

	public void setPersyncdate(Date persyncdate) {
		this.persyncdate = persyncdate;
	}

	public Date getDetsyncdate() {
		return detsyncdate;
	}

	public void setDetsyncdate(Date detsyncdate) {
		this.detsyncdate = detsyncdate;
	}

	public Date getCreateddate() {
		return createddate;
	}

	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}

	public Date getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	public Date getSyncdateforbookmarks() {
		return syncdateforbookmarks;
	}

	public void setSyncdateforbookmarks(Date syncdateforbookmarks) {
		this.syncdateforbookmarks = syncdateforbookmarks;
	}

	public Date getSyncdateforhighlights() {
		return syncdateforhighlights;
	}

	public void setSyncdateforhighlights(Date syncdateforhighlights) {
		this.syncdateforhighlights = syncdateforhighlights;
	}

	public Integer getFixforveriosfour() {
		return fixforveriosfour;
	}

	public void setFixforveriosfour(Integer fixforveriosfour) {
		this.fixforveriosfour = fixforveriosfour;
	}

	public Integer getFormattype() {
		return formattype;
	}

	public void setFormattype(Integer formattype) {
		if(formattype !=null && formattype ==9)
			this.formattype = 105;
		else if(formattype !=null && formattype ==11)
			this.formattype = 102;
		else if(formattype !=null && formattype ==22)
			this.formattype = 303;
		else
			this.formattype = formattype;
	}

	
	
	public void setStrpersyncdate(String strpersyncdate) {
		if(strpersyncdate!=null){
			String SYNC_DATE_INPUT_PATTERN="MM-dd-yyyy HH:mm:ss Z";
			org.joda.time.format.DateTimeFormatter SYNC_DATE_INPUT_FORMAT = DateTimeFormat.forPattern(SYNC_DATE_INPUT_PATTERN);
			DateTime temp = SYNC_DATE_INPUT_FORMAT.withOffsetParsed().parseDateTime(strpersyncdate);
			this.persyncdate = temp.toDate();
			this.strpersyncdate = strpersyncdate;
		}
	}

	public void setStrdetsyncdate(String strdetsyncdate) {
		if(strdetsyncdate !=null){
			String SYNC_DATE_INPUT_PATTERN="MM-dd-yyyy HH:mm:ss Z";
			org.joda.time.format.DateTimeFormatter SYNC_DATE_INPUT_FORMAT = DateTimeFormat.forPattern(SYNC_DATE_INPUT_PATTERN);
			DateTime temp = SYNC_DATE_INPUT_FORMAT.withOffsetParsed().parseDateTime(strdetsyncdate);
			this.detsyncdate = temp.toDate();
			this.strdetsyncdate = strdetsyncdate;
		}
		
	}
	
	public void setStrsyncdateforbookmarks(String strsyncdateforbookmarks) {
		if(strsyncdateforbookmarks!=null){
			String SYNC_DATE_INPUT_PATTERN="MM-dd-yyyy HH:mm:ss Z";
			org.joda.time.format.DateTimeFormatter SYNC_DATE_INPUT_FORMAT = DateTimeFormat.forPattern(SYNC_DATE_INPUT_PATTERN);
			DateTime temp = SYNC_DATE_INPUT_FORMAT.withOffsetParsed().parseDateTime(strsyncdateforbookmarks);
			this.syncdateforbookmarks = temp.toDate();
			this.strsyncdateforbookmarks = strsyncdateforbookmarks;
		}
	}

	public void setStrsyncdateforhighlights(String strsyncdateforhighlights) {
		//this.strsyncdateforhighlights = strsyncdateforhighlights;
		if(strsyncdateforhighlights!=null){
			String SYNC_DATE_INPUT_PATTERN="MM-dd-yyyy HH:mm:ss Z";
			org.joda.time.format.DateTimeFormatter SYNC_DATE_INPUT_FORMAT = DateTimeFormat.forPattern(SYNC_DATE_INPUT_PATTERN);
			DateTime temp = SYNC_DATE_INPUT_FORMAT.withOffsetParsed().parseDateTime(strsyncdateforhighlights);
			this.syncdateforhighlights = temp.toDate();
			this.strsyncdateforhighlights = strsyncdateforhighlights;
		}
	}

	public String getCfi() {
		return cfi;
	}

	public void setCfi(String cfi) {
		this.cfi = cfi;
	}

	public String getIdref() {
		return idref;
	}

	public void setIdref(String idref) {
		this.idref = idref;
	}
	
	
	
}
