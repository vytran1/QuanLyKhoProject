package com.quanlykho;

import java.util.Properties;

import org.springframework.mail.javamail.JavaMailSenderImpl;

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
	
}
