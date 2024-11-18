package com.quanlykho.category;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quanlykho.brand.BrandRepository;
import com.quanlykho.common.Category;
import com.quanlykho.common.setting.State;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
   
	@Autowired
	private BrandRepository brandRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping("/{brandId}")
	public ResponseEntity<?> getAllCategoriesByBrand(@PathVariable("brandId") Integer brandId){
		List<Category> categories = brandRepository.findCategoriesByBrandId(brandId);
		if(categories.size() > 0) {
			List<CategoryDTO> categoriesDTO = new ArrayList<>();
			categories.forEach(category -> {
				CategoryDTO categoryDTO = convertEntityToDTO(category);
				categoriesDTO.add(categoryDTO);
			});
			return ResponseEntity.ok(categoriesDTO);
		}else {
			return ResponseEntity.noContent().build();
		}
	}
	
	
	public CategoryDTO convertEntityToDTO(Category category) {
		CategoryDTO categoryDTO = modelMapper.map(category,CategoryDTO.class);
		return categoryDTO;
	}
	
}
