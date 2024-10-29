package com.quanlykho.exporting_form;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.quanlykho.common.exporting_form.ExportingForm;

public interface ExportingFormRepository extends JpaRepository<ExportingForm,String> {
    
	
	@Query("SELECT ef FROM ExportingForm ef WHERE "
			+ " CONCAT(ef.exportingFormId,' ',ef.customerName,' ',ef.customerPhoneNumber,' ',"
			+ "ef.inventoryUser.firstName,' ',ef.inventoryUser.lastName,' ',ef.inventory.inventoryId) LIKE %?1% ")
	public Page<ExportingForm> search(String keyWord,Pageable pageable);
}
