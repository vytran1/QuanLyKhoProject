package com.quanlykho.inventory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.quanlykho.common.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory,String> {
    
	
	@Query("SELECT iv FROM Inventory iv")
	public Page<Inventory> findAll(Pageable pageable);
	
	
}
