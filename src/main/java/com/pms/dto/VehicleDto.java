package com.pms.dto;

import org.springframework.beans.BeanUtils;

import com.pms.entity.Vehicle;

public class VehicleDto {
	
	private int vehicleId;
	private String vehiclTitle;
	private String vehicleCode;
	private String vehicleModel;
	private String year;
	private String licensePlate ;
	private String color;
	private String fuelType;
	private String insuranceExpDate ;
	private String createdAt;
	
	private int categoryId;
	private String categoryName;
	
	private String username;
	private String userFirstName;
	private String userMiddleName;
	private String userLastName;
	
	private String facilityId;
	
	
	public VehicleDto() {

	}

	public VehicleDto(Vehicle vehicle) {
		BeanUtils.copyProperties(vehicle, this);

		if (vehicle.getCategory() != null) {
			this.categoryId = vehicle.getCategory().getCategoryId();
			this.categoryName = vehicle.getCategory().getCategoryName();
		
			
		}
		if (vehicle.getUser() != null) {
			this.username = vehicle.getUser().getUsername();
			this.userFirstName = vehicle.getUser().getUserFirstName();
			this.userMiddleName = vehicle.getUser().getUserMiddleName();
			this.userLastName = vehicle.getUser().getUserLastName();
			
		}

	}
	
	public int getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}
	
	public String getVehiclTitle() {
		return vehiclTitle;
	}

	public void setVehiclTitle(String vehiclTitle) {
		this.vehiclTitle = vehiclTitle;
	}

	public String getVehicleCode() {
		return vehicleCode;
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
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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


	
	
	


}
