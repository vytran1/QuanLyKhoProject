package com.quanlykho.security.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quanlykho.common.InventoryUser;
import com.quanlykho.security.InventoryUserDetails;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
     
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping("/token")
	public ResponseEntity<?> getAccessToken(@RequestBody @Valid AuthRequest authRequest){
		String username = authRequest.getUsername();
		String password = authRequest.getPassword();
		try {
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
			InventoryUserDetails inventoryUserDetails = (InventoryUserDetails) authentication.getPrincipal();
			InventoryUser inventoryUser = inventoryUserDetails.getInventoryUser();
			AuthResponse authResponse = tokenService.generateToken(inventoryUser);
			return new ResponseEntity(authResponse,HttpStatus.OK);
		}catch(BadCredentialsException e) {
			return new ResponseEntity("Username or password is wrong",HttpStatus.UNAUTHORIZED);
		}
	}
	
	
}
