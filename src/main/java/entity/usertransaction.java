package entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "usertransaction")
public class usertransaction {
	
	@Id
	@Column(name = "transactionID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	String transactionID;
	@Column(name = "transactionDate")
	String transactionDate;
	@JoinColumn(name = "userID")
	@ManyToOne
	users userID;
	
	@OneToMany(mappedBy="transactionID")
	List<transactiondetail> transactiondetailList;
	
	public usertransaction() {
		// TODO Auto-generated constructor stub
	}
	
	public usertransaction(users userID) {
	
		this.userID = userID;
	}
	

	public usertransaction(String transactionID) {

		this.transactionID = transactionID;
	}

	public String getTransactionID() {
		return transactionID;
	}
	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}
	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	public users getUserID() {
		return userID;
	}
	public void setUserID(users userID) {
		this.userID = userID;
	}
	
}
