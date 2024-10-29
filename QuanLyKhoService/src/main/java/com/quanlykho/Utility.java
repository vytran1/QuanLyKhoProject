package com.quanlykho;

import java.util.Properties;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.core.context.SecurityContextHolder;

import com.quanlykho.common.InventoryUser;
import com.quanlykho.security.InventoryUserDetails;
import com.quanlykho.setting.EmailSettingBag;



public class Utility {
   
	 public static JavaMailSenderImpl preJavaMailSenderImpl(EmailSettingBag emailSettingBag) {
		   JavaMailSenderImpl mailSender =  new JavaMailSenderImpl();
		   
		   mailSender.setHost(emailSettingBag.getHost());
		   mailSender.setPort(emailSettingBag.getPort());
		   mailSender.setUsername(emailSettingBag.getUsername());
		   mailSender.setPassword(emailSettingBag.getPassword());
		   
		   Properties mailProperties = new Properties();
		   mailProperties.setProperty("mail.smtp.auth", emailSettingBag.getSmtpAuth());
		   mailProperties.setProperty("mail.smtp.starttls.enable", emailSettingBag.getSmtpSecured());
		   
		   mailSender.setJavaMailProperties(mailProperties);
		   return mailSender;
	}
	 
	 
    public static String getMaNhanVien() {
    	InventoryUserDetails inventoryUserDetails = (InventoryUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	InventoryUser inventoryUser = inventoryUserDetails.getInventoryUser();
    	String userId = inventoryUser.getUserId();
    	return userId;
    }
	
}
