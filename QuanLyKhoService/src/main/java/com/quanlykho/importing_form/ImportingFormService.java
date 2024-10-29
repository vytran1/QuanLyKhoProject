package com.quanlykho.importing_form;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.quanlykho.common.Inventory;
import com.quanlykho.common.InventoryProduct;
import com.quanlykho.common.InventoryProductId;
import com.quanlykho.common.Product;
import com.quanlykho.common.exception.ImportingFormAlreadyExistException;
import com.quanlykho.common.exception.ImportingFormNotFoundException;
import com.quanlykho.common.importing_form.ImportingForm;
import com.quanlykho.common.importing_form.ImportingFormDetail;
import com.quanlykho.importing_form_detail.ImportingFormDetailRepository;
import com.quanlykho.inventory_product.InventoryProductRepository;
import com.quanlykho.product.ProductRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ImportingFormService {
   
	@Autowired
	private ImportingFormRepository importingFormRepository;
	
	@Autowired
	private InventoryProductRepository inventoryProductRepository;
	
	@Autowired
	private ImportingFormDetailRepository importingFormDetailRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	public List<ImportingForm> getAll(){
		return importingFormRepository.findAll();
	}
	
	
	public Page<ImportingForm> getAllByPage(int pageNum, int pageSize, String sortField, String sortDir){
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
		return importingFormRepository.findAll(pageable);
	}


	public void createImportingForm(ImportingForm newImportingForm) throws ImportingFormAlreadyExistException {
		// TODO Auto-generated method stub
		//Check Mã Phiếu Nhập:
		String importingFormId = newImportingForm.getImportingFormId();
		Optional<ImportingForm> importingFromDatbase = importingFormRepository.findById(importingFormId);
		if(importingFromDatbase.isPresent()) {
			throw new ImportingFormAlreadyExistException("Importing Form ID: " + importingFormId + " has exist in system");
		}
		//Lưu phiếu nhập
		ImportingForm savedImportingForm = importingFormRepository.save(newImportingForm);
		
		String inventoryId = newImportingForm.getInventory().getInventoryId();
		
		//Cập nhật tồn kho 
		Set<ImportingFormDetail> newImportingDetails = savedImportingForm.getImportingDetails();
		for(ImportingFormDetail detail : newImportingDetails) {
		      Integer productId = detail.getImportingFormDetailId().getProductId();
		      Optional<InventoryProduct> inventoryProductOpt = inventoryProductRepository.findByInventoryIdAndProductId(inventoryId, productId);
		      if(inventoryProductOpt.isPresent()) {
		    	  InventoryProduct inventoryProduct = inventoryProductOpt.get();
		    	  inventoryProduct.setQuantity(inventoryProduct.getQuantity() + detail.getQuantity());
		    	  inventoryProductRepository.save(inventoryProduct);
		      }else {
		    	  InventoryProduct inventoryProduct = new InventoryProduct();
		    	  inventoryProduct.setId(new InventoryProductId(inventoryId, productId));
		    	  inventoryProduct.setQuantity(detail.getQuantity());
		    	  inventoryProduct.setProduct(new Product(productId));
		    	  inventoryProduct.setInventory(new Inventory(inventoryId));
		    	  inventoryProductRepository.save(inventoryProduct);
		      }
		}
		
		//Cập nhật giá 
		for(ImportingFormDetail detail : newImportingDetails) {
			Integer productId = detail.getProduct().getId();
			List<Object[]> importingDetailsByProductId = importingFormDetailRepository.loadAllByProductId(productId);
			
			
			float totalPrice = 0;
			int count = 0;
			
			for(Object[] objects : importingDetailsByProductId) {
				totalPrice += (float) objects[1];
				count++;
			}
			
			if(count > 0) {
				float averagePrice = totalPrice / count;
				productRepository.updatePriceOfProduct(productId, averagePrice);
			}
		}
	}
	
	
	
	public ImportingForm getIPFById(String importingFormId) throws ImportingFormNotFoundException {
		Optional<ImportingForm> imOptional = importingFormRepository.findById(importingFormId);
		if(!imOptional.isPresent()) {
			throw new ImportingFormNotFoundException("Importing Form with id: " + importingFormId + " does not exist in system");
		}
		return imOptional.get() ;
	}
	
	public Page<ImportingForm> search(String keyWord,int pageNum,int pageSize,String sortField,String sortDir){
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		Pageable pageable = PageRequest.of(pageNum -1 , pageSize, sort);
		return importingFormRepository.search(keyWord, pageable);
	}
	
}
