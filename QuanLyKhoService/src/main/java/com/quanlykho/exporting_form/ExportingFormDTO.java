package com.quanlykho.exporting_form;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ExportingFormDTO {
	private String exportingFormId;
	private Date createdTime;
	private String customerName;
	private String customerPhoneNumber;
	private String inventoryUser;
	private String inventory;
	private Set<ExportingFormDetailDTO> exportingDetails = new HashSet<>();

	public ExportingFormDTO() {

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

	@Override
	public int hashCode() {
		return Objects.hash(exportingFormId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExportingFormDTO other = (ExportingFormDTO) obj;
		return Objects.equals(exportingFormId, other.exportingFormId);
	}

	public Set<ExportingFormDetailDTO> getExportingDetails() {
		return exportingDetails;
	}

	public void setExportingDetails(Set<ExportingFormDetailDTO> exportingDetails) {
		this.exportingDetails = exportingDetails;
	}
	
	

}
