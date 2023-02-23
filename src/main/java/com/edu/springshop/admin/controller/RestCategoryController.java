package com.edu.springshop.admin.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.springshop.domain.Category;
import com.edu.springshop.exception.CategoryException;
import com.edu.springshop.model.category.CategoryService;
import com.edu.springshop.util.Message;

@RestController
@RequestMapping("/rest")
public class RestCategoryController {
	@Autowired
	private CategoryService categoryService;
	private Logger logger=LoggerFactory.getLogger(this.getClass().getName());
	
	
	// 등록 요청 처리
	@PostMapping("/category")
	public Message regist(Category category) {
		categoryService.insert(category);
		Message m=new Message();
		m.setCode(0);
		m.setMsg("등록 성공");
		return m;
	}
	
	
	// 목록 요청 처리
	@GetMapping("/category")
	public List<Category> getCategory() {
		//logger.info("어떻게 날라오니~~~"+category);
		return categoryService.selectAll();
	}
	
	
	// 상세보기 컨트롤러
	@GetMapping("/category/{category_idx}")
	public Category getDetail(@PathVariable("category_idx") int category_idx) {
		logger.info("어떻게 날라오니~~~"+category_idx);
		return categoryService.select(category_idx);
	}
	

	// 수정 요청 처리
	@PutMapping("/category")
	public ResponseEntity<Message> edit(@RequestBody Category category) {
		categoryService.update(category);
		Message message=new Message();
		message.setMsg("수정 성공");
		ResponseEntity<Message> entity=new ResponseEntity<Message>(message, HttpStatus.OK);
		return entity;
	}
	
	
	// 삭제
	@DeleteMapping("/category/{category_idx}")
	public ResponseEntity<Message> delete(@PathVariable("category_idx") int category_idx){
		categoryService.delete(category_idx);
		Message message=new Message();
		message.setMsg("삭제 성공");
		ResponseEntity<Message> entity=new ResponseEntity<Message>(message, HttpStatus.OK);
		return entity;
	}
	
	
	@ExceptionHandler(CategoryException.class)
	public ResponseEntity<Message> handle(CategoryException e) {
		Message message=new Message();
		message.setMsg(e.getMessage());

		// HTTP 응답 정보를 보다 세밀하게 구성하고 싶다면 응답 메시지를 구성할 수 있는 객체를 지원한다!
		ResponseEntity<Message> entity=new ResponseEntity<Message>(message, HttpStatus.INTERNAL_SERVER_ERROR);
		return entity;
	}
	
	
}
