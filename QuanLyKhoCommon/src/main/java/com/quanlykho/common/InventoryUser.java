package com.quanlykho.common;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "inventory_user")
public class InventoryUser {
   
	@Id
	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "identity_number")
	private String identityNumber;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "phone_number")
	private String phoneNumber;
	
	@Column(name = "photos")
	private String photos;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "reset_password_token")
	private String resetpasswordToken;
	
	@ManyToOne(targetEntity = InventoryRole.class)
	@JoinColumn(name = "inventory_role_id", referencedColumnName = "id")
	private InventoryRole inventoryRole;
	
	
	
	public InventoryUser() {
		
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIdentityNumber() {
		return identityNumber;
	}

	public void setIdentityNumber(String identityNumber) {
		this.identityNumber = identityNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPhotos() {
		return photos;
	}

	public void setPhotos(String photos) {
		this.photos = photos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getResetpasswordToken() {
		return resetpasswordToken;
	}

	public void setResetpasswordToken(String resetpasswordToken) {
		this.resetpasswordToken = resetpasswordToken;
	}

	public InventoryRole getInventoryRole() {
		return inventoryRole;
	}

	public void setInventoryRole(InventoryRole inventoryRole) {
		this.inventoryRole = inventoryRole;
	}

	@Override
	public String toString() {
		return "InventoryUser [userId=" + userId + ", identityNumber=" + identityNumber + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", address=" + address + ", phoneNumber=" + phoneNumber + ", photos="
				+ photos + ", email=" + email + ", inventoryRole=" + inventoryRole + "]";
	}
	
    
}
