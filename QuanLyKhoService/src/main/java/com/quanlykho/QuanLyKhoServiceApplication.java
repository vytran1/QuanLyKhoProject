package com.quanlykho;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.quanlykho.account.AccountDTO;
import com.quanlykho.common.Inventory;
import com.quanlykho.common.InventoryUser;
import com.quanlykho.common.Product;
import com.quanlykho.common.exception.CountryNotFoundException;
import com.quanlykho.common.exception.DistrictNotFoundException;
import com.quanlykho.common.exception.StateNotFoundException;
import com.quanlykho.common.exporting_form.ExportingForm;
import com.quanlykho.common.exporting_form.ExportingFormDetail;
import com.quanlykho.common.importing_form.ImportingForm;
import com.quanlykho.common.importing_form.ImportingFormDetail;
import com.quanlykho.common.inventory_order.InventoryOrder;
import com.quanlykho.common.inventory_order.InventoryOrderDetail;
import com.quanlykho.common.setting.Country;
import com.quanlykho.common.setting.District;
import com.quanlykho.common.setting.State;
import com.quanlykho.exporting_form.ExportingFormDTO;
import com.quanlykho.exporting_form.ExportingFormDetailDTO;
import com.quanlykho.importing_form.ImportingFormDTO;
import com.quanlykho.importing_form.ImportingFormDetailDTO;
import com.quanlykho.inventory.InventoryDTO;
import com.quanlykho.inventory.InventoryReportDTO;
import com.quanlykho.inventory_order.InventoryOrderDTO;
import com.quanlykho.inventory_order.InventoryOrderDetailDTO;
import com.quanlykho.inventory_order.InventoryOrderWithoutImportingFormDTO;
import com.quanlykho.product.ProductReportDTO;
import com.quanlykho.setting.country.CountryRepository;
import com.quanlykho.setting.district.DistrictRepository;
import com.quanlykho.setting.state.StateRepository;

@SpringBootApplication
public class QuanLyKhoServiceApplication {
    
	@Autowired
	private CountryRepository countryRepository;
	
	
	@Autowired
	private StateRepository stateRepository;
	
	@Autowired
	private DistrictRepository districtRepository;
	
	
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		
		modelMapper.typeMap(Inventory.class,InventoryDTO.class).addMappings(mapper -> {
			mapper.map(src -> src.getCountry().getId(),InventoryDTO::setCountryId);
			mapper.map(src -> src.getCountry().getName(),InventoryDTO::setCountryName);
			mapper.map(src -> src.getState().getId(),InventoryDTO::setStateId);
			mapper.map(src -> src.getState().getName(),InventoryDTO::setStateName);
            mapper.map(src -> src.getDistrict().getId(),InventoryDTO::setDistrictId);
            mapper.map(src -> src.getDistrict().getName(),InventoryDTO::setDistrictName);
		});
		
		
		modelMapper.typeMap(InventoryDTO.class,Inventory.class).addMappings(mapper -> {
			mapper.map(src -> {
				if(!ObjectUtils.isEmpty(src.getCountryId()) && !src.getCountryId().equals("")) {
					return countryRepository.findById(Integer.valueOf(src.getCountryId()))
							.orElseThrow(() -> new CountryNotFoundException("Not exist country with ID: " + src.getCountryId()));
				}
				return null;
			},Inventory::setCountry);
			mapper.map(src -> {
				if(!ObjectUtils.isEmpty(src.getStateId()) && !src.getStateId().equals("") ) {
				    return stateRepository.findById(Integer.valueOf(src.getStateId()))
				    		 .orElseThrow(() -> new StateNotFoundException("Not exist state with ID: " + src.getStateId()));
				}
				return null;
			},Inventory::setState);
			mapper.map(src -> {
				if(!ObjectUtils.isEmpty(src.getDistrictId()) && !src.getDistrictId().equals("")) {
					return districtRepository.findById(Integer.valueOf(src.getDistrictId()))
							 .orElseThrow(() -> new DistrictNotFoundException("Not exist district with ID: " + src.getDistrictId()));
				}
				return null;
			},Inventory::setDistrict);
		});
		
		modelMapper.typeMap(InventoryUser.class,AccountDTO.class).addMappings(mapper -> {
			mapper.map(src -> src.getInventoryRole().getName(),AccountDTO::setRole);
		});
		
		modelMapper.typeMap(InventoryOrder.class,InventoryOrderDTO.class).addMappings(mapper ->{
			mapper.map(src -> src.getInventory().getInventoryId(),InventoryOrderDTO::setInventoryId);
			mapper.map(src -> src.getInventoryUser().getFullName(),InventoryOrderDTO::setCreateUser);
			mapper.map(src -> src.getInventoryProvider().getProviderName(),InventoryOrderDTO::setProviderName);
			mapper.map(src -> src.getInventoryProvider().getProviderId(),InventoryOrderDTO::setProviderId);
		});
		
