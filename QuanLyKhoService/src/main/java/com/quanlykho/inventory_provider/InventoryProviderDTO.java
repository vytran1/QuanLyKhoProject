package com.quanlykho.inventory_provider;

public class InventoryProviderDTO {
    private Integer providerId;
    private String  providerName;
    private String  providerContactNumber;
    private String  providerAddress;
    private String  providerEmail;
    private boolean enabled;
    
    
	public InventoryProviderDTO() {
		super();
		// TODO Auto-generated constructor stub
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


	public boolean isEnabled() {
		return enabled;
	}


	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
    
    
}
