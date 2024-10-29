package com.quanlykho.importing_form;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImportingFormDTOForDetailFunction {
    private String importingFormId;
    private Date   createdTime;
    private String inventoryUserId;
    private String inventoryUserFullname;
    private String orderId;
    private String customerName;
    private String inventoryId;
    private String inventoryAddress;
    private List<ImportingFormDetailDTO> details = new ArrayList<>();
    private float totalAmount = 0;
	public ImportingFormDTOForDetailFunction() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getImportingFormId() {
		return importingFormId;
	}
	public void setImportingFormId(String importingFormId) {
		this.importingFormId = importingFormId;
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
	public String getInventoryUserFullname() {
		return inventoryUserFullname;
	}
	public void setInventoryUserFullname(String inventoryUserFullname) {
		this.inventoryUserFullname = inventoryUserFullname;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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

	public List<ImportingFormDetailDTO> getDetails() {
		return details;
	}

	public void setDetails(List<ImportingFormDetailDTO> details) {
		this.details = details;
	}

	public float getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
	}
	
    
    
}
