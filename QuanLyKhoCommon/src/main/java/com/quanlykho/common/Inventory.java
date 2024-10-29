package com.quanlykho.common;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.quanlykho.common.setting.Country;
import com.quanlykho.common.setting.District;
import com.quanlykho.common.setting.State;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "inventory")
public class Inventory {
	@Id
	@Column(name = "inventory_id")
    private String inventoryId;
	
	@Column(name = "inventory_name")
	private String inventoryName;
	
	@Column(name = "inventory_address")
	private String inventoryAddress;
	
	
	@ManyToOne(targetEntity = Country.class)
	@JoinColumn(name = "country_id",referencedColumnName = "id")
	private Country country;
	
	@ManyToOne(targetEntity = State.class)
	@JoinColumn(name = "state_id",referencedColumnName = "id")
	private State state;
	
	@ManyToOne(targetEntity = District.class)
	@JoinColumn(name = "district_id",referencedColumnName = "id")
	private District district;
	
	
	@OneToMany(mappedBy = "inventory",fetch = FetchType.LAZY)
	private Set<InventoryProduct> inventoryProduct = new HashSet<>();

	public Inventory() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public Inventory(String inventoryId) {
		super();
		this.inventoryId = inventoryId;
	}



	public String getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(String inventoryId) {
		this.inventoryId = inventoryId;
	}

	public String getInventoryName() {
		return inventoryName;
	}

	public void setInventoryName(String inventoryName) {
		this.inventoryName = inventoryName;
	}

	public String getInventoryAddress() {
		return inventoryAddress;
	}

	public void setInventoryAddress(String inventoryAddress) {
		this.inventoryAddress = inventoryAddress;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}

	@Override
	public int hashCode() {
		return Objects.hash(inventoryId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Inventory other = (Inventory) obj;
		return Objects.equals(inventoryId, other.inventoryId);
	}

	@Override
	public String toString() {
		return "Inventory [inventoryId=" + inventoryId + ", inventoryName=" + inventoryName + ", inventoryAddress="
				+ inventoryAddress + ", country=" + country.getName() + ", state=" + state.getName() + ", district=" + district.getName() + "]";
	}
	
	@Transient
	public String getDetailAddress() {
		String detailAddress = this.getInventoryAddress()+ ", " +this.getDistrict().getName() + ", " + this.getState().getName()+ ", " + this.getCountry().getName();
	    return detailAddress;
	}
	
	
}