		modelMapper.typeMap(InventoryOrderDTO.class,InventoryOrder.class).addMappings(mapper -> {
			mapper.map(src -> src.getInventoryId(),(dest,v) -> {
				Inventory inventory = new Inventory();
				inventory.setInventoryId((String)v);
				dest.setInventory(inventory);
				//System.out.println("Setting inventoryId in InventoryOrder: " + (String)v);
			});
		});
		
		modelMapper.typeMap(InventoryOrderDetail.class,InventoryOrderDetailDTO.class).addMappings(mapper -> {
			mapper.map(src -> src.getInventoryOrder().getOrderId(),InventoryOrderDetailDTO::setOrderId);
			mapper.map(src -> src.getProduct().getId(),InventoryOrderDetailDTO::setProductId);
		});
		
		modelMapper.typeMap(InventoryOrderDetailDTO.class,InventoryOrderDetail.class).addMappings(mapper -> {
			mapper.map(src -> {
				Integer productId = src.getProductId();
				Product product = new Product();
				product.setId(productId);
				return product;
			},InventoryOrderDetail::setProduct);
		});
		
		//ImportingFormGuideMapper
		modelMapper.typeMap(ImportingForm.class,ImportingFormDTO.class).addMappings(mapper -> {
			mapper.map(src -> src.getInventoryOrder().getOrderId(),ImportingFormDTO::setInventoryOrder);
			mapper.map(src -> src.getInventoryUser().getFullName(),ImportingFormDTO::setInventoryUser);
			mapper.map(src -> src.getInventory().getInventoryId(),ImportingFormDTO::setInventory);
		});
		
		modelMapper.typeMap(ImportingFormDetail.class,ImportingFormDetailDTO.class).addMappings(mapper -> {
			mapper.map(src -> src.getProduct().getId(),ImportingFormDetailDTO::setProductId);
			mapper.map(src -> src.getImportingForm().getImportingFormId(),ImportingFormDetailDTO::setImportingFormId);
		});
		
		modelMapper.typeMap(InventoryOrder.class,InventoryOrderWithoutImportingFormDTO.class).addMappings(mapper -> {
			mapper.map(src -> src.getInventoryUser().getFullName(),InventoryOrderWithoutImportingFormDTO::setInventoryUser);
			mapper.map(src -> src.getInventory().getInventoryId(),InventoryOrderWithoutImportingFormDTO::setInventory);
			mapper.map(src -> src.getInventoryProvider().getProviderName(),InventoryOrderWithoutImportingFormDTO::setInventoryProvider);
		});
		
		//ExportingFormGuideMapper
		modelMapper.typeMap(ExportingForm.class,ExportingFormDTO.class).addMappings(mapper -> {
			mapper.map(src -> src.getInventoryUser().getFullName(),ExportingFormDTO::setInventoryUser);
			mapper.map(src -> src.getInventory().getInventoryId(),ExportingFormDTO::setInventory);
		});
		
		modelMapper.typeMap(ExportingFormDetail.class,ExportingFormDetailDTO.class).addMappings(mapper -> {
			mapper.map(src -> src.getExportingFormDetailId().getExportingFormId(),ExportingFormDetailDTO::setExportingFormId);
			mapper.map(src -> src.getExportingFormDetailId().getProductId(),ExportingFormDetailDTO::setProductId);
		});
		
		configureModelMapperForProduct(modelMapper);
		configureModelMapperForInventory(modelMapper);
		return modelMapper;
	}
	
	
	public static void main(String[] args) {
		SpringApplication.run(QuanLyKhoServiceApplication.class, args);
	}
    
	public void configureModelMapperForProduct(ModelMapper modelMapper) {
		var typeMap = modelMapper.typeMap(Product.class,ProductReportDTO.class);
		typeMap.addMappings(mapper -> {
			mapper.map(src -> src.getBrand().getName(),ProductReportDTO::setBrand);
			mapper.map(src -> src.getCategory().getName(),ProductReportDTO::setCategory);
		});
	}
	
	public void configureModelMapperForInventory(ModelMapper modelMapper) {
		var typeMap = modelMapper.typeMap(Inventory.class,InventoryReportDTO.class);
		typeMap.addMappings(mapper -> {
			mapper.map(src -> src.getCountry().getName(),InventoryReportDTO::setCountry);
			mapper.map(src -> src.getState().getName(),InventoryReportDTO::setState);
			mapper.map(src -> src.getDistrict().getName(),InventoryReportDTO::setDistrict);
		});
	}
	
}
