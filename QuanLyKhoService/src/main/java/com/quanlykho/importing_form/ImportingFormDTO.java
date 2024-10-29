package com.quanlykho.importing_form;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ImportingFormDTO {
    private String importingFormId;
    private Date createdTime;
    private String inventoryOrder;
    private String inventoryUser;
    private String inventory;
    private Set<ImportingFormDetailDTO> importingDetails = new HashSet<>();
    
    public ImportingFormDTO() {
    	
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

	public String getInventoryOrder() {
		return inventoryOrder;
	}

	public void setInventoryOrder(String inventoryOrder) {
		this.inventoryOrder = inventoryOrder;
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

	public Set<ImportingFormDetailDTO> getImportingDetails() {
		return importingDetails;
	}

	public void setImportingDetails(Set<ImportingFormDetailDTO> importingDetails) {
		this.importingDetails = importingDetails;
	}
    
    
}
