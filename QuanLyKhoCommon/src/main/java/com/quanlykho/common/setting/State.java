package com.quanlykho.common.setting;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "states")
public class State {
	
	@Id
	@Column(name = "id")
    private Integer id;
	
	@Column(name = "name")
    private String name;
    
    @ManyToOne(targetEntity = Country.class)
    @JoinColumn(name = "country_id",referencedColumnName = "id")
    private Country country;
    
    
    
    public State() {
    	
    }

    

	public State(Integer id) {
		super();
		this.id = id;
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



	public Country getCountry() {
		return country;
	}



	public void setCountry(Country country) {
		this.country = country;
	}



	@Override
	public String toString() {
		return "State [id=" + id + ", name=" + name + "]";
	}
    
	
    
}
