package com.pms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pms.dto.ParkingSpaceDto;
import com.pms.dto.ReservationDto;
import com.pms.service.ReservationService;

import java.util.List;

@RestController
@RequestMapping("/pms")
@CrossOrigin()
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping("/saveReservation")
    public ResponseEntity<String> addNewReservation(@RequestBody ReservationDto reservationDto) {
        return reservationService.addNewReservation(reservationDto);
    }

    @GetMapping("/getAllCustomers/{facilityId}")
    public List<ReservationDto> getAllCustomers(@PathVariable int facilityId) {
        return reservationService.getAllCustomers(facilityId);
    }
    
    @GetMapping("/getAllReservation")
    public List<ReservationDto> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @GetMapping("/getReservationById/{id}")
    public ResponseEntity<ReservationDto> getReservationById(@PathVariable int id) {
        ReservationDto reservationDto = reservationService.getReservationById(id);
        if (reservationDto != null) {
            return new ResponseEntity<>(reservationDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/updateReservation/{reserveId}")
    public ResponseEntity<String> updateReservation(@RequestBody ReservationDto reservationDto, @PathVariable("reserveId") int reserveId) {
        return reservationService.updateReservation(reservationDto, reserveId);
    }
    @PutMapping("/extendReservationTime/{reserveId}")
    public ResponseEntity<String> extendReservationTime(@RequestBody ReservationDto reservationDto, @PathVariable("reserveId") int reserveId) {
        return reservationService.extendReservationTime(reservationDto, reserveId);
    }
    


    @DeleteMapping("/deleteReservation/{id}")
    public ResponseEntity<String> deleteReservationById(@PathVariable int id) {
        return reservationService.deleteReservationById(id);
    }
}
