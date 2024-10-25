package com.quanlykho.inventory_product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryProductService {
   
	@Autowired
	private InventoryProductRepository inventoryProductRepository;
	
	
	public List<Object[]> findQuantiyInEachInventoryOfProductX(String productName){
           List<Object[]> results = inventoryProductRepository.findInventoriesQuantityWithProductName(productName);
           return results;
	}
}
