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

import com.quanlykho.common.Brand;
import com.quanlykho.common.Category;
import com.quanlykho.common.InventoryRole;
import com.quanlykho.common.Product;
import com.quanlykho.common.exception.ProductNotFoundException;
import com.quanlykho.common.exception.UserAlreadyExistException;
import com.quanlykho.common.exception.UserNotExistException;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/products/")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("findAll")
	public ResponseEntity<?> listAllProduct(HttpServletRequest request){
		List<Product> result = productService.listAllProducts();
		if(result.size() == 0) {
			return ResponseEntity.noContent().build();
		}else {
			return ResponseEntity.ok(result);
		}
	}
	
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
	public ResponseEntity<?> createProduct(@RequestBody Product product){
		try {
			product.setBrand(new Brand(1));
			product.setCategory(new Category(2));
		
			productService.saveProduct(product);
			return ResponseEntity.ok().build();
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping("findByProductID/{productId}")
	public ResponseEntity<?> getProductById(@PathVariable("productId") int productId){
		try {
			Product product = productService.get(productId);
			return ResponseEntity.ok(product);
		} catch (ProductNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("updateProduct")
	public ResponseEntity<?> updateProduct(@RequestBody Product product){
		try {	
			productService.saveProduct(product);
			return ResponseEntity.ok().build();
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping("deleteProduct/{productId}")
	public ResponseEntity<?> deleteUserById(@PathVariable("productId") int productId){
		try {
			productService.deleteProductById(productId);
			return ResponseEntity.ok().build();
		} catch (ProductNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
}
