package com.quanlykho.product;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.quanlykho.common.Product;

public interface ProductRepository extends JpaRepository<Product,Integer> {
	
	public Product findByName(String name);
	
	@Query("SELECT p FROM Product p")
	public Page<Product> findAll(String keyword, Pageable pageable);
	
	public Long countById(Integer id);
	
	@Query("SELECT p FROM Product p WHERE p.category.id = ?1 "
			+ "OR p.category.allParentIDs LIKE %?2%")
	public Page<Product> findAllInCategory(Integer categoryId, String categoryIdMatch, Pageable pageable);
	
	@Query("SELECT p FROM Product p WHERE (p.category.id = ?1 "
			+ "OR p.category.allParentIDs LIKE %?2%) AND "
			+ "(p.name LIKE %?3% "
			+ "OR p.shortDescription LIKE %?3% " 
			+ "OR p.brand.name LIKE %?3% "
			+ "OR p.category.name LIKE %?3%)")
	public Page<Product> searchInCategory(Integer categoryId, String categoryIdMatch, 
							String keyword, Pageable pageable);
	
	
	@Query("UPDATE Product p SET p.price = ?2 WHERE p.id = ?1")
	@Modifying
	public void updatePriceOfProduct(Integer productId,float averagePrice);
	
	@Query("SELECT p FROM Product p "
			+ "WHERE CONCAT(p.id,' ',p.name,' ',p.alias,' ',p.shortDescription,' ',p.category.name,' ',p.brand.name) "
			+ "LIKE %?1%")
	public Page<Product> search(String keyWord,Pageable pageable);
}
