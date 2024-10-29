package com.quanlykho.importing_form;

public class ImportingFormDetailDTO {
	private String importingFormId;
	private Integer productId;
	private Integer quantity;
	private float unitPrice;

	public ImportingFormDetailDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getImportingFormId() {
		return importingFormId;
	}

	public void setImportingFormId(String importingFormId) {
		this.importingFormId = importingFormId;
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
