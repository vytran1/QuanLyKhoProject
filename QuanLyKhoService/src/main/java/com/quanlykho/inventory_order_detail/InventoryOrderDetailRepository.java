package com.quanlykho.inventory_order_detail;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quanlykho.common.inventory_order.InventoryOrderDetail;
import com.quanlykho.common.inventory_order.InventoryOrderDetailId;

public interface InventoryOrderDetailRepository extends JpaRepository<InventoryOrderDetail,InventoryOrderDetailId> {

}
