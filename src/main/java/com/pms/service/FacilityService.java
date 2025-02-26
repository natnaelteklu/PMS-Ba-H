package com.pms.service;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pms.entity.Facility;
import com.pms.entity.ParkingSpace;
import com.pms.entity.Reservation;
import com.pms.filter.JwtAuthFilter;
import com.pms.repository.FacilityDao;
import com.pms.repository.FacilityDao.FacilityReservationProjection;
import com.pms.repository.ReservationDao;

@Service
public class FacilityService {

    @Autowired
    private FacilityDao facilityDao;

    @Autowired
    private JwtAuthFilter jwtFilter;
    
    @Autowired
    private ReservationDao reservationDao;

    public ResponseEntity<String> addNewFacility(Facility facility) {
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a").format(new java.util.Date());
        facility.setCompanyName(facility.getCompanyName().trim());
        Facility existingFacility = facilityDao.findByCompanyNameIgnoreCase(facility.getCompanyName());
        if (existingFacility != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        facility.setCreatedBy(jwtFilter.currentUserName);
        facility.setCreatedAt(timeStamp);
        facilityDao.save(facility);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    
    public List<Facility> getAllFacility() {
        return facilityDao.findAll().stream()
                .sorted(Comparator.comparing(Facility::getFacilityId).reversed())
                .collect(Collectors.toList());
    }

    public List<Facility> getActiveFacility() {
        return facilityDao.findByStatus("Active");
    }
    

    public Facility getFacilityById(int id) {
        return facilityDao.findById(id).orElse(null);
    }

    public ResponseEntity<String> updateFacilityByFacilityId(Facility facility, int facilityId) {
        Facility existingFacility = facilityDao.findById(facilityId).orElse(null);
        if (existingFacility != null) {
        
            existingFacility.setCompanyName(facility.getCompanyName());
            existingFacility.setTin(facility.getTin());
            existingFacility.setContactPerson(facility.getContactPerson());
            existingFacility.setContactNumber(facility.getContactNumber());
            existingFacility.setAddress(facility.getAddress());
            existingFacility.setStatus(facility.getStatus());
            existingFacility.setVipFeePerHr(facility.getVipFeePerHr());
            existingFacility.setRegularFeePerHr(facility.getRegularFeePerHr());
            existingFacility.setBankAccountNumber(facility.getBankAccountNumber());
            existingFacility.setAccountDescription(facility.getAccountDescription());
            existingFacility.setContactPerson(facility.getContactPerson());
            
            
            facilityDao.save(existingFacility);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> deleteFacilityById(int id) {
        if (facilityDao.existsById(id)) {
            facilityDao.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

	public List<FacilityReservationProjection> getAllFacilityWithSpace() {
		// TODO Auto-generated method stub
		return facilityDao.findActiveFacilitiesWithSpacesAndReservationsNative();
	}

	public Facility findFacilityByReservationId(int reservationId) {
	    Reservation reservation = reservationDao.findById(reservationId).orElse(null);
	    if (reservation == null) {
	        return null; 
	    }
	    ParkingSpace parkingSpace = reservation.getSpaces(); 

	    if (parkingSpace == null) {
	        return null;
	    }
	    Facility facility = parkingSpace.getFacilities();
	    return facility;
	}


}
