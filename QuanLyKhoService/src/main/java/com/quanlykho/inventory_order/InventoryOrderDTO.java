package com.quanlykho.inventory_order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InventoryOrderDTO {
	
	private String orderId;
	private Date createdTime;
	private String supplier;
	private String customerName;
	private String customerPhoneNumber;
	private String createUser;
	private String inventoryId;
	
    private List<InventoryOrderDetailDTO> orderDetails = new ArrayList<>();
	public InventoryOrderDTO() {
		super();
		// TODO Auto-generated constructor stub
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

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(String inventoryId) {
		this.inventoryId = inventoryId;
	}

	public List<InventoryOrderDetailDTO> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<InventoryOrderDetailDTO> orderDetails) {
		this.orderDetails = orderDetails;
	}

	@Override
	public String toString() {
		return "InventoryOrderDTO [orderId=" + orderId + ", createdTime=" + createdTime + ", supplier=" + supplier
				+ ", customerName=" + customerName + ", customerPhoneNumber=" + customerPhoneNumber + ", createUser="
				+ createUser + ", inventoryId=" + inventoryId + ", orderDetails=" + orderDetails + "]";
	}

	
     
     
}
