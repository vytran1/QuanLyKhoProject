package com.quanlykho.setting.district;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.quanlykho.common.setting.District;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class DistrictRepositoryTests  {
   
	@Autowired
	private DistrictRepository districtRepository;
	
	
	@Test
	public void testLoadAllByStateId() {
		//Ho Chi Minh
		Integer stateId = 1;
		List<District> districts = districtRepository.findByStateId(stateId);
		assertThat(districts.size()).isGreaterThan(0);
		districts.forEach(district -> {
			System.out.println(district.toString());
		});
		
	}
}
