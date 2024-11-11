package com.quanlykho.security.auth;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotNull;

public class AuthRequest {
	
	@NotNull(message = "Username must not be null")
	@Length(min = 5, max = 60, message = "Username length should be in range 5 to 30")
    private String username;
	
	@NotNull(message = "Password must not be null")
	@Length(min = 5, max = 60, message = "Password length should be in range 5 to 30 ")
    private String password;
    
    
    
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
    
    
}
