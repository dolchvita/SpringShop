package com.edu.springshop.admin.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.springshop.domain.Product;
import com.edu.springshop.model.category.CategoryService;
import com.edu.springshop.util.Message;

@RestController
@RequestMapping("/rest")
public class RestProductController {
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	
	// 상품 등록 요청 처리
	@PostMapping("/product")
	public Message getList(Product product) {
		logger.info("컨트롤러가 받은 상품~~~"+product);

		return null;
	}

}
