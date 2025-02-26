package com.pms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pms.entity.Category;
@Repository
public interface CategoryDao extends JpaRepository<Category, Integer>{

	Category findByCategoryNameIgnoreCase(String categoryName);



}
