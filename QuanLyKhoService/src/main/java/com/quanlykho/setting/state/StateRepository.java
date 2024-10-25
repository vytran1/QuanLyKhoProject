package com.quanlykho.setting.state;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.quanlykho.common.setting.State;

public interface StateRepository extends JpaRepository<State,Integer> {
   
	
	@Query("Select st FROM State st WHERE st.country.id = ?1 ")
	public List<State> findByCountryId(Integer countryId);
}
