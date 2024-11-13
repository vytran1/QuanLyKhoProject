package com.quanlykho.inventory_order;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import com.quanlykho.Utility;
import com.quanlykho.common.Inventory;
import com.quanlykho.common.InventoryUser;
import com.quanlykho.common.Product;
import com.quanlykho.common.exception.CannotDeleteThisItemException;
import com.quanlykho.common.exception.InventoryOrderNotFoundException;
import com.quanlykho.common.exception.NotHavingPermissonToUpdateException;
import com.quanlykho.common.exception.OrderAlreadyExistException;
import com.quanlykho.common.inventory_order.InventoryOrder;
import com.quanlykho.common.inventory_order.InventoryOrderDetail;
import com.quanlykho.common.inventory_order.InventoryOrderDetailId;

@RestController
@RequestMapping("/api/inventory_order")
public class InventoryOrderController {
    
	@Autowired
	private InventoryOrderService inventoryOrderService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	//@Deprecated
	@GetMapping("/all")
	public ResponseEntity<?> getAll(){
		List<InventoryOrder> orders = inventoryOrderService.getAll();
		if(orders.size() > 0) {
			List<InventoryOrderDTO> orderDTOs = orders.stream().map(this::convertEntityToDTO).toList();
			return ResponseEntity.ok(orderDTOs);
		}else {
			return ResponseEntity.noContent().build();
		}
	}
	
	//Lấy danh sách toàn bộ các đơn hàng
	@GetMapping("")
	public ResponseEntity<?> getAllWithPage(
			@RequestParam("pageNum") int pageNum,
			@RequestParam("pageSize") int pageSize,
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir
			){
		
		Page<InventoryOrder> pages = inventoryOrderService.getByPage(pageNum, pageSize, sortField, sortDir);
		List<InventoryOrder> inventoryOrders = pages.getContent();
		if(inventoryOrders.size() > 0) {
			List<InventoryOrderDTO> orderDTOs = inventoryOrders.stream().map(this::convertEntityToDTO).toList();
			InventoryOrderListResult listResult = new InventoryOrderListResult();
			listResult.setOrderDTOs(orderDTOs);
			listResult.setPageNum(pageNum);
			listResult.setPageSize(pageSize);
			listResult.setSortField(sortField);
			listResult.setSortDir(sortDir);
			String reverseDir = sortDir.endsWith("asc") ? "desc" : "asc";
			listResult.setReverseDir(reverseDir);
			listResult.setTotalItems(pages.getTotalElements());
			listResult.setTotalPage(pages.getTotalPages());
			return ResponseEntity.ok(listResult);
		}else {
			return ResponseEntity.noContent().build();
		}
	}
	
	
	
	@GetMapping("/search")
	public ResponseEntity<?> search(
			@RequestParam(name = "keyWord",required = false) String keyWord,
			@RequestParam("pageNum") int pageNum,
			@RequestParam("pageSize") int pageSize,
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			@RequestParam(name = "from",required = false) LocalDateTime from,
			@RequestParam(name = "to",required = false) LocalDateTime to
			){
		
		Page<InventoryOrder> pages = null;
		if(keyWord != null && !keyWord.trim().isEmpty() && (from == null && to == null)) {
			pages = inventoryOrderService.search(keyWord, pageNum, pageSize, sortField, sortDir);
		}else if(from != null && to != null && (keyWord == null || keyWord.trim().isEmpty())) {
			pages = inventoryOrderService.search(from, to, pageNum, pageSize, sortField, sortDir);
		}else {
			pages = inventoryOrderService.search(keyWord, pageNum, pageSize, sortField, sortDir, from, to);
		}
		
//		Page<InventoryOrder> pages = inventoryOrderService.search(from,to, pageNum, pageSize, sortField, sortDir);
		if(pages.isEmpty()) {
			return ResponseEntity.noContent().build();
		}else {
			List<InventoryOrder> inventoryOrders = pages.getContent();
			List<InventoryOrderDTO> orderDTOs = inventoryOrders.stream().map(this::convertEntityToDTO).toList();
			InventoryOrderListResult listResult = new InventoryOrderListResult();
			listResult.setOrderDTOs(orderDTOs);
			listResult.setPageNum(pageNum);
			listResult.setPageSize(pageSize);
			listResult.setSortField(sortField);
			listResult.setSortDir(sortDir);
			String reverseDir = sortDir.endsWith("asc") ? "desc" : "asc";
			listResult.setReverseDir(reverseDir);
			listResult.setTotalItems(pages.getTotalElements());
			listResult.setTotalPage(pages.getTotalPages());
			listResult.setKeyWord(keyWord);
			if(from != null && to != null) {				
				listResult.setFrom(from);
				listResult.setTo(to);
			}
			return ResponseEntity.ok(listResult);
		}
	}
	
	
	//Lấy danh sách đơn hàng chưa có phiếu nhập
	@GetMapping("/withoutIPF")
	public ResponseEntity<?> getAllOrdersWithoutImportingForm(){
		List<InventoryOrder> results = inventoryOrderService.getAllOrderWithoutImporingForms();
		if(results.size() > 0) {
			List<InventoryOrderWithoutImportingFormDTO> orderWithoutIPF = results.stream().map(this::convertOrderEntityToOrderDTO).toList();
			return ResponseEntity.ok(orderWithoutIPF);
		}else {
			return ResponseEntity.noContent().build();
		}
	}
	
	
	//Lấy thông tin đơn hàng theo id để phục vụ cho chức năng cập nhật
	@GetMapping("/update/{orderId}")
	public ResponseEntity<?> getOrderInfoForUpdateFunction(@PathVariable("orderId") String orderId){
		String userId = Utility.getMaNhanVien();
		try {
			InventoryOrder inventoryOrder = inventoryOrderService.getOrderByOrderId(orderId, userId);
			InventoryOrderDTO inventoryOrderDTO = convertEntityToDTO(inventoryOrder);
			return ResponseEntity.ok(inventoryOrderDTO);
		} catch (InventoryOrderNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
		} catch (NotHavingPermissonToUpdateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity(e.getMessage(),HttpStatus.FORBIDDEN);
		}
	}
	
	
	@GetMapping("/isExist/{orderId}")
	public ResponseEntity<?> checkExistInventoryOrderId(@PathVariable("orderId") String orderId){
		boolean isExist = inventoryOrderService.checkUniqueForOrder(orderId);
		return ResponseEntity.ok(isExist);
	}
	
