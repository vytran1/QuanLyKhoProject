package com.quanlykho.report;

import java.math.BigDecimal;
import java.util.Date;

public class EmployeeActivityDTO {
    private Date createDate;
    private String formId;
    private String typeForm;
    private String customerName;
    private String productName;
    private Integer quantity;
    private float unitPrice;
    private String month;
    private BigDecimal triGia;
	public EmployeeActivityDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getFormId() {
		return formId;
	}
	public void setFormId(String formId) {
		this.formId = formId;
	}
	public String getTypeForm() {
		return typeForm;
	}
	public void setTypeForm(String typeForm) {
		this.typeForm = typeForm;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public float getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public BigDecimal getTriGia() {
		return triGia;
	}
	public void setTriGia(BigDecimal triGia) {
		this.triGia = triGia;
	}
    
    
    
}
