package com.quanlykho.account;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.quanlykho.Utility;
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
	
	@Value("${upload_directory}")
	private String directory;
	
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
	
	@PostMapping(value = "/updateImage",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> uploadImage(@RequestParam("photo") MultipartFile file){
		System.out.println("Uploaded file content type: " + file.getContentType());
		if(!file.getContentType().startsWith("image/")) {
			return new ResponseEntity("Chỉ chấp nhận ảnh",HttpStatus.BAD_REQUEST);
		}
		
		String userId = Utility.getMaNhanVien();
		
		String originalFileName = file.getOriginalFilename();
		System.out.println("Original File Name: " + originalFileName);
		String extensionFileName = originalFileName != null && originalFileName.contains(".") ? originalFileName.substring(originalFileName.lastIndexOf(".")) : ".png";
		System.out.println("Extension File Name: " +extensionFileName);
		String date = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy"));
		String officialFilename = date + userId+extensionFileName;
		System.out.println("Official file name: " + officialFilename);
		String directoryStoring = directory + "/" +userId;
		System.out.println("Directory Storing: " + directoryStoring);
		File file2 = new File(directoryStoring);
		
		if(!file2.exists()) {
			file2.mkdirs();
		}else {
            for (File oldFile : file2.listFiles((dir, name) -> name.endsWith(".png") || name.endsWith(".jpeg") || name.endsWith(".jpg"))) {
                oldFile.delete();
            }
		}
		
		String filePath =  directoryStoring + "/" + officialFilename;
		try {
			file.transferTo(new File(filePath));
			InventoryUser inventoryUser = inventoryUserService.getByUserId(userId);
			inventoryUser.setPhotos(filePath);
			inventoryUserService.simpleSave(inventoryUser);
			System.out.println("Upload Success");
			return ResponseEntity.ok().build();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (UserNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	public AccountDTO convertEntityToDTO(InventoryUser inventoryUser) {
		return modelMapper.map(inventoryUser,AccountDTO.class);
	}
	
	@GetMapping("/profileImage")
	public ResponseEntity<?> getProfileImage() {
	    String userId = Utility.getMaNhanVien();
	    try {
	        InventoryUser inventoryUser = inventoryUserService.getByUserId(userId);
	        String photoPath = inventoryUser.getPhotos();

	        // Kiểm tra nếu đường dẫn không tồn tại hoặc trống, sử dụng ảnh ẩn danh
	        if (photoPath == null || photoPath.isEmpty() || !Files.exists(Paths.get(photoPath))) {
	            photoPath = "D:/inventory_images/anomyus.jpg";
	        }

	        Path path = Paths.get(photoPath);
	        Resource resource = new UrlResource(path.toUri());
	        String contentType = Files.probeContentType(path);

	        if (contentType == null) {
	            contentType = "application/octet-stream";
	        }

	        return ResponseEntity.ok()
	                .contentType(MediaType.parseMediaType(contentType))
	                .body(resource);
	    } catch (UserNotExistException e) {
	        e.printStackTrace();
	        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	    } catch (MalformedURLException e) {
	        e.printStackTrace();
	        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    } catch (IOException e) {
	        e.printStackTrace();
	        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

}
