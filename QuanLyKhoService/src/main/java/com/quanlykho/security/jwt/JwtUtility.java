package com.quanlykho.security.jwt;

import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.quanlykho.common.InventoryUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtility {
    private static final String SECRET_KEY_ALGORITHM = "HmacSHA512";
	
    //name of company or business or organization
    @Value("${app.security.jwt.issuer}")
    private String issueName;
    
    @Value("${app.security.jwt.secret}")
	private String secretKey;
    
    @Value("${app.security.access-token.expiration}")
	private int accessTokenExpiration;
	
	
    public String generateAccessToken(InventoryUser user) {
    	if(user == null || user.getUserId().equals(null) 
    			        || user.getFirstName() == null || user.getLastName() == null
    			        || user.getInventoryRole() == null        
    			) {
    		throw new IllegalArgumentException("User object is null or its field have null value");
    	}
    	Long expirationTimeMilis = System.currentTimeMillis() + accessTokenExpiration * 60000;
    	String subject = String.format("%s %s,%s,%s",user.getFirstName(),user.getLastName(),user.getUserId(),user.getEmail());
    	String token = Jwts
    			.builder()
    			.subject(subject)
    			.issuer(this.issueName)
    			.issuedAt(new Date())
    			.expiration(new Date(expirationTimeMilis))
    			.claim("role",user.getInventoryRole().getName())
    			.signWith(Keys.hmacShaKeyFor(secretKey.getBytes()),Jwts.SIG.HS512)
    			.compact();
    	return token;
    }
	
    
    public Claims validateAccessToken(String token) throws JwtValidationException {
    	try {
    		SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(),SECRET_KEY_ALGORITHM);
    		return Jwts.parser()
    				   .verifyWith(keySpec)
    				   .build()
    				   .parseSignedClaims(token)
    				   .getPayload();
    	}catch(ExpiredJwtException e) {
    		throw new JwtValidationException("Access Token expirated",e);
    	}catch(IllegalArgumentException e) {
    		throw new JwtValidationException("Access Token is illegal",e);
    	}catch(MalformedJwtException e) {
    		throw new JwtValidationException("Access Token is not well formed",e);
    	}catch(UnsupportedJwtException e) {
    		throw new JwtValidationException("Access Token is not supported",e);
    	}
    }
	
	public String getIssueName() {
		return issueName;
	}
	public void setIssueName(String issueName) {
		this.issueName = issueName;
	}
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	public int getAccessTokenExpiration() {
		return accessTokenExpiration;
	}
	public void setAccessTokenExpiration(int accessTokenExpiration) {
		this.accessTokenExpiration = accessTokenExpiration;
	}
	
	
	
}
