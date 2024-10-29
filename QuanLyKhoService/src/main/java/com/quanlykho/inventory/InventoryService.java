package com.quanlykho.inventory;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.quanlykho.common.Inventory;
import com.quanlykho.common.exception.CannotDeleteThisItemException;
import com.quanlykho.common.exception.InventoryAlreadyExistInDatabaseException;
import com.quanlykho.common.exception.InventoryNotFoundException;

@Service
public class InventoryService {
    
	@Autowired
	private InventoryRepository inventoryRepository;
	
	
	public List<Inventory> getAll(){
		return inventoryRepository.findAll();
	}
	
	public Page<Inventory> getAllByPage(int pageNum, int pageSize, String sortField, String sortDir){
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		Pageable pageable = PageRequest.of(pageNum - 1, pageSize,sort);
		return inventoryRepository.findAll(pageable);
	}
	
	
	public Inventory getByInventoryId(String inventoryId) throws InventoryNotFoundException {
		Optional<Inventory> inventoryResult = inventoryRepository.findById(inventoryId);
		if(!inventoryResult.isPresent()) {
			throw new InventoryNotFoundException("Not exist inventory with id: " + inventoryId);
		}
		return inventoryResult.get();
	}
    
	
	public Page<Inventory> search(String keyWord,int pageNum,int pageSize, String sortField,String sortDir){
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
		return inventoryRepository.search(keyWord, pageable);
	}
	
	public void createInventory(Inventory inventory) throws InventoryAlreadyExistInDatabaseException {
		// TODO Auto-generated method stub
		//Check Unique InventoryId
		boolean isExist = checkInventoryIdIsUnique(inventory);
		if(isExist) {
			throw new InventoryAlreadyExistInDatabaseException("Inventory Id " + inventory.getInventoryId() + " has exist in database");
		}else {
			inventoryRepository.save(inventory);
		}
	}
	
	
	
	
	public void updateInventory(Inventory inventory) {
		inventoryRepository.save(inventory);
	}
	
	public void deleteInventory(Inventory inventory) throws CannotDeleteThisItemException {
		//Check candelete
		boolean canDelete = inventoryRepository.checkIsInventoryJoinBussiness(inventory.getInventoryId());
		if(!canDelete) {
			throw new CannotDeleteThisItemException("You can not delete this inventory because it already has join in business forming");
		}else {
			System.out.println("You can delete this inventory");
		}
		inventoryRepository.delete(inventory);
	}
	
	
	public boolean checkInventoryIdIsUnique(Inventory inventory) {
		return inventoryRepository.existsById(inventory.getInventoryId());
	}
}
