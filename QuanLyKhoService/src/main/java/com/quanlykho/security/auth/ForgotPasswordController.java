package com.quanlykho.security.auth;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quanlykho.Utility;
import com.quanlykho.common.exception.UserNotFoundException;
import com.quanlykho.inventory_user.InventoryUserService;
import com.quanlykho.setting.EmailSettingBag;
import com.quanlykho.setting.SettingService;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@RestController
public class ForgotPasswordController {
    
	private static final Logger LOGGER = LoggerFactory.getLogger(ForgotPasswordController.class);
	
	@Autowired
	private InventoryUserService inventoryUserService;
	
	@Autowired
	private SettingService settingService;
	
	@PostMapping("/api/forgot_password")
	public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest){
		try {
			String email = forgotPasswordRequest.getEmail();
			String token = inventoryUserService.updatePasswordToken(email);
			LOGGER.info("Reset Password Token has generated: " + token);
			String linkFrontend = "http://localhost:4200/reset_password?token=" + token;
			sendEmail(linkFrontend, email);
			return new ResponseEntity("We have send a resetLink to your email",HttpStatus.OK);
		}catch(UserNotFoundException e) {
			return ResponseEntity.notFound().build();
		}catch (UnsupportedEncodingException  |  MessagingException e) {
	    	return new ResponseEntity("Could Not Send Email",HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	
	
	
	@PostMapping("/api/reset_password")
	public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest, @RequestParam("token") String token){
			try {
				String newPassword = resetPasswordRequest.getNewPassword();
				inventoryUserService.updatePassword(newPassword, token);
				return ResponseEntity.ok().build();
			} catch (UserNotFoundException e) {
				// TODO Auto-generated catch block
				return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
			}
	}	
	
	
	private void sendEmail(String link,String email) throws UnsupportedEncodingException, MessagingException {
	    	EmailSettingBag settingsBag =  settingService.getEmailSettingBag();
			JavaMailSenderImpl mailSender =  Utility.preJavaMailSenderImpl(settingsBag);
			
			String toAddress = email;
			String subject = "Here's the link to reset your password";
			
			String content = "<p>Hello,</p>" 
			+ "<p>You have requested to reset your password</p" 
			+ "Click The Link below to change your password" 
			+"<p><a href=\"" + link +  "\">Change My Password</a></p>"
			+"<br>"
			+"<p>Ignore it if you have remember your password</p>";
			
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message);
			
			
			messageHelper.setFrom(settingsBag.getFromAddress(), settingsBag.getSenderName());
			messageHelper.setTo(toAddress);
			messageHelper.setSubject(subject);
			
			messageHelper.setText(content,true);
			mailSender.send(message);
	 }
	
}
