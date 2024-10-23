package com.quanlykho.inventory_user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.quanlykho.common.InventoryUser;

public interface InventoryUserRepository extends JpaRepository<InventoryUser,String> {
     
	public InventoryUser findByEmail(String email);
	
	public InventoryUser findByIdentityNumber(String identityNumber);
	
	@Query("SELECT u FROM InventoryUser u")
	public Page<InventoryUser> findAll(Pageable pageable);
	
	
	public InventoryUser findByResetPasswordToken(String resetpasswordToken);
}
