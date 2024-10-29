package com.quanlykho.inventory_order;

import java.util.Objects;

public class InventoryOrderDetailDTO {
	private String orderId;
	private Integer productId;
	private Integer quantity;
	private float unitPrice;

	public InventoryOrderDetailDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

	@Override
	public String toString() {
		return "InventoryOrderDetailDTO [orderId=" + orderId + ", productId=" + productId + ", quantity=" + quantity
				+ ", unitPrice=" + unitPrice + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(orderId, productId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InventoryOrderDetailDTO other = (InventoryOrderDetailDTO) obj;
		return Objects.equals(orderId, other.orderId) && Objects.equals(productId, other.productId);
	}
	
	

}
