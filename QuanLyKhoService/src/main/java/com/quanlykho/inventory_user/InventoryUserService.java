package com.quanlykho.inventory_user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.quanlykho.Utility;
import com.quanlykho.common.InventoryUser;
import com.quanlykho.common.exception.CannotDeleteThisItemException;
import com.quanlykho.common.exception.UserAlreadyExistException;
import com.quanlykho.common.exception.UserNotExistException;
import com.quanlykho.common.exception.UserNotFoundException;

import jakarta.transaction.Transactional;
import net.bytebuddy.utility.RandomString;

@Service
@Transactional
public class InventoryUserService {
   
	@Autowired
	private InventoryUserRepository inventoryUserRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//Load All User
	public List<InventoryUser> listAll(){
		List<InventoryUser> results = inventoryUserRepository.findAll();
		return results;
	}
	
	//Lưu User
	public void saveUser(InventoryUser inventoryUser) throws UserAlreadyExistException {
		
		//Kiểm Tra UserId có bị trùng không
		String userId = inventoryUser.getUserId();
		Optional<InventoryUser> userByUserId = inventoryUserRepository.findById(userId);
		if(userByUserId.isPresent()) {
			throw new UserAlreadyExistException("User with Email " + userId + "has already exist");
		}
		
		
		//Kiểm tra email có bị trùng không
		String emailFromRequest = inventoryUser.getEmail();
		if(checkEmailIsDupplicate(inventoryUser.getUserId(), emailFromRequest)) {
			throw new UserAlreadyExistException("User with Email " + emailFromRequest + "has already exist");
		}
		
		//Kiểm tra số chứng minh nhân dân có bị trùng không
		String identityNumberFromUser = inventoryUser.getIdentityNumber();
		if(checkIdentityNumberlIsDupplicate(inventoryUser.getUserId(), identityNumberFromUser)) {
			throw new UserAlreadyExistException("User with Identity Number " + identityNumberFromUser + "has already exist");
		}
		inventoryUser.setEnabled(true);
		encodePassword(inventoryUser);
		inventoryUserRepository.save(inventoryUser);
	}
	
	private void encodePassword(InventoryUser inventoryUser) {
		// TODO Auto-generated method stub
		String rawPassword = inventoryUser.getPassword();
		String encodePassword = passwordEncoder.encode(rawPassword);
		inventoryUser.setPassword(encodePassword);
	}

	//Cập nhật User
	public void updateUser(InventoryUser inventoryUser) throws UserAlreadyExistException, UserNotExistException {
		
		//Kiểm tra xem userId có tồn tại không
		String userId = inventoryUser.getUserId();
		InventoryUser inventoryUserFromDatabase = this.getByUserId(userId);
		if(inventoryUserFromDatabase == null) {
			throw new UserNotExistException("User with userId " + userId + " does not exist in database");
		}
		
		
		// Kiểm tra số chứng minh nhân dân có bị trùng không
		String identityNumberFromUser = inventoryUser.getIdentityNumber();
		if (checkIdentityNumberlIsDupplicate(inventoryUser.getUserId(), identityNumberFromUser)) {
			throw new UserAlreadyExistException("User with Identity Number " + identityNumberFromUser + "has already exist");
		}
		
		inventoryUser.setInventoryRole(inventoryUserFromDatabase.getInventoryRole());
		inventoryUserFromDatabase.setFirstName(inventoryUser.getFirstName());
		inventoryUserFromDatabase.setLastName(inventoryUser.getLastName());
		inventoryUserFromDatabase.setIdentityNumber(inventoryUser.getIdentityNumber());
		inventoryUserFromDatabase.setPhoneNumber(inventoryUser.getPhoneNumber());
		inventoryUserRepository.save(inventoryUserFromDatabase);
	}
	
	public void simpleSave(InventoryUser user) {
		inventoryUserRepository.save(user);
	}
	
