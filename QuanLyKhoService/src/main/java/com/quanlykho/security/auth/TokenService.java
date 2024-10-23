package com.quanlykho.security.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.quanlykho.common.InventoryUser;
import com.quanlykho.security.jwt.JwtUtility;

@Service
public class TokenService {
    
	@Autowired
	private JwtUtility jwtUtility;
	
	@Value("${app.security.refresh-token.expiration}")
	private int refreshTokenExpiration;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public AuthResponse generateToken(InventoryUser user) {
		String accessToken = jwtUtility.generateAccessToken(user);
		AuthResponse authResponse = new AuthResponse();
		authResponse.setAccessToken(accessToken);
		authResponse.setRefreshToken("");
		return authResponse;
	}
}
