package entity;


import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


import org.hibernate.annotations.DynamicUpdate;


@javax.persistence.Entity
@Table(name = "book")
@DynamicUpdate(value=true)
public class book {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	String id;
	@Column(name = "bookName")
	String bookName;
	@JoinColumn(name = "bookType")
	@ManyToOne
	booktype bookType;
	@JoinColumn(name = "languageId")
	@ManyToOne
	language languageId;
	@Column(name = "bookImage",updatable=false)
	String bookImage;
	@Column(name = "price")
	String price;
	@Column(name = "releaseDate",updatable=false)
	String releaseDate;

	@OneToMany(mappedBy="bookId")
	List<bookchaptermanagement> bookChapterList;
	@OneToMany(mappedBy="bookId")
	List<commentmanagement> commentmanagementList;
	@OneToMany(mappedBy="bookID")
	List<bookandauthor> bookAndAuthor;
	@OneToMany(mappedBy="bookID")
	List<bookrentandboughtexpiredtimemanagement> bookrentandboughtexpiredtimemanagementList;
	public book() {
		// TODO Auto-generated constructor stub
	}
	public book(String id) {
		this.id=id;
	}
	
	
	
	public book(String id, String bookName, booktype bookType, language languageId, String bookImage,
			String price, String releaseDate) {
	
		this.id = id;
		this.bookName = bookName;
		this.bookType = bookType;
	
		this.languageId = languageId;
		this.bookImage = bookImage;
		this.price = price;
		this.releaseDate = releaseDate;
	
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public booktype getBookType() {
		return bookType;
	}

	public void setBookType(booktype bookType) {
		this.bookType = bookType;
	}




	
	

	public language getLanguageId() {
		return languageId;
	}
	public void setLanguageId(language languageId) {
		this.languageId = languageId;
	}
	public String getBookImage() {
		return bookImage;
	}

	public void setBookImage(String bookImage) {
		this.bookImage = bookImage;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	
	

	



}