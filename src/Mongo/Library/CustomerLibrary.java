package Mongo.Library;

import java.util.Date;
import java.util.List;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity(value="library",noClassnameStored = true)
public class CustomerLibrary
{
	@Id
	private Integer libid;
	
	private String isbn;
	
	private Integer customerid;
	
	private Integer bookid;
	
	private Integer provider_productid;
	
	private String title;
	
	private Integer booktype;
	
	private String storagelocation;
	
	private String orderref;
	
	private Integer orderid;
	
	private Date createddate;
	
	private Integer errorcode;
	
	private Integer retry;
	
	private String providerorderid;
	
	private String status;
	
	private String purchasetype;
	
	private Integer downloadcount;
	
	private Date orderdate;
	
	private Date lastupdateddate;
	
	private String readstatus;
	
	private Date readcompleteddate;
	
	private Integer pubassid;
	
	private String addedas;
	
	private String allowedfor;
	
	private Integer allowedconfirmation;
	
	private Integer bookstatuscode;
	
	private boolean migrated;
	
	@Embedded
	private DownloadToken downloadtoken;
	
	/*@Embedded
	public List<LibraryTrackLog> librarytracklog;*/
	
	@Embedded
	private LibrarySyncSettingLog librarysyncsettinglog;
	
	/*@Embedded
	public List<LibrarySyncPagingLog> librarysyncpaginglog;
	*/
	@Embedded
	private LibrarySyncMasterLog librarysyncmasterlog;
	
	@Embedded
	private List<LibrarySyncHighlightLog> librarysynchighlightlog;
	
	@Embedded
	private List<LibrarySyncBookmarkLog> librarysyncbookmarklog;

	public Integer getLibid() {
		return libid;
	}

	public void setLibid(Integer libid) {
		this.libid = libid;
	}

	
	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Integer getCustomerid() {
		return customerid;
	}

	public void setCustomerid(Integer customerid) {
		this.customerid = customerid;
	}

	public Integer getBookid() {
		return bookid;
	}

	public void setBookid(Integer bookid) {
		this.bookid = bookid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	

	public Integer getBooktype() {
		return booktype;
	}

	public void setBooktype(Integer booktype) {
		this.booktype = booktype;
	}
	
	

	public Integer getProvider_productid() {
		return provider_productid;
	}

	public void setProvider_productid(Integer provider_productid) {
		this.provider_productid = provider_productid;
	}

	public String getStoragelocation() {
		return storagelocation;
	}

	public void setStoragelocation(String storagelocation) {
		this.storagelocation = storagelocation;
	}

	public String getOrderref() {
		return orderref;
	}

	public void setOrderref(String orderref) {
		this.orderref = orderref;
	}

	public Integer getOrderid() {
		return orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	public Date getCreateddate() {
		return createddate;
	}

	public void setCreateddate(Date createdate) {
		this.createddate = createdate;
	}

	public Integer getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(Integer errorcode) {
		this.errorcode = errorcode;
	}

	public Integer getRetry() {
		return retry;
	}

	public void setRetry(Integer retry) {
		this.retry = retry;
	}

	public String getProviderorderid() {
		return providerorderid;
	}

	public void setProviderorderid(String providerorderid) {
		this.providerorderid = providerorderid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPurchasetype() {
		return purchasetype;
	}

	public void setPurchasetype(String purchasetype) {
		this.purchasetype = purchasetype;
	}

	public Integer getDownloadcount() {
		return downloadcount;
	}

	public void setDownloadcount(Integer downloadcount) {
		this.downloadcount = downloadcount;
	}

	public Date getOrderdate() {
		return orderdate;
	}

	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}

	public Date getLastupdateddate() {
		return lastupdateddate;
	}

	public void setLastupdateddate(Date lastupdateddate) {
		this.lastupdateddate = lastupdateddate;
	}

	public String getReadstatus() {
		return readstatus;
	}

	public void setReadstatus(String readstatus) {
		this.readstatus = readstatus;
	}

	public Date getReadcompleteddate() {
		return readcompleteddate;
	}

	public void setReadcompleteddate(Date readcompleteddate) {
		if(readcompleteddate !=null && readcompleteddate instanceof java.sql.Date)
			this.readcompleteddate = new Date(readcompleteddate.getTime());
		else
			this.readcompleteddate = readcompleteddate;
	}

	public Integer getPubassid() {
		return pubassid;
	}

	public void setPubassid(Integer pubassid) {
		this.pubassid = pubassid;
	}

	public String getAddedas() {
		return addedas;
	}

	public void setAddedas(String addedas) {
		this.addedas = addedas;
	}

	public String getAllowedfor() {
		return allowedfor;
	}

	public void setAllowedfor(String allowedfor) {
		this.allowedfor = allowedfor;
	}

	public Integer getAllowedconfirmation() {
		return allowedconfirmation;
	}

	public void setAllowedconfirmation(Integer allowedconfirmation) {
		this.allowedconfirmation = allowedconfirmation;
	}

	public Integer getBookstatuscode() {
		return bookstatuscode;
	}

	public void setBookstatuscode(Integer bookstatuscode) {
		this.bookstatuscode = bookstatuscode;
	}
	
	public boolean isMigrated() {
		return migrated;
	}

	public void setMigrated(boolean migrated) {
		this.migrated = migrated;
	}

	public DownloadToken getDownloadtoken() {
		return downloadtoken;
	}

	public void setDownloadtoken(DownloadToken downloadtoken) {
		this.downloadtoken = downloadtoken;
	}

	/*public List<LibraryTrackLog> getLibrarytracklog() {
		return librarytracklog;
	}

	public void setLibrarytracklog(List<LibraryTrackLog> librarytracklog) {
		this.librarytracklog = librarytracklog;
	}*/

	public LibrarySyncSettingLog getLibrarysyncsettinglog() {
		return librarysyncsettinglog;
	}

	public void setLibrarysyncsettinglog(LibrarySyncSettingLog librarysyncsettinglog) {
		this.librarysyncsettinglog = librarysyncsettinglog;
	}

	/*public List<LibrarySyncPagingLog> getLibrarysyncpaginglog() {
		return librarysyncpaginglog;
	}

	public void setLibrarysyncpaginglog(List<LibrarySyncPagingLog> librarysyncpaginglog) {
		this.librarysyncpaginglog = librarysyncpaginglog;
	}*/

	public LibrarySyncMasterLog getLibrarysyncmasterlog() {
		return librarysyncmasterlog;
	}

	public void setLibrarysyncmasterlog(LibrarySyncMasterLog librarysyncmasterlog) {
		this.librarysyncmasterlog = librarysyncmasterlog;
	}

	public List<LibrarySyncHighlightLog> getLibrarysynchighlightlog() {
		return librarysynchighlightlog;
	}

	public void setLibrarysynchighlightlog(List<LibrarySyncHighlightLog> librarysynchighlightlog) {
		this.librarysynchighlightlog = librarysynchighlightlog;
	}

	public List<LibrarySyncBookmarkLog> getLibrarysyncbookmarklog() {
		return librarysyncbookmarklog;
	}

	public void setLibrarysyncbookmarklog(List<LibrarySyncBookmarkLog> librarysyncbookmarklog) {
		this.librarysyncbookmarklog = librarysyncbookmarklog;
	}
	
	
	
}