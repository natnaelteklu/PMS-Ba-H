package com.pms.entity;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pms.dto.UserInfoDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
@NamedQuery(name="UserInfo.findAll", query="SELECT t FROM UserInfo t")

public class UserInfo {
    @Id
    private String username;
    private String password;
    
	private String userFirstName;
	private String userMiddleName;
	private String userLastName;
	private String userPhone;
	private String userAdress;
    private Boolean terms;

	private String userGender;
	private String experience;
	private String dob;
	private String drivingLicence;
	private int userStatus;
	
	private String verifcationCode;
	private String lastModified;

	private String isPwdExpired;

	private String lastLoginDate;
	
	private String lastLogoutDate;
	
	private String passwordExpirationDate;
	
	private long invalidLoginTrails;


    
    @ManyToMany(fetch = FetchType.EAGER, cascade =  {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles; 
    

	@ManyToOne
	@JoinColumn(name="origin")
	private Facility facilitty;
	
	
	@JsonIgnore
	@OneToMany(mappedBy="user")
	private List<Vehicle> vehicle;
	
	@JsonIgnore
	@OneToMany(mappedBy="users")
	private List<Reservation> reservation;
	
	
	public UserInfo() {
		
	}

	public UserInfo(UserInfoDto userInfoDto) {
	    BeanUtils.copyProperties(userInfoDto, this);

	    if (userInfoDto.getFacilityId() > 0) {
	        Facility facility = new Facility();
	        facility.setFacilityId(userInfoDto.getFacilityId());
	        this.setFacilitty(facility);
	    }

	    if (userInfoDto.getRoles() != null && !userInfoDto.getRoles().isEmpty()) {
	        this.roles = userInfoDto.getRoles()
	                                .stream()
	                                .collect(Collectors.toSet());
	    }
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
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
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

	public String getUserGender() {
		return userGender;
	}
	public void setUserGender(String userGender) {
		this.userGender = userGender;
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

	public List<Vehicle> getVehicle() {
		return vehicle;
	}
	public void setVehicle(List<Vehicle> vehicle) {
		this.vehicle = vehicle;
	}
	public List<Reservation> getReservation() {
		return reservation;
	}
	public void setReservation(List<Reservation> reservation) {
		this.reservation = reservation;
	}

	public Facility getFacilitty() {
		return facilitty;
	}
	public void setFacilitty(Facility facilitty) {
		this.facilitty = facilitty;
	}

	public Boolean getTerms() {
		return terms;
	}

	public void setTerms(Boolean terms) {
		this.terms = terms;
	}


	
    
}
