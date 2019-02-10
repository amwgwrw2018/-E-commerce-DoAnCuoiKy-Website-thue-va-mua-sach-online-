package entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@javax.persistence.Entity
@Table(name = "booktype")
@DynamicUpdate(value=true)
public class booktype {
	@Id
	@Column(name = "bookTypeId")
	String bookTypeId;
	@Column(name = "bookType")
String bookType;
	@OneToMany(mappedBy="bookType")
	List<book> bookByBookType;
	public booktype() {
		// TODO Auto-generated constructor stub
	}
	
	public booktype(String bookTypeId) {
	
		this.bookTypeId = bookTypeId;
	}

	public String getBookTypeId() {
		return bookTypeId;
	}
	public void setBookTypeId(String bookTypeId) {
		this.bookTypeId = bookTypeId;
	}
	public String getBookType() {
		return bookType;
	}
	public void setBookType(String bookType) {
		this.bookType = bookType;
	}
	
}
