package com.quanlykho.setting.district;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.quanlykho.common.setting.District;

public interface DistrictRepository extends JpaRepository<District,Integer> {
    
	@Query("SELECT ds FROM District ds WHERE ds.state.id = ?1")
	public List<District> findByStateId(Integer stateId);
}
