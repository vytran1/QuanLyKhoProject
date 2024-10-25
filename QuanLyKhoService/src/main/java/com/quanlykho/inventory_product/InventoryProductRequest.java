package com.quanlykho.inventory_product;

public class InventoryProductRequest {
	private String productName;
	private String inventoryName;
   
	public InventoryProductRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getInventoryName() {
		return inventoryName;
	}

	public void setInventoryName(String inventoryName) {
		this.inventoryName = inventoryName;
	}
     
}
