package com.quanlykho.setting.state;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quanlykho.common.setting.State;

@RestController
@RequestMapping("/api/states")
public class StateController {
   
	@Autowired
	private StateRepository stateRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping("/{countryId}")
	public ResponseEntity<?> getAllStateByCountry(@PathVariable("countryId") Integer countryId){
		List<State> states = stateRepository.findByCountryId(countryId);
		if(states.size() > 0) {
			List<StateDTO> stateDTOs = new ArrayList<>();
			states.forEach(state -> {
				StateDTO stateDTO = convertEntityToDTO(state);
				stateDTOs.add(stateDTO);
			});
			return ResponseEntity.ok(stateDTOs);
		}else {
			return ResponseEntity.noContent().build();
		}
	}
	
	
	public StateDTO convertEntityToDTO(State state) {
		StateDTO stateDTO = modelMapper.map(state,StateDTO.class);
		return stateDTO;
	}
	
}
