package com.edu.springshop.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.edu.springshop.domain.Category;
import com.edu.springshop.domain.Product;
import com.edu.springshop.model.category.CategoryService;
import com.edu.springshop.model.product.ProductDAO;

// 상품 관련 컨트롤러
@Controller
public class ProductController {
	
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ProductDAO productDAO;
	
	
	@GetMapping("/product/registform")
	public ModelAndView getForm() {
		List<Category> categoryList=categoryService.selectAll();
		ModelAndView mav=new ModelAndView();
		mav.addObject("categoryList", categoryList);
		mav.setViewName("admin/product/regist");
		return mav;
	}
	
	
	@GetMapping("/product/list")
	public ModelAndView getList() {
		List<Product> productList=productDAO.selectAll();
		ModelAndView mav=new ModelAndView();
		mav.addObject("productList", productList);
		mav.setViewName("admin/product/list");
		return mav;
	}
	
	@GetMapping("/product/detail")
	public ModelAndView getDtaile(int product_idx) {
		List<Category> categoryList=categoryService.selectAll();
		Product product=productDAO.select(product_idx);
		
		ModelAndView mav=new ModelAndView();
		mav.addObject("product", product);
		mav.addObject("categoryList", categoryList);
		mav.setViewName("admin/product/detail");
		
		return mav;
	}
	
}
