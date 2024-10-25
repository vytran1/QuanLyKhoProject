package com.quanlykho.account;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quanlykho.common.InventoryUser;
import com.quanlykho.common.exception.UserNotExistException;
import com.quanlykho.inventory_user.InventoryUserService;
import com.quanlykho.security.InventoryUserDetails;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/account")
@Validated
public class AccountController {
    
	@Autowired
	private InventoryUserService inventoryUserService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@GetMapping("")
	public ResponseEntity<?> getPersonalInformation(){
		InventoryUserDetails userDetails = (InventoryUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		InventoryUser currentLogin = userDetails.getInventoryUser();
		
		String userIdFromCurrentLogin = currentLogin.getUserId();
		try {
			InventoryUser inventoryUserDetailInformation = inventoryUserService.getByUserId(userIdFromCurrentLogin);
			AccountDTO accountDTO = convertEntityToDTO(inventoryUserDetailInformation);
			return ResponseEntity.ok(accountDTO);
		} catch (UserNotExistException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
	
	
	@PostMapping("")
	public ResponseEntity<?> updatePersonalInformation(@RequestBody AccountDTO accountDTO){
		try {
			InventoryUser inventoryUser = inventoryUserService.getByUserId(accountDTO.getUserId());
			inventoryUser.setFirstName(accountDTO.getFirstName());
			inventoryUser.setLastName(accountDTO.getLastName());
			inventoryUser.setPhoneNumber(accountDTO.getPhoneNumber());
			inventoryUser.setAddress(accountDTO.getAddress());
			accountService.saveInfo(inventoryUser);
			return ResponseEntity.ok().build();
		} catch (UserNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
	
	
	@PostMapping("/changePassword")
	public ResponseEntity<?> changePassword(@RequestBody @Valid ChangePasswordRequestDTO request ){
		
		
		//Lấy thông tin người đăng nhập hiện tại
		InventoryUserDetails userDetails = (InventoryUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		InventoryUser currentLogin = userDetails.getInventoryUser();
		try {
			InventoryUser inventoryFromDatabase = inventoryUserService.getByUserId(currentLogin.getUserId());
			//Check xem mật khẩu current người dùng gửi có match với trong database không ?
			String currentPasswordFromRequest = request.getCurrentPassword();
			String currentPasswordFromDatabae = inventoryFromDatabase.getPassword();
			if(passwordEncoder.matches(currentPasswordFromRequest, currentPasswordFromDatabae)) {
				
				//Kiem tra confirmPassword và new Password
				if(!request.getNewPassword().equals(request.getConfirmPassword())) {
					return new ResponseEntity("New password and confirm password must be similar to each other",HttpStatus.BAD_REQUEST);
				}else {
					
				    String newPasswordEncoded = passwordEncoder.encode(request.getNewPassword());
				    inventoryFromDatabase.setPassword(newPasswordEncoded);
				    accountService.saveInfo(inventoryFromDatabase);
				    return ResponseEntity.ok().build();
				}
			
			}else {
				return new ResponseEntity("Current Password which you typed does not match with this in database",HttpStatus.BAD_REQUEST);
			}
			
		} catch (UserNotExistException e) {
			// TODO Auto-generated catch block
			return ResponseEntity.notFound().build();
		}
	}
	
	public AccountDTO convertEntityToDTO(InventoryUser inventoryUser) {
		return modelMapper.map(inventoryUser,AccountDTO.class);
	}
	
}
