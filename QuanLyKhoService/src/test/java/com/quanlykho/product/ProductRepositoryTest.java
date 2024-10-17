package com.quanlykho.product;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.quanlykho.common.Brand;
import com.quanlykho.common.Category;

import com.quanlykho.common.Product;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ProductRepositoryTest {
   
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private TestEntityManager entityManager;
	
	
	@Test 
	public void createProductTest() {
		Brand brand = entityManager.find(Brand.class, 1);
		Category category = entityManager.find(Category.class, 6);
		
		Product product = new Product();
		product.setName("Acer Asprire Desktop");
		product.setAlias("acer_aspire_desktop");
		product.setShortDescription("Short Description for Acer laptops");
		
		product.setBrand(brand);
		product.setCategory(category);
		product.setEnabled(true);

		product.setCreatedTime(new Date());
		product.setUpdatedTime(new Date());
		
		Product savedProduct = productRepository.save(product);
		
		assertThat(savedProduct).isNotNull();
		assertThat(savedProduct.getId()).isGreaterThan(0);
		
		
		System.out.println(savedProduct.toString());
	}
	
	@Test
	public void testListAllProduct() {
		Iterable<Product> iterableProducts = productRepository.findAll();
		
		iterableProducts.forEach(System.out::println);
	}
	
	@Test
	public void testGetProduct() {
		Integer id = 2;
		Product product = productRepository.findById(id).get();
		System.out.println(product);
		
		assertThat(product).isNotNull();
	}
	
	@Test
	public void testUpdateProduct() {
		Integer id = 1;
		Product product = productRepository.findById(id).get();
		product.setShortDescription("Test Update Short_description for product with id = 1");;
		
		productRepository.save(product);
		
		Product updatedProduct = entityManager.find(Product.class, id);
		
		assertThat(updatedProduct.getShortDescription()).isEqualTo("Test Update Short_description for product with id = 1");
	}
	
	@Test
	public void testDeleteProduct() {
		Integer id = 26;
		productRepository.deleteById(id);
		
		Optional<Product> result = productRepository.findById(id);
		
		assertThat(!result.isPresent());
	}


}
