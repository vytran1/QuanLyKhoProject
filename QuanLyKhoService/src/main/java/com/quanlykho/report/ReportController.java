package com.quanlykho.report;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quanlykho.common.Inventory;
import com.quanlykho.common.InventoryUser;
import com.quanlykho.common.Product;
import com.quanlykho.common.exception.UserNotExistException;
import com.quanlykho.common.inventory_order.InventoryOrder;
import com.quanlykho.common.inventory_order.InventoryOrderDetail;
import com.quanlykho.inventory.InventoryReportDTO;
import com.quanlykho.inventory_user.InventoryUserService;
import com.quanlykho.product.ProductReportDTO;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@RestController
@RequestMapping("/api/report")
public class ReportController {
    
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private InventoryUserService inventoryUserService;
	
	@GetMapping("/employees")
	public ResponseEntity<?> getReportEmployees(){
		List<InventoryUser> inventoryUsers = reportService.getAllInventoryUser();
		if(inventoryUsers.size() > 0) {
			String fileReportPath = "C:\\Users\\haotr\\JaspersoftWorkspace\\firstProject\\ReportEmployees.jrxml";
			JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(inventoryUsers);
			Map<String,Object> parameters = new HashMap<>();
			parameters.put("ReportDate",new Date());
			parameters.put("tableData", jrBeanCollectionDataSource);
			try {
				JasperReport japReport = JasperCompileManager.compileReport(fileReportPath);
				JasperPrint jasperPrint = JasperFillManager.fillReport(japReport, parameters,new JREmptyDataSource());
				JasperExportManager.exportReportToPdfFile(jasperPrint, "D:\\JasperReportHolder\\employeeReport.pdf");
				System.out.print("Report Created");
				return ResponseEntity.ok().build();
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Error while create report");
				return ResponseEntity.internalServerError().build();
			}
		}else {			
			return ResponseEntity.noContent().build();
		}
	}
	
	
	@GetMapping("/products")
	public ResponseEntity<?> getReportProducts(){
		List<Product> products = reportService.getAllProducts();
		if(products.size() > 0) {
			List<ProductReportDTO> productDTOs = products.stream().map(this::convertEntityToDTO).toList();
			String fileReportPath = "C:\\Users\\haotr\\JaspersoftWorkspace\\firstProject\\ReportProducts.jrxml";
			JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(productDTOs);
			Map<String,Object> parameters = new HashMap<>();
			parameters.put("tableData", jrBeanCollectionDataSource);
			try {
				JasperReport japReport = JasperCompileManager.compileReport(fileReportPath);
				JasperPrint jasperPrint = JasperFillManager.fillReport(japReport, parameters,new JREmptyDataSource());
				JasperExportManager.exportReportToPdfFile(jasperPrint, "D:\\JasperReportHolder\\productReport.pdf");
				System.out.print("Report Created");
				return ResponseEntity.ok().build();
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("Error while creating report");
				return ResponseEntity.internalServerError().build();
			}
		}else {
			return ResponseEntity.noContent().build();
		}
	}
	
	@GetMapping("/inventories")
	public ResponseEntity<?> getReportInventory(){
		List<Inventory> inventories = reportService.getAllInventories();
		if(inventories.size() > 0) {
			List<InventoryReportDTO> inventoryDTOs = inventories.stream().map(this::convertEntityToDTOWithInventory).toList();
			String fileReportPath = "C:\\Users\\haotr\\JaspersoftWorkspace\\firstProject\\ReportInventory.jrxml";
			JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(inventoryDTOs);
			Map<String,Object> parameters = new HashMap<>();
			parameters.put("tableData",jrBeanCollectionDataSource);
			try {
				JasperReport japReport = JasperCompileManager.compileReport(fileReportPath);
				JasperPrint jasperPrint = JasperFillManager.fillReport(japReport, parameters,new JREmptyDataSource());
				JasperExportManager.exportReportToPdfFile(jasperPrint, "D:\\JasperReportHolder\\inventoryReport.pdf");
				System.out.print("Report Created");
				return ResponseEntity.ok().build();
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("Error while creating report");
				return ResponseEntity.internalServerError().build();
			}
		}else {
			return ResponseEntity.noContent().build();
		}
	}
	
