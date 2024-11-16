package com.quanlykho.inventory_product;

import java.util.ArrayList;
import java.util.List;

public class InventoryProductForExportingForm {
	private String inventory;
	List<ProductQuantity> results = new ArrayList<>();

	public InventoryProductForExportingForm() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InventoryProductForExportingForm(String inventory, List<ProductQuantity> results) {
		super();
		this.inventory = inventory;
		this.results = results;
	}

	public String getInventory() {
		return inventory;
	}

	public void setInventory(String inventory) {
		this.inventory = inventory;
	}

	public List<ProductQuantity> getResults() {
		return results;
	}

	public void setResults(List<ProductQuantity> results) {
		this.results = results;
	}

}
