package com.quanlykho.setting.country;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quanlykho.common.setting.Country;

@RestController
@RequestMapping("/api/countries")
public class CountryController {
   
	@Autowired
	private CountryRepository countryRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping("")
	public ResponseEntity<?> getAllCountry(){
		List<Country> countries = countryRepository.findAll();
		if(countries.size() > 0) {
			List<CountryDTO> countriesDTO = new ArrayList<>();
			countries.forEach(country -> {
				CountryDTO countryDTO = convertEntityToDTO(country);
				countriesDTO.add(countryDTO);
			});
			return ResponseEntity.ok(countriesDTO);
		}else {
			return ResponseEntity.noContent().build();
		}
	}
	
	
	public CountryDTO convertEntityToDTO(Country country) {
		CountryDTO countryDTO = modelMapper.map(country,CountryDTO.class);
		return countryDTO;
	}
	
	
	
}
