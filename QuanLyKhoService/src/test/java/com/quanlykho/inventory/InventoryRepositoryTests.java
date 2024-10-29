package com.quanlykho.inventory;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.quanlykho.common.Inventory;
import com.quanlykho.common.setting.Country;
import com.quanlykho.common.setting.District;
import com.quanlykho.common.setting.State;

import jakarta.persistence.EntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class InventoryRepositoryTests {
   
	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private InventoryRepository inventoryRepository;
	
	@Test
	public void testLoadAll() {
		List<Inventory> inventories = inventoryRepository.findAll();
		assertThat(inventories.size()).isGreaterThan(0);
		inventories.forEach(inventory -> {
			System.out.println(inventory.toString());
		});
	}
	
	
	@Test
	public void testAddInventory() {
		Inventory inventory = new Inventory();
		inventory.setInventoryId("VNTPHCMQ4");
		inventory.setInventoryName("VN_HoChiMinh_Quan4");
		inventory.setInventoryAddress("11 Nguyen Ung Luong");
		
		//VietNam
		Country country = entityManager.find(Country.class,1);
		//TPHCM
		State state = entityManager.find(State.class,1);
		//GoVap
		District district = entityManager.find(District.class,4);
		
		inventory.setCountry(country);
		inventory.setState(state);
		inventory.setDistrict(district);
		
		Inventory savedInventory = inventoryRepository.save(inventory);
		
		assertThat(savedInventory).isNotNull();
		assertThat(savedInventory.getInventoryId()).isEqualTo("VNTPHCMQ4");
		
		System.out.println(savedInventory.toString());
	}
	
	@Test
	public void testUpdateInventory() {
		Inventory inventoryById = inventoryRepository.findById("VNTPHCMGV").get();
		//New Address 
		String newAddress = "365, khu phố 1, Nguyễn Thái Sơn";
		inventoryById.setInventoryAddress(newAddress);
		Inventory updatedInventory =  inventoryRepository.save(inventoryById);
		assertThat(updatedInventory.getInventoryAddress()).isEqualTo(newAddress);
	}
	
	
	@Test
	public void testDeleteInventory() {
		String inventoryID = "VNTPHCMQ4";
		inventoryRepository.deleteById(inventoryID);
		
		Optional<Inventory> deletedInventory = inventoryRepository.findById(inventoryID);
		assertThat(deletedInventory).isNotPresent();
	}
	
	@Test
	public void testCheckInventoryJoinBusinessTrue() {
		String inventoryID = "VNTPHCMQ3";
		boolean result = inventoryRepository.checkIsInventoryJoinBussiness(inventoryID);
		assertThat(result).isFalse();
	    if(!result) {
	    	System.out.println("You can not delete this inventory because it already has join business forming");
	    }else {
	    	System.out.println("You can delete this inventory");
	    }
	}
	
}
