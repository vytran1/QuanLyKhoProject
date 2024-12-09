package com.quanlykho.inventory_provider;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
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

import com.quanlykho.common.InventoryProvider;
import com.quanlykho.common.exception.ProviderEmailAlreadyExistException;
import com.quanlykho.common.exception.ProviderNotFoundException;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/api/providers")
public class InventoryProviderController {
    
	@Autowired
	private InventoryProviderService inventoryProviderService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping("")
	public ResponseEntity<?> listByPage(
			    @RequestParam("pageNum") int pageNum,
			    @RequestParam("pageSize") int pageSize,
			    @RequestParam("sortField") String sortField,
			    @RequestParam("sortDir") String sortDir
			){
		Page<InventoryProvider> pages = inventoryProviderService.listByPage(pageNum, pageSize, sortField, sortDir);
		if(pages.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}else {
			List<InventoryProvider> providers = pages.getContent();
			List<InventoryProviderDTO> providerDTOs = providers.stream().map(this::convertEntityToDTO).toList();
			
			InventoryProviderListResult listResult = new InventoryProviderListResult();
			listResult.setProviders(providerDTOs);
			listResult.setPageNum(pageNum);
			listResult.setPageSize(pageSize);
			listResult.setSortField(sortField);
			listResult.setSortDir(sortDir);
			String reverseDir = sortDir.endsWith("asc") ? "desc" : "asc";
			listResult.setReverseDir(reverseDir);
			listResult.setTotalItems(pages.getTotalElements());
			listResult.setTotalPage(pages.getTotalPages());
			
			return ResponseEntity.ok(listResult);
		}
	}
	
	
	@GetMapping("/search")
	public ResponseEntity<?> search(
			    @RequestParam("pageNum") int pageNum,
			    @RequestParam("pageSize") int pageSize,
			    @RequestParam("sortField") String sortField,
			    @RequestParam("sortDir") String sortDir,
			    @RequestParam("keyWord") String keyWord
			){
		Page<InventoryProvider> pages = inventoryProviderService.search(pageNum, pageSize, sortField, sortDir,keyWord);
		if(pages.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}else {
			List<InventoryProvider> providers = pages.getContent();
			List<InventoryProviderDTO> providerDTOs = providers.stream().map(this::convertEntityToDTO).toList();
			
			InventoryProviderListResult listResult = new InventoryProviderListResult();
			listResult.setProviders(providerDTOs);
			listResult.setPageNum(pageNum);
			listResult.setPageSize(pageSize);
			listResult.setSortField(sortField);
			listResult.setSortDir(sortDir);
			String reverseDir = sortDir.endsWith("asc") ? "desc" : "asc";
			listResult.setReverseDir(reverseDir);
			listResult.setTotalItems(pages.getTotalElements());
			listResult.setTotalPage(pages.getTotalPages());
			listResult.setKeyWord(keyWord);
			
			return ResponseEntity.ok(listResult);
		}
	}
	
	@GetMapping("/checkunique")
	public ResponseEntity<?> checkUniqueEmailForCreateFunction(
			   @RequestParam(required = false) Integer providerId,
			   @RequestParam String providerEmail
			){
		boolean isUnique = inventoryProviderService.checkUniqueEmail(providerId, providerEmail);
		return ResponseEntity.ok(isUnique);
	}
	
	
	
	@GetMapping("/{providerId}")
    public ResponseEntity<?> getInventoryProviderByID(@PathVariable("providerId") Integer providerId){
		try {
			InventoryProvider provider = inventoryProviderService.getById(providerId);
			InventoryProviderDTO providerDTO = convertEntityToDTO(provider);
			return ResponseEntity.ok(providerDTO);
		} catch (ProviderNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	
	@GetMapping("/forCreateOrder")
	public ResponseEntity<?> getAllWithOnlyIdAndName(){
		List<InventoryProvider> results = inventoryProviderService.findAllWithOnlyIdAndProvider();
		List<InventoryProviderDTO> providerDTOs = results.stream().map(this::convertEntityToDTO).toList();
		return ResponseEntity.ok(providerDTOs);
	}
	
	
	
	@PostMapping("")
	public ResponseEntity<?> createProvider(@RequestBody InventoryProviderDTO dto){
		InventoryProvider provider = convertDTOToEntity(dto);
		provider.setCreatedTime(new Date());
		try {
			inventoryProviderService.createProvider(provider);
			return ResponseEntity.ok().build();
		} catch (ProviderEmailAlreadyExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PostMapping("/update/{providerId}")
	public ResponseEntity<?> updateProvider(@RequestBody InventoryProviderDTO dto, @PathVariable("providerId") Integer providerId){
		InventoryProvider provider = convertDTOToEntity(dto);
		try {
			inventoryProviderService.updateProvider(providerId, provider);
			return ResponseEntity.ok().build();
		} catch (ProviderNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());			
		} catch (ProviderEmailAlreadyExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{providerId}")
	public ResponseEntity<?> deleteProvider(@PathVariable("providerId") Integer providerId){
		try {
			inventoryProviderService.deleteProvider(providerId);
			return ResponseEntity.ok().build();
		} catch (ProviderNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	
	private InventoryProviderDTO convertEntityToDTO(InventoryProvider entity) {
		return modelMapper.map(entity,InventoryProviderDTO.class);
	}
	
	private InventoryProvider convertDTOToEntity(InventoryProviderDTO dto) {
		return modelMapper.map(dto,InventoryProvider.class);
	}
	
}
