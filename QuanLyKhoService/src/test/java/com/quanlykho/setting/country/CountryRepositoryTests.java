package com.quanlykho.setting.country;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.quanlykho.common.setting.Country;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CountryRepositoryTests {
   
	@Autowired
	private CountryRepository countryRepository;
	
	@Test
	public void testLoadAll() {
		List<Country> countries = countryRepository.findAll();
		assertThat(countries.size()).isGreaterThan(0);
		countries.forEach(country -> {
			System.out.println(country.toString());
		});
	}
}
