package com.quanlykho.inventory_product;

public class InventoryQuantity {
    private String inventoryName;
    private Integer quantity;
    
    
	public InventoryQuantity() {
		super();
		// TODO Auto-generated constructor stub
	}


	public InventoryQuantity(String inventoryName, Integer quantity) {
		super();
		this.inventoryName = inventoryName;
		this.quantity = quantity;
	}


	public String getInventoryName() {
		return inventoryName;
	}


	public void setInventoryName(String inventoryName) {
		this.inventoryName = inventoryName;
	}


	public Integer getQuantity() {
		return quantity;
	}


	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
    
    
}
