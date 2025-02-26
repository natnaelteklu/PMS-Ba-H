package com.pms.entity;


import org.springframework.beans.BeanUtils;

import com.pms.dto.VehicleDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Vehicle {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int vehicleId;
	
	@Column(name="vehicle_title")
	private String vehiclTitle;
	private String vehicleCode;
	private String vehicleModel;
	private String year;
	private String licensePlate ;
	private String color;
	private String fuelType;
	private String insuranceExpDate ;
	private String createdAt;
	
	
	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category; 
	
	@ManyToOne
	@JoinColumn(name="owner_id")
	private UserInfo user;
	
	public Vehicle() {
	}

	public Vehicle(VehicleDto vehicleDto) {
		BeanUtils.copyProperties(vehicleDto, this);
		
		Category category = new Category();
		category.setCategoryId(vehicleDto.getCategoryId());
		this.setCategory(category);

		UserInfo user = new UserInfo();
		user.setUsername(vehicleDto.getUsername());
		this.setUser(user);
	}
	

	public int getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getVehicleCode() {
		return vehicleCode;
	}

	public String getVehiclTitle() {
		return vehiclTitle;
	}

	public void setVehiclTitle(String vehiclTitle) {
		this.vehiclTitle = vehiclTitle;
	}

	public void setVehicleCode(String vehicleCode) {
		this.vehicleCode = vehicleCode;
	}

	public String getVehicleModel() {
		return vehicleModel;
	}

	public void setVehicleModel(String vehicleModel) {
		this.vehicleModel = vehicleModel;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getFuelType() {
		return fuelType;
	}

	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}

	public String getInsuranceExpDate() {
		return insuranceExpDate;
	}

	public void setInsuranceExpDate(String insuranceExpDate) {
		this.insuranceExpDate = insuranceExpDate;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	} 
	
	
	
}
