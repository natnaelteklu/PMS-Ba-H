package com.pms.dto;

import org.springframework.beans.BeanUtils;

import com.pms.entity.Reservation;

public class ReservationDto {
	
    private int reservationId;
    private String startTime;
    private String endTime;
    private String status;
    
    private int spaceId;
    private String title;
	
	private String username;
	private String userFirstName;
	private String userMiddleName;
	private String userLastName;
	
	private int vehicleId;
	private String vehiclTitle;
	private String vehicleCode;
	private String vehicleModel;
	
	private int facilityId;
	private String conpanyName;
	
	
	public ReservationDto() {

	}

	public ReservationDto(Reservation reservation) {
		BeanUtils.copyProperties(reservation, this);

		if (reservation.getSpaces() != null) {
			this.spaceId = reservation.getSpaces().getSpaceId();
			this.title= reservation.getSpaces().getTitle();
			this.facilityId=reservation.getSpaces().getFacilities().getFacilityId();
			this.conpanyName=reservation.getSpaces().getFacilities().getCompanyName();
		}
		if (reservation.getUsers() != null) {
			this.username = reservation.getUsers().getUsername();
			this.userFirstName = reservation.getUsers().getUserFirstName();
			this.userMiddleName = reservation.getUsers().getUserMiddleName();
			this.userLastName = reservation.getUsers().getUserLastName();
			
		}
		if (reservation.getVehicles() != null) {
			this.vehicleId = reservation.getVehicles().getVehicleId();
			this.vehiclTitle = reservation.getVehicles().getVehiclTitle();
			this.vehicleCode = reservation.getVehicles().getVehicleCode();
			this.vehicleModel = reservation.getVehicles().getVehicleModel();
			
		}
		
	}
	
	
	public int getReservationId() {
		return reservationId;
	}
	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getSpaceId() {
		return spaceId;
	}
	public void setSpaceId(int spaceId) {
		this.spaceId = spaceId;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public int getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(int facilityId) {
		this.facilityId = facilityId;
	}

	public String getConpanyName() {
		return conpanyName;
	}

	public void setConpanyName(String conpanyName) {
		this.conpanyName = conpanyName;
	}
	
	
	

}
