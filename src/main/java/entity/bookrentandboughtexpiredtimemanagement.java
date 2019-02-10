package entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "bookrentandboughtexpiredtimemanagement")
public class bookrentandboughtexpiredtimemanagement {
	@Id
	@Column(name = "trackID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
String trackID;
	@JoinColumn(name = "userID")
	@ManyToOne
users userID;
	@JoinColumn(name = "bookID")
	@ManyToOne
book bookID;
	@Column(name = "expiredTime")
String expiredTime;
	@Column(name = "isBought")
String isBought;
public bookrentandboughtexpiredtimemanagement() {
	// TODO Auto-generated constructor stub
}

public bookrentandboughtexpiredtimemanagement(users userID, book bookID) {

	this.userID = userID;
	this.bookID = bookID;
}

public String getTrackID() {
	return trackID;
}
public void setTrackID(String trackID) {
	this.trackID = trackID;
}
public users getUserID() {
	return userID;
}
public void setUserID(users userID) {
	this.userID = userID;
}
public book getBookID() {
	return bookID;
}
public void setBookID(book bookID) {
	this.bookID = bookID;
}
public String getExpiredTime() {
	return expiredTime;
}
public void setExpiredTime(String expiredTime) {
	this.expiredTime = expiredTime;
}

public String getIsBought() {
	return isBought;
}

public void setIsBought(String isBought) {
	this.isBought = isBought;
}

}
