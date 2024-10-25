package com.quanlykho.inventory_product;

import java.util.ArrayList;
import java.util.List;

public class InventoryProductResult {
    
	private String productName;
	private List<InventoryQuantity> results = new ArrayList<>();
	
	
	
	
	public InventoryProductResult() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public List<InventoryQuantity> getResults() {
		return results;
	}
	public void setResults(List<InventoryQuantity> results) {
		this.results = results;
	}
	
	
	
}
