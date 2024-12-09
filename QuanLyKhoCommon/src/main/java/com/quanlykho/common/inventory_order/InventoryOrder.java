package com.quanlykho.common.inventory_order;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.quanlykho.common.Inventory;
import com.quanlykho.common.InventoryProvider;
import com.quanlykho.common.InventoryUser;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "inventory_order")
public class InventoryOrder {
    
	@Id
	@Column(name = "order_id")
	private String orderId;
	
	@Column(name = "created_time")
	private Date createdTime;
	
	@Column(name = "supplier")
	private String supplier;
	
	@Column(name = "customer_name")
	private String customerName;
	
	@Column(name = "customer_phone_number")
	private String customerPhoneNumber;
	
	@ManyToOne()
	@JoinColumn(name = "inventory_user_id",referencedColumnName = "user_id")
	private InventoryUser inventoryUser;
	
	@ManyToOne()
	@JoinColumn(name = "inventory_id",referencedColumnName = "inventory_id")
	private Inventory inventory;
	
	@ManyToOne()
	@JoinColumn(name = "provider_id")
	private InventoryProvider inventoryProvider;
	
	
	@OneToMany(mappedBy = "inventoryOrder",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true,targetEntity = InventoryOrderDetail.class)
	private Set<InventoryOrderDetail> orderDetails = new HashSet<>();
	
	public InventoryOrder() {
		
	}


	public InventoryOrder(String orderId, Date createdTime, String supplier, String customerName,
			String customerPhoneNumber, InventoryUser inventoryUser, Inventory inventory) {
		super();
		this.orderId = orderId;
		this.createdTime = createdTime;
		this.supplier = supplier;
		this.customerName = customerName;
		this.customerPhoneNumber = customerPhoneNumber;
		this.inventoryUser = inventoryUser;
		this.inventory = inventory;
	}
    
	

	public InventoryOrder(String orderId) {
		super();
		this.orderId = orderId;
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

    
	
	
	public Set<InventoryOrderDetail> getOrderDetails() {
		return orderDetails;
	}


	public void setOrderDetails(Set<InventoryOrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}
    
	
	

	public InventoryProvider getInventoryProvider() {
		return inventoryProvider;
	}


	public void setInventoryProvider(InventoryProvider inventoryProvider) {
		this.inventoryProvider = inventoryProvider;
	}


	@Override
	public int hashCode() {
		return Objects.hash(orderId);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InventoryOrder other = (InventoryOrder) obj;
		return Objects.equals(orderId, other.orderId);
	}


	@Override
	public String toString() {
		return "InventoryOrder [orderId=" + orderId + ", createdTime=" + createdTime + ", supplier=" + supplier
				+ ", customerName=" + customerName + ", customerPhoneNumber=" + customerPhoneNumber + ", inventoryUser="
				+ inventoryUser.getFullName() + ", inventory=" + inventory.getInventoryId() + "]";
	}
	
	public void addDetail(InventoryOrderDetail detail) {
		detail.setInventoryOrder(this);
		detail.getId().setOrderId(this.orderId);
		this.orderDetails.add(detail);
	}
	
}
