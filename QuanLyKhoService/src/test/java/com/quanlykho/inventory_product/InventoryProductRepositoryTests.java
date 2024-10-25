package com.quanlykho.inventory_product;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.quanlykho.common.Inventory;
import com.quanlykho.common.InventoryProduct;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class InventoryProductRepositoryTests {
    
	
	@Autowired
	private InventoryProductRepository inventoryProductRepository;
	
	
	@Test
	public void testLoadAll() {
		List<InventoryProduct> inventoryproducts = inventoryProductRepository.findAll();
		assertThat(inventoryproducts.size()).isGreaterThan(0);
		inventoryproducts.forEach(item -> {
			System.out.println(item.toString());
		});
	}
	
	@Test
	public void testLoadAllInventoriesQuantityFollowProductName() {
		String productName = "Laptop Lenovo Gaming LOQ - 15IAX9 i5 12450HX/AI/16GB/512GB/15.6\"FHD/Nvidia RTX 2050 4GB/Win11";
		List<Object[]> results = inventoryProductRepository.findInventoriesQuantityWithProductName(productName);
		assertThat(results.size()).isGreaterThan(0);
		System.out.println("Tồn kho của : " + productName);
		for(Object[] object : results) {
			Inventory inventory = (Inventory) object[0];
			Integer quantity = (Integer) object[1];
			System.out.println("In inventory " + inventory.getInventoryName() + " .There are " + quantity + " item here");
		}
	}
}
