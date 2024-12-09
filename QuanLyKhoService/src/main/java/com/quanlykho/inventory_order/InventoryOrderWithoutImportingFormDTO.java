package com.quanlykho.inventory_order;

import java.util.Date;

public class InventoryOrderWithoutImportingFormDTO {
    private String orderId;
    private Date createdTime;
    private String supplier;
    private String customerName;
    private String inventoryUser;
    private String inventory;
    private String inventoryProvider;
    
    public InventoryOrderWithoutImportingFormDTO() {
    	
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

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getInventoryUser() {
		return inventoryUser;
	}

	public void setInventoryUser(String inventoryUser) {
		this.inventoryUser = inventoryUser;
	}

	public String getInventory() {
		return inventory;
	}

	public void setInventory(String inventory) {
		this.inventory = inventory;
	}

	public String getInventoryProvider() {
		return inventoryProvider;
	}

	public void setInventoryProvider(String inventoryProvider) {
		this.inventoryProvider = inventoryProvider;
	}
    
    
}
