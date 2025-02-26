package com.pms.entity;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pms.dto.ParkingSpaceDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;

@Entity
@NamedQuery(name="ParkingSpace.findAll", query="SELECT t FROM ParkingSpace t")
public class ParkingSpace {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
   // @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="parking_space_seq")
   // @SequenceGenerator(name="parking_space_seq", sequenceName="parking_space_seq", initialValue=101, allocationSize=1)
   
    private int spaceId;
    private String description;
    private String title;
    private String lastReleasedTime;
    
    private String spaceType; //VIP OR Regular
    private String timeLeft;
    private String spaceStatus;
    
    
    
    
    private String createdAt;
    private String createdBy;
    
    @JsonIgnore
	@OneToMany(mappedBy="spaces")
	private List<Reservation> reservation;
	
	@ManyToOne
	@JoinColumn(name="facility_id")
	private Facility facilities;
	
	public ParkingSpace() {
	}

	public ParkingSpace(ParkingSpaceDto parkingSpaceDto) {
		BeanUtils.copyProperties(parkingSpaceDto, this);
		
		Facility facility = new Facility();
		facility.setFacilityId(parkingSpaceDto.getFacilityId());
		this.setFacilities(facility);


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
	

	public String getLastReleasedTime() {
		return lastReleasedTime;
	}

	public void setLastReleasedTime(String lastReleasedTime) {
		this.lastReleasedTime = lastReleasedTime;
	}

	public List<Reservation> getReservation() {
		return reservation;
	}

	public void setReservation(List<Reservation> reservation) {
		this.reservation = reservation;
	}

	public Facility getFacilities() {
		return facilities;
	}

	public void setFacilities(Facility facilities) {
		this.facilities = facilities;
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
    
    

}