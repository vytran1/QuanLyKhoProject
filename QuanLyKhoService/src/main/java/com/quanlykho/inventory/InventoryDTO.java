package com.quanlykho.inventory;

public class InventoryDTO {
	private String inventoryId;
	private String inventoryName;
	private String inventoryAddress;
	private String countryId;
	private String countryName;
	private String stateId;
	private String stateName;
	private String districtId;
	private String districtName;
    
	public InventoryDTO() {
		super();
		// TODO Auto-generated constructor stub
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

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getStateId() {
		return stateId;
	}

	public void setStateId(String stateId) {
		this.stateId = stateId;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getDistrictId() {
		return districtId;
	}

	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	@Override
	public String toString() {
		return "InventoryDTO [inventoryId=" + inventoryId + ", inventoryName=" + inventoryName + ", inventoryAddress="
				+ inventoryAddress + ", countryId=" + countryId + ", countryName=" + countryName + ", stateId="
				+ stateId + ", stateName=" + stateName + ", districtId=" + districtId + ", districtName=" + districtName
				+ "]";
	}
     
     
}
