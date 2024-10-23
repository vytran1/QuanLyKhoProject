package com.quanlykho.security.auth;

public class ResetPasswordRequest {
	private String newPassword;

	public ResetPasswordRequest(String newPassword) {
		super();
		this.newPassword = newPassword;
	}

	public ResetPasswordRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}
