package com.quanlykho.common;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "inventory_product")
public class InventoryProduct {
    
	@EmbeddedId
	private InventoryProductId id = new InventoryProductId();
	
	@ManyToOne
	@MapsId("inventoryId")
	@JoinColumn(name = "inventory_id")
	private Inventory inventory;
	
	
	@ManyToOne
	@MapsId("productId")
	@JoinColumn(name = "product_id")
	private Product product;
	
	@Column(name = "quantity")
	private Integer quantity;
	
	
	
	public InventoryProduct() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InventoryProduct(InventoryProductId id, Integer quantity) {
		super();
		this.id = id;
		this.quantity = quantity;
	}

	public InventoryProductId getId() {
		return id;
	}

	public void setId(InventoryProductId id) {
		this.id = id;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "InventoryProduct [id=" + id.toString() + ", inventory=" + inventory.getInventoryName() + ", product=" + product.getName() + ", quantity="
				+ quantity + "]";
	}
	
	
	
}
