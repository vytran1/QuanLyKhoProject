package com.quanlykho.common.setting;

import java.util.List;


public class SettingBag {
    private List<Setting> listSettings;

	public SettingBag(List<Setting> settings) {
		super();
		this.listSettings = settings;
	}

    
    
	public List<Setting> getListSettings() {
		return listSettings;
	}



	public void setListSettings(List<Setting> listSettings) {
		this.listSettings = listSettings;
	}



	public Setting get(String key) {
		int index = listSettings.indexOf(new Setting(key));
		if(index >= 0) {
			return listSettings.get(index);
		}
		return null;
	}
	
	
	public String getValue(String key) {
	    Setting setting = this.get(key);
	    if(setting != null) {
	    	return setting.getValue();
	    }
	    return null;
	}
}
