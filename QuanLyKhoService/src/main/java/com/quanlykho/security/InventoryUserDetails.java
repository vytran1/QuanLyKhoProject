package com.quanlykho.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.quanlykho.common.InventoryUser;

public class InventoryUserDetails implements UserDetails {
     
	private InventoryUser inventoryUser;
	
	
	
	public InventoryUserDetails(InventoryUser inventoryUser) {
		super();
		this.inventoryUser = inventoryUser;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		List<SimpleGrantedAuthority> listAuthories = new ArrayList<>();
		listAuthories.add(new SimpleGrantedAuthority(inventoryUser.getInventoryRole().getName()));
		return listAuthories;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return inventoryUser.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return inventoryUser.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return this.inventoryUser.getEnabled();
	}

	public InventoryUser getInventoryUser() {
		return inventoryUser;
	}

	public void setInventoryUser(InventoryUser inventoryUser) {
		this.inventoryUser = inventoryUser;
	}
    
	
}
