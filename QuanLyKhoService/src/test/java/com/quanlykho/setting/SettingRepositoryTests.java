package com.quanlykho.setting;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.quanlykho.common.setting.Setting;
import com.quanlykho.common.setting.SettingCategory;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class SettingRepositoryTests {
    
	@Autowired
	private SettingRepository settingRepository;
	
	@Test
	public void testFindByCategoryWithMailServer() {
		List<Setting> listSettingsMailServer = settingRepository.findByCategory(SettingCategory.MAIL_SERVER);
		assertThat(listSettingsMailServer.size()).isGreaterThan(0);
		listSettingsMailServer.forEach(settingItem -> {
			System.out.println(settingItem.toString());
		});
	}
	
	@Test
	public void testFindByCategoryWithMailTemplate() {
		List<Setting> listSettingsMailTemplate = settingRepository.findByCategory(SettingCategory.MAIL_TEMPLATES);
		assertThat(listSettingsMailTemplate.size()).isGreaterThan(0);
		listSettingsMailTemplate.forEach(settingItem -> {
			System.out.println(settingItem.toString());
		});
	}
}
