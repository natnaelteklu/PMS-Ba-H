package com.pms.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pms.dto.UserInfoDto;
import com.pms.entity.Facility;
import com.pms.entity.Role;
import com.pms.entity.UserInfo;
import com.pms.repository.FacilityDao;
import com.pms.repository.RoleRepository;
import com.pms.repository.UserInfoRepository;

@Service
public class UserService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private FacilityDao facilityDao;

//    public ResponseEntity<String> registerUser(UserInfo userInfo, int roleId) {
//    	
//    	Role roles = roleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("Role not found: " + roleId));
//        Set<Role> userRoles = new HashSet<>();
//        userRoles.add(roles);
//        String timeStamp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a").format(new java.util.Date());
//	
//        if (roles != null && 
//        	    (userInfoRepository.existsById(userInfo.getUsername().toLowerCase()) || 
//        	     userInfoRepository.existsByUserPhone(userInfo.getUserPhone()))) {
//        	    return new ResponseEntity<>("User already exists.", HttpStatus.BAD_REQUEST);
//        	}
//        userInfo.setRoles(userRoles);
//        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
//        userInfo.setLastModified(timeStamp);
//        
//        if(roleId!=2) {
//        userInfo.setIsPwdExpired("Yes");
//        }
//
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//		Date date = new Date();
//		Calendar c = Calendar.getInstance();
//		c.setTime(date);
//		c.add(Calendar.MONTH, 1);
//		Date expirationDate = c.getTime();
//		userInfo.setPasswordExpirationDate(dateFormat.format(expirationDate));
//		userInfoRepository.save(userInfo);
//		sendHtmlVerificationEmail(userInfo);
//        return new ResponseEntity<>(HttpStatus.OK); 
//    }
//    
    public ResponseEntity<String> registerCustomer(UserInfoDto userInfoDto, int roleId) {
        Role roles = roleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("Role not found: " + roleId));
        UserInfo userInfo = new UserInfo(userInfoDto); 
    
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(roles); 
        userInfo.setRoles(userRoles);
  

        String timeStamp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a").format(new java.util.Date());
        userInfo.setLastModified(timeStamp);
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, 1);
        Date expirationDate = c.getTime();
        userInfo.setPasswordExpirationDate(dateFormat.format(expirationDate));
        if (userInfoRepository.existsById(userInfo.getUsername().toLowerCase()) || userInfoRepository.existsByUserPhone(userInfo.getUserPhone())) {
            return new ResponseEntity<>("User already exists.", HttpStatus.BAD_REQUEST);
        }
     
        sendHtmlVerificationEmail(userInfo);
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userInfoRepository.save(userInfo); 
        return new ResponseEntity<>(HttpStatus.OK); 
    }
    
    public ResponseEntity<String> adminRegisterUser(UserInfoDto userInfoDto, int roleId) {
        Role roles = roleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("Role not found: " + roleId));
        UserInfo userInfo = new UserInfo(userInfoDto); 
            userInfo.setIsPwdExpired("Yes");
            userInfo.setUserStatus(1);
        
         if (roleId != 1) {
             Facility facility = facilityDao.findById(userInfoDto.getFacilityId()).orElseThrow(() -> new RuntimeException("Facility not found: " + userInfoDto.getFacilityId()));
             userInfo.setFacilitty(facility);
             
         }
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(roles); 
        userInfo.setRoles(userRoles);
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a").format(new java.util.Date());
        userInfo.setLastModified(timeStamp);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, 1);
        Date expirationDate = c.getTime();
        userInfo.setPasswordExpirationDate(dateFormat.format(expirationDate));
        if (userInfoRepository.existsById(userInfo.getUsername().toLowerCase()) || userInfoRepository.existsByUserPhone(userInfo.getUserPhone())) {
            return new ResponseEntity<>("User already exists.", HttpStatus.BAD_REQUEST);
        }
   
        sendInitialConformation(userInfo);
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userInfoRepository.save(userInfo); 

        return new ResponseEntity<>(HttpStatus.OK); 
    }
    

	public UserInfo getUserByUsername(String username) {
		return userInfoRepository.findByusernameIgnoreCase(username);
	}


	public List<UserInfo> getUserByOrogin(int origin) {
		return userInfoRepository.findByfacilitty_FacilityId(origin);
	}

    public ResponseEntity<UserInfo> getUserByUsernameAndEnabled(String username, int status) {
        UserInfo user = userInfoRepository.findByUsernameAndUserStatus(username, status);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    public List<UserInfoDto> getAllUsers() {
        return userInfoRepository.findAll().stream()
        		 .map(UserInfoDto::new)
                .sorted(Comparator.comparing(UserInfoDto::getUsername).reversed())
                .collect(Collectors.toList());
    }
    


	public ResponseEntity<String> deleteUserByUsername(String username) {

		try {
			userInfoRepository.deleteById(username);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Cannot delete User because it is referenced by entity");
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	 public boolean verify(String code) {
	    UserInfo user = userInfoRepository.findByverifcationCode(code);

	    if (user == null) {
	        return false;
	    } else {
	   
	        user.setVerifcationCode(null);
	        user.setUserStatus(1);
	        userInfoRepository.save(user); 
	        return true; 
	    }
	}
	public ResponseEntity<String> verifyCode(String code, String userName, String password) {
	    UserInfo user = userInfoRepository.findByUsernameAndVerifcationCode(userName, code);

	    if (user != null) {
	        user.setVerifcationCode(null);
	        user.setUserStatus(1);

	        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	        String hashedPassword = encoder.encode(password);
	        user.setPassword(hashedPassword);
	        userInfoRepository.save(user);
	    	return new ResponseEntity<>(HttpStatus.OK);
	    } else {
	         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

	    }
	}


	
	
	public ResponseEntity<String> sendVerificationEmail(String username) {
	    UserInfo user = userInfoRepository.findByUsernameAndUserStatus(username, 1);
	    if (user != null) {
	        String verificationCode = generateVerificationCode();
	        user.setVerifcationCode(verificationCode);
	        userInfoRepository.save(user);

	        String subject = "Account Verification";
	        
	        // HTML email content with inline styles
	        String message = "<html>" +
	                         "<body style=\"font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;\">" +
	                         "<div style=\"background-color: #ffffff; padding: 20px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); max-width: 600px; margin: auto;\">" +
	                             "<h2 style=\"color: #4CAF50; text-align: center;\">Account Verification</h2>" +
	                             "<p style=\"font-size: 16px; line-height: 1.5;\">Hello <strong>" + user.getUserFirstName() + "</strong>,</p>" +
	                             "<p style=\"font-size: 16px; line-height: 1.5;\">Please verify your account using the following code:</p>" +
	                             "<div style=\"text-align: center; margin-top: 20px;\">" +
	                                 "<span style=\"font-size: 24px; font-weight: bold; padding: 10px 20px; background-color: #4CAF50; color: white; border-radius: 5px;\">" + verificationCode + "</span>" +
	                             "</div>" +
	                             "<p style=\"font-size: 16px; line-height: 1.5; text-align: center; margin-top: 20px;\">" +
	                                 "If you did not request this, please ignore this email. The code will expire in 30 minutes." +
	                             "</p>" +
	                             "<p style=\"font-size: 16px; line-height: 1.5; text-align: center;\">" +
	                                 "Thank you for using our service!" +
	                             "</p>" +
	                             "<footer style=\"text-align: center; margin-top: 30px; color: #888; font-size: 12px;\">" +
	                                 "<p>&copy; 2024 Parking Managemet System. All Rights Reserved.</p>" +
	                             "</footer>" +
	                         "</div>" +
	                         "</body>" +
	                         "</html>";

	        // Sender's email address and name
	        String fromAddress = "natea2989@gmail.com";
	        String senderName = "Parking System";

	        // Send the email using your email service's sendHtmlEmail method
	        boolean emailSent = emailService.sendHtmlEmail(user.getUsername(), fromAddress, senderName, subject, message);
	        if (emailSent) {
	            return new ResponseEntity<>(HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    } else {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	}


    public ResponseEntity<String> updateUser(UserInfoDto user, String username,  int roleId) {
    	String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss aa").format(new java.util.Date());
     	UserInfo userData = userInfoRepository.findByusernameIgnoreCase(username);
    	Role roles = roleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("Role not found: " + roleId));
    	 Set<Role> userRoles = new HashSet<>();
		if (userData != null) {
       if(roles!=null) {
    	   userRoles.add(roles);
    	   userData.setRoles(userRoles);
       }
        
        userData.setLastModified(timeStamp);
        userData.setIsPwdExpired("Yes");
        
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, 1);
		Date expirationDate = c.getTime();
		userData.setPasswordExpirationDate(dateFormat.format(expirationDate));
		userData.setDob(user.getDob());
		userData.setDrivingLicence(user.getDrivingLicence());
		userData.setExperience(user.getExperience());
		userData.setUserAdress(user.getUserAdress());
		userData.setUserFirstName(user.getUserFirstName());
		userData.setUserMiddleName(user.getUserMiddleName());
		userData.setUserLastName(user.getUserLastName());
		userData.setUserGender(user.getUserGender());
		userData.setUserPhone(user.getUserPhone());
		Facility facilityData= facilityDao.findById(user.getFacilityId()).orElse(null);
		userData.setFacilitty(facilityData);
		
		userInfoRepository.save(userData);
        return new ResponseEntity<>(HttpStatus.OK); 
		}
		else {
			 return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
		}
    }
    
	public ResponseEntity<String> customerUpdateDetailByUsername(UserInfo user, String username) {
     	UserInfo userData = userInfoRepository.findByusernameIgnoreCase(username);
		if (userData != null) {
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss aa").format(new java.util.Date());
        userData.setLastModified(timeStamp);
		userData.setDob(user.getDob());
		userData.setDrivingLicence(user.getDrivingLicence());
		userData.setExperience(user.getExperience());
		userData.setPassword(user.getPassword());
		userData.setUserAdress(user.getUserAdress());
		userData.setUserFirstName(user.getUserFirstName());
		userData.setUserMiddleName(user.getUserMiddleName());
		userData.setUserLastName(user.getUserLastName());
		userData.setUserGender(user.getUserGender());
		userData.setUserPhone(user.getUserPhone());
		userInfoRepository.save(userData);
        return new ResponseEntity<>(HttpStatus.OK); 
		}
		else {
			 return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
		}
	}
    
	public ResponseEntity<String> changePassword(String userName, String password) {
		UserInfo userData = getUserByUsername(userName);
		if (userData != null) {
			BCryptPasswordEncoder vb = new BCryptPasswordEncoder();
			String result = vb.encode(password);
			userData.setPassword(result);
			userData.setIsPwdExpired(null);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.MONTH, 1);
			Date expirationDate = c.getTime();
			userData.setPasswordExpirationDate(dateFormat.format(expirationDate));
			userInfoRepository.save(userData);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}


	public ResponseEntity<String> changePassword(String userName, String oldPassword, String newPassword) {
	    UserInfo userData = getUserByUsername(userName);
	    if (userData != null) {
	        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	        if (encoder.matches(oldPassword, userData.getPassword())) {
	            System.out.println("Old password matches. Updating password...");
	            String result = encoder.encode(newPassword);
	            userData.setPassword(result);
	            userData.setIsPwdExpired(null);
	            
	            // Set the password expiration date
	            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	            Date date = new Date();
	            Calendar c = Calendar.getInstance();
	            c.setTime(date);
	            c.add(Calendar.MONTH, 1);
	            Date expirationDate = c.getTime();
	            userData.setPasswordExpirationDate(dateFormat.format(expirationDate));

	            userInfoRepository.save(userData);

	            System.out.println("Password updated successfully for user: " + userName);
	            return new ResponseEntity<>(HttpStatus.OK);
	        } else {
	            System.out.println("Old password does not match.");
	            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	        }
	    }

	    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	
    private String generateVerificationCode() {
        return String.valueOf((int) (Math.random() * 1000000));
    }

	public UserInfo getUserByUserNames(String userName) {
		
		return userInfoRepository.findById(userName).orElse(null);
	}
	
	public void updateUserStatus(UserInfo user, int status) { 
		userInfoRepository.save(user);
	}


	public UserInfo getUserByUsernameAndstatusEnabled(String username) {
		return userInfoRepository.findByUsernameAndUserStatus(username.toUpperCase(), 1);
	}

	public ResponseEntity<String> sendHtmlVerificationEmail(UserInfo user) {
	    try {
	        // Generate the verification code and update the user's record
	        String verificationCode = generateVerificationCode();
	        user.setVerifcationCode(verificationCode);
	        userInfoRepository.save(user);

	        String fromAddress = "natea2989@gmail.com";
	        String senderName = "Parking System";
	        String subject = "Please complete your registration";

	        // HTML content with inline styles for a more polished look
	        String content = "<html>"
	                + "<body style=\"font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;\">"
	                + "<div style=\"background-color: #ffffff; padding: 30px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); max-width: 600px; margin: auto;\">"
	                + "<h2 style=\"color: #4CAF50; text-align: center;\">Welcome to the Parking Management System</h2>"
	                + "<p style=\"font-size: 16px; line-height: 1.5;\">Dear <strong>" + user.getUserFirstName() + "</strong>,</p>"
	                + "<p style=\"font-size: 16px; line-height: 1.5;\">Thank you for registering with our system. To complete your registration, please verify your account by clicking the link below:</p>"
	                + "<div style=\"text-align: center; margin-top: 20px;\">"
	                + "<a href=\"[[URL]]\" target=\"_self\" style=\"font-size: 18px; background-color: #4CAF50; color: white; padding: 12px 30px; text-decoration: none; border-radius: 5px;\">VERIFY NOW</a>"
	                + "</div>"
	                + "<p style=\"font-size: 16px; line-height: 1.5; text-align: center; margin-top: 20px;\">If you did not request this, you can safely ignore this email.</p>"
	                + "<footer style=\"text-align: center; margin-top: 30px; color: #888; font-size: 12px;\">"
	                + "<p>&copy; 2024 Parking Management System. All Rights Reserved.</p>"
	                + "</footer>"
	                + "</div>"
	                + "</body>"
	                + "</html>";

	        // Replace the placeholders with actual values
	        String verifyURL = "http://localhost:4200/verify?code=" + user.getVerifcationCode();
	          //String verifyURL = "http://localhost:8080/PMS/verify?code=" + user.getVerifcationCode();
	        content = content.replace("[[URL]]", verifyURL);

	        // Send the email using the sendHtmlEmail method from the EmailService
	        boolean emailSent = emailService.sendHtmlEmail(user.getUsername(), fromAddress, senderName, subject, content);
	        if (emailSent) {
	            return new ResponseEntity<>("Verification email sent successfully.", HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("Failed to send verification email.", HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ResponseEntity<>("An error occurred while sending the verification email.", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}



	public ResponseEntity<String> sendInitialConformation(UserInfo user) { 
	    try {
	        // HTML content for the email
	        String fromAddress = "natea2989@gmail.com";
	        String senderName = "Parking System";
	        String subject = "Your User Account Details";

	        // Custom email content
	        String content = "<html>"
	                + "<body style=\"font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;\">"
	                + "<div style=\"background-color: #ffffff; padding: 30px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); max-width: 600px; margin: auto;\">"
	                + "<h2 style=\"color: #4CAF50; text-align: center;\">Welcome to the Parking Management System</h2>"
	                + "<p style=\"font-size: 16px; line-height: 1.5;\">Dear <strong>[[name]]</strong>,</p>"
	                + "<p style=\"font-size: 16px; line-height: 1.5;\">I have created a user account for you in the Parking Management System.</p>"
	                + "<p style=\"font-size: 16px; line-height: 1.5;\">Here is your username and password. I recommend you change your password immediately:</p>"
	                + "<ul style=\"font-size: 16px; line-height: 1.5;\">"
	                + "<li><strong>Username:</strong> [[uname]]</li>"
	                + "<li><strong>Password:</strong> [[password]]</li>"
	                + "</ul>"
	                + "<p style=\"font-size: 16px; line-height: 1.5;\">Thank you,</p>"
	                + "<footer style=\"text-align: center; margin-top: 30px; color: #888; font-size: 12px;\">"
	                + "<p>&copy; 2024 Parking Management System. All Rights Reserved.</p>"
	                + "</footer>"
	                + "</div>"
	                + "</body>"
	                + "</html>";

	        content = content.replace("[[name]]", user.getUserFirstName());
	        content = content.replace("[[uname]]", user.getUsername());
	        content = content.replace("[[password]]", user.getPassword());

	        // Send the email using the sendHtmlEmail method from the EmailService
	        boolean emailSent = emailService.sendHtmlEmail(user.getUsername(), fromAddress, senderName, subject, content);
	        if (emailSent) {
	            return new ResponseEntity<>("Initial confirmation email sent successfully.", HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("Failed to send initial confirmation email.", HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ResponseEntity<>("An error occurred while sending the initial confirmation email.", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}







}
