package com.quanlykho.brand;

public class BrandDTO {
    private Integer id;
    private String name;
    
    
	public BrandDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BrandDTO(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
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
    
    
}
