package com.quanlykho.common.importing_form;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.quanlykho.common.Inventory;
import com.quanlykho.common.InventoryUser;
import com.quanlykho.common.inventory_order.InventoryOrder;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "importing_form")
public class ImportingForm {
	@Id
	@Column(name = "importing_form_id")
    private String importingFormId;
	
	@Column(name = "created_time")
	private Date createdTime;
	
	@OneToOne(targetEntity = InventoryOrder.class)
	@JoinColumn(name = "order_id")
	private InventoryOrder inventoryOrder;
	
	@ManyToOne(targetEntity = InventoryUser.class)
	@JoinColumn(name = "inventory_user_id")
	private InventoryUser inventoryUser;
	
	@ManyToOne(targetEntity = Inventory.class)
	@JoinColumn(name = "inventory_id")
	private Inventory inventory;
	
	
	@OneToMany(mappedBy = "importingForm",cascade = CascadeType.ALL,orphanRemoval = true)
	private Set<ImportingFormDetail> importingDetails = new HashSet<>(); 
	
	public ImportingForm() {
		
	}

	public ImportingForm(String importingFormId) {
		super();
		this.importingFormId = importingFormId;
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

	public InventoryOrder getInventoryOrder() {
		return inventoryOrder;
	}

	public void setInventoryOrder(InventoryOrder inventoryOrder) {
		this.inventoryOrder = inventoryOrder;
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
    
	
	
	public Set<ImportingFormDetail> getImportingDetails() {
		return importingDetails;
	}

	public void setImportingDetails(Set<ImportingFormDetail> importingDetails) {
		this.importingDetails = importingDetails;
	}

	@Override
	public int hashCode() {
		return Objects.hash(importingFormId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ImportingForm other = (ImportingForm) obj;
		return Objects.equals(importingFormId, other.importingFormId);
	}

	@Override
	public String toString() {
		return "ImportingForm [importingFormId=" + importingFormId + ", createdTime=" + createdTime
				+ ", inventoryOrder=" + inventoryOrder.getOrderId() + ", inventoryUser=" + inventoryUser.getFullName() + ", inventory=" + inventory.getInventoryId()
				+ "]";
	}
	
	public void addImportingDetail(ImportingFormDetail detail) {
		detail.getImportingFormDetailId().setImportingFormId(this.importingFormId);
		detail.setImportingForm(this);
		this.importingDetails.add(detail);
	}
	
}
