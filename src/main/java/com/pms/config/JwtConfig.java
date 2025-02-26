package com.pms.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {
    private String secret;
    private long expirationTime; 
    private long refteshTokenExpirationTime; 
    private String uploadPath;
    private String slideUploadPath;
    
    
    public String getSlideUploadPath() {
		return slideUploadPath;
	}

	public void setSlideUploadPath(String slideUploadPath) {
		this.slideUploadPath = slideUploadPath;
	}

	private long jobscheduleTime;
    

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(long expirationTime) {
        this.expirationTime = expirationTime;
    }

	public long getRefteshTokenExpirationTime() {
		return refteshTokenExpirationTime;
	}

	public void setRefteshTokenExpirationTime(long refteshTokenExpirationTime) {
		this.refteshTokenExpirationTime = refteshTokenExpirationTime;
	}

	public long getJobscheduleTime() {
		return jobscheduleTime;
	}

	public void setJobscheduleTime(long jobscheduleTime) {
		this.jobscheduleTime = jobscheduleTime;
	}

	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}
    
    
}
