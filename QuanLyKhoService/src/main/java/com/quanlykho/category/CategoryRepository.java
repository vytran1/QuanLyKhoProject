package com.quanlykho.category;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.quanlykho.common.Category;


public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
