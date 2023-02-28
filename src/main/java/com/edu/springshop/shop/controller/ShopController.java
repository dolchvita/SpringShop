package com.edu.springshop.shop.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.edu.springshop.domain.Category;
import com.edu.springshop.domain.Product;
import com.edu.springshop.model.category.CategoryService;
import com.edu.springshop.model.product.ProductService;

@Controller
public class ShopController {
	@Autowired
	private ProductService productService;
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	// 상품 리스트 페이지 요청
	@GetMapping("/shop/list")
	public ModelAndView getList() {
		List<Product> productList=productService.selectAll();
		ModelAndView mav=new ModelAndView("shop/shop");
		mav.addObject("productList", productList);

		return mav;
	}
	
	
	// 상품 상세 요청 처리
	@GetMapping ("/shop/detail")
	public ModelAndView getDetail(int product_idx) {
		Product product=productService.select(product_idx);
		ModelAndView mav=new ModelAndView("shop/shop_detail");
		logger.info("디테일 컨트롤러에서 "+product);
		mav.addObject("product", product);
		return mav;
	}
	
	
	// 컨트롤러 메서드의 반환형 바꿔보기!
	@GetMapping("/shop/test")
	public String test() {
		// ModelAndView 에서 모델을 제외한 View만 반환
		return "shop/test_result";
	}
	
}
