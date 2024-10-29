package com.quanlykho.common.inventory_order;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class InventoryOrderDetailId implements Serializable {
    
	@Column(name = "order_id")
	private String orderId;
	
	@Column(name = "product_id")
	private Integer productId;
	
	public InventoryOrderDetailId() {
		
	}
	
	

	public InventoryOrderDetailId(String orderId, Integer productId) {
		super();
		this.orderId = orderId;
		this.productId = productId;
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
		InventoryOrderDetailId other = (InventoryOrderDetailId) obj;
		return Objects.equals(orderId, other.orderId) && Objects.equals(productId, other.productId);
	}

	@Override
	public String toString() {
		return "InventoryOrderDetailId [orderId=" + orderId + ", productId=" + productId + "]";
	}
	
	
	
}
