package com.quanlykho.security;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quanlykho.security.auth.AuthRequest;
import com.quanlykho.security.auth.AuthResponse;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityTests {
    
	private static final String GET_ACCESSTOKEN_ENDPOINT = "/api/auth/token";
	private static final String INVENTORY_USER_LIST_ENDPOINT = "/api/v1/inventory_users/findByPage?pageNum=2&pageSize=1&sortField=firstName&sortDir=asc";
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Test
	public void testGetAccessTokenBadRequest() throws Exception {
		AuthRequest authRequest = new AuthRequest();
		authRequest.setUsername("a");
		authRequest.setPassword("");
		
		String requestBody = objectMapper.writeValueAsString(authRequest);
		
		mockMvc.perform(post(GET_ACCESSTOKEN_ENDPOINT).contentType("application/json").content(requestBody))
		       .andDo(print())
		       .andExpect(status().isBadRequest());
	}
	
	@Test
	public void testGetAccessTokenFail() throws Exception {
		AuthRequest authRequest = new AuthRequest();
		authRequest.setUsername("N21DCCN120");
		authRequest.setPassword("123456788");
		
		String requestBody = objectMapper.writeValueAsString(authRequest);
		
		mockMvc.perform(post(GET_ACCESSTOKEN_ENDPOINT).contentType("application/json").content(requestBody))
		       .andDo(print())
		       .andExpect(status().isUnauthorized());
	}
	
	@Test
	public void testGetAccessTokenSuccess() throws Exception {
		AuthRequest authRequest = new AuthRequest();
		authRequest.setUsername("vietvo@gmail.com");
		authRequest.setPassword("123456789");
		
		String requestBody = objectMapper.writeValueAsString(authRequest);
		
		mockMvc.perform(post(GET_ACCESSTOKEN_ENDPOINT).contentType("application/json").content(requestBody))
		       .andDo(print())
		       .andExpect(status().isOk())
		       .andExpect(jsonPath("$.accessToken").isNotEmpty());
	}
	
	@Test
	public void testGetListInventoryUserFail() throws Exception {
		mockMvc.perform(get(INVENTORY_USER_LIST_ENDPOINT).header("Authorization","Bearer SomethingInvalid"))
		       .andDo(print())
		       .andExpect(status().isBadRequest());
		       
	}
	
	@Test
	public void testGetListInventoryUserSuccess() throws Exception {
		//Get Token
		AuthRequest authRequest = new AuthRequest();
		authRequest.setUsername("vietvo@gmail.com");
		authRequest.setPassword("123456789");
		
		String requestBody = objectMapper.writeValueAsString(authRequest);
		
		MvcResult result = mockMvc.perform(post(GET_ACCESSTOKEN_ENDPOINT).contentType("application/json").content(requestBody))
		       .andDo(print())
		       .andExpect(status().isOk())
		       .andExpect(jsonPath("$.accessToken").isNotEmpty())
		       .andReturn();
		String responseBody = result.getResponse().getContentAsString();
		System.out.println("ResponseBody :" +  responseBody);
		AuthResponse response = objectMapper.readValue(responseBody,AuthResponse.class);
		
		//Call Api
		String bearerToken = "Bearer " + response.getAccessToken(); 
		mockMvc.perform(get(INVENTORY_USER_LIST_ENDPOINT).header("Authorization",bearerToken))
	       .andDo(print())
	       .andExpect(status().isOk());
	}
}
