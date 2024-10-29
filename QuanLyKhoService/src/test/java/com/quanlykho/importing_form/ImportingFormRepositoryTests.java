package com.quanlykho.importing_form;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.quanlykho.common.importing_form.ImportingForm;
import com.quanlykho.common.importing_form.ImportingFormDetail;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ImportingFormRepositoryTests {
   
	
	@Autowired
	private ImportingFormRepository importingFormRepository;
	
	@Test
	public void testLoadAll() {
		List<ImportingForm> results = importingFormRepository.findAll();
		assertThat(results.size()).isGreaterThan(0);
		results.forEach(item -> {
			System.out.println(item.toString());
			System.out.println("Chi tiết phiếu nhập");
			Set<ImportingFormDetail> details = item.getImportingDetails();
			if(details.size() > 0) {
				details.forEach(detail -> {
					System.out.println(detail.toString());
				});
			}else {
				System.out.println("Chưa có chi tiết phiếu nhập");
			}
		});
		
	}
	
	@Test
	public void testCheckOrderWhichHaveImportingForm() {
		String orderId = "ORDER001";
		ImportingForm importingForm = importingFormRepository.checkOrderHaveIPFOrNot(orderId);
		assertThat(importingForm).isNotNull();
		System.out.println("Importing Form ID: " + importingForm.getImportingFormId());
	}
	
	@Test
	public void testCheckOrderWhichhavenotAnyImportingForm() {
		String orderId = "ĐĐH11331313";
		ImportingForm importingForm = importingFormRepository.checkOrderHaveIPFOrNot(orderId);
		assertThat(importingForm).isNull();
		System.out.println("Order ID: " + orderId + "does not have importing form");
	}
	
	
	
}
