package com.quanlykho.common.setting;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "settings")
public class Setting {
    
	@Id
	@Column(name = "key")
	private String key;
	
	@Column(name = "value")
	private String value;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "category")
	private SettingCategory category;

	public Setting() {
		
	}

	public Setting(String key) {
		super();
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public SettingCategory getCategory() {
		return category;
	}

	public void setCategory(SettingCategory category) {
		this.category = category;
	}

	@Override
	public int hashCode() {
		return Objects.hash(key);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Setting other = (Setting) obj;
		return Objects.equals(key, other.key);
	}

	@Override
	public String toString() {
		return "Setting [key=" + key + ", value=" + value + ", category=" + category + "]";
	}
	
	
	
	
	
}
