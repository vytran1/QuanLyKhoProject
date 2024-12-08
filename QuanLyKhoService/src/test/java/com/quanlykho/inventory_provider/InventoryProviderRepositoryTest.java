package com.quanlykho.inventory_provider;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.quanlykho.common.InventoryProvider;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class InventoryProviderRepositoryTest {
    
	@Autowired
	private InventoryProviderRepository inventoryProviderRepository;
	
	
	@Test
	public void firstTest() {
		List<InventoryProvider> providers = inventoryProviderRepository.findAll();
		
		assertThat(providers.size()).isGreaterThan(0);
		
		providers.forEach(provider -> {
			System.out.println(provider.toString());
		});
	}
}
