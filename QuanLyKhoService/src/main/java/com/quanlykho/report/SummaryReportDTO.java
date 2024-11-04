package com.quanlykho.report;

import java.math.BigDecimal;
import java.util.Date;

public class SummaryReportDTO {
	private Date ngay;
	private BigDecimal nhap;
	private Double tylenhap;
	private BigDecimal xuat;
	private Double tylexuat;

	public SummaryReportDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Date getNgay() {
		return ngay;
	}

	public void setNgay(Date ngay) {
		this.ngay = ngay;
	}

	public BigDecimal getNhap() {
		return nhap;
	}

	public void setNhap(BigDecimal nhap) {
		this.nhap = nhap;
	}

	public Double getTylenhap() {
		return tylenhap;
	}

	public void setTylenhap(Double tylenhap) {
		this.tylenhap = tylenhap;
	}

	public BigDecimal getXuat() {
		return xuat;
	}

	public void setXuat(BigDecimal xuat) {
		this.xuat = xuat;
	}

	public Double getTylexuat() {
		return tylexuat;
	}

	public void setTylexuat(Double tylexuat) {
		this.tylexuat = tylexuat;
	}
   
}
