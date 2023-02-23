package com.edu.springshop.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.edu.springshop.domain.Category;
import com.edu.springshop.model.category.CategoryService;

// 상품 관련 컨트롤러
@Controller
public class ProductController {
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/product/registform")
	public ModelAndView getForm() {
		List<Category> categoryList=categoryService.selectAll();
		ModelAndView mav=new ModelAndView();
		mav.addObject("categoryList", categoryList);
		mav.setViewName("admin/product/regist");
		return mav;
	}
	
}
