package com.quanlykho.report;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quanlykho.common.Inventory;
import com.quanlykho.common.InventoryUser;
import com.quanlykho.common.Product;
import com.quanlykho.common.inventory_order.InventoryOrder;
import com.quanlykho.importing_form.ImportingFormRepository;
import com.quanlykho.inventory.InventoryRepository;
import com.quanlykho.inventory_order.InventoryOrderService;
import com.quanlykho.inventory_user.InventoryUserRepository;
import com.quanlykho.product.ProductRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ReportService {
   
	@Autowired
	private InventoryUserRepository inventoryUserRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	
	@Autowired
	private InventoryRepository inventoryRepository;
	
	@Autowired
	private InventoryOrderService inOrderService;
	
	@Autowired
	private ImportingFormRepository importingFormRepository;
	
	public List<InventoryUser> getAllInventoryUser(){
		return inventoryUserRepository.findAll();
	}
	
	public List<Product> getAllProducts(){
		return productRepository.findAll();
	}
	
	public List<Inventory> getAllInventories(){
		return inventoryRepository.findAll(); 
	}
	
	public List<Object[]> getEmployeeActivities(Date startDate,Date endDate,String userId){
		return inventoryUserRepository.spHoatDongNhanVien(startDate, endDate, userId);
	}
	
	public List<InventoryOrder> getOrderWithoutImportingForm(){
		return inOrderService.getAllOrderWithoutImporingForms();
	}
	
	public List<Object[]> getDetailOfImportingOrExportingAtATime(String type,Date startDate, Date endDate){
		return importingFormRepository.spChitietSoLuongTriGiaHangHoaNhapXuat(type, startDate, endDate);
	}
	
	public List<Object[]> getImportingAndExportingSummary(Date startDate, Date endDate){
		return importingFormRepository.spTongHopNhapXuat(startDate, endDate);
	}
}
