package Mongo.Library;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class LibrarySyncPagingLog {

	public Integer chapterindex;
	
	public Integer numofpagesinchapter;
	
	public String fontname;
	
	public Integer fontsize;
	
	public Integer linespacing;
	
	public Integer height;
	
	public String verticalgapratio;
	
	public String horizontalgapratio;
	
	public Integer portrait;
	
	public Integer doublepagedforlandscape;

	public Integer getChapterindex() {
		return chapterindex;
	}

	public void setChapterindex(Integer chapterindex) {
		this.chapterindex = chapterindex;
	}

	public Integer getNumofpagesinchapter() {
		return numofpagesinchapter;
	}

	public void setNumofpagesinchapter(Integer numofpagesinchapter) {
		this.numofpagesinchapter = numofpagesinchapter;
	}

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

	public Integer getLinespacing() {
		return linespacing;
	}

	public void setLinespacing(Integer linespacing) {
		this.linespacing = linespacing;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public String getVerticalgapratio() {
		return verticalgapratio;
	}

	public void setVerticalgapratio(String verticalgapratio) {
		this.verticalgapratio = verticalgapratio;
	}

	public String getHorizontalgapratio() {
		return horizontalgapratio;
	}

	public void setHorizontalgapratio(String horizontalgapratio) {
		this.horizontalgapratio = horizontalgapratio;
	}

	public Integer getPortrait() {
		return portrait;
	}

	public void setPortrait(Integer portrait) {
		this.portrait = portrait;
	}

	public Integer getDoublepagedforlandscape() {
		return doublepagedforlandscape;
	}

	public void setDoublepagedforlandscape(Integer doublepagedforlandscape) {
		this.doublepagedforlandscape = doublepagedforlandscape;
	}
	
	
	
}
