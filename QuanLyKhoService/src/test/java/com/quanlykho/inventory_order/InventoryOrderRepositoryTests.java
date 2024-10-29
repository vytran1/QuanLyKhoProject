package com.quanlykho.inventory_order;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.quanlykho.common.Inventory;
import com.quanlykho.common.InventoryUser;
import com.quanlykho.common.Product;
import com.quanlykho.common.inventory_order.InventoryOrder;
import com.quanlykho.common.inventory_order.InventoryOrderDetail;

import jakarta.persistence.EntityManager;
import net.bytebuddy.utility.RandomString;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
@Transactional
public class InventoryOrderRepositoryTests {
     
	
	@Autowired
	private InventoryOrderRepository inventoryOrderRepository;
	
	@Autowired
	private EntityManager entityManager;
	
	@Test
	public void loadAdll() {
		List<InventoryOrder> results = inventoryOrderRepository.findAll();
		assertThat(results.size()).isGreaterThan(0);
		System.out.println(results.toString());
		for(InventoryOrder order : results) {
			Set<InventoryOrderDetail> details = order.getOrderDetails();
			details.forEach(detail -> {
				System.out.println(detail.toString());
			});
		}
	}
	
	@Test
	public void createOrder() {
		InventoryOrder order = new InventoryOrder();
		String random = RandomString.make(10);
		order.setOrderId("ĐĐH" + random);
		order.setCreatedTime(new Date());
		order.setSupplier("PhongVũ");
		order.setCustomerName("LuDePhanAnh");
		order.setCustomerPhoneNumber("0977373357");
		
		//NguoiLap So 
		InventoryUser nguoiLap =  entityManager.find(InventoryUser.class,"N21DCVT128");
		order.setInventoryUser(nguoiLap);
        
		//Kho Lập
		Inventory inventoryQ1 = entityManager.find(Inventory.class,"VNTPHCMQ1");
		order.setInventory(inventoryQ1);
		
		//Chi tiet don hang
		InventoryOrderDetail inventoryOrderDetail = new InventoryOrderDetail();
		inventoryOrderDetail.getId().setProductId(25);
		inventoryOrderDetail.getId().setOrderId(order.getOrderId());
		inventoryOrderDetail.setQuantity(25);
		inventoryOrderDetail.setUnitPrice(50000);
		Product product = new Product();
		product.setId(25);
		order.addDetail(inventoryOrderDetail);
		inventoryOrderDetail.setProduct(product);
		
		InventoryOrder savedOrder = inventoryOrderRepository.save(order);
		assertThat(savedOrder).isNotNull();
		assertThat(savedOrder.getOrderId()).isEqualTo(order.getOrderId());
		assertThat(savedOrder.getOrderDetails().size()).isGreaterThan(0);
	}
	
	@Test
	public void callSP() {
		List<InventoryOrder> inventoryOrderWithoutImportingForm = inventoryOrderRepository.getInventoryOrderWithoutImportingOrder();
		assertThat(inventoryOrderWithoutImportingForm.size()).isGreaterThan(0);
		inventoryOrderWithoutImportingForm.forEach(item -> {
			System.out.println("Inventory Order ID: " + item.getOrderId());
		});
	}
	
}
