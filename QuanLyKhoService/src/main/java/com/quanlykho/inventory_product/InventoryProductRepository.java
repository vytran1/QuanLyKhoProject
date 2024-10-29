package com.quanlykho.inventory_product;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.quanlykho.common.InventoryProduct;
import com.quanlykho.common.InventoryProductId;

public interface InventoryProductRepository extends JpaRepository<InventoryProduct,InventoryProductId> {
    @Query("SELECT ip.inventory, ip.quantity FROM InventoryProduct ip WHERE ip.product.name = ?1") 
	public List<Object[]> findInventoriesQuantityWithProductName(String productName);
	
	
	@Query("SELECT iv FROM InventoryProduct iv WHERE iv.inventory.inventoryId = ?1 AND iv.product.id = ?2")
	public Optional<InventoryProduct> findByInventoryIdAndProductId(String inventoryId,Integer productId);
}
