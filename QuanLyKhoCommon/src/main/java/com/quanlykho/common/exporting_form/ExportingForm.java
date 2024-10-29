package com.quanlykho.common.exporting_form;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.quanlykho.common.Inventory;
import com.quanlykho.common.InventoryUser;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "exporting_form")
public class ExportingForm {
    
	@Id
	@Column(name = "exporting_form_id")
	private String exportingFormId;
	
	@Column(name = "created_time")
	private Date createdTime;
	
	@Column(name = "customer_name")
	private String customerName;
	
	@Column(name = "customer_phone_number")
	private String customerPhoneNumber;
	
	@ManyToOne(targetEntity = InventoryUser.class)
	@JoinColumn(name = "inventory_user_id")
	private InventoryUser inventoryUser;
	
	@ManyToOne(targetEntity = Inventory.class)
	@JoinColumn(name = "inventory_id")
	private Inventory inventory;
	
	@OneToMany(mappedBy = "exportingForm",cascade = CascadeType.ALL,orphanRemoval = true)
	private Set<ExportingFormDetail> exportingDetails = new HashSet<>();

	public ExportingForm() {
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

	public InventoryUser getInventoryUser() {
		return inventoryUser;
	}

	public void setInventoryUser(InventoryUser inventoryUser) {
		this.inventoryUser = inventoryUser;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
	
	

	public Set<ExportingFormDetail> getExportingDetails() {
		return exportingDetails;
	}

	public void setExportingDetails(Set<ExportingFormDetail> exportingDetails) {
		this.exportingDetails = exportingDetails;
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
		ExportingForm other = (ExportingForm) obj;
		return Objects.equals(exportingFormId, other.exportingFormId);
	}

	@Override
	public String toString() {
		return "ExportingForm [exportingFormId=" + exportingFormId + ", createdTime=" + createdTime + ", customerName="
				+ customerName + ", customerPhoneNumber=" + customerPhoneNumber + ", inventoryUser=" + inventoryUser.getFullName()
				+ ", inventory=" + inventory.getInventoryId() + "]";
	}
	
	public void addDetail(ExportingFormDetail exportingDetail) {
		exportingDetail.getExportingFormDetailId().setExportingFormId(exportingFormId);
		exportingDetail.setExportingForm(this);
		this.exportingDetails.add(exportingDetail);
	}
	
	
}
