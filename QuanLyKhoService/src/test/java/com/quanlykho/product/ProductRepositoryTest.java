package com.quanlykho.product;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.quanlykho.common.Product;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ProductRepositoryTest {
   
	@Autowired
	private ProductRepository productRepository;
	
	
	@Test
	public void firstTest() {
		List<Product> products = productRepository.findAll();
		assertThat(products.size()).isGreaterThan(0);
		products.forEach(product -> {
			System.out.println(product.getName());
		});
	}
}
