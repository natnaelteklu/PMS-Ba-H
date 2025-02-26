package com.pms.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pms.entity.Category;
import com.pms.service.CategoryService;

@RestController
@RequestMapping("/pms")

public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	@PostMapping("/saveCategory")
	public ResponseEntity<String> addNewCategory(@RequestBody Category categoy){
		return categoryService.addNewCategory(categoy);
	}
    @GetMapping("/getAllCategory")
	 public List<Category> getAllCategory(){
	     return categoryService.getAllCategory();
	 }
	 

    @GetMapping("/getCategoryById/{id}")
	public Category getCategoryById(@PathVariable int id) {
		return categoryService.getCategoryById(id);
	}

	@PutMapping("/updateCategory/{categoryId}")
	public ResponseEntity<String> updateCategoryByCategoryId(@RequestBody Category category,@PathVariable("categoryId") int categoryId) {
		return categoryService.updateCategoryByCategoryId(category,categoryId);
	}

	@DeleteMapping("deleteCategory/{id}")
	public ResponseEntity<String> deleteCategory(@PathVariable("id") int id) {
		return categoryService.deleteCategoryById(id);
	}
	
}
