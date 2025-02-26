package com.pms.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pms.config.JwtConfig;
import com.pms.entity.Setting;
import com.pms.service.CustomizationService;

@RestController
@RequestMapping("/pms")
@CrossOrigin()
public class CustomizationController {

    private static final Logger log = LoggerFactory.getLogger(CustomizationController.class);
    
    //public static final String uploadDir = "D:/PMS/PMS-FrontEnd/src/assets/images";
    @Autowired
    private CustomizationService customizationService;

    private final JwtConfig jwtConfig;

    @Autowired
    public CustomizationController(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @GetMapping("/getCustomization")
    public List<Setting> getSettings() {
        return customizationService.getSettings();
    }

    @PutMapping("/updateSetting/{id}")
    public Setting changeTitle(@RequestBody Setting setting, @PathVariable Long id) {
        return customizationService.updateSetting(setting, id);
    }

    @PutMapping("/updateConsttaint/{id}")
    public Setting updateConsttaint(@RequestBody Setting setting, @PathVariable Long id) {
        return customizationService.updateConsttaint(setting, id);
    }

    @PutMapping("/updateSliderImage/{settingId}")
    public ResponseEntity<String>  updateSliderImage(@PathVariable("settingId") Long settingId,@RequestParam(name = "image") MultipartFile file,
    	@RequestParam(name = "userName") String userName
    		,@RequestParam(name = "sliderName") String sliderName) throws IOException, JsonProcessingException {
        
    	Setting settingCheck = customizationService.getById(settingId);
    	String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss aa").format(new java.util.Date());
    	
    	String sliderUploadPath = jwtConfig.getSlideUploadPath();
    	
    	int slidername = Integer.parseInt(sliderName);
    	if(settingCheck != null) {
    	
    		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
    		String filename = sliderName + "." + extension;
    		
//    		if(slidername==4)
//    	     filename = "admin" + sliderName + "." + extension;
//    		if(slidername==5)
//	    	     filename = "trainer" + sliderName + "." + extension;
//    		if(slidername==6)
//	    	     filename = "trainee" + sliderName + "." + extension;
    		
        	Path FileNameAndPath = Paths.get(sliderUploadPath,filename);
        	try {
        	
        		Files.write(FileNameAndPath, file.getBytes());
        	}
        	catch (IOException e){
        		e.printStackTrace();
        	}
        	settingCheck.setLastmodified(timeStamp);
        	if(slidername==1) {
        	settingCheck.setSliderImage1(filename);
        	customizationService.updateSliderImage(settingCheck);
        	}
        	
        	 if(slidername==3) {
	        	settingCheck.setSliderImage3(filename);
	        	customizationService.updateSliderImage(settingCheck);
	        	}
        	 if(slidername==2) {
		        	settingCheck.setSliderImage2(filename);
		        	customizationService.updateSliderImage(settingCheck);
		        	}
        	 
//        	 if(slidername==4) {
//		        	settingCheck.setAdminManual(filename);
//		        	customizationService.updateSliderImage(settingCheck);
//        	 }
//        	 if(slidername==5) {
//		        	settingCheck.setTrainerManual(filename);
//		        	customizationService.updateSliderImage(settingCheck);
//		     }
//        	 if(slidername==6) {
//		        	settingCheck.setTraineeManual(filename);
//		        	customizationService.updateSliderImage(settingCheck);
//		     }
        	 return new ResponseEntity<>(HttpStatus.OK);
    	}
    	else {
    		 return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
    }
    
    @PutMapping("/updateSocialLinks")
    public ResponseEntity<String>  updateSocialLinks(
    		@RequestParam(name = "socialLinkData") String pageData,@RequestParam(name="userName") String userName,
    		@RequestParam(name="socialLinkId") Long settingId
    		) throws IOException, JsonProcessingException {
        
    	Setting checkTitle = customizationService.getById(settingId);
    	
    	if(checkTitle!= null) {
    		customizationService.updateSocialLinks(checkTitle,pageData);
        	 return new ResponseEntity<>(HttpStatus.OK);
    	}
    	else {
    		 return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
    }
    
    @PutMapping("/updateContactUsAddress")
    public ResponseEntity<String>  updateContactUsAddress(@RequestParam(name = "address") String addressData,
    		@RequestParam(name="addressId") Long addressId) throws IOException, JsonProcessingException {
        
    	Setting settingData = new ObjectMapper().readValue(addressData,Setting.class);
    	Setting checkAddress = customizationService.getById(addressId);
    	
    	if(checkAddress!= null) {
    		customizationService.updateContactUsAddress(checkAddress,settingData);
        	 return new ResponseEntity<>(HttpStatus.OK);
    	}
    	else {
    		 return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
    }
    
    
    @PutMapping("/changeLogoImage/{id}/{userName}")
    public ResponseEntity<String> changeLogoImage(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "image") MultipartFile file,
            @PathVariable(name = "userName") String userName) {

        log.info("Received request to update logo image for ID: {}", id);

        String timeStamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss aa").format(new java.util.Date());
        Setting settingCheck = customizationService.getById(id);

        if (file.isEmpty()) {
            log.error("File is empty!");
            return new ResponseEntity<>("File is empty", HttpStatus.BAD_REQUEST);
        }

        // Ensure jwtConfig is properly loaded
        String uploadPath = jwtConfig.getUploadPath();
        if (uploadPath == null || uploadPath.trim().isEmpty()) {
            log.error("Upload path is not configured in application.properties");
            return new ResponseEntity<>("Upload path is not configured", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("Upload Path: {}", uploadPath);

        // Generate file path
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String filename = "Logo." + extension;
        Path filePath = getFilePath(uploadPath, filename);

        try {
            // Ensure directory exists
            Files.createDirectories(filePath.getParent());

            // Write file to the destination
            Files.write(filePath, file.getBytes(), StandardOpenOption.CREATE);

            log.info("File saved at: {}", filePath.toString());
        } catch (IOException e) {
            log.error("Error writing file: {}", e.getMessage());
            return new ResponseEntity<>("Error saving file", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Update database record
        settingCheck.setLogo(filename);
        settingCheck.setLastmodified(timeStamp);
        settingCheck.setUpdatedby(userName);

        customizationService.updateLogoImage(settingCheck);
        log.info("Logo updated successfully for ID: {}", id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Path getFilePath(String uploadPath, String filename) {
        return Paths.get(uploadPath, filename);
    }
    
    
}
