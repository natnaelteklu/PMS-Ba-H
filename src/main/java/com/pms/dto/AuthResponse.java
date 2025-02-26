package com.pms.dto;

import com.pms.entity.UserInfo;

public class AuthResponse {

    private UserInfo details;
    private String token;
    
    private String refreshToken;

    public AuthResponse() {}
    // Constructor with both details and token
    public AuthResponse(UserInfo details, String token, String string ) {
        super();
        this.details = details;
        this.token = token;
        this.refreshToken=string;
    }

    // Constructor with only token (for token refresh scenario)
    public AuthResponse(String token) {
        super();
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserInfo getDetails() {
        return details;
    }

    public void setDetails(UserInfo details) {
        this.details = details;
    }

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}




    
}
