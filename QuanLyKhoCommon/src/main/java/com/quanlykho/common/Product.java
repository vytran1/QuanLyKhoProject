package com.quanlykho.common;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(unique = true, length = 256, nullable = false)
    private String name;
	
	@Column(unique = true, length = 256, nullable = false)
    private String alias;
	
	@Column(name = "enabled")
	private boolean enabled;
	
	@Column(name = "created_time")
    private Date createdTime;
	
	@Column(name = "updated_time")
    private Date updatedTime;
	
	@Column(name = "unit")
	private String unit;
	
	@Column(name = "short_description")
	private String shortDescription;
	
	@Column(name = "price")
	private float price;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
	@ManyToOne
	@JoinColumn(name = "brand_id")
	private Brand brand;
	
	@OneToMany(mappedBy = "product",fetch = FetchType.LAZY)
	private Set<InventoryProduct> inventoryProducts = new HashSet<>();

	public Product() {
		
	}
    
	
	
	public Product(Integer id) {
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

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", alias=" + alias + ", enabled=" + enabled + ", createdTime="
				+ createdTime + ", updatedTime=" + updatedTime + ", unit=" + unit + ", short_description="
				+ shortDescription + ", category=" + category + ", brand=" + brand + "]";
	}
	
	
	@PrePersist
	@PreUpdate
	public void updateTimestamps() {
	    this.updatedTime = new Date();
	    
	    if (this.createdTime == null) { // Chỉ đặt createdTime nếu nó chưa có giá trị (khi đối tượng lần đầu được lưu vào DB)
	        this.createdTime = new Date();
	    }
	    
	    System.out.println("Timestamps have been updated");
	}



	public float getPrice() {
		return price;
	}



	public void setPrice(float price) {
		this.price = price;
	}

	
}
