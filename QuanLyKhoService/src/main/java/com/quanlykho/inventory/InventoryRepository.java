package com.quanlykho.inventory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import com.quanlykho.common.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory,String> {
    
	
	@Query("SELECT iv FROM Inventory iv")
	public Page<Inventory> findAll(Pageable pageable);
	
	@Query("SELECT iv FROM Inventory iv WHERE "
			+ "CONCAT(iv.inventoryId,' ',iv.inventoryName,' ',iv.inventoryAddress,' ',iv.country.name,' ',iv.state.name,' ',iv.district.name) "
			+ "LIKE %?1% ")
	public Page<Inventory> search(String keyWord,Pageable pageable);
	
	@Procedure(name = "checkIsInventoryJoinBussiness")
	public boolean checkIsInventoryJoinBussiness(@Param("inventoryId") String inventoryId);
}
