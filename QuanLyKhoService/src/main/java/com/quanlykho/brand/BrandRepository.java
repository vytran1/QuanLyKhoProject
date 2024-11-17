package com.quanlykho.brand;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.quanlykho.common.Brand;
import com.quanlykho.common.Category;

public interface BrandRepository extends JpaRepository<Brand, Integer> {
	
	@Query("SELECT b.categories FROM Brand b WHERE b.id = :brandId")
    List<Category> findCategoriesByBrandId(@Param("brandId") Integer brandId);

}
