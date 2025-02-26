package com.pms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pms.dto.UserInfoDto;
import com.pms.entity.UserInfo;
import com.pms.service.UserService;

@RestController
@RequestMapping("/pms")
@CrossOrigin()

public class UserController {

    @Autowired
    private UserService userService;

  
    @PostMapping("/adminRegisterUser/{roleId}")
    public ResponseEntity<String> registerUser(@RequestBody UserInfoDto userInfoDto, @PathVariable  int roleId) {
        return userService.adminRegisterUser(userInfoDto,roleId);
    } 
    
    @PostMapping("/registerCustomer/{roleId}")
    public ResponseEntity<String> registerCustomer(@RequestBody UserInfoDto userInfoDto, @PathVariable  int roleId) {
        return userService.registerCustomer(userInfoDto,roleId);
    } 
    
    
	@GetMapping("/getUserByUsername/{username}")
	public UserInfo getUserByUsername(@PathVariable("username") String username) {
		return userService.getUserByUsername(username.toUpperCase());
	}

    @GetMapping("/getUserByUsernameAndEnabled/{username}/{status}")
    public ResponseEntity<UserInfo> getUserByUsernameAndEnabled(@PathVariable String username, @PathVariable int status) {
        return userService.getUserByUsernameAndEnabled(username, status);
    }
    
	@PutMapping("/updateuserStatus/{userName}")
	public void updateStatus( @PathVariable("userName") String userName,
			@RequestBody int status) {
		UserInfo userData = userService.getUserByUserNames(userName.toUpperCase());
		if (userData != null) {
			userData.setUserStatus(status);
			userService.updateUserStatus(userData, status);
		}
	}

	@PutMapping({ "/verifyCode" })
	public ResponseEntity<String> verifyUser(@RequestParam(name = "code") String code,
	                                         @RequestParam(name = "userName") String userName, 
	                                         @RequestParam(name = "password") String password) {
	    return userService.verifyCode(code, userName, password);
	}

	

    @PostMapping({"/verifyUser"})
    public ResponseEntity<Map<String, Boolean>> verifyUser(@RequestParam("code") String code) {
        boolean isVerified = userService.verify(code);

        Map<String, Boolean> response = new HashMap<>();
        response.put("success", isVerified); // Return the result as success: true or success: false
      
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping("/getAllUsers")
    public List<UserInfoDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/deleteUserByUsername/{username}")
    public ResponseEntity<String> deleteUserByUsername(@PathVariable String username) {
        return userService.deleteUserByUsername(username);
    }

    @PostMapping("/sendVerificationEmail/{username}")
    public ResponseEntity<String> sendVerificationEmail(@PathVariable String username) {
        return userService.sendVerificationEmail(username);
    }

	@PutMapping("/updateUserByUsername/{username}/{roleId}")
	public ResponseEntity<String> updateUser(@PathVariable("username") String username, @PathVariable("roleId") int roleId,@RequestBody UserInfoDto user) {
		return userService.updateUser(user, username, roleId);
	}
	
	@PutMapping("/customerUpdateDetailByUsername/{username}")
	public ResponseEntity<String> customerUpdateDetailByUsername(@PathVariable("username") String username,@RequestBody UserInfo user) {
		return userService.customerUpdateDetailByUsername(user, username);
	}

	@PostMapping("/forgotPassword")
	public ResponseEntity<String> forgotPassword(@RequestParam(name = "userName") String userName){
			return userService.sendVerificationEmail(userName);
		} 
	
	@PutMapping("/changePassword/{userName}")
	public ResponseEntity<String> changeUserPassword(
			   @PathVariable("userName") String userName,
			    @RequestParam("oldPassword") String oldPassword,
			    @RequestParam("newPassword") String newPassword) {
			    
		return userService.changePassword(userName, oldPassword,newPassword );
	}

}
