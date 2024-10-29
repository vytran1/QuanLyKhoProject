package com.quanlykho.inventory_order;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.quanlykho.common.exception.CannotDeleteThisItemException;
import com.quanlykho.common.exception.InventoryOrderNotFoundException;
import com.quanlykho.common.exception.NotHavingPermissonToUpdateException;
import com.quanlykho.common.exception.OrderAlreadyExistException;
import com.quanlykho.common.importing_form.ImportingForm;
import com.quanlykho.common.inventory_order.InventoryOrder;
import com.quanlykho.common.inventory_order.InventoryOrderDetail;
import com.quanlykho.importing_form.ImportingFormRepository;
import com.quanlykho.inventory_order_detail.InventoryOrderDetailRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class InventoryOrderService {
   
	@Autowired
	private InventoryOrderRepository inventoryOrderRepository;
	
	@Autowired
	private InventoryOrderDetailRepository inventoryOrderDetailRepository;
	
	@Autowired
	private ImportingFormRepository importingFormRepository;
	
	public List<InventoryOrder> getAll(){
		return inventoryOrderRepository.findAll();
	}
	
	
	public Page<InventoryOrder> getByPage(int pageNum,int pageSize,String sortField,String sortDir){
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
		return inventoryOrderRepository.findAll(pageable);
	}


	public void createOrder(InventoryOrder inventoryOrder) throws OrderAlreadyExistException {
		// TODO Auto-generated method stub
		if(checkUniqueForOrder(inventoryOrder.getOrderId())) {
			throw new OrderAlreadyExistException("Order Id: " + inventoryOrder.getOrderId() + " has exist in system");
		}
		inventoryOrderRepository.save(inventoryOrder);
	}
	
	public void updateOrder(InventoryOrderDTO requestBody,Set<InventoryOrderDetail> newListOrderDetails) throws CannotDeleteThisItemException {
		ImportingForm importingForm = importingFormRepository.checkOrderHaveIPFOrNot(requestBody.getOrderId());
		if(importingForm != null) {
			throw new CannotDeleteThisItemException("Order with ID already have importing form so you should not modify to maintain consistent");
		}
		
		InventoryOrder inventoryOrderFromDatabase = inventoryOrderRepository.findById(requestBody.getOrderId()).get();
		inventoryOrderFromDatabase.setCustomerName(requestBody.getCustomerName());
		inventoryOrderFromDatabase.setCustomerPhoneNumber(requestBody.getCustomerPhoneNumber());
		
		
		Set<InventoryOrderDetail> listOrderDetailInDatabase = inventoryOrderFromDatabase.getOrderDetails();
		Set<InventoryOrderDetail> listOrderDetailNeedToBeDeleted = new HashSet<>();
		
		for(InventoryOrderDetail detailInDatabase : listOrderDetailInDatabase) {
			if(!newListOrderDetails.contains(detailInDatabase)) {
				listOrderDetailNeedToBeDeleted.add(detailInDatabase.copy());
			}
		}
		
		
		for(InventoryOrderDetail detailNeedToBeDeleted : listOrderDetailNeedToBeDeleted) {
			listOrderDetailInDatabase.remove(detailNeedToBeDeleted);
		}
		
		
		inventoryOrderDetailRepository.saveAll(newListOrderDetails);
		
	}
	
	
	public boolean checkUniqueForOrder(String orderId) {
		boolean isExist = inventoryOrderRepository.existsById(orderId);
		return isExist;
	}
	
	public InventoryOrder getOrderByOrderId(String orderId) throws InventoryOrderNotFoundException {
		Optional<InventoryOrder> inventoryOrder = inventoryOrderRepository.findById(orderId);
		if(!inventoryOrder.isPresent()) {
			throw new InventoryOrderNotFoundException("Order with id: " + orderId + " not exist in system");
		}
		return inventoryOrder.get();
	}
	
	
	public void deleteOrderById(String orderId) throws InventoryOrderNotFoundException {
		Optional<InventoryOrder> inventoryOrder = inventoryOrderRepository.findById(orderId);
		if(!inventoryOrder.isPresent()) {
			throw new InventoryOrderNotFoundException("Order with id: " + orderId + " not exist in system");
		}
		inventoryOrderRepository.delete(inventoryOrder.get());
	}
	
	
	public void deleteOrderById(String orderId,String userId) throws InventoryOrderNotFoundException, NotHavingPermissonToUpdateException, CannotDeleteThisItemException {
		Optional<InventoryOrder> inventoryOrder = inventoryOrderRepository.findById(orderId);
		if (!inventoryOrder.isPresent()) {
			throw new InventoryOrderNotFoundException("Order with id: " + orderId + " not exist in system");
		}
		InventoryOrder invenOrderFromDatabase = inventoryOrder.get();
		String createrUserFromDatabase = invenOrderFromDatabase.getInventoryUser().getUserId();
		if (!userId.equals(createrUserFromDatabase)) {
			throw new NotHavingPermissonToUpdateException(
					"You do not have permisson to update Order with ID: " + orderId);
		}

		Optional<ImportingForm> importingFormByOrderId = Optional
				.ofNullable(importingFormRepository.checkOrderHaveIPFOrNot(orderId));
		if (importingFormByOrderId.isPresent()) {
			throw new CannotDeleteThisItemException("Order with id: " + orderId + " already has an importing form");
		}

		inventoryOrderRepository.delete(inventoryOrder.get());
	}
	
	
	
	public InventoryOrder getOrderByOrderId(String orderId, String userId) throws InventoryOrderNotFoundException, NotHavingPermissonToUpdateException {
		Optional<InventoryOrder> inventoryOrder = inventoryOrderRepository.findById(orderId);
		if(!inventoryOrder.isPresent()) {
			throw new InventoryOrderNotFoundException("Order with id: " + orderId + " not exist in system");
		}
		String createrUserFromDatabase = inventoryOrder.get().getInventoryUser().getUserId();
		if(!userId.equals(createrUserFromDatabase)) {
			throw new NotHavingPermissonToUpdateException("You do not have permisson to update Order with ID: " + orderId);
		}
		return inventoryOrder.get();
	}
	
	public List<InventoryOrder> getAllOrderWithoutImporingForms(){
		return inventoryOrderRepository.getInventoryOrderWithoutImportingOrder();
	}
	
	public Page<InventoryOrder> search(String keyWord,int pageNum,int pageSize,String sortField,String sortDir){
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		Pageable pageable = PageRequest.of(pageNum -1, pageSize, sort);
		return inventoryOrderRepository.search(keyWord, pageable);
	}
	
	public Page<InventoryOrder> search(LocalDateTime from,LocalDateTime to,int pageNum,int pageSize,String sortField,String sortDir){
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		Pageable pageable = PageRequest.of(pageNum -1, pageSize, sort);
		return inventoryOrderRepository.search(from, to, pageable);
	}
	
	public Page<InventoryOrder> search(String keyWord,int pageNum,int pageSize, String sortField,String sortDir,LocalDateTime from,LocalDateTime to){
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		Pageable pageable = PageRequest.of(pageNum -1, pageSize, sort);
		return inventoryOrderRepository.search(keyWord, from, to, pageable);
	}
	
	
}
