package com.quanlykho.inventory;

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

import com.quanlykho.common.Inventory;
import com.quanlykho.common.exception.CannotDeleteThisItemException;
import com.quanlykho.common.exception.InventoryAlreadyExistInDatabaseException;
import com.quanlykho.common.exception.InventoryNotFoundException;
import com.quanlykho.common.setting.Country;
import com.quanlykho.common.setting.District;
import com.quanlykho.common.setting.State;

@RestController
@RequestMapping("/api/inventories")
public class InventoryController {
    
	
	@Autowired
	private InventoryService inventoryService;
	
	@Autowired
	private ModelMapper modelMapper;
	
//	@GetMapping("")
	@Deprecated
	public ResponseEntity<?> getAllInventory(){
		List<Inventory> inventories = inventoryService.getAll();
		if(inventories.size() > 0) {
			List<InventoryDTO> inventoryDTOs = inventories.stream().map(this::convertEntityToDTO).toList();
			return ResponseEntity.ok(inventoryDTOs);
		}else {
			return ResponseEntity.noContent().build();
		}
	}
	
	@GetMapping("")
	public ResponseEntity<?> getAllInventoryWithPage(
			@RequestParam("pageNum") int pageNum,
			@RequestParam("pageSize") int pageSize,
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir
			
			){
		Page<Inventory> pages= inventoryService.getAllByPage(pageNum, pageSize, sortField, sortDir);
		List<Inventory> inventories = pages.getContent();
		if(inventories.size() > 0) {
			List<InventoryDTO> inventoryDTOs = inventories.stream().map(this::convertEntityToDTO).toList();
			InventoryListResult inventoryListResult = new InventoryListResult();
			inventoryListResult.setInventoryDTOs(inventoryDTOs);
			inventoryListResult.setPageNum(pageNum);
			inventoryListResult.setPageSize(pageSize);
			inventoryListResult.setSortField(sortField);
			inventoryListResult.setSortDir(sortDir);
			String reverseSort = sortDir.equals("asc") ? "desc" : "asc";
			inventoryListResult.setReverseDir(reverseSort);
			inventoryListResult.setTotalItems(pages.getTotalElements());
			inventoryListResult.setTotalPage(pages.getTotalPages());
			return ResponseEntity.ok(inventoryListResult);
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
		Page<Inventory> pages = inventoryService.search(keyWord, pageNum, pageSize, sortField, sortDir);
		if(pages.isEmpty()) {
			return ResponseEntity.noContent().build();
		}else {
			List<Inventory> inventories = pages.getContent();
			List<InventoryDTO> inventoryDTOs = inventories.stream().map(this::convertEntityToDTO).toList();
			InventoryListResult inventoryListResult = new InventoryListResult();
			inventoryListResult.setInventoryDTOs(inventoryDTOs);
			inventoryListResult.setPageNum(pageNum);
			inventoryListResult.setPageSize(pageSize);
			inventoryListResult.setSortField(sortField);
			inventoryListResult.setSortDir(sortDir);
			String reverseSort = sortDir.equals("asc") ? "desc" : "asc";
			inventoryListResult.setReverseDir(reverseSort);
			inventoryListResult.setTotalItems(pages.getTotalElements());
			inventoryListResult.setTotalPage(pages.getTotalPages());
			return ResponseEntity.ok(inventoryListResult);
		}
	}
	
	@GetMapping("/{inventoryId}")
	public ResponseEntity<?> getInventoryById(@PathVariable("inventoryId") String inventoryId){
		try {
			Inventory inventory =  inventoryService.getByInventoryId(inventoryId);
			InventoryDTO inventoryDTO = convertEntityToDTO(inventory);
			return ResponseEntity.ok(inventoryDTO);
		} catch (InventoryNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PostMapping()
	public ResponseEntity<?> createInventory(@RequestBody InventoryDTO inventoryDTO){
		Inventory inventory = new Inventory();
		inventory.setInventoryId(inventoryDTO.getInventoryId());
		inventory.setInventoryAddress(inventoryDTO.getInventoryAddress());
		inventory.setInventoryName(inventoryDTO.getInventoryName());
		inventory.setCountry(new Country(Integer.valueOf(inventoryDTO.getCountryId())));
		inventory.setDistrict(new District(Integer.valueOf(inventoryDTO.getDistrictId())));
		inventory.setState(new State(Integer.valueOf(inventoryDTO.getStateId())));
		try {
			inventoryService.createInventory(inventory);
			return ResponseEntity.ok().build();
		} catch (InventoryAlreadyExistInDatabaseException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/update")
	public ResponseEntity<?> updateInventory(@RequestBody InventoryDTO inventoryDTO){
		try {
			Inventory inventoryFromDatabase = inventoryService.getByInventoryId(inventoryDTO.getInventoryId());
			inventoryFromDatabase.setInventoryName(inventoryDTO.getInventoryName());
			inventoryFromDatabase.setInventoryAddress(inventoryDTO.getInventoryAddress());
			inventoryService.updateInventory(inventoryFromDatabase);
			return ResponseEntity.ok().build();
		} catch (InventoryNotFoundException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/{inventoryId}")
	public ResponseEntity<?> deleteInventory(@PathVariable("inventoryId") String inventoryId){
		try {
			Inventory inventoryNeedToBeDeleted = inventoryService.getByInventoryId(inventoryId);
			inventoryService.deleteInventory(inventoryNeedToBeDeleted);
			return ResponseEntity.ok().build();
		} catch (InventoryNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
		} catch (CannotDeleteThisItemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@GetMapping("/isExist/{inventoryId}")
	public ResponseEntity<?> checkIsExistInventoryId(@PathVariable("inventoryId") String inventoryId){
		return new ResponseEntity(this.inventoryService.isExist(inventoryId),HttpStatus.OK);
	}
	
	
	
	public InventoryDTO convertEntityToDTO(Inventory inventory) {
		return modelMapper.map(inventory,InventoryDTO.class);
	}
	
	public Inventory convertDTOToEntity(InventoryDTO inventoryDTO) {
		return modelMapper.map(inventoryDTO,Inventory.class);
	}
}
