package com.quanlykho.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

@SpringBootTest
public class AuthenticationTests {
   
	@Autowired
	AuthenticationManager authManager;
	
	@Test
	public void testAuthenticationFail() {
		assertThrows(BadCredentialsException.class,() -> {
			authManager.authenticate(new UsernamePasswordAuthenticationToken("abc@gmail.com","123456789"));
		});
	}
	
	@Test
	public void testAuthenticateSuccess() {
		String email = "vy.tn171003@gmail.com";
		String password = "123456789";
		
		Authentication authentication =  authManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
		
		assertThat(authentication.isAuthenticated()).isTrue();
		
		InventoryUserDetails inventoryUserDetails = (InventoryUserDetails) authentication.getPrincipal();
		
		assertThat(inventoryUserDetails.getUsername()).isEqualTo(email);
	}
	
	
}
