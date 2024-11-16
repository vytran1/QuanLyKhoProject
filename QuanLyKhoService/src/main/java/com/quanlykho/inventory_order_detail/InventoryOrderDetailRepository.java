package com.quanlykho.inventory_order_detail;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.quanlykho.common.inventory_order.InventoryOrderDetail;
import com.quanlykho.common.inventory_order.InventoryOrderDetailId;

public interface InventoryOrderDetailRepository extends JpaRepository<InventoryOrderDetail,InventoryOrderDetailId> {
    
	
	
	@Query("SELECT od FROM InventoryOrderDetail od WHERE od.inventoryOrder.orderId = ?1")
	public List<InventoryOrderDetail> getOrderDetailsByOrderId(String orderId);
}
