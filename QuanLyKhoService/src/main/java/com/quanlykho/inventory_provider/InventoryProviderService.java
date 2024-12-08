package com.quanlykho.inventory_provider;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.quanlykho.common.InventoryProvider;
import com.quanlykho.common.exception.ProviderEmailAlreadyExistException;
import com.quanlykho.common.exception.ProviderNotFoundException;

@Service
public class InventoryProviderService {
   
	@Autowired
	private InventoryProviderRepository inventoryProviderRepository;
	
	
	public List<InventoryProvider> listAll(){
		return inventoryProviderRepository.findAll();
	}
	
	
	public Page<InventoryProvider> listByPage(int pageNum, int pageSize, String sortField, String sortDir){
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
		return inventoryProviderRepository.findAll(pageable);
	}
	
	
	public Page<InventoryProvider> search(int pageNum, int pageSize, String sortField, String sortDir,String keyWord){
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
		return inventoryProviderRepository.search(keyWord, pageable);
	}
	
	
	public void createProvider(InventoryProvider provider) throws ProviderEmailAlreadyExistException {
		boolean isUnique = checkUniqueEmail(null,provider.getProviderEmail());
		if(!isUnique) {
			throw new ProviderEmailAlreadyExistException("Provider email: " + provider.getProviderEmail() + " has already existed in system");
		}
		inventoryProviderRepository.save(provider);
	}
	
	public void updateProvider(Integer id, InventoryProvider providerInRequest) throws ProviderNotFoundException, ProviderEmailAlreadyExistException {
	    Optional<InventoryProvider> providerOTP = inventoryProviderRepository.findById(id);
		if(!providerOTP.isPresent()) {
			throw new ProviderNotFoundException("Provider with id: " + id + " does not exit in system");
		}
		
		boolean isUnique = checkUniqueEmail(id,providerInRequest.getProviderEmail());
		if(!isUnique) {
			throw new ProviderEmailAlreadyExistException("Provider email: " + providerInRequest.getProviderEmail() + " has already existed in system");
		}
		
		InventoryProvider providerInDatabase = providerOTP.get();
		providerInDatabase.setEnabled(providerInRequest.isEnabled());
		providerInDatabase.setProviderEmail(providerInRequest.getProviderEmail());
		providerInDatabase.setProviderContactNumber(providerInRequest.getProviderContactNumber());
		providerInDatabase.setProviderAddress(providerInRequest.getProviderAddress());
		providerInDatabase.setProviderName(providerInRequest.getProviderName());
		inventoryProviderRepository.save(providerInDatabase);
	}
	
	public void deleteProvider(Integer providerId) throws ProviderNotFoundException {
		Optional<InventoryProvider> providerOTP = inventoryProviderRepository.findById(providerId);
		if(!providerOTP.isPresent()) {
			throw new ProviderNotFoundException("Provider with id: " + providerId + " does not exit in system");
		}		
		inventoryProviderRepository.delete(providerOTP.get());
	}
	
	public boolean checkUniqueEmail(Integer id, String email) {
	    boolean isCreatingNew = (id == null || id == 0);
	    Optional<InventoryProvider> existingProvider = inventoryProviderRepository.findByProviderEmail(email);

	    if (existingProvider.isPresent()) {
	        InventoryProvider provider = existingProvider.get();
	        if (isCreatingNew || !provider.getProviderId().equals(id)) {
	            return false;
	        }
	    }
	    return true;
	}
    
	public InventoryProvider getById(Integer providerId) throws ProviderNotFoundException {
		Optional<InventoryProvider> providerOTP = inventoryProviderRepository.findById(providerId);
		if(!providerOTP.isPresent()) {
			throw new ProviderNotFoundException("Provider with id: " + providerId + " does not exit in system");
		}
		return providerOTP.get();
	}
}
