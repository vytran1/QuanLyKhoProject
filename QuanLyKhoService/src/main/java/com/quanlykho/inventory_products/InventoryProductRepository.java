package com.quanlykho.inventory_products;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quanlykho.common.Product;

public interface InventoryProductRepository extends JpaRepository<Product, Integer> {

}
