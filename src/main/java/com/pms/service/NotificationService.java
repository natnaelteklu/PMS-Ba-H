package com.pms.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pms.entity.Notification;
import com.pms.filter.JwtAuthFilter;
import com.pms.repository.NotificationDao;

@Service
public class NotificationService {
	@Autowired
	private NotificationDao notificationDao;
	
    @Autowired
    private JwtAuthFilter jwtFilter;

	  public ResponseEntity<String> saveNotification(List<Notification> notification) {
		  String timeStamp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a").format(new java.util.Date());
          List<Notification> notificationData = new ArrayList<>();

        for(Notification model:notification) {
        	model.setTimestamp(timeStamp);
                notificationData.add(model);
               }
 
           notificationDao.saveAll(notificationData);
           return new ResponseEntity<>(HttpStatus.OK);
  }
	  

	public List<Notification>  getAllNotification(String username) {
		return notificationDao.getByuser(username);
	}
	public List<Notification> getSomeNotification(String username) {
		return notificationDao.getSomeByuser(username);
	}
	
	public Long getCountUnseen(String username) {
		return notificationDao.getCountUnseen(username);
	}
	public Notification getNotificationById(Long id) {
		return notificationDao.getById(id);
	}
	
	public Notification saveChange(Notification checkExistance) {
		return notificationDao.save(checkExistance);
		
	}
	public void deleteNotification(Long id) {
		notificationDao.deleteById(id);
	}


	public void saveAllNotification(List<Notification> notifications) {
		notificationDao.saveAll(notifications);
	}
	



}
