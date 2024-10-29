package com.quanlykho.common.inventory_order;

import java.util.Objects;

import com.quanlykho.common.Product;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "inventory_order_detail")
public class InventoryOrderDetail {
    
	@EmbeddedId
	private InventoryOrderDetailId id = new InventoryOrderDetailId();
	
	@Column(name = "quantity")
	private Integer quantity;
	
	@Column(name = "unit_price")
	private float unitPrice;
	
	@ManyToOne()
	@MapsId("orderId")
	@JoinColumn(name = "order_id")
	private InventoryOrder inventoryOrder;
	
	@ManyToOne()
	@MapsId("productId")
	@JoinColumn(name = "product_id")
	private Product product;
	
	public InventoryOrderDetail() {
		
	}

	public InventoryOrderDetail(InventoryOrderDetailId id, Integer quantity, float unitPrice,
			InventoryOrder inventoryOrder, Product product) {
		super();
		this.id = id;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.inventoryOrder = inventoryOrder;
		this.product = product;
	}

	public InventoryOrderDetailId getId() {
		return id;
	}

	public void setId(InventoryOrderDetailId id) {
		this.id = id;
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

	public InventoryOrder getInventoryOrder() {
		return inventoryOrder;
	}

	public void setInventoryOrder(InventoryOrder inventoryOrder) {
		this.inventoryOrder = inventoryOrder;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InventoryOrderDetail other = (InventoryOrderDetail) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "InventoryOrderDetail [id=" + id.toString() + ", quantity=" + quantity + ", unitPrice=" + unitPrice
				+ ", inventoryOrder=" + inventoryOrder.getOrderId() + ", product=" + product.getName() + "]";
	}
	
	
	public InventoryOrderDetail copy() {
		InventoryOrderDetail inventoryOrderDetail = new InventoryOrderDetail();
		inventoryOrderDetail.setId(this.getId());
		return inventoryOrderDetail;
	}
	
}
