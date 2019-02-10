package entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@javax.persistence.Entity
@Table(name = "commentmanagement")
@DynamicUpdate(value=true)
public class commentmanagement {
	@Id
	@Column(name = "commentId")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	String commentId;
	@ManyToOne
	@JoinColumn(name = "bookId")
	book bookId;
	@ManyToOne
	@JoinColumn(name = "userId")
	users userId;
	@Column(name = "content")
	String content;
	@Column(name = "commentDate")
	String commentDate;
	public commentmanagement() {
		// TODO Auto-generated constructor stub
	}
	
	public String getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(String commentDate) {
		this.commentDate = commentDate;
	}

	public String getCommentId() {
		return commentId;
	}
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}
	public book getBookId() {
		return bookId;
	}
	public void setBookId(book bookId) {
		this.bookId = bookId;
	}
	public users getUserId() {
		return userId;
	}
	public void setUserId(users userId) {
		this.userId = userId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
