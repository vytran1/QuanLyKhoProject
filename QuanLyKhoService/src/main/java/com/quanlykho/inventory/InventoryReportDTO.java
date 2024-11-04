package com.quanlykho.inventory;

public class InventoryReportDTO {
    private String inventoryId;
    private String inventoryName;
    private String inventoryAddress;
    private String country;
    private String state;
    private String district;
	public InventoryReportDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public InventoryReportDTO(String inventoryId, String inventoryName, String inventoryAddress, String country,
			String state, String district) {
		super();
		this.inventoryId = inventoryId;
		this.inventoryName = inventoryName;
		this.inventoryAddress = inventoryAddress;
		this.country = country;
		this.state = state;
		this.district = district;
	}
	public String getInventoryId() {
		return inventoryId;
	}
	public void setInventoryId(String inventoryId) {
		this.inventoryId = inventoryId;
	}
	public String getInventoryName() {
		return inventoryName;
	}
	public void setInventoryName(String inventoryName) {
		this.inventoryName = inventoryName;
	}
	public String getInventoryAddress() {
		return inventoryAddress;
	}
	public void setInventoryAddress(String inventoryAddress) {
		this.inventoryAddress = inventoryAddress;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
    
    
}
