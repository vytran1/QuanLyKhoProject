package com.quanlykho.product;

public class ProductReportDTO {
	private Integer id;
	private String name;
	private String shortDescription;
	private String brand;
	private String category;

	public ProductReportDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProductReportDTO(Integer id, String name, String shortDescription, String brand, String category) {
		super();
		this.id = id;
		this.name = name;
		this.shortDescription = shortDescription;
		this.brand = brand;
		this.category = category;
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

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
