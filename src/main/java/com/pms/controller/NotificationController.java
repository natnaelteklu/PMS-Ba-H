package com.pms.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.pms.entity.Notification;
import com.pms.service.NotificationService;

@RestController
@RequestMapping("/pms")
@CrossOrigin()
public class NotificationController {
	@Autowired
	private NotificationService notificationService;
	
	@PostMapping("/saveNotification")
	public ResponseEntity<String> saveNotification(@RequestBody List<Notification> notification) {
		return notificationService.saveNotification(notification);
	}
  
	 @GetMapping({"/getAllNotification/{userName}"})
	 public List<Notification> getAllNotification(@PathVariable(name="userName") String username){
	     return notificationService.getAllNotification(username);
	 }

	 @GetMapping({"/getSomeNotification/{userId}"})
	 public List<Notification> getSomeNotification(@PathVariable(name="userId") String username){
	     return notificationService.getSomeNotification(username);
	 }
	 

	 @GetMapping({"/getCountUnseen/{userName}"})
	 public Long getCountUnseen(@PathVariable(name="userName") String username){
	     return notificationService.getCountUnseen(username);
	 }
	 

	 @PutMapping("/setToSeen/{id}")
	 public ResponseEntity<String> setToSeen(@PathVariable(name="id") Long id) {
			Notification checkExistance = notificationService.getNotificationById(id);
				checkExistance.setStatus(1);
				notificationService.saveChange(checkExistance);	
				return new ResponseEntity<>(HttpStatus.OK);
				
	 }
	 
	
	 @PutMapping("/setAllToSeen/{userName}")
	 public ResponseEntity<String> updateStatus(@PathVariable(name="userName") String userName) {
			List<Notification> checkNotification = notificationService.getAllNotification(userName);
			for(int i=0; i<checkNotification.size(); i++) {
				checkNotification.get(i).setStatus(1);
				notificationService.saveChange(checkNotification.get(i));
				
			}
			return new ResponseEntity<>(HttpStatus.OK);
	 }
	 

	 @DeleteMapping("/deleteNotification/{id}")
	 public ResponseEntity<String>  deleteNotification(@PathVariable Long id) {
	 	notificationService.deleteNotification(id);
	 	return new ResponseEntity<>(HttpStatus.OK);
			 
	 }

	 
}