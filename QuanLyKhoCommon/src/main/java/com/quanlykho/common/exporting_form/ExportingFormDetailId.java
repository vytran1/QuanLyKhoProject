package com.quanlykho.common.exporting_form;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ExportingFormDetailId implements Serializable {
	
	@Column(name = "exporting_form_id")
    private String exportingFormId;
	
	@Column(name = "product_id")
	private Integer productId;
	
	public ExportingFormDetailId() {
		
	}

	public ExportingFormDetailId(String exportingFormId, Integer productId) {
		super();
		this.exportingFormId = exportingFormId;
		this.productId = productId;
	}

	public String getExportingFormId() {
		return exportingFormId;
	}

	public void setExportingFormId(String exportingFormId) {
		this.exportingFormId = exportingFormId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(exportingFormId, productId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExportingFormDetailId other = (ExportingFormDetailId) obj;
		return Objects.equals(exportingFormId, other.exportingFormId) && Objects.equals(productId, other.productId);
	}

	@Override
	public String toString() {
		return "ExportingFormDetailId [exportingFormId=" + exportingFormId + ", productId=" + productId + "]";
	}
	
	
}