	//Hàm check Email có bị trùng lặp hay không
	public boolean checkEmailIsDupplicate(String userId,String email) {
		InventoryUser userCheckDupplicate = inventoryUserRepository.findByEmail(email);
		if(userCheckDupplicate == null) {
			return false;
		}
		boolean isCreatingNewUser = userId == null ? true : false;
		if(isCreatingNewUser) {
			if(userCheckDupplicate != null) {
				return true;
			}
		}else {
			if(!userCheckDupplicate.getUserId().equals(userId)) {
				return true;
			}
		}
		return false;
	}
	
	//Hàm check Số điện thoại có bị trùng lặp hay không
	public boolean checkIdentityNumberlIsDupplicate(String userId,String identityNumber) {
		InventoryUser userCheckDupplicate = inventoryUserRepository.findByIdentityNumber(identityNumber);
		if(userCheckDupplicate == null) {
			return false;
		}
		boolean isCreatingNewUser = userId == null ? true : false;
		if(isCreatingNewUser) {
			if(userCheckDupplicate != null) {
				return true;
			}
		}else {
			if(!userCheckDupplicate.getUserId().equals(userId)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean checkUserIdIsDupplicate(String userId) {
		boolean isExist = inventoryUserRepository.existsById(userId);
		return isExist;
	}
	
	//Hàm lấy thông tin User by UserId
	public InventoryUser getByUserId(String userId) throws UserNotExistException {
		Optional<InventoryUser> inventoryUser = inventoryUserRepository.findById(userId);
		if(!inventoryUser.isPresent()) {
			throw new UserNotExistException("User with userId " + userId + " does not exist in database");
		}
		return inventoryUser.get();
	}
	
	//Hàm delete User By UserId
	public void deleteById(String userId) throws UserNotExistException, CannotDeleteThisItemException {
		//Kiểm tra có tồn tại hay không
		Optional<InventoryUser> inventoryUser = inventoryUserRepository.findById(userId);
		if(!inventoryUser.isPresent()) {
			throw new UserNotExistException("User with userId " + userId + " does not exist in database");
		}
		
		boolean canDelete = inventoryUserRepository.checkIsUserJoinBussiness(userId);
		if(!canDelete) {
			throw new CannotDeleteThisItemException("User with id: " + userId + "has take part in creating business form");
		}else {
			System.out.println("You can delete this User");
		}
		inventoryUserRepository.deleteById(userId);
	}
	
	//Hàm lấy danh sách user có phân trang
	public Page<InventoryUser> findByPage(Integer pageNum, Integer pageSize, String sortField, String sortDir){
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		Pageable pageable = PageRequest.of(pageNum - 1,pageSize,sort);
		return inventoryUserRepository.findAll(pageable);
	}
	
	public Page<InventoryUser> search(String keyWord,Integer pageNum, Integer pageSize, String sortField, String sortDir){
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		Pageable pageable = PageRequest.of(pageNum -1,pageSize, sort);
		return inventoryUserRepository.search(keyWord, pageable);
	}
	
	public String updatePasswordToken(String email) throws UserNotFoundException {
		InventoryUser inventoryUser = inventoryUserRepository.findByEmail(email);
		if(inventoryUser == null) {
			throw new UserNotFoundException("Not exist account with email: " + email);
		}else {
			String token = RandomString.make(30);
			inventoryUser.setResetPasswordToken(token);;
			inventoryUserRepository.save(inventoryUser);
			return token;
		}
	}
	
	public void updatePassword(String newPassword, String token) throws UserNotFoundException {
		InventoryUser inventoryUser = inventoryUserRepository.findByResetPasswordToken(token);
		if(inventoryUser == null) {
			throw new UserNotFoundException("Invalid Token Found"); 
		}else {
			inventoryUser.setPassword(newPassword);
			this.encodePassword(inventoryUser);
			inventoryUser.setResetPasswordToken(null);;
			inventoryUserRepository.save(inventoryUser);
		}
	}
	
	public void createMultipleUsers(List<InventoryUser> inventoryUsers) {
		inventoryUsers.forEach(user -> {
			encodePassword(user);
		});
		inventoryUserRepository.saveAll(inventoryUsers);
	}
	
	
}
