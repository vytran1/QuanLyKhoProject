package com.quanlykho.report;

import java.util.Date;

public class OrderWithourImportingFormReportDTO {
    private String orderId;
    private Date createdDate;
    private String supplier;
    private String inventoryUser;
    private Integer totalQuantity;
    private float totalValue;
	public OrderWithourImportingFormReportDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public String getInventoryUser() {
		return inventoryUser;
	}
	public void setInventoryUser(String inventoryUser) {
		this.inventoryUser = inventoryUser;
	}
	public Integer getTotalQuantity() {
		return totalQuantity;
	}
	public void setTotalQuantity(Integer totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
	public float getTotalValue() {
		return totalValue;
	}
	public void setTotalValue(float totalValue) {
		this.totalValue = totalValue;
	}
    
    
    
}
