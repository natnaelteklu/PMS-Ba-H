package com.pms.service;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pms.entity.Category;
import com.pms.entity.Facility;
import com.pms.filter.JwtAuthFilter;
import com.pms.repository.CategoryDao;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryDao catDao;
	
	@Autowired
	private JwtAuthFilter jwtFilter;

	public ResponseEntity<String> addNewCategory(Category category) {
		 category.setCategoryName(category.getCategoryName().trim());
		 Category catData = catDao.findByCategoryNameIgnoreCase(category.getCategoryName());
		 if(catData != null) {
			 return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
		 }
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a").format(new java.util.Date());
	
		category.setCreatedBy(jwtFilter.currentUserName);;
		category.setCreatedAt(timeStamp);
		 catDao.save(category);
		 return new ResponseEntity<>(HttpStatus.OK); 
	}

	public List<Category> getAllCategory() {
	       return catDao.findAll().stream()
	                .sorted(Comparator.comparing(Category::getCategoryId).reversed())
	                .collect(Collectors.toList());
	}

	public Category getCategoryById(int id) {
	
		return catDao.findById(id).orElse(null);
	}

	public ResponseEntity<String> updateCategoryByCategoryId(Category category, int categoryId) {

		
		Category categoryData = catDao.findById(categoryId).orElse(null);
		if(categoryData !=null) {
			category.setCategoryName(category.getCategoryName().trim());
			if(!categoryData.getCategoryName().equalsIgnoreCase(category.getCategoryName())){
				Category registerData = catDao.findByCategoryNameIgnoreCase(category.getCategoryName());
				if(registerData!=null) {
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
				
			}
		
			categoryData.setCategoryName(category.getCategoryName());
			categoryData.setDescription(category.getDescription());

			catDao.save(categoryData);
	    return new ResponseEntity<>(HttpStatus.OK);
	  }
	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<String> deleteCategoryById(int id) {
		
		 catDao.deleteById(id);
		 return new ResponseEntity<>(HttpStatus.OK); 
	}




}
