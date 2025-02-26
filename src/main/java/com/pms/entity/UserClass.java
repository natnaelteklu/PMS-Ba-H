package com.pms.entity;

import org.springframework.beans.BeanUtils;

import com.pms.dto.UserClassDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class UserClass {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int classId;
	private String classType;
	private String customerSince;
	
	@ManyToOne
	@JoinColumn(name="facility_id")
	private Facility facilitie;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private UserInfo userInf;

	
	public UserClass() {
	}

	public UserClass(UserClassDto userClassDto) {
		BeanUtils.copyProperties(userClassDto, this);
		Facility facility = new Facility();
		facility.setFacilityId(userClassDto.getFacilityId());
		this.setFacilitie(facility);

		UserInfo user = new UserInfo();
		user.setUsername(userClassDto.getUsername());
		this.setUserInf(user);
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

	public Facility getFacilitie() {
		return facilitie;
	}

	public void setFacilitie(Facility facilitie) {
		this.facilitie = facilitie;
	}

	public UserInfo getUserInf() {
		return userInf;
	}

	public void setUserInf(UserInfo userInf) {
		this.userInf = userInf;
	}
	
	
}
