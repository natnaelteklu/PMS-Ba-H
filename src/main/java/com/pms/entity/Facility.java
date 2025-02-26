package com.pms.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;

@Entity
@NamedQuery(name="Facility.findAll", query="SELECT t FROM Facility t")

public class Facility {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private int facilityId;
    private String companyName;
    private String tin;
    
    private String contactNumber;
    @Column(name="adress")
    private String address;
    private String status;
    private String contactPerson;
    private String vipFeePerHr;
    private String regularFeePerHr;
    
    private String bankAccountNumber;
    private String accountDescription;
    
   
    private String createdAt;
    private String createdBy;
    
	@JsonIgnore
	@OneToMany(mappedBy="facilitty")
	private List<UserInfo> userInfo;
    
    @JsonIgnore
	@OneToMany(mappedBy="facilities")
	private List<ParkingSpace> space;

	public Facility() {

	}
	public int getFacilityId() {
		return facilityId;
	}
	public void setFacilityId(int facilityId) {
		this.facilityId = facilityId;
	}
	public String getTin() {
		return tin;
	}
	public void setTin(String tin) {
		this.tin = tin;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getVipFeePerHr() {
		return vipFeePerHr;
	}
	public void setVipFeePerHr(String vipFeePerHr) {
		this.vipFeePerHr = vipFeePerHr;
	}
	public String getRegularFeePerHr() {
		return regularFeePerHr;
	}
	public void setRegularFeePerHr(String regularFeePerHr) {
		this.regularFeePerHr = regularFeePerHr;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public List<ParkingSpace> getSpace() {
		return space;
	}
	public void setSpace(List<ParkingSpace> space) {
		this.space = space;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	public String getBankAccountNumber() {
		return bankAccountNumber;
	}
	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}
	public String getAccountDescription() {
		return accountDescription;
	}
	public void setAccountDescription(String accountDescription) {
		this.accountDescription = accountDescription;
	}
	public List<UserInfo> getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(List<UserInfo> userInfo) {
		this.userInfo = userInfo;
	}

    

}
