package com.pms.service;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pms.entity.Setting;
import com.pms.repository.CustomizationDao;

@Service
public class CustomizationService {

    @Autowired
	private CustomizationDao customizationDao;
	public List<Setting> getSettings() {
		return customizationDao.findAll();
	}

	public Setting updateSetting(Setting setting, Long id) {
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a").format(new java.util.Date());
		Setting existingSetting = customizationDao.findById(id).orElse(null);
		existingSetting.setPagetitle(setting.getPagetitle());

		existingSetting.setLastmodified(timeStamp);
		existingSetting.setUpdatedby(setting.getUpdatedby());
		return customizationDao.save(existingSetting);
	}
	
	public Setting updateConsttaint(Setting setting, Long id) {
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a").format(new java.util.Date());
		Setting existingSetting = customizationDao.findById(id).orElse(null);
	
		existingSetting.setLastRemainingTime(setting.getLastRemainingTime());	
		existingSetting.setTimeToCancel(setting.getTimeToCancel());

		
		existingSetting.setLastmodified(timeStamp);
		existingSetting.setUpdatedby(setting.getUpdatedby());
		return customizationDao.save(existingSetting);
	}

	public Setting getById(Long id) {
		return customizationDao.findById(id).orElse(null);
	}
	public Setting updateLogoImage(Setting settingCheck) {
		return customizationDao.save(settingCheck);
	}

	public int getTimeTocancel() {
	    return customizationDao.findById(1L)
	        .map(Setting::getTimeToCancel) 
	        .orElse(2);
	}

	public int getTimeToNotify() {
	    return customizationDao.findById(1L)
	        .map(Setting::getLastRemainingTime) 
	        .orElse(5); 
	}

	public Setting updateSliderImage(Setting settingCheck) {
		return customizationDao.save(settingCheck);
		
	}

	public void updateSocialLinks(Setting checkTitle,String pageData) {
		checkTitle.setSocialLinks(pageData);

		customizationDao.save(checkTitle);
		
	}

	public void updateContactUsAddress(Setting checkAddress, Setting settingData) {
		checkAddress.setAddress(settingData.getAddress());
		checkAddress.setEmail(settingData.getEmail());
		checkAddress.setPhone(settingData.getPhone());
		customizationDao.save(checkAddress);
	}
	
}
