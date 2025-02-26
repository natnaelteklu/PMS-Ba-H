package com.pms.dto;

import org.springframework.beans.BeanUtils;

import com.pms.entity.ParkingSpace;

import jakarta.persistence.Column;

public class ParkingSpaceDto {
	
    private int spaceId;
    private String description;
    private String title;
    private String  lastReleasedTime;
    
    private String spaceType; //VIP OR Regular
    private String timeLeft;
    private String spaceStatus;
    
    private String createdAt;
    private String createdBy;
    
    
    private int facilityId;
    private String companyName;
    private String contactNumber;
    
    private String tin;
    private String address;

    private String vipFeePerHr;
    private String regularFeePerHr;
    private String bankAccountNumber;
    private String accountDescription;
    private String contactPerson;
    
    
	public ParkingSpaceDto() {

	}

	public ParkingSpaceDto(ParkingSpace parkingSpace) {
		BeanUtils.copyProperties(parkingSpace, this);

		if (parkingSpace.getFacilities() != null) {
			this.facilityId = parkingSpace.getFacilities().getFacilityId();
			this.companyName = parkingSpace.getFacilities().getCompanyName();
			this.contactNumber = parkingSpace.getFacilities().getContactNumber();
			
			this.tin = parkingSpace.getFacilities().getTin();
			this.address = parkingSpace.getFacilities().getAddress();
			
			this.vipFeePerHr = parkingSpace.getFacilities().getVipFeePerHr();
			this.regularFeePerHr = parkingSpace.getFacilities().getRegularFeePerHr();
			this.accountDescription = parkingSpace.getFacilities().getAccountDescription();
			this.bankAccountNumber = parkingSpace.getFacilities().getBankAccountNumber();
			this.contactPerson=parkingSpace.getFacilities().getContactPerson();
			
			
		}

	}
	
	public int getSpaceId() {
		return spaceId;
	}
	public void setSpaceId(int spaceId) {
		this.spaceId = spaceId;
	}



	public String getSpaceType() {
		return spaceType;
	}

	public void setSpaceType(String spaceType) {
		this.spaceType = spaceType;
	}

	public String getTimeLeft() {
		return timeLeft;
	}

	public void setTimeLeft(String timeLeft) {
		this.timeLeft = timeLeft;
	}

	public String getSpaceStatus() {
		return spaceStatus;
	}

	public void setSpaceStatus(String spaceStatus) {
		this.spaceStatus = spaceStatus;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getFacilityId() {
		return facilityId;
	}
	public void setFacilityId(int facilityId) {
		this.facilityId = facilityId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLastReleasedTime() {
		return lastReleasedTime;
	}

	public void setLastReleasedTime(String lastReleasedTime) {
		this.lastReleasedTime = lastReleasedTime;
	}

	public String getTin() {
		return tin;
	}

	public void setTin(String tin) {
		this.tin = tin;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	

    

}
