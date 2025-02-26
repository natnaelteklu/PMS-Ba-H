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

import com.pms.dto.VehicleDto;
import com.pms.service.VehicleService;

@RestController
@RequestMapping("/pms")
@CrossOrigin()
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/getAllVehicle")
    public List<VehicleDto> getAllVehicles() {
        return vehicleService.getAllVehicles();
    }

    @PostMapping("/saveVehicle")
    public ResponseEntity<String> addNewVehicle(@RequestBody VehicleDto vehicleDto) {
        return vehicleService.addNewVehicle(vehicleDto);
    }
    
    @GetMapping("/getVehicleById/{vehicleId}")
    public VehicleDto getVehicleById(@PathVariable int vehicleId) {
        return vehicleService.getVehicleById(vehicleId);
    }

    @GetMapping("/getVehicleByUserName/{userId}")
    public List<VehicleDto> getVehicleByUserName(@PathVariable String userId) {
        return vehicleService.getVehicleByUserName(userId);
    }
    

    @PutMapping("/updateVehicle/{vehicleId}")
    public ResponseEntity<String> updateVehicle(@RequestBody VehicleDto vehicleDto, @PathVariable int vehicleId ) {
        return vehicleService.updateVehicle(vehicleDto, vehicleId );
    }

    @DeleteMapping("/deleteVehicle/{vehicleId}")
    public ResponseEntity<String> deleteVehicle(@PathVariable int vehicleId) {
        return vehicleService.deleteVehicle(vehicleId);
    }
    

}
