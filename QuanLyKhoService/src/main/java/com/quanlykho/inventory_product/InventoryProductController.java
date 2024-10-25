package com.quanlykho.inventory_product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quanlykho.common.Inventory;
import com.quanlykho.inventory.InventoryService;

@RestController
@RequestMapping("/api/inventory_product")
public class InventoryProductController {
    
	@Autowired
	private InventoryService inventoryService;
	
	@Autowired
	private InventoryProductService inventoryProductService;
	
	@PostMapping("")
	public ResponseEntity<?> getQuantityInEachInventoryOfProductX(@RequestBody InventoryProductRequest inventoryProductRequest){
		String productName = inventoryProductRequest.getProductName();
		System.out.println(productName);
		List<Object[]> results = inventoryProductService.findQuantiyInEachInventoryOfProductX(productName);
		InventoryProductResult inventoryProductResult = new InventoryProductResult();
		for(Object [] object : results) {
			Inventory inventory = (Inventory) object[0];
			String inventoryName = inventory.getInventoryName();
			Integer quantity = (Integer)object[1];
			InventoryQuantity inventoryQuantity = new InventoryQuantity(inventoryName, quantity);
			inventoryProductResult.getResults().add(inventoryQuantity);
		}
		inventoryProductResult.setProductName(productName);
		return ResponseEntity.ok(inventoryProductResult);
	}
}
