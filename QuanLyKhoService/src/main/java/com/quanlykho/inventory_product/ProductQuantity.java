package com.quanlykho.inventory_product;

public class ProductQuantity {
    private Integer productId;
    private Integer quantity;
	public ProductQuantity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProductQuantity(Integer productId, Integer quantity) {
		super();
		this.productId = productId;
		this.quantity = quantity;
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
    
    
}
