package com.quanlykho.inventory_provider;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.quanlykho.common.InventoryProvider;

public interface InventoryProviderRepository extends JpaRepository<InventoryProvider,Integer> {
    
	
	@Query("SELECT pv FROM InventoryProvider pv "
			+ "WHERE CONCAT(pv.providerId,' ',pv.providerName,' ',pv.providerContactNumber,' '"
			+ ",pv.providerAddress,' ',pv.providerEmail) LIKE %?1%")
	public Page<InventoryProvider> search(String keyWord,Pageable pageable);
	
	
	public Optional<InventoryProvider> findByProviderEmail(String email);
	
	
	@Query("SELECT new com.quanlykho.common.InventoryProvider(ip.providerId, ip.providerName) FROM InventoryProvider ip")
	List<InventoryProvider> findAllWithOnlyIdAndName();

}
