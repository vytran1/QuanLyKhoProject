package com.quanlykho.report;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;


public class ReportRepositoryImpl implements ReportRepository {
     
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Object[]> spHoatDongNhanVien(Date startDate, Date endDate, String userId) {
		// TODO Auto-generated method stub
		StoredProcedureQuery query = entityManager
				.createStoredProcedureQuery("spHoatDongNhanVien")
				.registerStoredProcedureParameter("start_date",Date.class,ParameterMode.IN)
				.registerStoredProcedureParameter("end_date",Date.class,ParameterMode.IN)
				.registerStoredProcedureParameter("user_id",String.class,ParameterMode.IN)
				.setParameter("start_date",startDate)
				.setParameter("end_date",endDate)
				.setParameter("user_id", userId);
		
		return query.getResultList();
	}
  
}
