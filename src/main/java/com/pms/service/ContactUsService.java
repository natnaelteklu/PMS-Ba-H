package com.pms.service;


import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.pms.entity.Category;
import com.pms.entity.ContactUs;
import com.pms.filter.JwtAuthFilter;
import com.pms.repository.ContactUsDao;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;



@Service
public class ContactUsService {
	@Autowired
	ContactUsDao contactUsDao;

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private JwtAuthFilter jwtFilter;
	

	public ContactUs contactUsMessage(ContactUs contactUs) {
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a").format(new java.util.Date());
		contactUs.setDateSend(timeStamp);
		contactUs.setReplyStatus("No");
		return contactUsDao.save(contactUs);
	}

	public List<ContactUs> getContactUs() {

	 return contactUsDao.findAll().stream()
	                .sorted(Comparator.comparing(ContactUs::getId).reversed())
	                .collect(Collectors.toList());
	}

	public ResponseEntity<String> deleteEnqury(long id) {
		contactUsDao.deleteById(id);
		 return new ResponseEntity<>(HttpStatus.OK); 
	}


	public void sendReply(ContactUs replays, long id) 
	        throws MessagingException, UnsupportedEncodingException {
	    ContactUs contact = contactUsDao.findById((long) id).orElse(null);
	    String timeStamp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a").format(new java.util.Date());
	    if (contact == null) {
	        throw new IllegalArgumentException("Contact message not found for ID: " + id);
	    }

	    String toAddress = contact.getEmail();
	    String fromAddress = "natea2989@gmail.com"; 
	    String senderName = "Parking Management System";
	    String emailSubject = "Response to Your Parking Inquiry";
	    
	    String content = "<html>"
	            + "<body style='font-family:Arial, sans-serif; color:#333;'>"
	            + "<h3 style='color:#d9534f;'>Dear [[uname]],</h3>"
	            + "<p>Thank you for reaching out to <strong>Our System</strong>. "
	            + "We have received your inquiry, and we are pleased to assist you with the following information:</p>"
	            + "<p style='background:#f8f9fa; padding:15px; border-left:5px solid #d9534f;'>"
	            + "[[msgContent]]</p>"
	            + "<p>If you need further assistance, please don't hesitate to contact us.</p>"
	            + "<br>"
	            + "<p>Best regards,</p>"
	            + "<p><strong>Parking Support Team</strong></p>"
	            + "<p style='font-size:12px; color:#777;'>This is an automated email. Please do not reply directly to this message.</p>"
	            + "</body></html>";

	    content = content.replace("[[uname]]", contact.getName());
	    content = content.replace("[[msgContent]]", replays.getReplays());

	
	    MimeMessage message = mailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message, true);
	    helper.setFrom(fromAddress, senderName);
	    helper.setTo(toAddress);
	    helper.setSubject(emailSubject);
	    helper.setText(content, true);
	    mailSender.send(message);


	    contact.setReplays(replays.getReplays()); 
	    contact.setReplyStatus("Yes"); 
	    contact.setReplayedBy(jwtFilter.currentUserName); 
	    contact.setDatereplay(timeStamp); 
	    
	    contactUsDao.save(contact); 
	}



	public ContactUs findById(Long id) {
		ContactUs status = contactUsDao.findById(id).orElse(null);
		return status;
	}

}
