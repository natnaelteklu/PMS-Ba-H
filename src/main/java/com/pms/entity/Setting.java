package com.pms.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.NamedQuery;


@Entity
@NamedQuery(name="Setting.findAll", query="SELECT s FROM Setting s")
public class Setting implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String logo;
	private String pagetitle;
	
	@Column(name="UPDATED_AT")
	private String lastmodified;
	private String updatedby;
	
	private int lastRemainingTime;
	private int timeToCancel;
	private int timeToSchedule;
	
	private String address;

	private String email;
	
	private String phone;
	
	@Column(name="SLIDER_IMAGE1")
	private String sliderImage1;

	@Column(name="SLIDER_IMAGE2")
	private String sliderImage2;

	@Column(name="SLIDER_IMAGE3")
	private String sliderImage3;
	
	
	@Lob
	@Column(name="SOCIAL_LINKS")
	private String socialLinks;
	


	

	public Setting() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLogo() {
		return this.logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getPagetitle() {
		return this.pagetitle;
	}

	public void setPagetitle(String pagetitle) {
		this.pagetitle = pagetitle;
	}

	public String getLastmodified() {
		return lastmodified;
	}

	public void setLastmodified(String lastmodified) {
		this.lastmodified = lastmodified;
	}

	public String getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}

	public int getLastRemainingTime() {
		return lastRemainingTime;
	}

	public void setLastRemainingTime(int lastRemainingTime) {
		this.lastRemainingTime = lastRemainingTime;
	}

	public int getTimeToCancel() {
		return timeToCancel;
	}

	public void setTimeToCancel(int timeToCancel) {
		this.timeToCancel = timeToCancel;
	}

	public int getTimeToSchedule() {
		return timeToSchedule;
	}

	public void setTimeToSchedule(int timeToSchedule) {
		this.timeToSchedule = timeToSchedule;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSliderImage1() {
		return sliderImage1;
	}

	public void setSliderImage1(String sliderImage1) {
		this.sliderImage1 = sliderImage1;
	}

	public String getSliderImage2() {
		return sliderImage2;
	}

	public void setSliderImage2(String sliderImage2) {
		this.sliderImage2 = sliderImage2;
	}

	public String getSliderImage3() {
		return sliderImage3;
	}

	public void setSliderImage3(String sliderImage3) {
		this.sliderImage3 = sliderImage3;
	}

	public String getSocialLinks() {
		return socialLinks;
	}

	public void setSocialLinks(String socialLinks) {
		this.socialLinks = socialLinks;
	}



}