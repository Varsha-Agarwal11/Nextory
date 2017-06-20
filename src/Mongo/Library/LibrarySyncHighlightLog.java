package Mongo.Library;

import java.util.Date;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class LibrarySyncHighlightLog 
{
	public Integer chapterindex;
	
	public Integer formattype;
	
	public Integer startindex;
	
	public Integer startoffset;
	
	public Integer endindex;
	
	public Integer endoffset;
	
	public String pagepositioninchapter;
	
	public String pagepositioninbook;
	
	public Integer color;
	
	public String text;
	
	public String note;
	
	public Integer isnote;
	
	private String cfi;
	private String idref;
	
	//NEX-2666 code starts 
	private String title ;
	private Date datetime;
	
	
	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	//NEX-2666 code ends
	
	
	public Integer getChapterindex() {
		return chapterindex;
	}

	public void setChapterindex(Integer chapterindex) {
		this.chapterindex = chapterindex;
	}

	public Integer getStartindex() {
		return startindex;
	}

	public void setStartindex(Integer startindex) {
		this.startindex = startindex;
	}

	public Integer getStartoffset() {
		return startoffset;
	}

	public void setStartoffset(Integer startoffset) {
		this.startoffset = startoffset;
	}

	public Integer getEndindex() {
		return endindex;
	}

	public void setEndindex(Integer endindex) {
		this.endindex = endindex;
	}

	public Integer getEndoffset() {
		return endoffset;
	}

	public void setEndoffset(Integer endoffset) {
		this.endoffset = endoffset;
	}

	public String getPagepositioninchapter() {
		return pagepositioninchapter;
	}

	public void setPagepositioninchapter(String pagepositioninchapter) {
		this.pagepositioninchapter = pagepositioninchapter;
	}

	public String getPagepositioninbook() {
		return pagepositioninbook;
	}

	public void setPagepositioninbook(String pagepositioninbook) {
		this.pagepositioninbook = pagepositioninbook;
	}

	public Integer getColor() {
		return color;
	}

	public void setColor(Integer color) {
		this.color = color;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getIsnote() {
		return isnote;
	}

	public void setIsnote(Integer isnote) {
		this.isnote = isnote;
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