	@PostMapping("/employeeActivities")
	public ResponseEntity<?> getReportHoatDongNhanVien(@RequestBody EmployeeActivityRequest employeeActivityRequest) throws UserNotExistException{
		Date startDate = employeeActivityRequest.getStartDate();
		Date endDate = employeeActivityRequest.getEndDate();
		if(startDate != null && endDate != null) {
			if(startDate.before(endDate)) {
				
				String userId = employeeActivityRequest.getUserId();
				InventoryUser inventoryUser = inventoryUserService.getByUserId(userId);
				List<Object[]> results = reportService.getEmployeeActivities(startDate,endDate,userId);
				if(results.size() > 0) {
					List<EmployeeActivityDTO> activityDTOS = results.stream().map(this::convertEntityToDTOWithEmployeeActivitySP).toList();
					String fileReportPath = "C:\\Users\\haotr\\JaspersoftWorkspace\\firstProject\\ReportEmployeeActivity.jrxml";
					JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(activityDTOS);
					Map<String,Object> parameters = new HashMap<>();
					parameters.put("tableData",jrBeanCollectionDataSource);
					parameters.put("userName",inventoryUser.getFullName());
					parameters.put("fromDate",startDate);
					parameters.put("endDat",endDate);
					try {
						JasperReport japReport = JasperCompileManager.compileReport(fileReportPath);
						JasperPrint jasperPrint = JasperFillManager.fillReport(japReport, parameters,new JREmptyDataSource());
						JasperExportManager.exportReportToPdfFile(jasperPrint, "D:\\JasperReportHolder\\employeeActivityReport.pdf");
						System.out.print("Report Created");
						return ResponseEntity.ok(activityDTOS);
					}catch(Exception e) {
						e.printStackTrace();
						System.out.println("Error while creating report");
						return ResponseEntity.internalServerError().build();
					}
				}
				
				else {
					return ResponseEntity.noContent().build();
				}
			}else {
				return new ResponseEntity("Start Date must be before End Date",HttpStatus.BAD_REQUEST);
			}
		}else {
			return new ResponseEntity("Invalid start date or end date",HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/orderNoImport")
	public ResponseEntity<?> getReportOrderWithoutImportingForm(){
		List<InventoryOrder> inventoryOrders = reportService.getOrderWithoutImportingForm();
		if(inventoryOrders.size() > 0) {
		    List<OrderWithourImportingFormReportDTO> results = inventoryOrders.stream().map(this::convertEntityToDTOWithOrderNoImporting).toList();
			String fileReportPath = "C:\\Users\\haotr\\JaspersoftWorkspace\\firstProject\\ReportOrderWithoutImportingForm.jrxml";
			JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(results);
			Map<String,Object> parameters = new HashMap<>();
			parameters.put("tableData",jrBeanCollectionDataSource);
			try {
				JasperReport japReport = JasperCompileManager.compileReport(fileReportPath);
				JasperPrint jasperPrint = JasperFillManager.fillReport(japReport, parameters,new JREmptyDataSource());
				JasperExportManager.exportReportToPdfFile(jasperPrint, "D:\\JasperReportHolder\\orderWithoutImportingFormReport.pdf");
				System.out.print("Report Created");
				return ResponseEntity.ok(results);
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("Error while creating report");
				return ResponseEntity.internalServerError().build();
			}
		}else {
			return ResponseEntity.noContent().build();
		}
	}
	
	@PostMapping("/detailIPAndEP")
	public ResponseEntity<?> getReportDetailOfImportingOrExportingAtATime(@RequestBody DetailImportingOrExportingDTO request){
		String type = request.getType();
		Date startDate = request.getStartDate();
		Date endDate = request.getEndDate();
		if(startDate != null && endDate != null) {
			if(startDate.before(endDate)) {
				List<Object[]> results = reportService.getDetailOfImportingOrExportingAtATime(type,startDate,endDate);
				if(results.size() >0) {	
					List<DetailImportingOrExportingResult> resultDTOs =results.stream().map(this::convertEntityToDTOWithDetailIPOrEP).toList();
					String fileReportPath = "C:\\Users\\haotr\\JaspersoftWorkspace\\firstProject\\ReportDetailImportingOrExporting.jrxml";
					JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(resultDTOs);
					Map<String,Object> parameters = new HashMap<>();
					parameters.put("tableData",jrBeanCollectionDataSource);
					parameters.put("type",type);
					parameters.put("fromDate",startDate);
					parameters.put("endDate",endDate);
					try {
						JasperReport japReport = JasperCompileManager.compileReport(fileReportPath);
						JasperPrint jasperPrint = JasperFillManager.fillReport(japReport, parameters,new JREmptyDataSource());
						JasperExportManager.exportReportToPdfFile(jasperPrint, "D:\\JasperReportHolder\\DetailImportingOrExportingReport.pdf");
						System.out.print("Report Created");
						return ResponseEntity.ok(resultDTOs);
					}catch(Exception e) {
						e.printStackTrace();
						System.out.println("Error while creating report");
						return ResponseEntity.internalServerError().build();
					}
				}else {
					return ResponseEntity.noContent().build();
				}
			}else {
				return new ResponseEntity("Start date must be before end date",HttpStatus.BAD_REQUEST);
			}
		}else {
			return new ResponseEntity("Invalid start date and end date",HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/summary")
	public ResponseEntity<?> getReportImportingAndExportingSummary(@RequestBody SummaryReportRequest request){
		Date startDate = request.getStartDate();
		Date endDate = request.getEndDate();
		if(startDate != null && endDate != null) {
			if(startDate.before(endDate)) {	
				List<Object[]> resultFromDatabase = reportService.getImportingAndExportingSummary(startDate, endDate);
				if(resultFromDatabase.size() > 0) {
					List<SummaryReportDTO> resultDTOs = resultFromDatabase.stream().map(this::convertEntityToDTOWithSummeryReport).toList();
					String fileReportPath = "C:\\Users\\haotr\\JaspersoftWorkspace\\firstProject\\ReportImportingExportingSummary.jrxml";
					JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(resultDTOs);
					Map<String,Object> parameters = new HashMap<>();
					parameters.put("tableData",jrBeanCollectionDataSource);
					parameters.put("startDate",startDate);
					parameters.put("endDate",endDate);
					try {
						JasperReport japReport = JasperCompileManager.compileReport(fileReportPath);
						JasperPrint jasperPrint = JasperFillManager.fillReport(japReport, parameters,new JREmptyDataSource());
						JasperExportManager.exportReportToPdfFile(jasperPrint, "D:\\JasperReportHolder\\ImportingAndExportingSummaryReport.pdf");
						System.out.print("Report Created");
						return ResponseEntity.ok(resultDTOs);
					}catch(Exception e) {
						e.printStackTrace();
						System.out.println("Error while creating report");
						return ResponseEntity.internalServerError().build();
					}
				}else {
					return ResponseEntity.noContent().build();
				}
			}else {
				return new ResponseEntity("Start date must be before end date",HttpStatus.BAD_REQUEST);
			}
		}else {
            return new ResponseEntity("Invalid start date or end date",HttpStatus.BAD_REQUEST);			
		}
	}
	
	
	public ProductReportDTO convertEntityToDTO(Product product) {
		return modelMapper.map(product,ProductReportDTO.class);
	}
	
	public InventoryReportDTO convertEntityToDTOWithInventory(Inventory inventory) {
		return modelMapper.map(inventory,InventoryReportDTO.class);
	}
	
	
	public EmployeeActivityDTO convertEntityToDTOWithEmployeeActivitySP(Object[] object) {
		EmployeeActivityDTO activityObject = new EmployeeActivityDTO();
		Date ngay = (Date) object[0];
		activityObject.setCreateDate(ngay);
		String maPhieu = (String) object[1];
		activityObject.setFormId(maPhieu);
		String loadiPhieu = (String) object[2];
		activityObject.setTypeForm(loadiPhieu);
		String hotenKH = (String) object[3];
		activityObject.setCustomerName(hotenKH);
		String tenVT = (String) object[4];
		activityObject.setProductName(tenVT);
		Integer soLuong = (Integer) object[5];
		activityObject.setQuantity(soLuong);
		Float donGia = (Float) object[6];
		activityObject.setUnitPrice(donGia);
		String thang = (String) object[7];
		activityObject.setMonth(thang);
		BigDecimal triGia = BigDecimal.valueOf(soLuong).multiply(BigDecimal.valueOf(donGia));
		activityObject.setTriGia(triGia);
		return activityObject;
	}
	
	public OrderWithourImportingFormReportDTO convertEntityToDTOWithOrderNoImporting(InventoryOrder order) {
		OrderWithourImportingFormReportDTO orderWithourImportingFormReportDTO = new OrderWithourImportingFormReportDTO();
		String maSoĐonHang = order.getOrderId();
		orderWithourImportingFormReportDTO.setOrderId(maSoĐonHang);
		Date ngaLap = order.getCreatedTime();
		orderWithourImportingFormReportDTO.setCreatedDate(ngaLap);
		String supplier = order.getSupplier();
		orderWithourImportingFormReportDTO.setSupplier(supplier);
		String userName = order.getInventoryUser().getFullName();
		orderWithourImportingFormReportDTO.setInventoryUser(userName);
		String inventoryProvider = order.getInventoryProvider().getProviderName();
		orderWithourImportingFormReportDTO.setInventoryProvider(inventoryProvider);
		
		Set<InventoryOrderDetail> details = order.getOrderDetails();
		Integer totalQuantity = 0;
		float totalValue = 0;
		for(InventoryOrderDetail detail : details) {
			totalQuantity = totalQuantity + detail.getQuantity();
			totalValue = totalValue + (detail.getQuantity() * detail.getUnitPrice());
		}
		orderWithourImportingFormReportDTO.setTotalQuantity(totalQuantity);
		orderWithourImportingFormReportDTO.setTotalValue(totalValue);
		return orderWithourImportingFormReportDTO;
	}
	
	
	public DetailImportingOrExportingResult convertEntityToDTOWithDetailIPOrEP(Object[] entity) {
		DetailImportingOrExportingResult result = new DetailImportingOrExportingResult();
		String thangNam = (String) entity[0];
		result.setMonthYear(thangNam);
		String tenVT = (String) entity[1];
		result.setProductName(tenVT);
		BigDecimal tongSoLuong = (BigDecimal) entity[2];
		result.setTotalQuantity(tongSoLuong);
		Double tongTriGia = (Double) entity[3];
		result.setTotalValue(tongTriGia);
		return result;
	}
	
	public SummaryReportDTO convertEntityToDTOWithSummeryReport(Object[] entity) {
		SummaryReportDTO dto = new SummaryReportDTO();
		Date ngay = (Date) entity[0];
		dto.setNgay(ngay);
		BigDecimal nhap = BigDecimal.valueOf((Double)entity[1]);
		dto.setNhap(nhap);
		Double tylenhap = (Double) entity[2];
		dto.setTylenhap(tylenhap);
		BigDecimal xuat = BigDecimal.valueOf((Double)entity[3]);
		dto.setXuat(xuat);
		Double tylexuat = (Double) entity[4];
		dto.setTylexuat(tylexuat);
		return dto;
	}
	
}
