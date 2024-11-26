package com.quanlykho.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quanlykho.brand.BrandDTO;
import com.quanlykho.category.CategoryDTO;
import com.quanlykho.common.Brand;
import com.quanlykho.common.Category;
import com.quanlykho.common.Product;
import com.quanlykho.common.exception.ProductNotFoundException;


import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/products/")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	//Method này tạm chưa dùng đến
	@GetMapping("findByPage")
	public ResponseEntity<?> listByPage(
			@RequestParam("pageNum") int pageNum,
			@RequestParam("pageSize") int pageSize,
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			@RequestParam("keyword") String keyword,
			@RequestParam("categoryId") int categoryId ){
		Page<Product> pages = productService.listByPage(pageNum, pageSize, sortDir, sortField, keyword, categoryId);
		
		if(pages.isEmpty()) {
			return ResponseEntity.noContent().build();
		}else {
			List<Product> listResults = pages.getContent();
			
			String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
			Long totalItems = pages.getTotalElements();
			int totalPages = pages.getTotalPages();
			
			ProductList listPageProduct = new ProductList(listResults, pageNum, pageSize, sortField, sortDir, keyword, reverseSortDir, totalItems, totalPages);
			
			return ResponseEntity.ok(listPageProduct);
		}
	}
	
	@PostMapping("createProduct")
	public ResponseEntity<?> createProduct(@RequestBody ProductDTOManagement productDTO){
		try {
			Product product = new Product();
			product.setName(productDTO.getName());
			product.setAlias(productDTO.getAlias());
			product.setShortDescription(productDTO.getDescription());;
			product.setUnit(productDTO.getUnit());
			product.setPrice(productDTO.getPrice());
			product.setBrand(new Brand(Integer.valueOf(productDTO.getBrandId())));
			product.setCategory(new Category(Integer.valueOf(productDTO.getCategoryId())));
			product.setEnabled(productDTO.isEnabled());
			
			productService.saveProduct(product);
			return ResponseEntity.ok().build();
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping("findByProductID/{productId}")
	public ResponseEntity<?> getProductById(@PathVariable("productId") Integer productId){
		try {
			Product product = productService.get(productId);
			ProductDTOManagement productDTO = convertEntityToDTO_ProductManagement(product);
			
			return ResponseEntity.ok(productDTO);
		} catch (ProductNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("updateProduct")
	public ResponseEntity<?> updateProduct(@RequestBody ProductDTOManagement productDTO){
		try {	
			Product productFromDB = productService.get(productDTO.getId());
			productFromDB.setName(productDTO.getName());
			productFromDB.setAlias(productDTO.getAlias());
			productFromDB.setShortDescription(productDTO.getDescription());
			productFromDB.setUnit(productDTO.getUnit());
			productFromDB.setPrice(productDTO.getPrice());
			productFromDB.setEnabled(productDTO.isEnabled());
			
			productService.saveProduct(productFromDB);
			return ResponseEntity.ok().build();
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping("deleteProduct/{productId}")
	public ResponseEntity<?> deleteProductById(@PathVariable("productId") int productId){
		try {
			productService.deleteProductById(productId);
			return ResponseEntity.ok().build();
		} catch (ProductNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
		}  catch (IllegalArgumentException e) {
	        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	    } catch (Exception e) {
	        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> getAllProductsByPage(@RequestParam("pageNum") int pageNum
			,@RequestParam("pageSize") int pageSize 
			,@RequestParam("sortField") String sortField
			,@RequestParam("sortDir") String sortDir
			){
		Page<Product> pages = productService.getAllProductByPage(pageNum, pageSize, sortField, sortDir);
		if(!pages.isEmpty()) {
			List<Product> products = pages.getContent();
			List<ProductDTO> productDTOs = products.stream().map(this::convertEntityToDTO).toList();
			ProductListResult listResult = new ProductListResult();
			listResult.setProductDTOs(productDTOs);
			listResult.setPageNum(pageNum);
			listResult.setPageSize(pageSize);
			listResult.setSortField(sortField);
			listResult.setSortDir(sortDir);
			listResult.setTotalItems(pages.getTotalElements());
			listResult.setTotalPage(pages.getTotalPages());
			return ResponseEntity.ok(listResult);
		}else {
			return ResponseEntity.noContent().build();
		}
	}
	
	@GetMapping("/findAll")
	public ResponseEntity<?> getAllProduct(@RequestParam("pageNum") int pageNum
			,@RequestParam("pageSize") int pageSize 
			,@RequestParam("sortField") String sortField
			,@RequestParam("sortDir") String sortDir
			){
		Page<Product> pages = productService.getAllProductByPage(pageNum, pageSize, sortField, sortDir);
		if(!pages.isEmpty()) {
			List<Product> products = pages.getContent();
			List<ProductDTOManagement> productDTOs = products.stream().map(this::convertEntityToDTO_ProductManagement).toList();
			ProductListManagementResult listResult = new ProductListManagementResult();
			listResult.setProductDTOs(productDTOs);
			listResult.setPageNum(pageNum);
			listResult.setPageSize(pageSize);
			listResult.setSortField(sortField);
			listResult.setSortDir(sortDir);
			listResult.setTotalItems(pages.getTotalElements());
			listResult.setTotalPage(pages.getTotalPages());
			return ResponseEntity.ok(listResult);
		}else {
			return ResponseEntity.noContent().build();
		}
	}
	
	@GetMapping("/search")
	public ResponseEntity<?> search(
			@RequestParam("pageNum") int pageNum
			,@RequestParam("pageSize") int pageSize 
			,@RequestParam("sortField") String sortField
			,@RequestParam("sortDir") String sortDir
			,@RequestParam("keyWord") String keyWord
			){
		Page<Product> pages = productService.search(pageNum, pageSize, sortField, sortDir, keyWord);
		if(pages.isEmpty()) {
			return ResponseEntity.noContent().build();
		}else {
			List<Product> products = pages.getContent();
			List<ProductDTOManagement> productDTOs = products.stream().map(this::convertEntityToDTO_ProductManagement).toList();
			ProductListManagementResult listResult = new ProductListManagementResult();
			listResult.setProductDTOs(productDTOs);
			listResult.setPageNum(pageNum);
			listResult.setPageSize(pageSize);
			listResult.setSortField(sortField);
			listResult.setSortDir(sortDir);
			listResult.setTotalItems(pages.getTotalElements());
			listResult.setTotalPage(pages.getTotalPages());
			listResult.setKeyword(keyWord);
			return ResponseEntity.ok(listResult); 
		}
	}
	
	public ProductDTOManagement convertEntityToDTO_ProductManagement(Product product) {
		ProductDTOManagement productDTO = new ProductDTOManagement();
		productDTO.setId(product.getId());
		productDTO.setName(product.getName());
		productDTO.setAlias(product.getAlias());
		productDTO.setUnit(product.getUnit());
		productDTO.setPrice(product.getPrice());
		productDTO.setEnabled(product.isEnabled());
		productDTO.setDescription(product.getShortDescription());
		productDTO.setBrandId(product.getBrand().getId());
		productDTO.setBrandName(product.getBrand().getName());
		productDTO.setCategoryId(product.getCategory().getId());
		productDTO.setCategoryName(product.getCategory().getName());
		
		return productDTO;
	}
	
	public ProductDTO convertEntityToDTO(Product product) {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(product.getId());
		productDTO.setName(product.getName());
		productDTO.setAlias(product.getAlias());
		productDTO.setDescription(product.getShortDescription());
		
		CategoryDTO categoryDTO = new CategoryDTO(product.getCategory().getId(),product.getCategory().getName());
		BrandDTO brandDTO = new BrandDTO(product.getBrand().getId(),product.getBrand().getName());
		
		productDTO.setCategory(categoryDTO);
		productDTO.setBrand(brandDTO);
		return productDTO;
	}
}
