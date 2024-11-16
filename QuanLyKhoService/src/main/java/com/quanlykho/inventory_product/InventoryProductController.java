package com.quanlykho.inventory_product;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quanlykho.common.Inventory;
import com.quanlykho.common.InventoryProduct;
import com.quanlykho.common.exception.InventoryNotFoundException;
import com.quanlykho.common.exception.ProductNotFoundException;
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
	
	@GetMapping("/findStock/{productId}")
	public ResponseEntity<?> getStockQuantityByProductId(@PathVariable("productId") Integer productId){
		try {
			List<InventoryProduct> listResults = inventoryProductService.findStockByProductId(productId);
			if(listResults.size() > 0) {
				InventoryProductResult inventoryProductResult = new InventoryProductResult();
				inventoryProductResult = convertEntityToDTO(listResults);
				return ResponseEntity.ok(inventoryProductResult);
			}else {
				return ResponseEntity.noContent().build();
			}
		} catch (ProductNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/stock_inventory/{inventoryId}")
	public ResponseEntity<?> getStockQuantiryByInventory(@PathVariable("inventoryId") String inventoryId){
		try {
			List<InventoryProduct> listResults = inventoryProductService.findStockByInventoryId(inventoryId);
			if(listResults.size() > 0) {
				InventoryProductForExportingForm resultDTO = this.convertEntityToDTO2(listResults);
				return ResponseEntity.ok(resultDTO);
			}else {
				return ResponseEntity.noContent().build();
			}
		} catch (InventoryNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
	
	
	
	public InventoryProductResult convertEntityToDTO(List<InventoryProduct> entities) {
	    InventoryProductResult inventoryProductResult = new InventoryProductResult();
	    String productName = entities.get(0).getProduct().getName();
	    inventoryProductResult.setProductName(productName);

	    List<InventoryQuantity> quantities = entities.stream()
	        .map(inventoryProduct -> new InventoryQuantity(
	                inventoryProduct.getInventory().getInventoryName(),
	                inventoryProduct.getQuantity()))
	        .collect(Collectors.toList());

	    inventoryProductResult.setResults(quantities);
	    return inventoryProductResult;
	}
	
	public InventoryProductForExportingForm convertEntityToDTO2(List<InventoryProduct> results) {
		InventoryProductForExportingForm resultDTO = new InventoryProductForExportingForm();
		String inventoryName = results.get(0).getInventory().getInventoryName();
		resultDTO.setInventory(inventoryName);
		
		for(InventoryProduct iv : results) {
			Integer productId = iv.getProduct().getId();
			Integer quantity = iv.getQuantity();
			resultDTO.getResults().add(new ProductQuantity(productId, quantity));
		}
		return resultDTO;
	}
	
}
