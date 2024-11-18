package com.quanlykho.inventory_user;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.multipart.MultipartFile;

import com.quanlykho.Utility;
import com.quanlykho.common.InventoryRole;
import com.quanlykho.common.InventoryUser;
import com.quanlykho.common.exception.CannotDeleteThisItemException;
import com.quanlykho.common.exception.UserAlreadyExistException;
import com.quanlykho.common.exception.UserNotExistException;

import jakarta.mail.Multipart;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/inventory_users/")
@Validated
public class InventoryUserController {
   
	@Autowired
	private InventoryUserService inventoryUserService;
	
	@Value("${upload_directory}")
	private String directory;
	
	private static final String anonymouseImage = "D:/inventory_images/anomyus.jpg";
	
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
	
	@GetMapping("/search")
	public ResponseEntity<?> searchKeyWord(@RequestParam("keyWord") String keyWord, 
			                               @RequestParam("pageNum") int pageNum, 
			                               @RequestParam("pageSize") int pageSize,
			                               @RequestParam("sortField") String sortField,
			                               @RequestParam("sortDir") String sortDir
			){
		Page<InventoryUser> pages = inventoryUserService.search(keyWord,pageNum,pageSize, sortField, sortDir);
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
			inventoryUser.setPassword("123456789ASD");
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
			String currentUserIdLoggin = Utility.getMaNhanVien();
			System.out.println("Current User Logged: " + currentUserIdLoggin);
			boolean isEqualToCurrentLogin = currentUserIdLoggin.equals(userId);
			if(isEqualToCurrentLogin) {
				return new ResponseEntity("You could not delete yourself",HttpStatus.BAD_REQUEST);
			}
			inventoryUserService.deleteById(userId);
			return ResponseEntity.ok().build();
		} catch (UserNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
		} catch (CannotDeleteThisItemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/createByExcel")
	public ResponseEntity<?> createUsersByExcelFile(@RequestParam("file") MultipartFile file){
		System.out.println(file);
		List<InventoryUser> listUsers = new ArrayList<>();
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
			XSSFSheet sheet = workbook.getSheetAt(0);
			for(int i =1; i<sheet.getPhysicalNumberOfRows();i++) {
				InventoryUser inventoryUser = new InventoryUser();
				XSSFRow row = sheet.getRow(i);
				inventoryUser.setUserId((String) row.getCell(0).getStringCellValue());
				String identityNumber = (String)row.getCell(1).getStringCellValue();
				if(identityNumber.startsWith("'")) {
					identityNumber = identityNumber.substring(1);
				}
				inventoryUser.setIdentityNumber(identityNumber);
				inventoryUser.setFirstName((String)row.getCell(2).getStringCellValue());
				inventoryUser.setLastName((String)row.getCell(3).getStringCellValue());
				inventoryUser.setAddress((String)row.getCell(4).getStringCellValue());
				String phoneNumberCell = (String)row.getCell(5).getStringCellValue();
				if(phoneNumberCell.startsWith("'")) {
					phoneNumberCell = phoneNumberCell.substring(1);
				}
				inventoryUser.setPhoneNumber(phoneNumberCell);
				inventoryUser.setEmail((String)row.getCell(6).getStringCellValue());
				inventoryUser.setPhotos("photos.png");
				inventoryUser.setPassword("123456789ASD");
				inventoryUser.setInventoryRole(new InventoryRole(2));
				inventoryUser.setEnabled(true);
				listUsers.add(inventoryUser);
			}
			listUsers.forEach(user -> {
				System.out.println(user.getUserId());
				System.out.println(user.getFirstName());
				System.out.println(user.getIdentityNumber());
				System.out.println(user.getPhoneNumber());
			});
			inventoryUserService.createMultipleUsers(listUsers);
			return ResponseEntity.ok().build();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("/excel")
	public ResponseEntity<?> reportUserThroughExcel(HttpServletResponse response) throws IOException{
		try {
			List<InventoryUser> listUsers = inventoryUserService.listAll();
			InventoryUserExcelExport excelReport = new InventoryUserExcelExport();
			excelReport.export(listUsers, response);
			return ResponseEntity.ok().build();
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@GetMapping("/profileImage/{userId}")
	public ResponseEntity<?> getPersonalImage(@PathVariable("userId") String userId){
		try {
			InventoryUser inventoryUser = inventoryUserService.getByUserId(userId);
			String photoPath = inventoryUser.getPhotos();
			if(photoPath == null || photoPath.isEmpty() || !Files.exists(Paths.get(photoPath))) {
				photoPath = anonymouseImage;
			}
			Path path = Paths.get(photoPath);
			Resource resource = new UrlResource(path.toUri());
			String contentType = Files.probeContentType(path);
			if(contentType == null) {
				contentType = "application/octet-stream";
			}
			return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(resource);
		} catch (UserNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/checkUserId/{userId}")
	public ResponseEntity<?> checkUserIdIsDupplicate(@PathVariable("userId") String userId){
		return ResponseEntity.ok(inventoryUserService.checkUserIdIsDupplicate(userId));
	}
	
	@GetMapping("/checkEmail/{email}")
	public ResponseEntity<?> checkEmailIsDupplicate(@PathVariable("email") String email){
		return ResponseEntity.ok(inventoryUserService.checkEmailIsDupplicate(null, email));
	}
	
	@GetMapping("/checkIdentityNumber/{identityNumber}")
	public ResponseEntity<?> checkIdentityNumberIsDupplicate(@PathVariable("identityNumber") String identityNumber){
		return ResponseEntity.ok(inventoryUserService.checkIdentityNumberlIsDupplicate(null, identityNumber));
	}
	
	@GetMapping("/listForActivityReport")
	public ResponseEntity<?> getAllEmployeeForActivityReport(){
		List<InventoryUser> results = inventoryUserService.getListUserForEmployeeActivityReport();
		if(results.size() > 0) {
			List<InventoryUserDTOForReport> dtoResults = results.stream().map(this::convertEntityToDTO).toList();
			return ResponseEntity.ok(dtoResults);
		}else {
			return ResponseEntity.noContent().build();
		}
	}
     
	public InventoryUserDTOForReport convertEntityToDTO(InventoryUser user) {
		InventoryUserDTOForReport dto = new InventoryUserDTOForReport();
		String name = user.getUserId() + "-" + user.getFullName();
		String userId = user.getUserId();
		dto.setName(name);
		dto.setUserId(userId);
		return dto;
	}  
}
