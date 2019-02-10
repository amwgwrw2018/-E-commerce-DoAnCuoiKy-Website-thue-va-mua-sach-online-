package entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "users")
public class users {
@Id
@Column(name="id")
@GeneratedValue(strategy=GenerationType.IDENTITY)
	String id;
@Column(name="username")
String username;

@Column(name="email")
String email;
@Column(name="FullName")
String FullName;
@Column(name="phoneNumber")
String phoneNumber;
@Column(name="address")
String address;
@Column(name="password")
String password;
@Column(name="avatar")
String avatar;
@Column(name="enabled")
String enabled;

@OneToMany(mappedBy="userID")
List<usertransaction> usertransactionList;
@OneToMany(mappedBy="userId")
List<commentmanagement> commentmanagementList;

@OneToMany(mappedBy="userID")
List<bookrentandboughtexpiredtimemanagement> bookrentandboughtexpiredtimemanagementList;
public users() {
	// TODO Auto-generated constructor stub
}

public users(String id) {
	
	this.id = id;
}

public users(String username, String email, String password, String enabled) {
	
	this.username = username;
	this.email = email;
	this.password = password;
	this.enabled = enabled;
}

public String getAvatar() {
	return avatar;
}

public void setAvatar(String avatar) {
	this.avatar = avatar;
}

public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getEnabled() {
	return enabled;
}
public void setEnabled(String enabled) {
	this.enabled = enabled;
}


public String getFullName() {
	return FullName;
}

public void setFullName(String fullName) {
	FullName = fullName;
}

public String getPhoneNumber() {
	return phoneNumber;
}

public void setPhoneNumber(String phoneNumber) {
	this.phoneNumber = phoneNumber;
}

public String getAddress() {
	return address;
}

public void setAddress(String address) {
	this.address = address;
}





}
