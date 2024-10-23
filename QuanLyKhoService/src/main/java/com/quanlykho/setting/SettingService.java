package com.quanlykho.setting;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quanlykho.common.setting.Setting;
import com.quanlykho.common.setting.SettingCategory;


@Service
public class SettingService {
    
	@Autowired
	private SettingRepository settingRepository;
	
	public EmailSettingBag getEmailSettingBag() {
	    	List<Setting> settings = settingRepository.findByCategory(SettingCategory.MAIL_SERVER);
	    	settings.addAll(settingRepository.findByCategory(SettingCategory.MAIL_TEMPLATES));
	    	return new EmailSettingBag(settings);
	}
	
	
}
