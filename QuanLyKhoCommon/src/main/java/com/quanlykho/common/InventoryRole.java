package com.quanlykho.common;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name ="inventory_role")
public class InventoryRole {
   
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Integer id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	public InventoryRole() {
		
	}

	public InventoryRole(Integer id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}
	
	public InventoryRole(Integer id) {
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "InventoryRole [id=" + id + ", name=" + name + ", description=" + description + "]";
	}
	
	
	
}
