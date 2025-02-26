package com.pms.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pms.entity.ContactUs;
import com.pms.service.ContactUsService;

import jakarta.mail.MessagingException;


@RestController
@RequestMapping("/pms")
public class ContactUsController {
	@Autowired
	ContactUsService contactUsService;

	@PostMapping("/addContactUsMsg")
	public ContactUs saveMessage(@RequestBody ContactUs message) {
		return contactUsService.contactUsMessage(message);

	}
	
	@GetMapping("/getContactUs")
	public List<ContactUs> getContactUs() {
		return contactUsService.getContactUs();
	}

	@DeleteMapping("deleteEnqury/{id}")
	public ResponseEntity<String> deleteEnqury(@PathVariable("id") long id) {
		return contactUsService.deleteEnqury(id);
	}

	 @PostMapping("/replyEnquiry/{id}")
	    public ResponseEntity<String> sendReplayToContact( @RequestBody ContactUs replays, @PathVariable("id") long id) {
	        try {
	        	contactUsService.sendReply(replays,id);
	        	return new ResponseEntity<>(HttpStatus.OK); 
	        } catch (UnsupportedEncodingException | MessagingException e) {
	
	            e.printStackTrace();
	            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	        }
	    }

	@PutMapping("/ReplayStatusChange/{id}")
	public ContactUs ReplayStatusChange(@PathVariable("id") Long id, @RequestBody String status) {
		ContactUs newStatus = contactUsService.findById(id);
		newStatus.setReplyStatus(status);
		return contactUsService.contactUsMessage(newStatus);

	}
}
