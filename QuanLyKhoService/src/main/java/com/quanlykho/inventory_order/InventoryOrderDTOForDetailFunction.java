package com.quanlykho.inventory_order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InventoryOrderDTOForDetailFunction {
    private String orderId;
    private Date createdTime;
    private String inventoryUserId;
    private String inventoryUserFullname;
    private String customerName;
    private String customerPhoneNumber;
    private String inventoryId;
    private String inventoryAddress;
    private float total = 0;
    private List<InventoryOrderDetailDTO> details = new ArrayList<>();
    
    public InventoryOrderDTOForDetailFunction() {
    	
    }

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public List<InventoryOrderDetailDTO> getDetails() {
		return details;
	}

	public void setDetails(List<InventoryOrderDetailDTO> details) {
		this.details = details;
	}
    
    
}
