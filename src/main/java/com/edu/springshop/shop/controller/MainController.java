package com.edu.springshop.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.edu.springshop.domain.Category;
import com.edu.springshop.model.category.CategoryService;
import com.edu.springshop.model.product.ProductService;

@Controller
public class MainController {
	@Autowired
	private CategoryService categoryService;

	
	@GetMapping("/")
	public ModelAndView main() {
		List<Category> categoryList=categoryService.selectAll();
		ModelAndView mav=new ModelAndView("shop/index");
		mav.addObject("categoryList", categoryList);
		return mav;
	}

	
}
