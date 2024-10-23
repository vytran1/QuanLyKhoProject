package com.quanlykho.setting;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quanlykho.common.setting.Setting;
import com.quanlykho.common.setting.SettingCategory;

public interface SettingRepository extends JpaRepository<Setting,String> {
    public List<Setting> findByCategory(SettingCategory settingCategory);
}
