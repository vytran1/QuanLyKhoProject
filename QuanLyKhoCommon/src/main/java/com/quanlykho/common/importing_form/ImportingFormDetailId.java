package com.quanlykho.common.importing_form;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ImportingFormDetailId implements Serializable {
	@Column(name = "importing_form_id")
    private String importingFormId;
	
	@Column(name = "product_id")
	private Integer productId;

	public ImportingFormDetailId(String importingFormId, Integer productId) {
		super();
		this.importingFormId = importingFormId;
		this.productId = productId;
	}

	public ImportingFormDetailId() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getImportingFormId() {
		return importingFormId;
	}

	public void setImportingFormId(String importingFormId) {
		this.importingFormId = importingFormId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(importingFormId, productId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ImportingFormDetailId other = (ImportingFormDetailId) obj;
		return Objects.equals(importingFormId, other.importingFormId) && Objects.equals(productId, other.productId);
	}

	@Override
	public String toString() {
		return "ImportingFormDetailId [importingFormId=" + importingFormId + ", productId=" + productId + "]";
	}
	
	
}
