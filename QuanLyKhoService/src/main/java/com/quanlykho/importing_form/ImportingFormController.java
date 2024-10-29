package com.quanlykho.importing_form;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quanlykho.Utility;
import com.quanlykho.common.Inventory;
import com.quanlykho.common.InventoryUser;
import com.quanlykho.common.Product;
import com.quanlykho.common.exception.ImportingFormAlreadyExistException;
import com.quanlykho.common.exception.ImportingFormNotFoundException;
import com.quanlykho.common.importing_form.ImportingForm;
import com.quanlykho.common.importing_form.ImportingFormDetail;
import com.quanlykho.common.importing_form.ImportingFormDetailId;
import com.quanlykho.common.inventory_order.InventoryOrder;

@RestController
@RequestMapping("/api/importing_form")
public class ImportingFormController {
   
	@Autowired
	private ImportingFormService importingFormService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Deprecated
	public ResponseEntity<?> getAllImportingForm(){
		List<ImportingForm> importingForms = importingFormService.getAll();
		List<ImportingFormDTO> importingFormDTOs = importingForms.stream().map(this::convertEntityToDTO).toList();
		return ResponseEntity.ok(importingFormDTOs);
	}
	
	@GetMapping("")
	public ResponseEntity<?> getAllImportingFormByPage(
			@RequestParam("pageNum") int pageNum,
			@RequestParam("pageSize") int pageSize,
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir
			){
		Page<ImportingForm> pages = importingFormService.getAllByPage(pageNum, pageSize, sortField, sortDir);
		List<ImportingForm> importingForms = pages.getContent();
		if(importingForms.size() > 0) {
			List<ImportingFormDTO> importingFormDTOs = importingForms.stream().map(this::convertEntityToDTO).toList();
			ImportingFormListResult listResult = new ImportingFormListResult();
			listResult.setDtos(importingFormDTOs);
			listResult.setPageNum(pageNum);
			listResult.setPageSize(pageSize);
			listResult.setSortField(sortField);
			listResult.setSortDir(sortDir);
		    String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
		    listResult.setReverseDir(reverseSortDir);
		    listResult.setTotalItems(pages.getTotalElements());
		    listResult.setTotalPage(pages.getTotalPages());
			return ResponseEntity.ok(listResult);
		}else {
			return ResponseEntity.noContent().build();
		}
	}
	
