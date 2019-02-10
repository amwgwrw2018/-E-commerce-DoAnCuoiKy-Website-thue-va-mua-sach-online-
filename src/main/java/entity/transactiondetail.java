package entity;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
@javax.persistence.Entity
@DynamicUpdate(value=true)
@IdClass(transactiondetail.class)
@Table(name = "transactiondetail")
public class transactiondetail implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@ManyToOne
	@JoinColumn(name = "bookID")
	book bookID;
	@Id
	@ManyToOne
	@JoinColumn(name = "transactionID")
	usertransaction transactionID;

	@ManyToOne
	@JoinColumn(name = "rentPackageID")
	rentpackage rentPackageID;
	public transactiondetail() {
		// TODO Auto-generated constructor stub
	}
	public book getBookID() {
		return bookID;
	}
	public void setBookID(book bookID) {
		this.bookID = bookID;
	}
	public usertransaction getTransactionID() {
		return transactionID;
	}
	public void setTransactionID(usertransaction transactionID) {
		this.transactionID = transactionID;
	}
	public rentpackage getRentPackageID() {
		return rentPackageID;
	}
	public void setRentPackageID(rentpackage rentPackageID) {
		this.rentPackageID = rentPackageID;
	}

}
