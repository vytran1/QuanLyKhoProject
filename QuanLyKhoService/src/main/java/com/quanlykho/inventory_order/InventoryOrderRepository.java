package com.quanlykho.inventory_order;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;

import com.quanlykho.common.inventory_order.InventoryOrder;

public interface InventoryOrderRepository extends  JpaRepository<InventoryOrder,String> {
   
	@Query("SELECT o FROM InventoryOrder o WHERE o.orderId = ?1 AND o.inventoryUser.userId = ?2")
	public InventoryOrder checkOrderByInventoryIdAndUserId(String orderId,String userId);
	
	
	@Procedure(procedureName = "getInventoryOrderWithoutImportingForm")
	public List<InventoryOrder> getInventoryOrderWithoutImportingOrder();
    
	
	@Query("SELECT o FROM InventoryOrder o "
			+ "WHERE "
			+ "CONCAT(o.orderId,' ',o.supplier,' '"
			+ ",o.customerName,' ',o.customerPhoneNumber,' '"
			+ ",o.inventoryUser.firstName,' ',o.inventoryUser.lastName,' ',o.inventoryUser.userId,' ',"
			+ "o.inventory.inventoryId) LIKE %?1%")
	public Page<InventoryOrder> search(String keyWord,Pageable pageable);
	
	@Query("SELECT o FROM InventoryOrder o WHERE o.createdTime between ?1 AND ?2")
	public Page<InventoryOrder> search(LocalDateTime from,LocalDateTime to,Pageable pageable);
	
	@Query("SELECT o FROM InventoryOrder o WHERE"
			+ " CONCAT(o.orderId,' ',o.supplier,' ',o.customerName,' ',o.customerPhoneNumber,' ',"
			+ "o.inventoryUser.firstName,' ',o.inventoryUser.lastName,' ',o.inventoryUser.userId,' ',o.inventory.inventoryId) LIKE %?1%"
			+ " AND o.createdTime between ?2 AND ?3")
	public Page<InventoryOrder> search(String keyWord,LocalDateTime from,LocalDateTime to,Pageable pageable);
}
