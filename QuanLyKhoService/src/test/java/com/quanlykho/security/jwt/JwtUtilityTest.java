package com.quanlykho.security.jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import com.quanlykho.common.InventoryRole;
import com.quanlykho.common.InventoryUser;

public class JwtUtilityTest {
   private static JwtUtility jwtUtility;
   
   @BeforeAll
   static void setup() {
	   jwtUtility = new JwtUtility();
	   jwtUtility.setIssueName("My Company");
	   jwtUtility.setAccessTokenExpiration(2);
	   jwtUtility.setSecretKey("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuwxyz+9-$!*&%");
   }
   
   @Test
   public void generateFail() {
	   assertThrows(IllegalArgumentException.class,new Executable() {
		
		@Override
		public void execute() throws Throwable {
			// TODO Auto-generated method stub
			InventoryUser user = null;
			jwtUtility.generateAccessToken(user);
		}
	   });
   }
   
   @Test
   public void generateSuccess() {
		InventoryUser inventoryUser = new InventoryUser();
		inventoryUser.setUserId("N21DCCN121");
		inventoryUser.setIdentityNumber("078764395211");
		inventoryUser.setFirstName("Ha Huy");
		inventoryUser.setLastName("HAU");
		inventoryUser.setAddress("99/9 adadadaad");
		inventoryUser.setPhoneNumber("0988202231");
		inventoryUser.setPhotos("Thumbnail.png");
		inventoryUser.setEmail("huyhau@gmail.com");
		inventoryUser.setPassword("123456789");
		
		InventoryRole inventoryRole = new InventoryRole();
		inventoryRole.setId(1);
		inventoryRole.setName("COMPANY");
		inventoryRole.setDescription("Company xxxx");
		
		inventoryUser.setInventoryRole(inventoryRole);
		String token = jwtUtility.generateAccessToken(inventoryUser);
		assertThat(token).isNotNull();
		System.out.println(token);
   }
   
   @Test
   public void validateFail() {
	   assertThrows(JwtValidationException.class,() -> {
		   jwtUtility.validateAccessToken("a.b.c.d");
	   });
   }
   
   @Test
   public void validateSuccess() {
	    InventoryUser inventoryUser = new InventoryUser();
		inventoryUser.setUserId("N21DCCN121");
		inventoryUser.setIdentityNumber("078764395211");
		inventoryUser.setFirstName("Ha Huy");
		inventoryUser.setLastName("HAU");
		inventoryUser.setAddress("99/9 adadadaad");
		inventoryUser.setPhoneNumber("0988202231");
		inventoryUser.setPhotos("Thumbnail.png");
		inventoryUser.setEmail("huyhau@gmail.com");
		inventoryUser.setPassword("123456789");
		
		InventoryRole inventoryRole = new InventoryRole();
		inventoryRole.setId(1);
		inventoryRole.setName("COMPANY");
		inventoryRole.setDescription("Company xxxx");
		
		inventoryUser.setInventoryRole(inventoryRole);
		String token = jwtUtility.generateAccessToken(inventoryUser);
		assertThat(token).isNotNull();
		assertDoesNotThrow(() -> {
			jwtUtility.validateAccessToken(token);
		});
   }
}
