package com.edu.springshop.admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.edu.springshop.domain.Category;
import com.edu.springshop.model.category.CategoryService;

@Controller
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	private Logger logger=LoggerFactory.getLogger(this.getClass().getName());

	@GetMapping("/category/main")
	public ModelAndView getMain(HttpServletRequest request) {
		List<Category> categoryList=categoryService.selectAll();
		ModelAndView mav=new ModelAndView();
		mav.addObject("categoryList", categoryList);
		mav.setViewName("admin/category/main");
		return mav;
	}
	
	
	@PostMapping("/category/edit")
	public ModelAndView edit(Category category) {
		logger.info("넘어온 카테고리!!!~~~~"+category);
		
		return null;
	}
	
}
