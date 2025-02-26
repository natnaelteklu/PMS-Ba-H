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

import com.pms.dto.ParkingSpaceDto;
import com.pms.service.ParkingSpaceService;

@RestController
@RequestMapping("/pms")
@CrossOrigin()
public class ParkingSpaceController {

    @Autowired
    private ParkingSpaceService parkingSpaceService;

    @PostMapping("/saveParkingSpace")
    public ResponseEntity<String> addNewParkingSpace(@RequestBody ParkingSpaceDto parkingSpaceDto) {
        return parkingSpaceService.addNewParkingSpace(parkingSpaceDto);
    }

    @GetMapping("/getAllParkingSpaces")
    public List<ParkingSpaceDto> getAllParkingSpaces() {
        return parkingSpaceService.getAllParkingSpaces();
    }
    
    @GetMapping("/getAvailableSlots")
    public List<ParkingSpaceDto> getAvailableSlots() {
        return parkingSpaceService.getAvailableSlots();
    }

    @GetMapping("/getParkingSpaceById/{id}")
    public ParkingSpaceDto getParkingSpaceById(@PathVariable int id) {
        return parkingSpaceService.getParkingSpaceById(id);
    }

    @PutMapping("/updateParkingSpace/{spaceId}")
    public ResponseEntity<String> updateParkingSpaceBySpaceId(@RequestBody ParkingSpaceDto parkingSpaceDto, @PathVariable("spaceId") int spaceId) {
        return parkingSpaceService.updateParkingSpaceBySpaceId(parkingSpaceDto, spaceId);
    }

    @DeleteMapping("/deleteParkingSpace/{id}")
    public ResponseEntity<String> deleteParkingSpace(@PathVariable("id") int id) {
        return parkingSpaceService.deleteParkingSpaceById(id);
    }

    
}
