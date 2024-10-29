package com.quanlykho.inventory_user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import com.quanlykho.common.InventoryRole;
import com.quanlykho.common.InventoryUser;



@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class InventoryUserRepositoryTest {
   
	@Autowired
	private InventoryUserRepository inventoryUserRepository;
	
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@Test
	public void loadAllUserTest() {
		List<InventoryUser> results = inventoryUserRepository.findAll();
		assertThat(results.size()).isGreaterThan(0);
		results.forEach(user -> {
			System.out.println(user.toString());
		});
	}
	
	@Test 
	public void createUser() {
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
		
		inventoryUser.setInventoryRole(inventoryRole);
		
		InventoryUser savedUser = inventoryUserRepository.save(inventoryUser);
		assertThat(savedUser.getUserId()).isEqualTo("N21DCCN121");
		System.out.println(savedUser.toString());
	}
	
	@Test
	public void updateUser() {
		String userID = "N21DCCN120";
		InventoryUser inventoryUser = inventoryUserRepository.findById(userID).get();
		
		inventoryUser.setFirstName("Huy Nguyễn");
		inventoryUser.setLastName(inventoryUser.getLastName());
		inventoryUser.setAddress(inventoryUser.getAddress());
		inventoryUser.setPhoneNumber(inventoryUser.getPhoneNumber());
		inventoryUser.setPhotos("HuyHieu.png");
		
		InventoryUser updatedUser =  inventoryUserRepository.save(inventoryUser);
		assertThat(updatedUser.getFirstName()).isEqualTo("Huy Nguyễn");
		System.out.println(updatedUser.toString());
	}
	
	@Test
	public void deleteUser() {
		String userID = "N21DCCN121";
		inventoryUserRepository.deleteById(userID);
		InventoryUser inventoryUser = inventoryUserRepository.findById(userID).get();
		assertThat(inventoryUser).isNull();
	}
	
	@Test
	public void updatePassword() {
		String rawPassword = "123456789";
		String encodedPassword = passwordEncoder.encode(rawPassword);
		InventoryUser inventoryUser = inventoryUserRepository.findById("N21DCCN120").get();
		inventoryUser.setPassword(encodedPassword);
		inventoryUserRepository.save(inventoryUser);
		assertThat(inventoryUser).isNotNull();
	}
	
	@Test
	public void testFindByEmail() {
		String email = "vietvo@gmail.com";
		InventoryUser inventoryUser = inventoryUserRepository.findByEmail(email);
		assertThat(inventoryUser).isNotNull();
		System.out.println(inventoryUser);
	}
	
	@Test 
	public void testSpCheckUserBusinessFalse() {
		String userId = "N21DCVT128";
		Boolean result = inventoryUserRepository.checkIsUserJoinBussiness(userId);
		assertThat(result).isFalse();
	}
	
	@Test 
	public void testSpCheckUserBusinessTrue() {
		String userId = "N21DCCN091";
		Boolean result = inventoryUserRepository.checkIsUserJoinBussiness(userId);
		assertThat(result).isTrue();
	}
}
