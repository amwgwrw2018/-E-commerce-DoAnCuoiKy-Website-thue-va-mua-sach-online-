package entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


import org.hibernate.annotations.DynamicUpdate;


@javax.persistence.Entity
@Table(name = "bookchaptermanagement")
@DynamicUpdate(value=true)

public class bookchaptermanagement {
	

	
	@ManyToOne
    @JoinColumn(name = "bookId")
book bookId;
	@Id
	@Column(name="ChapterID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	String ChapterID;
	@Column(name="ChapterContentFile")
	String ChapterContentFile;
	@Column(name="UploadDate")
	String UploadDate;
	@Column(name="NumberOfChapter")	
String NumberOfChapter;
	public bookchaptermanagement() {
		// TODO Auto-generated constructor stub
	}

	public bookchaptermanagement(String chapterID) {
	
		ChapterID = chapterID;
	}

	public book getBookId() {
		return bookId;
	}

	public void setBookId(book bookId) {
		this.bookId = bookId;
	}


	public String getNumberOfChapter() {
		return NumberOfChapter;
	}

	public void setNumberOfChapter(String numberOfChapter) {
		NumberOfChapter = numberOfChapter;
	}

	public String getChapterID() {
		return ChapterID;
	}

	public void setChapterID(String chapterID) {
		ChapterID = chapterID;
	}

	public String getChapterContentFile() {
		return ChapterContentFile;
	}
	public void setChapterContentFile(String chapterContentFile) {
		ChapterContentFile = chapterContentFile;
	}

	public String getUploadDate() {
		return UploadDate;
	}

	public void setUploadDate(String uploadDate) {
		UploadDate = uploadDate;
	}
	
	
}