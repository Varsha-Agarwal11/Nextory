package Mongo.Library;

import java.util.Date;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Transient;

@Embedded
public class LibrarySyncSettingLog {
	
	public String fontname;
	
	public Integer fontsize;
	
	public Integer playerspeed;
	
	public Integer linespacing;
	
	public Integer foreground;
	
	public Integer background;
	
	public Integer theme;
	
	public String brightness;
	
	public Integer transistiontype;
	
	public Integer lockrotation;
	
	public Integer doublepaged;
	
	public Integer allowthreeg;
	
	public Integer globalpagination;
	
	public Date synctimestamp;
	
	private Double margin;
	private String justified;
	private Integer readerversion;
	
	@Transient
	public Long lsynctimestamp;

	public String getFontname() {
		return fontname;
	}

	public void setFontname(String fontname) {
		this.fontname = fontname;
	}

	public Integer getFontsize() {
		return fontsize;
	}

	public void setFontsize(Integer fontsize) {
		this.fontsize = fontsize;
	}

	public Integer getPlayerspeed() {
		return playerspeed;
	}

	public void setPlayerspeed(Integer playerspeed) {
		this.playerspeed = playerspeed;
	}

	public Integer getLinespacing() {
		return linespacing;
	}

	public void setLinespacing(Integer linespacing) {
		this.linespacing = linespacing;
	}

	public Integer getForeground() {
		return foreground;
	}

	public void setForeground(Integer foreground) {
		this.foreground = foreground;
	}

	public Integer getBackground() {
		return background;
	}

	public void setBackground(Integer background) {
		this.background = background;
	}

	public Integer getTheme() {
		return theme;
	}

	public void setTheme(Integer theme) {
		this.theme = theme;
	}

	public String getBrightness() {
		return brightness;
	}

	public void setBrightness(String brightness) {
		this.brightness = brightness;
	}

	public Integer getTransistiontype() {
		return transistiontype;
	}

	public void setTransistiontype(Integer transistiontype) {
		this.transistiontype = transistiontype;
	}

	public Integer getLockrotation() {
		return lockrotation;
	}

	public void setLockrotation(Integer lockrotation) {
		this.lockrotation = lockrotation;
	}

	public Integer getDoublepaged() {
		return doublepaged;
	}

	public void setDoublepaged(Integer doublepaged) {
		this.doublepaged = doublepaged;
	}

	public Integer getAllowthreeg() {
		return allowthreeg;
	}

	public void setAllowthreeg(Integer allowthreeg) {
		this.allowthreeg = allowthreeg;
	}

	public Integer getGlobalpagination() {
		return globalpagination;
	}

	public void setGlobalpagination(Integer globalpagination) {
		this.globalpagination = globalpagination;
	}

	public Date getSynctimestamp() {
		return synctimestamp;
	}

	public void setSynctimestamp(Date synctimestamp) {
		this.synctimestamp = synctimestamp;
	}

	public Long getLsynctimestamp() {
		return lsynctimestamp;
	}

	public void setLsynctimestamp(Long lsynctimestamp) {
		if(lsynctimestamp!=null)
			this.synctimestamp = new Date(lsynctimestamp);
		
		this.lsynctimestamp = lsynctimestamp;
	}

	public Double getMargin() {
		return margin;
	}
	public void setMargin(Double margin) {
		this.margin = margin;
	}
	 
	public Integer getReaderversion() {
		return readerversion;
	}
	public void setReaderversion(Integer readerversion) {
		this.readerversion = readerversion;
	}

	public String getJustified() {
		return justified;
	}

	public void setJustified(String justified) {
		this.justified = justified;
	}
	
	
}
