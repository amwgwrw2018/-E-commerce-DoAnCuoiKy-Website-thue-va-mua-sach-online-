package entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@javax.persistence.Entity
@Table(name = "bookandauthor")
@DynamicUpdate(value=true)
@IdClass(bookandauthor.class)
public class bookandauthor implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@ManyToOne
	@JoinColumn(name = "authorID")
author authorID;
	@Id
	@ManyToOne
	@JoinColumn(name = "bookID")
book bookID;
	public author getAuthorID() {
		return authorID;
	}
	public void setAuthorID(author authorID) {
		this.authorID = authorID;
	}
	public book getBookID() {
		return bookID;
	}
	public void setBookID(book bookID) {
		this.bookID = bookID;
	}

	
	
	
	
}
