package com.pms.service;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pms.dto.ReservationDto;
import com.pms.dto.UserClassDto;
import com.pms.dto.VehicleDto;
import com.pms.entity.Facility;
import com.pms.entity.Notification;
import com.pms.entity.Reservation;
import com.pms.entity.UserClass;
import com.pms.entity.UserInfo;
import com.pms.repository.ReservationDao;
import com.pms.repository.UserClassDao;

@Service
public class UserClassService {

    @Autowired
    private UserClassDao userClassDao;
    
    @Autowired
    private   NotificationService notificationService;

	public List<UserClassDto> getVIPCustomers(int facilityId) {
        
        return userClassDao.findByFacilitie_facilityId(facilityId).stream()
                .map(UserClassDto::new)
                .sorted(Comparator.comparing(UserClassDto::getClassId).reversed()) 
                .collect(Collectors.toList());
    }

	public ResponseEntity<String> saveVipCustomer(UserClassDto userClassDto) {
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a").format(new java.util.Date());
		userClassDto.setUsername(userClassDto.getUsername().trim());
		
		UserClass existingUserclass = userClassDao.findByuserInf_username(userClassDto.getUsername());
        if (existingUserclass != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userClassDto.setClassType("VIP");
        userClassDto.setCustomerSince(timeStamp);
     
        UserClass userclass = new UserClass(userClassDto);
        userClassDao.save(userclass);
        
        Notification vipNotification = new Notification();
        vipNotification.setContent("Congratulations! You have been assigned as a VIP customer at " + userClassDto.getCompanyName() + ". Enjoy exclusive benefits and personalized services tailored just for you!");
        vipNotification.setType("success");
        vipNotification.setUserId(userClassDto.getUsername());
        vipNotification.setSubject("You’re Now a VIP Customer");
        vipNotification.setStatus(0);
        vipNotification.setTimestamp(timeStamp);
        notificationService.saveChange(vipNotification);
        return new ResponseEntity<>(HttpStatus.OK);
	}

	public ResponseEntity<String> deleteVipById(int id) {
	
        if (userClassDao.existsById(id)) {
        	userClassDao.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    
	}


}
