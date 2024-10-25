package com.quanlykho.setting.district;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quanlykho.common.setting.District;

@RestController
@RequestMapping("/api/districts")
public class DistrictController {
   
	@Autowired
	private DistrictRepository districtRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping("/{stateId}")
	public ResponseEntity<?> getAllDistrictByState(@PathVariable("stateId") Integer stateId){
		List<District> districts = districtRepository.findByStateId(stateId);
		if(districts.size() > 0) {
			List<DistrictDTO> districtDTOs = districts.stream().map(this::convertEntityToDTO).toList();
			return ResponseEntity.ok(districtDTOs);
		}else {
			return ResponseEntity.noContent().build();
		}
	}
	
	
	
	public DistrictDTO convertEntityToDTO(District district) {
		return modelMapper.map(district,DistrictDTO.class);
	}
}
