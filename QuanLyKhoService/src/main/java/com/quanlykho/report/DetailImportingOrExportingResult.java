package com.quanlykho.report;

import java.math.BigDecimal;

public class DetailImportingOrExportingResult {
    private String monthYear;
    private String productName;
    private BigDecimal totalQuantity;
    private Double totalValue;
	public DetailImportingOrExportingResult() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getMonthYear() {
		return monthYear;
	}
	public void setMonthYear(String monthYear) {
		this.monthYear = monthYear;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public BigDecimal getTotalQuantity() {
		return totalQuantity;
	}
	public void setTotalQuantity(BigDecimal totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
	public Double getTotalValue() {
		return totalValue;
	}
	public void setTotalValue(Double totalValue) {
		this.totalValue = totalValue;
	}
    
    
    
}
