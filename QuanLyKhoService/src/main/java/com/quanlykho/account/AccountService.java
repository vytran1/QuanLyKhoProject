package com.quanlykho.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quanlykho.common.InventoryUser;
import com.quanlykho.inventory_user.InventoryUserRepository;

@Service
public class AccountService {
    
	
	@Autowired
	private InventoryUserRepository inventoryUserRepository;
	
	
	public void saveInfo(InventoryUser inventoryUser) {
		inventoryUserRepository.save(inventoryUser);
	}
}
