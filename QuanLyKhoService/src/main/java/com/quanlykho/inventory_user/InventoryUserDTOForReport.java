package com.quanlykho.inventory_user;

public class InventoryUserDTOForReport {
	private String userId;
	private String name;

	public InventoryUserDTOForReport(String userId, String name) {
		super();
		this.userId = userId;
		this.name = name;
	}

	public InventoryUserDTOForReport() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
