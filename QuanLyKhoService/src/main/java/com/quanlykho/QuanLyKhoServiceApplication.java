package com.quanlykho;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.quanlykho.account.AccountDTO;
import com.quanlykho.common.Inventory;
import com.quanlykho.common.InventoryUser;
import com.quanlykho.common.exception.CountryNotFoundException;
import com.quanlykho.common.exception.DistrictNotFoundException;
import com.quanlykho.common.exception.StateNotFoundException;
import com.quanlykho.common.setting.Country;
import com.quanlykho.common.setting.District;
import com.quanlykho.common.setting.State;
import com.quanlykho.inventory.InventoryDTO;
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
		
		return modelMapper;
	}
	
	
	public static void main(String[] args) {
		SpringApplication.run(QuanLyKhoServiceApplication.class, args);
	}

}
