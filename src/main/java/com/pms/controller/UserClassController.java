package com.pms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pms.dto.ReservationDto;
import com.pms.dto.UserClassDto;
import com.pms.service.UserClassService;

@RestController
@RequestMapping("/pms")
@CrossOrigin()
public class UserClassController {
	
    @Autowired
    private UserClassService userClassService;
    
    @GetMapping("/getVIPCustomers/{facilityId}")
    public List<UserClassDto> getVIPCustomers( @PathVariable int facilityId ) {
        return userClassService.getVIPCustomers(facilityId);
    }

    @PostMapping("/saveVipCustomer")
    public ResponseEntity<String> saveVipCustomer(@RequestBody UserClassDto UserClassDto) {
        return userClassService.saveVipCustomer(UserClassDto);
    }
    
    
    @DeleteMapping("/deleteVipById/{id}")
    public ResponseEntity<String> deleteVipById(@PathVariable int id) {
        return userClassService.deleteVipById(id);
    }
    
}
