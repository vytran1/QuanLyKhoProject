package com.quanlykho.category;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quanlykho.common.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
