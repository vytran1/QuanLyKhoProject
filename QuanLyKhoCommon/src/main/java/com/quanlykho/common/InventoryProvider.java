package com.quanlykho.common;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity()
@Table(name = "inventory_provider")
public class InventoryProvider {
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "provider_id")
	private Integer providerId;
	
	@Column(name = "provider_name")
	private String providerName;
	
	@Column(name = "provider_contact_number")
	private String providerContactNumber;
	
	@Column(name = "provider_address")
	private String providerAddress;
	
	@Column(name = "provider_email")
	private String providerEmail;
	
	@Column(name = "created_time")
	private Date createdTime;
	
	@Column(name = "enabled")
	private boolean enabled;

	public InventoryProvider() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InventoryProvider(Integer providerId) {
		super();
		this.providerId = providerId;
	}
	
	

	public InventoryProvider(Integer providerId, String providerName) {
		super();
		this.providerId = providerId;
		this.providerName = providerName;
	}

	public Integer getProviderId() {
		return providerId;
	}

	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getProviderContactNumber() {
		return providerContactNumber;
	}

	public void setProviderContactNumber(String providerContactNumber) {
		this.providerContactNumber = providerContactNumber;
	}

	public String getProviderAddress() {
		return providerAddress;
	}

	public void setProviderAddress(String providerAddress) {
		this.providerAddress = providerAddress;
	}

	public String getProviderEmail() {
		return providerEmail;
	}

	public void setProviderEmail(String providerEmail) {
		this.providerEmail = providerEmail;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "InventoryProvider [providerId=" + providerId + ", providerName=" + providerName
				+ ", providerContactNumber=" + providerContactNumber + ", providerAddress=" + providerAddress
				+ ", providerEmail=" + providerEmail + ", createdTime=" + createdTime + ", enabled=" + enabled + "]";
	}
	
	
	
}
