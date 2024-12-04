package com.quanlykho.exporting_form;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.quanlykho.common.InventoryProduct;
import com.quanlykho.common.exception.ExportingFormAlreadyExistException;
import com.quanlykho.common.exception.ExportingFormNotFoundException;
import com.quanlykho.common.exception.InSufficientStockException;
import com.quanlykho.common.exception.ProductNotInInventoryException;
import com.quanlykho.common.exporting_form.ExportingForm;
import com.quanlykho.common.exporting_form.ExportingFormDetail;
import com.quanlykho.inventory_product.InventoryProductRepository;



@Service
@Transactional(rollbackFor = {ExportingFormAlreadyExistException.class,ProductNotInInventoryException.class,InSufficientStockException.class,Throwable.class})
public class ExportingFormService {
	
   @Autowired	
   private ExportingFormRepository exportingFormRepository;
   
   @Autowired
   private InventoryProductRepository inventoryProductRepository;
   
   
   
   public List<ExportingForm> getAll(){
	   return exportingFormRepository.findAll();
   }
   
   
   public Page<ExportingForm> getAllByPage(int pageNum,int pageSize, String sortField, String sortDir){
	   Sort sort = Sort.by(sortField);
	   sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
	   Pageable pageable = PageRequest.of(pageNum -1, pageSize, sort);
	   return exportingFormRepository.findAll(pageable);
   }
   
   public ExportingForm getById(String id) throws ExportingFormNotFoundException {
	   Optional<ExportingForm> epfOTP = exportingFormRepository.findById(id);
	   if(!epfOTP.isPresent()) {
		   throw new ExportingFormNotFoundException("Not exist exporting form with id: " + id);
	   }
	   return epfOTP.get();
   }
   
   
   
   public void createExportingForm(ExportingForm exportingForm) throws ExportingFormAlreadyExistException, ProductNotInInventoryException, InSufficientStockException {
	   //Lưu phiếu xuất và chi tiết phiếu xuất
	   String exportingFormId = exportingForm.getExportingFormId();
	   Optional<ExportingForm> epfOPT = exportingFormRepository.findById(exportingFormId);
	   
	   if(epfOPT.isPresent()) {
		   throw new ExportingFormAlreadyExistException("Exporting Form with id: " + exportingFormId  + " is already exist");
	   }
	   
	   ExportingForm savedExportingForm = exportingFormRepository.save(exportingForm);
	   
	   
	   //Cập nhật tồn kho 
	   String inventory = savedExportingForm.getInventory().getInventoryId();
	   Set<ExportingFormDetail> savedDetails = savedExportingForm.getExportingDetails();
	   for(ExportingFormDetail detail : savedDetails) {
		   Integer productId = detail.getExportingFormDetailId().getProductId();
		   Optional<InventoryProduct> inventoryProductOTP = inventoryProductRepository.findByInventoryIdAndProductId(inventory, productId);
		   //Nếu không không có kho nào chứa sản phẩm này thì báo lỗi vì chưa kinh doanh
		   if(!inventoryProductOTP.isPresent()) {
			   throw new ProductNotInInventoryException("Product with id: " + productId + "does not have any quantity in inventory with id:" + inventory);
		   }else {
			   InventoryProduct inventoryProduct = inventoryProductOTP.get();
			   Integer quantityFromRequest = detail.getQuantity();
			   Integer quantityFromDatabase = inventoryProduct.getQuantity();
			   if(quantityFromRequest > quantityFromDatabase) {
				   throw new InSufficientStockException("Your request quantity is: " + quantityFromRequest + ", but database only has:  " + quantityFromDatabase);
			   }
			   inventoryProduct.setQuantity(quantityFromDatabase - quantityFromRequest);
			   inventoryProductRepository.save(inventoryProduct);
		   }
	   }
   }
   
   public Page<ExportingForm> search(String keyWord, int pageNum, int pageSize, String sortField, String sortDir){
	   Sort sort = Sort.by(sortField);
	   sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
	   Pageable pageable = PageRequest.of(pageNum -1, pageSize, sort);
	   return exportingFormRepository.search(keyWord, pageable);
   }
   
   public boolean checkExist(String exportingFormId) {
	   return this.exportingFormRepository.existsById(exportingFormId);
   }
   
}
