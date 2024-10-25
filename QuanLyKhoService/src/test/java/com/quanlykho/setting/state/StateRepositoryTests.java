package com.quanlykho.setting.state;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.quanlykho.common.setting.State;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class StateRepositoryTests {
   
	
	@Autowired
	private StateRepository stateRepository;
	
	@Test
	public void testLoadAll() {
		//VietName
		Integer countryId = 1;
		
		List<State> statesByCountryId = stateRepository.findByCountryId(countryId);
		
		assertThat(statesByCountryId.size()).isGreaterThan(0);
		
		statesByCountryId.forEach(state -> {
			System.out.println(state.toString());
		});
		
	}
}
