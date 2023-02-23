package com.edu.springshop.admin.controller;

import java.io.File;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.edu.springshop.domain.Product;
import com.edu.springshop.exception.UploadException;
import com.edu.springshop.model.category.CategoryService;
import com.edu.springshop.model.product.ProductService;
import com.edu.springshop.util.FileManager;
import com.edu.springshop.util.Message;

@RestController
@RequestMapping("/rest")
public class RestProductController {
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ProductService productService;		// 컨트롤러 계층과 모델 계층의 구분 모호..
	
	
	// 상품 등록 요청 처리
	@PostMapping("/product")
	public Message getList(Product product, HttpServletRequest request) {
		logger.info("컨트롤러가 받은 상품~~~"+product);
		
		// 웹 환경과 관련된 코드이므로 , 컨트롤러의 책임이다!
		ServletContext application=request.getSession().getServletContext();
		String path=application.getRealPath("/resources/data/");
		logger.info("저장된 실제 경로!"+path);
		
		productService.regist(product, path);
		
		return null;
	}

	@ExceptionHandler(UploadException.class)
	public ResponseEntity<Message> handle(UploadException e){
		Message message=new Message();
		message.setMsg(e.getMessage());
		ResponseEntity<Message> entity=new ResponseEntity<Message>(message, HttpStatus.INTERNAL_SERVER_ERROR);
		return entity;
	}
	
}
