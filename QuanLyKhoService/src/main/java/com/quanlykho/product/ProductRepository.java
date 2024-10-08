package com.quanlykho.product;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quanlykho.common.Product;

public interface ProductRepository extends JpaRepository<Product,Integer> {

}
