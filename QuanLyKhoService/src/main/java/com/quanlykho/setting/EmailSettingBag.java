package com.quanlykho.setting;

import java.util.List;

import com.quanlykho.common.setting.Setting;
import com.quanlykho.common.setting.SettingBag;

public class EmailSettingBag extends SettingBag {

	public EmailSettingBag(List<Setting> settings) {
		super(settings);
		// TODO Auto-generated constructor stub
	}
    
	public String getHost() {
		return super.getValue("MAIL_HOST");
	}
	public String getUsername() {
		return super.getValue("MAIL_USERNAME");
	}
	public String getPassword() {
		return super.getValue("MAIL_PASSWORD");
	}
	public Integer getPort() {
		return Integer.parseInt(super.getValue("MAIL_PORT"));
	}
	
	public String getSmtpAuth() {
		return super.getValue("SMTP_AUTH");
	}
	public String getSmtpSecured() {
		return super.getValue("SMTP_SECURED");
	}
	public String getFromAddress() {
		return super.getValue("MAIL_FROM");
	}
	public String getSenderName() {
		return super.getValue("MAIL_SENDER_NAME");
	}
	
	public String getCustomerVerifySubject() {
		return super.getValue("CUSTOMER_VERIFY_SUBJECT");
	}
	
	public String getCustomerVerifyContent() {
		return super.getValue("CUSTOMER_VERIFY_CONTENT");
	}
	public String getOrderConfirmationSubject() {
		return super.getValue("ORDER_CONFIRMATION_SUBJECT");
	}
	public String getOrderConfirmationContent() {
		return super.getValue("ORDER_CONFIRMATION_CONTENT");
	}
}
