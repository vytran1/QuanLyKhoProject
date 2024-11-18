package com.quanlykho.brand;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quanlykho.common.Brand;
import com.quanlykho.common.setting.Country;

@RestController
@RequestMapping("/api/brands")
public class BrandController {
   
	@Autowired
	private BrandRepository brandRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping("")
	public ResponseEntity<?> getAllBrands(){
		List<Brand> brands = brandRepository.findAll();
		if(brands.size() > 0) {
			List<BrandDTO> brandsDTO = new ArrayList<>();
			brands.forEach(brand -> {
				BrandDTO brandDTO = convertEntityToDTO(brand);
				brandsDTO.add(brandDTO);
			});
			return ResponseEntity.ok(brandsDTO);
		}else {
			return ResponseEntity.noContent().build();
		}
	}
	
	
	public BrandDTO convertEntityToDTO(Brand brand) {
		BrandDTO brandDTO = modelMapper.map(brand,BrandDTO.class);
		return brandDTO;
	}
	
	
	
}
