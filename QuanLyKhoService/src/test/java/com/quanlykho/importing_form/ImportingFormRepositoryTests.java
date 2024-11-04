package com.quanlykho.importing_form;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
	
	@Test
	public void callSpChitietSoluongTriGiaHangHoaNhapXuat() {
		Date startDate = new GregorianCalendar(2024, Calendar.OCTOBER, 1, 23, 59, 59).getTime();
        Date endDate = new Date();
        String type = "NHAP";
        List<Object[]> results = importingFormRepository.spChitietSoLuongTriGiaHangHoaNhapXuat(type, startDate, endDate);
        assertThat(results.size()).isGreaterThan(0);
        for(Object [] object : results) {
        	String thangName = (String) object[0];
        	String tenVT = (String) object[1];
        	BigDecimal tongSoLuong = (BigDecimal) object[2];
        	Double triGia = (Double) object[3];
        	System.out.println("Tháng Năm: " + thangName + " tên vật tư: " + tenVT +  " tổng số lượng: " + tongSoLuong + " trị giá: " + triGia);
        }
	}
	
	@Test
	public void callSpTongHopNhapXuat() {
		Date startDate = new GregorianCalendar(2024, Calendar.OCTOBER, 1, 23, 59, 59).getTime();
        Date endDate = new Date();
        List<Object[]> results = importingFormRepository.spTongHopNhapXuat(startDate, endDate);
        assertThat(results.size()).isGreaterThan(0);
        for(Object[] object : results) {
        	Date NGAY = (Date) object[0];
        	BigDecimal NHAP = BigDecimal.valueOf((Double)object[1]);
        	Double TYLENHAP = (Double) object[2];
        	BigDecimal XUAT = BigDecimal.valueOf((Double) object[3]);
        	Double TYLEXUAT = (Double) object[4];
        	System.out.println("NGAY: " + NGAY + " NHAP: " + NHAP + " TYLENHAP: " + TYLENHAP + " XUAT: " + XUAT + " TYLEXUAT: " + TYLEXUAT);
        }
	}
}
