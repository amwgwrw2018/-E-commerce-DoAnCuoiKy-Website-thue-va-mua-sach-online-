package entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@javax.persistence.Entity
@Table(name = "rentpackage")
@DynamicUpdate(value=true)
public class rentpackage {
	@Column(name = "numberOfDay")
	int numberOfDay;
	@Column(name = "Price")
	int Price;
	@Id
	@Column(name = "RentPackageID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	String RentPackageID;
	
	@OneToMany(mappedBy="rentPackageID")
	List<transactiondetail> transactiondetailList;
	public rentpackage() {
		// TODO Auto-generated constructor stub
	}
	
	public rentpackage(String rentPackageID) {

		RentPackageID = rentPackageID;
	}

	public int getNumberOfDay() {
		return numberOfDay;
	}
	public void setNumberOfDay(int numberOfDay) {
		this.numberOfDay = numberOfDay;
	}
	public int getPrice() {
		return Price;
	}
	public void setPrice(int price) {
		Price = price;
	}
	public String getRentPackageID() {
		return RentPackageID;
	}
	public void setRentPackageID(String rentPackageID) {
		RentPackageID = rentPackageID;
	}
	
}
