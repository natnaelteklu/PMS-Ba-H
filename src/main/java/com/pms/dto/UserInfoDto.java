package com.pms.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import com.pms.entity.Role;
import com.pms.entity.UserInfo;

import jakarta.persistence.Column;

public class UserInfoDto {
    private String username;
    private String password;
    private String userFirstName;
    private String userMiddleName;
    private String userLastName;
    private String userPhone;
    private String userAdress;
    private String userType;
    private String userGender;
    private String experience;
    private String dob;
    private String drivingLicence;
    private int userStatus;	

    private Boolean terms;
    
    private String verifcationCode;
    private String lastModified;
    private String isPwdExpired;
    private String lastLoginDate;
    private String lastLogoutDate;
    private String passwordExpirationDate;
    private long invalidLoginTrails;

    private int facilityId;
    private String companyName;
    private String contactNumber;

    // Store List<Role> instead of List<String>
    private List<Role> roles;

    public UserInfoDto() {
    }

    public UserInfoDto(UserInfo userInfo) {
        BeanUtils.copyProperties(userInfo, this);

        if (userInfo.getRoles() != null) {
            this.roles = userInfo.getRoles()
                                 .stream()
                                 .collect(Collectors.toList());
        }

        if (userInfo.getFacilitty() != null) {
            this.facilityId = userInfo.getFacilitty().getFacilityId();
            this.companyName = userInfo.getFacilitty().getCompanyName();
            this.contactNumber = userInfo.getFacilitty().getContactNumber();
        }
    }

    // Getters and Setters

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getUserAdress() {
		return userAdress;
	}

	public void setUserAdress(String userAdress) {
		this.userAdress = userAdress;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserGender() {
		return userGender;
	}

	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getDrivingLicence() {
		return drivingLicence;
	}

	public void setDrivingLicence(String drivingLicence) {
		this.drivingLicence = drivingLicence;
	}

	public int getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
	}

	public String getVerifcationCode() {
		return verifcationCode;
	}

	public void setVerifcationCode(String verifcationCode) {
		this.verifcationCode = verifcationCode;
	}

	public String getLastModified() {
		return lastModified;
	}

	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}

	public String getIsPwdExpired() {
		return isPwdExpired;
	}

	public void setIsPwdExpired(String isPwdExpired) {
		this.isPwdExpired = isPwdExpired;
	}

	public String getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getLastLogoutDate() {
		return lastLogoutDate;
	}

	public void setLastLogoutDate(String lastLogoutDate) {
		this.lastLogoutDate = lastLogoutDate;
	}

	public String getPasswordExpirationDate() {
		return passwordExpirationDate;
	}

	public void setPasswordExpirationDate(String passwordExpirationDate) {
		this.passwordExpirationDate = passwordExpirationDate;
	}

	public long getInvalidLoginTrails() {
		return invalidLoginTrails;
	}

	public void setInvalidLoginTrails(long invalidLoginTrails) {
		this.invalidLoginTrails = invalidLoginTrails;
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

	public Boolean getTerms() {
		return terms;
	}

	public void setTerms(Boolean terms) {
		this.terms = terms;
	}


    
    
}
