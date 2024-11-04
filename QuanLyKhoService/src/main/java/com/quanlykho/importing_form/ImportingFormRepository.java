package com.quanlykho.importing_form;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import com.quanlykho.common.importing_form.ImportingForm;

public interface ImportingFormRepository extends JpaRepository<ImportingForm,String> {
    
	
	@Query("SELECT ip FROM ImportingForm ip WHERE ip.inventoryOrder.orderId = ?1 ")
	public ImportingForm checkOrderHaveIPFOrNot(String orderId);
	
	
	@Query("SELECT ip FROM ImportingForm ip WHERE "
			+ " CONCAT(ip.importingFormId,' ',ip.inventoryOrder.customerName,' ',ip.inventoryOrder.customerPhoneNumber,' ',"
			+ "ip.inventoryUser.firstName,' ',ip.inventoryUser.lastName,' ',"
			+ "ip.inventory.inventoryId) LIKE %?1%")
	public Page<ImportingForm> search(String keyWord,Pageable pageable);
	
	@Procedure(name = "spChitietSoLuongTriGiaHangHoaNhapXuat")
	public List<Object[]> spChitietSoLuongTriGiaHangHoaNhapXuat(@Param("type") String type,@Param("startDate") Date startDate,@Param("endDate") Date endDate);
	
	@Procedure(name = "spTongHopNhapXuat")
	public List<Object[]> spTongHopNhapXuat(@Param("startDate") Date startDate,@Param("endDate") Date endDate);
	
}
