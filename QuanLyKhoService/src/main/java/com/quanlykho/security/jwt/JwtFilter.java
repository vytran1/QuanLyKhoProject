package com.quanlykho.security.jwt;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.quanlykho.common.InventoryRole;
import com.quanlykho.common.InventoryUser;
import com.quanlykho.security.InventoryUserDetails;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {
    
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtFilter.class);
	
	@Autowired
	private JwtUtility jwtUtility;
	
	@Autowired
	@Qualifier("handlerExceptionResolver")
	private HandlerExceptionResolver handlerExceptionResolver;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
        if(!hasAuthorizationBearer(request)) {
        	filterChain.doFilter(request, response);
        	return;
        }
        String token = getBearerToken(request);
        LOGGER.info("Token: " + token);
        try {
			Claims claims =  jwtUtility.validateAccessToken(token);
			UserDetails userDetails = getUserDetails(claims);
			setAuthenticationContext(userDetails,request);
			filterChain.doFilter(request, response);
			clearAuthenticationContext();
		} catch (JwtValidationException e) {
			// TODO Auto-generated catch block
			LOGGER.error(e.getMessage(),e);
			handlerExceptionResolver.resolveException(request, response, null, e);
			return;
		}
        
	}


	private void clearAuthenticationContext() {
		// TODO Auto-generated method stub
		SecurityContextHolder.clearContext();
	}


	private void setAuthenticationContext(UserDetails userDetails, HttpServletRequest request) {
		// TODO Auto-generated method stub
		var authentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}


	private boolean hasAuthorizationBearer(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String header = request.getHeader("Authorization");
		LOGGER.info("Authorization Header: " + header);
		if(ObjectUtils.isEmpty(header) || !header.startsWith("Bearer") ) {			
			return false;
		}else {
			return true;
		}
	}
	
	private String getBearerToken(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		String[] array = header.split(" ");
		if(array.length == 2) {
			return array[1];
		}
		return null;
	}
    
	private UserDetails getUserDetails(Claims claims) {
		String subject = (String) claims.get(Claims.SUBJECT);
		String[] array = subject.split(",");
		
		String[] firstNameAndLastName = array[0].split(" ");
		String firstName = firstNameAndLastName[0];
		String lastName = firstNameAndLastName[1];
		String userId = array[1];
		String userName = array[2];
		
		InventoryUser inventoryUser = new InventoryUser();
		inventoryUser.setEmail(userName);
		inventoryUser.setUserId(userId);
		inventoryUser.setFirstName(firstName);
		inventoryUser.setLastName(lastName);
		
		String role = (String) claims.get("role");
		InventoryRole inventoryRole = new InventoryRole();
		inventoryRole.setName(role);
		
		inventoryUser.setInventoryRole(inventoryRole);
		
		LOGGER.info("Inventory User is parsed from JWT: " 
		                 + inventoryUser.getEmail() 
		                 + "," + inventoryUser.getUserId()
		                 + "," + inventoryUser.getInventoryRole().getName());
		return new InventoryUserDetails(inventoryUser);
		
	}
}
