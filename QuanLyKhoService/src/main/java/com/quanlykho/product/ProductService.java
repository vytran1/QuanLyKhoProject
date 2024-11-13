package com.quanlykho.product;


import java.util.List;
import java.util.NoSuchElementException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.quanlykho.common.Product;

import com.quanlykho.common.exception.ProductNotFoundException;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductService {

	@Autowired
	private ProductRepository productRepo;
	
	//Load all products
	public List<Product> listAllProducts() {
		return (List<Product>) productRepo.findAll();
	}
	
	//List by page product
	public Page<Product> listByPage(Integer pageNum, Integer pageSize, String sortDir, 
			String sortField, String keyword,Integer categoryId){	
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		Pageable pageable = PageRequest.of(pageNum - 1,pageSize, sort);

		Page<Product> page = null;
		
		if(keyword != null && !keyword.isEmpty()) {
			if(categoryId != null && categoryId > 0) {
				String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";
				page = productRepo.searchInCategory(categoryId, categoryIdMatch, keyword, pageable);
			}else {
				page = productRepo.findAll(keyword, pageable);
			}		
		} else {
			if(categoryId != null && categoryId > 0) {
				String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";
				page = productRepo.findAllInCategory(categoryId, categoryIdMatch, pageable);
			} else {
				page = productRepo.findAll(pageable);
			}	
		}
		return page;
	}
	
	//Save products
	public void saveProduct(Product product) {				
		if(product.getAlias() == null || product.getAlias().isEmpty()) {
			String defaultAlias = product.getName().replaceAll(" ", "-");
			product.setAlias(defaultAlias);
		}else {
			product.setAlias(product.getAlias().replaceAll(" ", "-"));
		}
		
		productRepo.save(product);
	}
	
	
	//Method get product by id
	public Product get(Integer id) throws ProductNotFoundException {
		try {
			return productRepo.findById(id).get();
		}catch (NoSuchElementException ex) {
			throw new ProductNotFoundException("Could not find any product with ID " + id);
		}
	}
	
	//Method check product duplicate
	public boolean checkUnique(Integer id, String name) {
		boolean isCreatingNew = (id == null || id == 0);
		Product productByName = productRepo.findByName(name);
		
		if(isCreatingNew) {
			if(productByName != null) return true;
		}else {
			if(productByName != null && productByName.getId() != id) {
				return true;
			}
		}
		
		return false;
	}
	
	//Method deleteProduct
	public void deleteProductById(Integer id) throws ProductNotFoundException {
		Long countById = productRepo.countById(id);
		if(countById == null || countById == 0) {
			throw new ProductNotFoundException("Could not found any product with ID " + id);
		}
		productRepo.deleteById(id);
	}
	
	public void updatePriceOfProduct(Integer productId, float averagePrice) {
		
	}
	
	public Page<Product> listByPage2(int pageNum,int pageSize,String sortField,String sortDir){
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
		return productRepo.findAll(pageable);
	}
	
	public Page<Product> search(int pageNum,int pageSize,String sortField,String sortDir,String keyWord){
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
		return productRepo.search(keyWord, pageable);
	}
}
