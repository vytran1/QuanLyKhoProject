package com.quanlykho.exporting_form;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ExportingFormDTOForDetailFunction {
	private String exportingFormId;
	private Date createdTime;
	private String inventoryUserId;
	private String inventoryUserName;
	private String customerName;
	private String customerPhoneNumber;
	private String inventoryId;
	private String inventoryAddress;
	private Set<ExportingFormDetailDTO> exportingDetails = new HashSet<>();
	private float total = 0;

	public ExportingFormDTOForDetailFunction() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getExportingFormId() {
		return exportingFormId;
	}

	public void setExportingFormId(String exportingFormId) {
		this.exportingFormId = exportingFormId;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getInventoryUserId() {
		return inventoryUserId;
	}

	public void setInventoryUserId(String inventoryUserId) {
		this.inventoryUserId = inventoryUserId;
	}

	public String getInventoryUserName() {
		return inventoryUserName;
	}

	public void setInventoryUserName(String inventoryUserName) {
		this.inventoryUserName = inventoryUserName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerPhoneNumber() {
		return customerPhoneNumber;
	}

	public void setCustomerPhoneNumber(String customerPhoneNumber) {
		this.customerPhoneNumber = customerPhoneNumber;
	}

	public String getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(String inventoryId) {
		this.inventoryId = inventoryId;
	}

	public String getInventoryAddress() {
		return inventoryAddress;
	}

	public void setInventoryAddress(String inventoryAddress) {
		this.inventoryAddress = inventoryAddress;
	}

	public Set<ExportingFormDetailDTO> getExportingDetails() {
		return exportingDetails;
	}

	public void setExportingDetails(Set<ExportingFormDetailDTO> exportingDetails) {
		this.exportingDetails = exportingDetails;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}
    
	
}
