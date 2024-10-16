package com.quanlykho.inventory_user;

import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quanlykho.common.InventoryRole;
import com.quanlykho.common.InventoryUser;
import com.quanlykho.common.exception.UserAlreadyExistException;
import com.quanlykho.common.exception.UserNotExistException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/inventory_users/")
@Validated
public class InventoryUserController {
   
	@Autowired
	private InventoryUserService inventoryUserService;
	
	@Deprecated
	@GetMapping("findAll")
	public ResponseEntity<?> listAllUser(HttpServletRequest request){
		List<InventoryUser> results = inventoryUserService.listAll();
		if(results.size() == 0) {
			return ResponseEntity.noContent().build();
		}else {
			return ResponseEntity.ok(results);
		}
	}
	
	@GetMapping("findByPage")
    public ResponseEntity<?> listByPage(
    		    @RequestParam("pageNum") int pageNum,
    		    @RequestParam("pageSize") int pageSize,
    		    @RequestParam("sortField") String sortField,
    		    @RequestParam("sortDir") String sortDir    		
    		) {
		Page<InventoryUser> pages =  inventoryUserService.findByPage(pageNum,pageSize, sortField, sortDir);
		if(pages.isEmpty()) {
			return ResponseEntity.noContent().build();
		}else {
			List<InventoryUser> listResults = pages.getContent();
			InventoryUserList listWithPageInfo = new InventoryUserList(listResults, pageNum, pageSize, sortField, sortDir, sortDir);
			listWithPageInfo.setTotalItems(pages.getTotalElements());
			listWithPageInfo.setTotalPage(pages.getTotalPages());
			return ResponseEntity.ok(listWithPageInfo);			
		}
	}
	
	
	@PostMapping("createUser")
	public ResponseEntity<?> createUser(@RequestBody @Valid InventoryUser inventoryUser){
		try {
			inventoryUser.setInventoryRole(new InventoryRole(1));
			inventoryUser.setPhotos("Thumbnail.png");
			inventoryUserService.saveUser(inventoryUser);
			return ResponseEntity.ok().build();
		} catch (UserAlreadyExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping("findByUserID/{userId}")
	public ResponseEntity<?> getUserByUserId(@PathVariable("userId") @Length(max = 15) String userId){
		try {
			InventoryUser inventoryUser = inventoryUserService.getByUserId(userId);
			return ResponseEntity.ok(inventoryUser);
		} catch (UserNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("updateUser")
	public ResponseEntity<?> updateUser(@RequestBody @Valid InventoryUser inventoryUser){
		try {
			inventoryUserService.updateUser(inventoryUser);
			return ResponseEntity.ok().build();
		} catch (UserAlreadyExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (UserNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("deleteUser/{userId}")
	public ResponseEntity<?> deleteUserById(@PathVariable("userId") @Length(max = 15)  String userId){
		try {
			inventoryUserService.deleteById(userId);
			return ResponseEntity.ok().build();
		} catch (UserNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
	
}
