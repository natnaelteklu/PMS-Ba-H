package com.pms.service;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pms.dto.VehicleDto;
import com.pms.entity.Vehicle;
import com.pms.repository.CategoryDao;
import com.pms.repository.UserInfoRepository;
import com.pms.repository.VehicleDao;

import jakarta.persistence.EntityNotFoundException;

@Service
public class VehicleService {

    @Autowired
    private VehicleDao vehicleDao; 
    
    @Autowired
    private CategoryDao categoryDao; 

    @Autowired
    private UserInfoRepository userInfoDao; 

    public List<VehicleDto> getAllVehicles() {
        return vehicleDao.findAll().stream()
                .map(VehicleDto::new)
                .sorted(Comparator.comparing(VehicleDto::getVehicleId).reversed()) // Sort in descending order
                .collect(Collectors.toList());
    }


    public VehicleDto getVehicleById(int vehicleId) {
        Vehicle vehicle = vehicleDao.findById(vehicleId)
            .orElseThrow(() -> new EntityNotFoundException("Vehicle not found with ID: " + vehicleId));
        return new VehicleDto(vehicle);
    }

    public ResponseEntity<String> addNewVehicle(VehicleDto vehicleDto) {
    	String timeStamp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a").format(new java.util.Date());
		 vehicleDto.setVehiclTitle(vehicleDto.getVehiclTitle().trim());
		 
        Vehicle existingVehicle = vehicleDao.findByVehiclTitle(vehicleDto.getVehiclTitle());
        if (existingVehicle != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
       
        Vehicle vehicle = new Vehicle(vehicleDto);
        vehicle.setCreatedAt(timeStamp);
        vehicleDao.save(vehicle);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<String> updateVehicle(VehicleDto vehicleDto, int vehicleId) {
    	String timeStamp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a").format(new java.util.Date());
        Vehicle existingVehicle = vehicleDao.findById(vehicleId).orElse(null);
		if (existingVehicle != null) {
			
			vehicleDto.setVehicleId(existingVehicle.getVehicleId());
			vehicleDto.setCreatedAt(timeStamp);
			existingVehicle = new Vehicle(vehicleDto);
			vehicleDao.save(existingVehicle);
			return new ResponseEntity<>(HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> deleteVehicle(int vehicleId) {
    	vehicleDao.deleteById(vehicleId);
		 return new ResponseEntity<>(HttpStatus.OK); 
    }


	public List<VehicleDto> getVehicleByUserName(String userId) {
		
        return vehicleDao.findByUser_username(userId).stream()
                .map(VehicleDto::new)
                .sorted(Comparator.comparing(VehicleDto::getVehicleId).reversed()) 
                .collect(Collectors.toList());
	}
}