	@GetMapping("/search")
	public ResponseEntity<?> search(
			@RequestParam("keyWord") String keyWord,
			@RequestParam("pageNum") int pageNum,
			@RequestParam("pageSize") int pageSize,
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir
			){
		Page<ImportingForm> pages = importingFormService.search(keyWord, pageNum, pageSize, sortField, sortDir);
		if(pages.isEmpty()) {
			return ResponseEntity.noContent().build();
		}else {
			List<ImportingForm> importingForms = pages.getContent();
			List<ImportingFormDTO> importingFormDTOs = importingForms.stream().map(this::convertEntityToDTO).toList();
			ImportingFormListResult listResult = new ImportingFormListResult();
			listResult.setDtos(importingFormDTOs);
			listResult.setPageNum(pageNum);
			listResult.setPageSize(pageSize);
			listResult.setSortField(sortField);
			listResult.setSortDir(sortDir);
		    String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
		    listResult.setReverseDir(reverseSortDir);
		    listResult.setTotalItems(pages.getTotalElements());
		    listResult.setTotalPage(pages.getTotalPages());
		    listResult.setKeyWord(keyWord);
			return ResponseEntity.ok(listResult);
		}
	}
	
	
	@PostMapping("")
	public ResponseEntity<?> createImportingForm(@RequestBody ImportingFormDTO importingFormDTO){
		//Check Input From User
//		System.out.println("Importing Form Id: " + importingFormDTO.getImportingFormId());
//		System.out.println("Inventory Order Id: " + importingFormDTO.getInventoryOrder());
//		System.out.println("Inventory Id: " + importingFormDTO.getInventory());
//		Set<ImportingFormDetailDTO> detailDTOs = importingFormDTO.getImportingDetails();
//		for(ImportingFormDetailDTO detailDTO : detailDTOs) {
//			System.out.println("Product ID: " + detailDTO.getProductId());
//			System.out.println("Quantity: " + detailDTO.getQuantity());
//			System.out.println("Unit Price: " + detailDTO.getUnitPrice());
//		}
		
		//ConvertDTOToEntity
		ImportingForm newImportingForm = convertDTOToEntity(importingFormDTO);
		String userId = Utility.getMaNhanVien();
		newImportingForm.setInventoryUser(new InventoryUser(userId));
		setDetailForImportingForm(newImportingForm,importingFormDTO.getImportingDetails());
		
		//Check Complete Entity Data From DTO:
//		System.out.println("Importing Form Id: " + newImportingForm.getImportingFormId());
//		System.out.println("Created Time: " + newImportingForm.getCreatedTime());
//		System.out.println("Inventory Order ID:  " + newImportingForm.getInventoryOrder().getOrderId());
//		System.out.println("Inventory User ID: " + newImportingForm.getInventoryUser().getUserId());
//		System.out.println("Inventory ID: " + newImportingForm.getInventory().getInventoryId());
		Set<ImportingFormDetail> details = newImportingForm.getImportingDetails();
//		System.out.println("Importing Form Details : ");
//		details.forEach(item -> {
//			System.out.println("Importing Form Id: " + item.getImportingFormDetailId().getImportingFormId());
//			System.out.println("Product Id: " + item.getImportingFormDetailId().getProductId());
//			System.out.println("Quantity: " + item.getQuantity());
//			System.out.println("Unit Price: " + item.getUnitPrice());
//			System.out.println("ImportingForm Reference: " + item.getImportingForm().getImportingFormId());
//			System.out.println("Product Reference: " + item.getProduct().getId());
//		});
		
		try {
			importingFormService.createImportingForm(newImportingForm);
			return ResponseEntity.ok().build();
		} catch (ImportingFormAlreadyExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	
	@GetMapping("/{importingFormId}")
	public ResponseEntity<?> getImportingFormById(@PathVariable("importingFormId") String importingFormId){
		try {
			ImportingForm importingForm = importingFormService.getIPFById(importingFormId);
			ImportingFormDTOForDetailFunction imDetailFunction = getIPFForDetailFunction(importingForm);
			return ResponseEntity.ok(imDetailFunction);
		} catch (ImportingFormNotFoundException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
	
	
	
	
	public ImportingFormDTO convertEntityToDTO(ImportingForm importingForm) {
		return modelMapper.map(importingForm,ImportingFormDTO.class);
	}
	
	public ImportingForm convertDTOToEntity(ImportingFormDTO importingFormDTO) {
		ImportingForm importingForm = new ImportingForm();
		importingForm.setImportingFormId(importingFormDTO.getImportingFormId());
		importingForm.setCreatedTime(new Date());
		importingForm.setInventory(new Inventory(importingFormDTO.getInventory()));
		importingForm.setInventoryOrder(new InventoryOrder(importingFormDTO.getInventoryOrder()));
		return importingForm;
	}
	
	public void setDetailForImportingForm(ImportingForm importingForm,Set<ImportingFormDetailDTO> importingFormDetailDTOs) {
		for(ImportingFormDetailDTO detailDTO : importingFormDetailDTOs) {
			ImportingFormDetail detailEntity = new ImportingFormDetail();
			detailEntity.setImportingFormDetailId(new ImportingFormDetailId(importingForm.getImportingFormId(),detailDTO.getProductId()));
			detailEntity.setQuantity(detailDTO.getQuantity());
			detailEntity.setUnitPrice(detailDTO.getUnitPrice());
			detailEntity.setProduct(new Product(detailDTO.getProductId()));
			importingForm.addImportingDetail(detailEntity);
		}
	}
	
	public ImportingFormDTOForDetailFunction getIPFForDetailFunction(ImportingForm importingForm) {
		ImportingFormDTOForDetailFunction imDetailFunction =  new ImportingFormDTOForDetailFunction();
	    setInformationForImportingDTO(imDetailFunction, importingForm);
	    setListImportingDetail(imDetailFunction, importingForm);
        return imDetailFunction;		
	}
	
	
	public void setInformationForImportingDTO(ImportingFormDTOForDetailFunction imDetailFunction, ImportingForm importingForm) {
		imDetailFunction.setImportingFormId(importingForm.getImportingFormId());
		imDetailFunction.setCreatedTime(importingForm.getCreatedTime());
		imDetailFunction.setInventoryUserId(importingForm.getInventoryUser().getUserId());
		imDetailFunction.setInventoryUserFullname(importingForm.getInventoryUser().getFullName());
		imDetailFunction.setOrderId(importingForm.getInventoryOrder().getOrderId());
		imDetailFunction.setCustomerName(importingForm.getInventoryOrder().getCustomerName());
		imDetailFunction.setInventoryId(importingForm.getInventory().getInventoryId());
		imDetailFunction.setInventoryAddress(importingForm.getInventory().getDetailAddress());
	}
	
	public void setListImportingDetail(ImportingFormDTOForDetailFunction imDetailFunction,ImportingForm importingForm) {
		Set<ImportingFormDetail> entityDetails = importingForm.getImportingDetails();
		String importingFormId = importingForm.getImportingFormId();
		if(entityDetails.size() > 0) {	
			entityDetails.forEach(detail -> {
				ImportingFormDetailDTO imDetailDTO = new ImportingFormDetailDTO();
				imDetailDTO.setImportingFormId(importingFormId);
				imDetailDTO.setProductId(detail.getProduct().getId());
				imDetailDTO.setQuantity(detail.getQuantity());
				imDetailDTO.setUnitPrice(detail.getUnitPrice());
				imDetailFunction.getDetails().add(imDetailDTO);
				float total = detail.getQuantity() * detail.getUnitPrice();
				imDetailFunction.setTotalAmount(imDetailFunction.getTotalAmount() + total);
			});
		}
	}
	
}
