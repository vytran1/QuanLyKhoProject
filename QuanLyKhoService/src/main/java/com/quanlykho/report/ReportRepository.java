package com.quanlykho.report;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;



public interface ReportRepository {
  
	
	public List<Object[]> spHoatDongNhanVien(Date startDate,Date endDate,String userId);
}
