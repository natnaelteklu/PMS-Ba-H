package com.pms.dto;

import org.springframework.beans.BeanUtils;

import com.pms.entity.UserClass;

public class UserClassDto {
	private int classId;
	private String classType;
	private String customerSince;
	
    private int facilityId;
    private String companyName;
	
	private String username;
	private String userFirstName;
	private String userMiddleName;
	private String userLastName;
	private String userPhone;
	
	
	public UserClassDto() {

	}

	public UserClassDto(UserClass userClass) {
		BeanUtils.copyProperties(userClass, this);

		if (userClass.getFacilitie() != null) {
			this.facilityId = userClass.getFacilitie().getFacilityId();
			this.companyName = userClass.getFacilitie().getCompanyName();
			
		}
		if (userClass.getUserInf() != null) {
			this.username = userClass.getUserInf().getUsername();
			this.userFirstName = userClass.getUserInf().getUserFirstName();
			this.userMiddleName = userClass.getUserInf().getUserMiddleName();
			this.userLastName = userClass.getUserInf().getUserLastName();
			this.userPhone=userClass.getUserInf().getUserPhone();
			
		}

	}
	
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}
	public String getClassType() {
		return classType;
	}
	public void setClassType(String classType) {
		this.classType = classType;
	}
	public String getCustomerSince() {
		return customerSince;
	}
	public void setCustomerSince(String customerSince) {
		this.customerSince = customerSince;
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
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserFirstName() {
		return userFirstName;
	}
	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}
	public String getUserMiddleName() {
		return userMiddleName;
	}
	public void setUserMiddleName(String userMiddleName) {
		this.userMiddleName = userMiddleName;
	}
	public String getUserLastName() {
		return userLastName;
	}
	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	
	
}
