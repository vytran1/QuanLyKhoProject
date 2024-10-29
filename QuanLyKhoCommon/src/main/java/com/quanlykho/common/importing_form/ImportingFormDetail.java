package com.quanlykho.common.importing_form;

import java.util.Objects;
import java.util.Set;

import com.quanlykho.common.Product;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "importing_form_detail")
public class ImportingFormDetail {
   
	@EmbeddedId
	private ImportingFormDetailId importingFormDetailId;
	
	@Column(name = "quantity")
	private Integer quantity;
	
	@Column(name = "unit_price")
	private float unitPrice;
	
	@ManyToOne
	@MapsId("importingFormId")
	@JoinColumn(name = "importing_form_id")
	private ImportingForm importingForm;
	
	@ManyToOne
	@MapsId("productId")
	@JoinColumn(name = "product_id")
	private Product product;
 
	
	public ImportingFormDetail() {
		
	}

	public ImportingFormDetail(ImportingFormDetailId importingFormDetailId, Integer quantity, float unitPrice,
			ImportingForm importingForm, Product product) {
		super();
		this.importingFormDetailId = importingFormDetailId;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.importingForm = importingForm;
		this.product = product;
	}

	public ImportingFormDetailId getImportingFormDetailId() {
		return importingFormDetailId;
	}

	public void setImportingFormDetailId(ImportingFormDetailId importingFormDetailId) {
		this.importingFormDetailId = importingFormDetailId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public float getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
	}

	public ImportingForm getImportingForm() {
		return importingForm;
	}

	public void setImportingForm(ImportingForm importingForm) {
		this.importingForm = importingForm;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public int hashCode() {
		return Objects.hash(importingFormDetailId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ImportingFormDetail other = (ImportingFormDetail) obj;
		return Objects.equals(importingFormDetailId, other.importingFormDetailId);
	}

	@Override
	public String toString() {
		return "ImportingFormDetail [importingFormDetailId=" + importingFormDetailId + ", quantity=" + quantity
				+ ", unitPrice=" + unitPrice + ", importingForm=" + importingForm.getImportingFormId() + ", product=" + product.getName() + "]";
	}
	
	
}
