package Mongo.Library;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Transient;

@Embedded
public class LibrarySyncBookmarkLog 
{	
	public Integer formattype;
	
	public Integer chapterindex;
	
	public String pagepositioninchapter;
	
	public String pagepositioninbook;
	
	public String chaptername;
	
	public String contentheight;
	
	public String bookname;
	
	public String title;
	
	public String type;
	
	public Date datetime;
	@Transient
	public String strdatetime;
	
	public String code;

	private String cfi;
	private String idref;
	
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

	public Integer getChapterindex() {
		return chapterindex;
	}

	public void setChapterindex(Integer chapterindex) {
		this.chapterindex = chapterindex;
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

	public String getChaptername() {
		return chaptername;
	}

	public void setChaptername(String chaptername) {
		this.chaptername = chaptername;
	}

	public String getContentheight() {
		return contentheight;
	}

	public void setContentheight(String contentheight) {
		this.contentheight = contentheight;
	}

	public String getBookname() {
		return bookname;
	}

	public void setBookname(String bookname) {
		this.bookname = bookname;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getDatetime() {
		return datetime;
	}
	public void setDatetime(Date datetime) {
		this.datetime =datetime;
	}
	
	
	public String getStrdatetime() {
		return strdatetime;
	}

	public void setStrdatetime(String strdatetime) {
		String SYNC_DATE_INPUT_PATTERN="MM-dd-yyyy HH:mm:ss Z";
		org.joda.time.format.DateTimeFormatter SYNC_DATE_INPUT_FORMAT = DateTimeFormat.forPattern(SYNC_DATE_INPUT_PATTERN);
		DateTime temp = SYNC_DATE_INPUT_FORMAT.withOffsetParsed().parseDateTime(strdatetime);
		this.datetime = temp.toDate();
		this.strdatetime = strdatetime;
	}
	/*public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}*/

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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
	
	@Override
	public String toString() {
		return "LibrarySyncBookmark [chapterindex=" + chapterindex
				+ ", pagepositioninchapter=" + pagepositioninchapter
				+ ", pagepositioninbook=" + pagepositioninbook
				+ ", chaptername=" + chaptername + ", contentheight="
				+ contentheight + ", bookname=" + bookname + ", title=" + title
				+ ", type=" + type + ", formattype=" + formattype + ", datetime="
				+ datetime + ", code=" + code + ", strdatetime="+strdatetime+"]";
	}
	

}