	//Tạo đơn hàng
	@PostMapping("")
	public ResponseEntity<?> createOrder(@RequestBody InventoryOrderDTO requestBody){
          InventoryOrder inventoryOrder = convertOrderDTOToOrderEntity(requestBody);
          
          inventoryOrder.setCreatedTime(new Date());
          
          //Set CreateUser
          String userId = Utility.getMaNhanVien();
          inventoryOrder.setInventoryUser(new InventoryUser(userId));
          
          setOrderDetailForOrder(inventoryOrder,requestBody.getOrderDetails());
          
          try {
			inventoryOrderService.createOrder(inventoryOrder);
			return ResponseEntity.ok().build();			
		} catch (OrderAlreadyExistException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
		} 
	}
	
	//Update đơn hàng
	@PostMapping("/update")
	public ResponseEntity<?> updateOrder(@RequestBody InventoryOrderDTO requestBody){
		Set<InventoryOrderDetail> newListOrderDetails = getNewListOrderDetail(requestBody.getOrderId(),requestBody.getOrderDetails());
		try {
			inventoryOrderService.updateOrder(requestBody, newListOrderDetails);
			return ResponseEntity.ok().build();
		} catch (CannotDeleteThisItemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
	
	@GetMapping("/detail/{orderId}")
	public ResponseEntity<?> getOrderByOrderId(@PathVariable("orderId") String orderId){
		try {
			String userId = Utility.getMaNhanVien();
			InventoryOrder inventoryOrder = inventoryOrderService.getOrderByOrderId(orderId);
//			InventoryOrderDTO inventoryOrderDTO = convertEntityToDTO(inventoryOrder);
			InventoryOrderDTOForDetailFunction orderDTOForDetailFunction = getOrderDetailFunction(inventoryOrder);
			return ResponseEntity.ok(orderDTOForDetailFunction);
		} catch (InventoryOrderNotFoundException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
		} 
	}
	
	
	@DeleteMapping("/{orderId}")
	public ResponseEntity<?> deleteOrderById(@PathVariable("orderId") String orderId){
		try {
			String currentLoginUser = Utility.getMaNhanVien();
			inventoryOrderService.deleteOrderById(orderId,currentLoginUser);
			return ResponseEntity.ok().build();
		} catch (InventoryOrderNotFoundException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
		} catch (NotHavingPermissonToUpdateException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity(e.getMessage(),HttpStatus.FORBIDDEN);
		} catch (CannotDeleteThisItemException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	
	public InventoryOrderDTO convertEntityToDTO(InventoryOrder order) {
		return modelMapper.map(order,InventoryOrderDTO.class);
	}
	
	public InventoryOrder convertDTOToEntity(InventoryOrderDTO orderDTO) {
		return modelMapper.map(orderDTO,InventoryOrder.class);
	}
	

	
	public InventoryOrderDetail convertDetailDTOToDetailEntity(InventoryOrder order,InventoryOrderDetailDTO inventoryOrderDetailDTO) {
		InventoryOrderDetail detailEntity = new InventoryOrderDetail();
    	detailEntity.getId().setProductId(inventoryOrderDetailDTO.getProductId());;
		detailEntity.setProduct(new Product(inventoryOrderDetailDTO.getProductId()));
    	detailEntity.setQuantity(inventoryOrderDetailDTO.getQuantity());
    	detailEntity.setUnitPrice(inventoryOrderDetailDTO.getUnitPrice());
    	return detailEntity;
	}
	
	
	
	public InventoryOrder convertOrderDTOToOrderEntity(InventoryOrderDTO orderDTO) {
		InventoryOrder inventoryOrder = new InventoryOrder();
		inventoryOrder.setOrderId(orderDTO.getOrderId());
		inventoryOrder.setSupplier(orderDTO.getSupplier());
		inventoryOrder.setCustomerName(orderDTO.getCustomerName());
		inventoryOrder.setCustomerPhoneNumber(orderDTO.getCustomerPhoneNumber());
		inventoryOrder.setInventory(new Inventory(orderDTO.getInventoryId()));
		return inventoryOrder;
	}
	
	public void setOrderDetailForOrder(InventoryOrder order,List<InventoryOrderDetailDTO> orderDetailDTOs) {
		for(InventoryOrderDetailDTO detailDTO : orderDetailDTOs) {
			InventoryOrderDetail detailEntity = convertDetailDTOToDetailEntity(order, detailDTO);
			order.addDetail(detailEntity);
		}
	}
	
	public Set<InventoryOrderDetail> getNewListOrderDetail(String orderID,List<InventoryOrderDetailDTO> orderDetailDTO){
		Set<InventoryOrderDetail> orderDetails = new HashSet<>();
		for(InventoryOrderDetailDTO detailDTO : orderDetailDTO) {
			InventoryOrderDetail inventoryOrderDetail = new InventoryOrderDetail();
			inventoryOrderDetail.setId(new InventoryOrderDetailId(orderID,detailDTO.getProductId()));
			inventoryOrderDetail.setInventoryOrder(new InventoryOrder(orderID));
			inventoryOrderDetail.setProduct(new Product(detailDTO.getProductId()));
			inventoryOrderDetail.setQuantity(detailDTO.getQuantity());
			inventoryOrderDetail.setUnitPrice(detailDTO.getUnitPrice());
			orderDetails.add(inventoryOrderDetail);
		}
		return orderDetails;
	}
	
	public InventoryOrderWithoutImportingFormDTO convertOrderEntityToOrderDTO(InventoryOrder entity) {
		return modelMapper.map(entity,InventoryOrderWithoutImportingFormDTO.class);
	}
	
	public InventoryOrderDTOForDetailFunction getOrderDetailFunction(InventoryOrder inventoryOrder) {
		InventoryOrderDTOForDetailFunction imDtoForDetailFunction = new InventoryOrderDTOForDetailFunction();
	    setInfoForOrderDetailFunction(imDtoForDetailFunction, inventoryOrder);
	    setDetailForDetailFunction(imDtoForDetailFunction, inventoryOrder);
		return imDtoForDetailFunction;
	}
	
	public void setInfoForOrderDetailFunction(InventoryOrderDTOForDetailFunction imDtoForDetailFunction,InventoryOrder inventoryOrder ) {
		String orderId = inventoryOrder.getOrderId();
		imDtoForDetailFunction.setOrderId(orderId);
		imDtoForDetailFunction.setCreatedTime(inventoryOrder.getCreatedTime());
		imDtoForDetailFunction.setInventoryUserId(inventoryOrder.getInventoryUser().getUserId());
		imDtoForDetailFunction.setInventoryUserFullname(inventoryOrder.getInventoryUser().getFullName());
		imDtoForDetailFunction.setCustomerName(inventoryOrder.getCustomerName());
		imDtoForDetailFunction.setCustomerPhoneNumber(inventoryOrder.getCustomerPhoneNumber());
		imDtoForDetailFunction.setInventoryId(inventoryOrder.getInventory().getInventoryId());
		imDtoForDetailFunction.setInventoryAddress(inventoryOrder.getInventory().getDetailAddress());
	}
	
	public void setDetailForDetailFunction(InventoryOrderDTOForDetailFunction imDtoForDetailFunction,InventoryOrder inventoryOrder) {
		Set<InventoryOrderDetail> orderDetails = inventoryOrder.getOrderDetails();
		String orderId = inventoryOrder.getOrderId();
		orderDetails.forEach(item -> {
			InventoryOrderDetailDTO inventoryOrderDetailDTO = new InventoryOrderDetailDTO();
			inventoryOrderDetailDTO.setOrderId(orderId);
			inventoryOrderDetailDTO.setProductId(item.getProduct().getId());
			inventoryOrderDetailDTO.setQuantity(item.getQuantity());
			inventoryOrderDetailDTO.setUnitPrice(item.getUnitPrice());
			float total = item.getQuantity() * item.getUnitPrice();
			imDtoForDetailFunction.getDetails().add(inventoryOrderDetailDTO);
			imDtoForDetailFunction.setTotal(imDtoForDetailFunction.getTotal() + total);
		});
	}
	
}
