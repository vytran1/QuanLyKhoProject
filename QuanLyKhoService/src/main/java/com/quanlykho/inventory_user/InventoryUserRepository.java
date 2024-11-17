package com.quanlykho.inventory_user;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import com.quanlykho.common.InventoryUser;

public interface InventoryUserRepository extends JpaRepository<InventoryUser,String> {
     
	public InventoryUser findByEmail(String email);
	
	public InventoryUser findByIdentityNumber(String identityNumber);
	
	@Query("SELECT u FROM InventoryUser u")
	public Page<InventoryUser> findAll(Pageable pageable);
	
	@Query("SELECT new com.quanlykho.common.InventoryUser(u.userId, u.firstName, u.lastName) FROM InventoryUser u")
    List<InventoryUser> findAllBasicInfo();
	
	public InventoryUser findByResetPasswordToken(String resetpasswordToken);
	
	@Query("SELECT iu FROM InventoryUser iu WHERE CONCAT(iu.userId,' ',iu.firstName,' ',iu.lastName,' ',iu.email) LIKE %?1%")
	public Page<InventoryUser> search(String keyWord,Pageable pageable);
	
	@Procedure(name = "checkIsUserJoinBussiness")
	public boolean checkIsUserJoinBussiness(@Param("userId") String userId);
	
	@Procedure(name = "spHoatDongNhanVien")
	public List<Object[]>  spHoatDongNhanVien(@Param("start_date") Date startDate,@Param("end_date") Date endDate,@Param("user_id") String userId);
}
