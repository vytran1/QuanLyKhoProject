package com.quanlykho.importing_form_detail;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.quanlykho.common.importing_form.ImportingFormDetail;
import com.quanlykho.common.importing_form.ImportingFormDetailId;

public interface ImportingFormDetailRepository extends JpaRepository<ImportingFormDetail,ImportingFormDetailId> {
    
	@Query("SELECT detail.product.id, detail.unitPrice FROM ImportingFormDetail detail WHERE detail.product.id = ?1  ")
	public List<Object[]> loadAllByProductId(Integer productId);
	
}
