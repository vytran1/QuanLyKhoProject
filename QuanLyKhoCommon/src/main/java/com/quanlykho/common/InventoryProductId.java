package com.quanlykho.common;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class InventoryProductId implements Serializable {
    
	
	@Column(name = "inventory_id")
	private String inventoryId;
	
	@Column(name = "product_id")
	private Integer productId;
	
	
	public InventoryProductId() {
		
	}


	public InventoryProductId(String inventoryId, Integer productId) {
		super();
		this.inventoryId = inventoryId;
		this.productId = productId;
	}


	public String getInventoryId() {
		return inventoryId;
	}


	public void setInventoryId(String inventoryId) {
		this.inventoryId = inventoryId;
	}


	public Integer getProductId() {
		return productId;
	}


	public void setProductId(Integer productId) {
		this.productId = productId;
	}


	@Override
	public int hashCode() {
		return Objects.hash(inventoryId, productId);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InventoryProductId other = (InventoryProductId) obj;
		return Objects.equals(inventoryId, other.inventoryId) && Objects.equals(productId, other.productId);
	}


	@Override
	public String toString() {
		return "InventoryProductId [inventoryId=" + inventoryId + ", productId=" + productId + "]";
	}
	
	
}
