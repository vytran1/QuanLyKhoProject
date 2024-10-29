package com.quanlykho.exporting_form;

public class ExportingFormDetailDTO {
    private String exportingFormId;
    private Integer productId;
    private Integer quantity;
    private float unitPrice;
	public ExportingFormDetailDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getExportingFormId() {
		return exportingFormId;
	}
	public void setExportingFormId(String exportingFormId) {
		this.exportingFormId = exportingFormId;
	}
	
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
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
    
    
}
