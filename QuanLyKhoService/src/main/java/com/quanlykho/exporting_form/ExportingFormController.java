package com.quanlykho.exporting_form;

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
import com.quanlykho.common.exception.ExportingFormAlreadyExistException;
import com.quanlykho.common.exception.ExportingFormNotFoundException;
import com.quanlykho.common.exception.InSufficientStockException;
import com.quanlykho.common.exception.ProductNotInInventoryException;
import com.quanlykho.common.exporting_form.ExportingForm;
import com.quanlykho.common.exporting_form.ExportingFormDetail;
import com.quanlykho.common.exporting_form.ExportingFormDetailId;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/exporting_forms")
public class ExportingFormController {
	
   @Autowired	
   private ExportingFormService exportingFormService;
   
   @Autowired
   private ModelMapper modelMapper;
   
   //@GetMapping("")
   public ResponseEntity<?> getAll(){
	   List<ExportingForm> exportingForms = exportingFormService.getAll();
	   if(exportingForms.size() > 0) {
		   List<ExportingFormDTO> exportingFormDTOs = exportingForms.stream().map(this::convertEntityToDTO).toList();
		   return ResponseEntity.ok(exportingFormDTOs);
	   }else {
		   return ResponseEntity.noContent().build();
	   }
   }
   
   
   @GetMapping("")
   public ResponseEntity<?> getAllWithByPage(
		   @RequestParam("pageNum") int pageNum,
		   @RequestParam("pageSize") int pageSize,
		   @RequestParam("sortField") String sortField,
		   @RequestParam("sortDir") String sortDir
		   
		   )
   {
	   Page<ExportingForm> pages = exportingFormService.getAllByPage(pageNum, pageSize, sortField, sortDir);
	   List<ExportingForm> exportingForms = pages.getContent();
	   if(exportingForms.size() > 0) {
		   List<ExportingFormDTO> exportingFormDTOs = exportingForms.stream().map(this::convertEntityToDTO).toList();
		   ExportingFormListResult listResult = new ExportingFormListResult();
		   listResult.setDtos(exportingFormDTOs);
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
   
   @GetMapping("/{exportingFormId}")
   public ResponseEntity<?> getExportingFormById(@PathVariable("exportingFormId") String exportingFormId){
	   try {
		ExportingForm exportingForm = exportingFormService.getById(exportingFormId);
		ExportingFormDTOForDetailFunction exportingFormDTOForDetailFunction = convertForDetailFunction(exportingForm);
		return ResponseEntity.ok(exportingFormDTOForDetailFunction);
	} catch (ExportingFormNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
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
	   Page<ExportingForm> pages = exportingFormService.search(keyWord, pageNum, pageSize, sortField, sortDir);
	   if(pages.isEmpty()) {
		   return ResponseEntity.noContent().build();
	   }else {
		   List<ExportingForm> exportingForms = pages.getContent();
		   List<ExportingFormDTO> exportingFormDTOs = exportingForms.stream().map(this::convertEntityToDTO).toList();
		   ExportingFormListResult listResult = new ExportingFormListResult();
		   listResult.setDtos(exportingFormDTOs);
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
   public ResponseEntity<?> createExportingForm(@RequestBody ExportingFormDTO exportingFormDTO){
		ExportingForm exportingForm = convertDTOToEntity(exportingFormDTO);
		String userId = Utility.getMaNhanVien();
		exportingForm.setInventoryUser(new InventoryUser(userId));
		try {
			exportingFormService.createExportingForm(exportingForm);
			return ResponseEntity.ok().build();
		} catch (ExportingFormAlreadyExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (ProductNotInInventoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (InSufficientStockException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
   }
   
   public ExportingFormDTO convertEntityToDTO(ExportingForm exportingForm) {
	   return modelMapper.map(exportingForm,ExportingFormDTO.class);
   }
   
   public ExportingFormDTOForDetailFunction convertForDetailFunction(ExportingForm exportingForm) {
	   ExportingFormDTOForDetailFunction exportingFormDTOForDetailFunction = new ExportingFormDTOForDetailFunction();
	   setInfoForDTODetailFunction(exportingFormDTOForDetailFunction, exportingForm);
	   setExportingDetailDTODetailFunction(exportingFormDTOForDetailFunction, exportingForm);
	   return exportingFormDTOForDetailFunction;
   }
   
   public void setInfoForDTODetailFunction(ExportingFormDTOForDetailFunction exportingFormDTOForDetailFunction,ExportingForm exportingForm) {
	   exportingFormDTOForDetailFunction.setExportingFormId(exportingForm.getExportingFormId());
	   exportingFormDTOForDetailFunction.setCreatedTime(exportingForm.getCreatedTime());
	   exportingFormDTOForDetailFunction.setInventoryUserId(exportingForm.getInventoryUser().getUserId());
	   exportingFormDTOForDetailFunction.setInventoryUserName(exportingForm.getInventoryUser().getFullName());
	   exportingFormDTOForDetailFunction.setCustomerName(exportingForm.getCustomerName());
	   exportingFormDTOForDetailFunction.setCustomerPhoneNumber(exportingForm.getCustomerPhoneNumber());
	   exportingFormDTOForDetailFunction.setInventoryId(exportingForm.getInventory().getInventoryId());
	   exportingFormDTOForDetailFunction.setInventoryAddress(exportingForm.getInventory().getDetailAddress());
   }
   
   public void setExportingDetailDTODetailFunction(ExportingFormDTOForDetailFunction exportingFormDTOForDetailFunction,ExportingForm exportingForm) {
	   String exportingFormId = exportingForm.getExportingFormId();
	   Set<ExportingFormDetail> exportingDetails = exportingForm.getExportingDetails();
	   for(ExportingFormDetail detailItem : exportingDetails) {
		   ExportingFormDetailDTO detailDTO = new ExportingFormDetailDTO();
		   detailDTO.setExportingFormId(exportingFormId);
		   detailDTO.setProductId(detailItem.getProduct().getId());
		   detailDTO.setQuantity(detailItem.getQuantity());
		   detailDTO.setUnitPrice(detailItem.getUnitPrice());
		   float total = detailItem.getQuantity() * detailItem.getUnitPrice();
		   exportingFormDTOForDetailFunction.getExportingDetails().add(detailDTO);
		   exportingFormDTOForDetailFunction.setTotal(exportingFormDTOForDetailFunction.getTotal() + total);
	   }
   }
   
   public ExportingForm convertDTOToEntity(ExportingFormDTO dto) {
	   ExportingForm exportingForm = new ExportingForm();
	   exportingForm.setExportingFormId(dto.getExportingFormId());
	   exportingForm.setCreatedTime(new Date());
	   exportingForm.setCustomerName(dto.getCustomerName());
	   exportingForm.setCustomerPhoneNumber(dto.getCustomerPhoneNumber());
	   exportingForm.setInventory(new Inventory(dto.getInventory()));
	   setDetailDtoToDetailEntity(exportingForm, dto);
	   return exportingForm;
   }
   
   public void setDetailDtoToDetailEntity(ExportingForm exportingForm,ExportingFormDTO dto) {
	   Set<ExportingFormDetailDTO> detailDTOS = dto.getExportingDetails();
	   String exportingFormId = exportingForm.getExportingFormId();
	   for(ExportingFormDetailDTO detailDTO : detailDTOS) {
		   ExportingFormDetail detailEntity = new ExportingFormDetail();
		   Integer productId = detailDTO.getProductId();
		   detailEntity.setExportingFormDetailId(new ExportingFormDetailId(exportingFormId, productId));
		   detailEntity.setQuantity(detailDTO.getQuantity());
		   detailEntity.setUnitPrice(detailDTO.getUnitPrice());
		   detailEntity.setProduct(new Product(productId));
		   exportingForm.addDetail(detailEntity);
	   }
   }
}
