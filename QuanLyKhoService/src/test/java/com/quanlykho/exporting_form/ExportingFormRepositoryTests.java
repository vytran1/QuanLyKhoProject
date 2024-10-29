package com.quanlykho.exporting_form;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.quanlykho.common.exporting_form.ExportingForm;
import com.quanlykho.common.exporting_form.ExportingFormDetail;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ExportingFormRepositoryTests {
     
	@Autowired
	private ExportingFormRepository exportingFormRepository;
	
	@Test
	public void testLoadAll() {
		List<ExportingForm> results = exportingFormRepository.findAll();
		assertThat(results.size()).isGreaterThan(0);
		results.forEach(item -> {
			System.out.println(item.toString());
			System.out.println("Exporting Form Detail");
			Set<ExportingFormDetail> exFormDetails = item.getExportingDetails();
			exFormDetails.forEach(detailItem -> {
				System.out.println(detailItem.toString());
			});
		});
	}
	
	
}
