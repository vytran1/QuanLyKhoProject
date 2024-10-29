package com.quanlykho.inventory_order;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


import com.quanlykho.common.inventory_order.InventoryOrder;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
@Repository
public class FilterableInventoryOrderRepositoryImpl implements FilterableInventoryOrderRepostiroy {
    
	@Autowired
	private EntityManager entityManager;
	
	@Override
	public Page<InventoryOrder> listWithFilter(Pageable pageable, Map<String, Object> filterFields) {
		// TODO Auto-generated method stub
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<InventoryOrder> query =  builder.createQuery(InventoryOrder.class);
		
		
		//From
		Root<InventoryOrder> root = query.from(InventoryOrder.class);
		
		
		int i = 0;
		//Where
		if(!filterFields.isEmpty()) {
			Predicate[] predicates = new Predicate[filterFields.size()];
			
			Iterator<String> iterator = filterFields.keySet().iterator();
			
			while(iterator.hasNext()) {
				String fieldName = iterator.next();
				Object filterValue = filterFields.get(fieldName);
				
				System.out.println("Field Name: " + fieldName + " Field Value: " + filterValue);
				predicates[i++] = builder.equal(root.get(fieldName),filterValue);
				
			}
			
			query.where(predicates);
		}
		
		//OrderBy
		List<Order> listOrders = new ArrayList<>();
		pageable.getSort().stream().forEach(order -> {
			System.out.println("Order field: " + order.getProperty());
			listOrders.add(builder.asc(root.get(order.getProperty())));
		});;
		query.orderBy(listOrders);
		
		
		TypedQuery<InventoryOrder> typeQuery = entityManager.createQuery(query);
		typeQuery.setFirstResult((int)pageable.getOffset());
		typeQuery.setMaxResults((int)pageable.getPageSize());
		
		
		
		List<InventoryOrder> listResult = typeQuery.getResultList();
		int totalRowCount = 0;
		return new PageImpl<>(listResult, pageable, 0);
	}
    
}
