package models;

import java.io.Serializable;

public class Users implements Serializable {
  private int userid;
  private String name;
  private String password;
  private long phonenumber;
  private String address;
  private String role;
  
  
  
  
public Users(int userid, String name, String password, long phonenumber, String address,String role) {
	this.userid = userid;
	this.name = name;
	this.password = password;
	this.phonenumber = phonenumber;
	this.address = address;
	this.role=role;
}


public String getRole() {
	return role;
}


public void setRole(String role) {
	this.role = role;
}


public int getUserid() {
	return userid;
}
public void setUserid(int userid) {
	this.userid = userid;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public long getPhonenumber() {
	return phonenumber;
}
public void setPhonenumber(long phonenumber) {
	this.phonenumber = phonenumber;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}


@Override
public String toString() {
	return "Users [userid=" + userid + ", name=" + name + ", password=" + password + ", phonenumber=" + phonenumber
			+ ", address=" + address + ", role=" + role + "]";
}


  
  
}
