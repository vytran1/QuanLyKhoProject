package com.quanlykho.setting.district;

public class DistrictDTO {
     private Integer id;
     private String name;
     
     
	public DistrictDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	public DistrictDTO(Integer id, String name) {
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
