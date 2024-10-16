package com.quanlykho.common;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "inventory_products")
public class InventoryProducts {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "alias")
	private String alias;
	
	@Column(name = "unit")
	private String unit;
	
	@Column(name = "short_description")
	private String short_description;
	
	@Column(name = "enabled")
	private boolean enabled;

	public InventoryProducts(Integer id, String name, String alias, String unit, String short_description,
			boolean enabled) {
		super();
		this.id = id;
		this.name = name;
		this.alias = alias;
		this.unit = unit;
		this.short_description = short_description;
		this.enabled = enabled;
	}

	public InventoryProducts() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getShort_description() {
		return short_description;
	}

	public void setShort_description(String short_description) {
		this.short_description = short_description;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "InventoryProducts [id=" + id + ", name=" + name + ", alias=" + alias + ", unit=" + unit
				+ ", short_description=" + short_description + ", enabled=" + enabled + "]";
	}
	
	
	
	
}
