package com.quanlykho.common;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "inventory_user")
public class InventoryUser {
   
	@Id
	@Column(name = "user_id")
	@Length(max = 30,min = 10)
	@NotNull(message = "UserId must not be null")
	private String userId;
	
	@Column(name = "identity_number")
	@Length(min = 12,max = 12)
	@NotNull(message = "Identity Number must not be null")
	private String identityNumber;
	
	@Column(name = "first_name")
	@Length(min = 2, max = 20)
	@NotNull(message = "First Name must not be null")
	private String firstName;
	
	@Column(name = "last_name")
	@Length(min = 2, max = 20)
	@NotNull(message = "Last Name must not be null")
	private String lastName;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "phone_number")
	@Length(max = 10, min = 10)
	@NotNull(message = "Phone number must not be null")
	private String phoneNumber;
	
	@Column(name = "photos")
	private String photos;
	
	@Column(name = "email")
	@Email(message = "Email must follow pattern abc@gmail.com")
	private String email;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "reset_password_token")
	private String resetPasswordToken;
	
	@ManyToOne(targetEntity = InventoryRole.class)
	@JoinColumn(name = "inventory_role_id", referencedColumnName = "id")
	private InventoryRole inventoryRole;
	
	@Column(name = "enabled")
	private Boolean enabled;
	
	
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

	

	public String getResetPasswordToken() {
		return resetPasswordToken;
	}

	public void setResetPasswordToken(String resetPasswordToken) {
		this.resetPasswordToken = resetPasswordToken;
	}

	public InventoryRole getInventoryRole() {
		return inventoryRole;
	}

	public void setInventoryRole(InventoryRole inventoryRole) {
		this.inventoryRole = inventoryRole;
	}
     
	
	
	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "InventoryUser [userId=" + userId + ", identityNumber=" + identityNumber + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", address=" + address + ", phoneNumber=" + phoneNumber + ", photos="
				+ photos + ", email=" + email + ", inventoryRole=" + inventoryRole + "]";
	}
	
    
}
