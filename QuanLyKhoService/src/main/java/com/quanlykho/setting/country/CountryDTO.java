package com.quanlykho.setting.country;

public class CountryDTO {
    private Integer id;
    private String code;
    private String name;
    
    
    
    
	public CountryDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	public CountryDTO(Integer id, String code, String name) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
	}




	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
    
    
    
}
