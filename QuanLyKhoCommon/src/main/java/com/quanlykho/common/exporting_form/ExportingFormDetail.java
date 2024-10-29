package com.quanlykho.common.exporting_form;

import java.util.Objects;

import com.quanlykho.common.Product;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "exporting_form_detail")
public class ExportingFormDetail {
    
	@EmbeddedId
	private ExportingFormDetailId exportingFormDetailId = new ExportingFormDetailId();
	
	@Column(name = "quantity")
	private Integer quantity;
	
	@Column(name = "unit_price")
	private float unitPrice;
	
	@ManyToOne()
	@MapsId("exportingFormId")
	@JoinColumn(name = "exporting_form_id")
	private ExportingForm exportingForm;
	
	@ManyToOne()
	@MapsId("productId")
	@JoinColumn(name = "product_id")
	private Product product;

	public ExportingFormDetail() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExportingFormDetailId getExportingFormDetailId() {
		return exportingFormDetailId;
	}

	public void setExportingFormDetailId(ExportingFormDetailId exportingFormDetailId) {
		this.exportingFormDetailId = exportingFormDetailId;
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

	public ExportingForm getExportingForm() {
		return exportingForm;
	}

	public void setExportingForm(ExportingForm exportingForm) {
		this.exportingForm = exportingForm;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public int hashCode() {
		return Objects.hash(exportingFormDetailId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExportingFormDetail other = (ExportingFormDetail) obj;
		return Objects.equals(exportingFormDetailId, other.exportingFormDetailId);
	}

	@Override
	public String toString() {
		return "ExportingFormDetail [exportingFormDetailId=" + exportingFormDetailId + ", quantity=" + quantity
				+ ", unitPrice=" + unitPrice + ", exportingForm=" + exportingForm.getExportingFormId() + ", product=" + product.getId() + "]";
	}
	
	
}
