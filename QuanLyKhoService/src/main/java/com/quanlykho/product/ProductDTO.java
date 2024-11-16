package com.quanlykho.product;

import com.quanlykho.brand.BrandDTO;
import com.quanlykho.category.CategoryDTO;

public class ProductDTO {
    private Integer id;
    private String alias;
    private String name;
    private String unit;
    private Boolean enabled;
    private Float price;
    private String description;
    private CategoryDTO category;
    private BrandDTO brand;
    
    
	public ProductDTO(Integer id, String alias, String name, String unit, Float price, Boolean enabled, String description, CategoryDTO category, BrandDTO brand) {
		super();
		this.id = id;
		this.alias = alias;
		this.name = name;
		this.unit = unit;
		this.enabled = enabled;
		this.price = price;
		this.description = description;
		this.category = category;
		this.brand = brand;
	}


	public ProductDTO() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getAlias() {
		return alias;
	}


	public void setAlias(String alias) {
		this.alias = alias;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public CategoryDTO getCategory() {
		return category;
	}


	public void setCategory(CategoryDTO category) {
		this.category = category;
	}


	public BrandDTO getBrand() {
		return brand;
	}


	public void setBrand(BrandDTO brand) {
		this.brand = brand;
	}


	public String getUnit() {
		return unit;
	}


	public void setUnit(String unit) {
		this.unit = unit;
	}


	public Boolean isEnabled() {
		return enabled;
	}


	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}


	public Float getPrice() {
		return price;
	}


	public void setPrice(Float price) {
		this.price = price;
	}
    
    
}
