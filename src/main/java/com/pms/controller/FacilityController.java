package com.pms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pms.entity.Facility;
import com.pms.repository.FacilityDao.FacilityReservationProjection;
import com.pms.service.FacilityService;

@RestController
@RequestMapping("/pms")
@CrossOrigin()
public class FacilityController {

    @Autowired
    private FacilityService facilityService;

    @PostMapping("/saveFacility")
    public ResponseEntity<String> addNewFacility(@RequestBody Facility facility) {
        return facilityService.addNewFacility(facility);
    }

    @GetMapping("/getAllFacility")
    public List<Facility> getAllFacility() {
        return facilityService.getAllFacility();
    }
    
    @GetMapping("/getActiveFacility")
    public List<Facility> getActiveFacility() {
        return facilityService.getActiveFacility();
    }
    
    @GetMapping("/getAllFacilityWithSpace")
    public List<FacilityReservationProjection> getAllFacilityWithSpace() {
        return facilityService.getAllFacilityWithSpace();
    }


    @GetMapping("/getFacilityById/{id}")
    public Facility getFacilityById(@PathVariable int id) {
        return facilityService.getFacilityById(id);
    }

    @PutMapping("/updateFacility/{facilityId}")
    public ResponseEntity<String> updateFacilityByFacilityId(@RequestBody Facility facility, @PathVariable("facilityId") int facilityId) {
        return facilityService.updateFacilityByFacilityId(facility, facilityId);
    }

    @DeleteMapping("/deleteFacility/{id}")
    public ResponseEntity<String> deleteFacility(@PathVariable("id") int id) {
        return facilityService.deleteFacilityById(id);
    }
    

    
}
