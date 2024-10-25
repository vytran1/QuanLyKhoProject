package com.quanlykho.account;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ChangePasswordRequestDTO {
	@NotNull(message = "current Password must not be null")
	@NotBlank(message = "current Password should not contain blank")
    private String currentPassword;
	
	@NotNull(message = "new Password must not be null")
	@NotBlank(message = "new Password should not contain blank")
    private String newPassword;
	
	@NotNull(message = "confirm Password must not be null")
	@NotBlank(message = "confirm Password should not contain blank")
    private String confirmPassword;
    
    public ChangePasswordRequestDTO() {
    	
    }

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
    
    
}
