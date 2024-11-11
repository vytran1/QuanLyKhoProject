package com.quanlykho.inventory_product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quanlykho.common.InventoryProduct;
import com.quanlykho.common.exception.ProductNotFoundException;
import com.quanlykho.product.ProductRepository;

@Service
public class InventoryProductService {
   
	@Autowired
	private InventoryProductRepository inventoryProductRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	public List<Object[]> findQuantiyInEachInventoryOfProductX(String productName){
           List<Object[]> results = inventoryProductRepository.findInventoriesQuantityWithProductName(productName);
           return results;
	}
	
	public List<InventoryProduct> findStockByProductId(Integer productId) throws ProductNotFoundException{
		if(productRepository.existsById(productId)) {
			return inventoryProductRepository.findStockByProductId(productId);
		}else {
			throw new ProductNotFoundException("Not exist product with id: " + productId);
		}
	}
	
}
