package com.pms.entity;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pms.dto.ReservationDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;

@Entity
@NamedQuery(name="Reservation.findAll", query="SELECT t FROM Reservation t")

public class Reservation {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private int reservationId;
    private String startTime;
    private String endTime;
    private String status;
    
	@ManyToOne
	@JoinColumn(name="space_id")
	private ParkingSpace spaces; 
	
    
	@ManyToOne
	@JoinColumn(name="user_id")
	private UserInfo users; 
	
	@ManyToOne
	@JoinColumn(name="vehicle_id")
	private Vehicle vehicles; 

	@JsonIgnore
	@OneToMany(mappedBy="reservation")
	private List<Payment> payment;
	
	public Reservation() {
	}

	public Reservation(ReservationDto reservationDto) {
		BeanUtils.copyProperties(reservationDto, this);
		
		ParkingSpace space = new ParkingSpace();
		space.setSpaceId(reservationDto.getSpaceId());
		this.setSpaces(space);

		UserInfo user = new UserInfo();
		user.setUsername(reservationDto.getUsername());
		this.setUsers(user);
		
		Vehicle vehicle= new Vehicle();
		vehicle.setVehicleId(reservationDto.getVehicleId());
		this.setVehicles(vehicle);
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

	public ParkingSpace getSpaces() {
		return spaces;
	}

	public void setSpaces(ParkingSpace spaces) {
		this.spaces = spaces;
	}

	public UserInfo getUsers() {
		return users;
	}

	public void setUsers(UserInfo users) {
		this.users = users;
	}

	public List<Payment> getPayment() {
		return payment;
	}

	public void setPayment(List<Payment> payment) {
		this.payment = payment;
	}

	public Vehicle getVehicles() {
		return vehicles;
	}

	public void setVehicles(Vehicle vehicles) {
		this.vehicles = vehicles;
	}
	
	
}