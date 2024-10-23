package com.quanlykho.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.quanlykho.common.InventoryUser;
import com.quanlykho.inventory_user.InventoryUserRepository;

@Service
public class InventoryUserDetailsService implements UserDetailsService {
    
	@Autowired
	private InventoryUserRepository inventoryUserRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		InventoryUser inventoryUserByEmail =  inventoryUserRepository.findByEmail(email);
		if(inventoryUserByEmail == null) {
			throw new UsernameNotFoundException("No user found with email: " + email);
		}
		return new InventoryUserDetails(inventoryUserByEmail);
	}

}
