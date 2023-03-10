package com.edu.springshop.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.edu.springshop.exception.AdminException;
import com.edu.springshop.exception.EmailException;
import com.edu.springshop.exception.HashException;
import com.edu.springshop.exception.MemberException;
import com.edu.springshop.util.Message;

// 관리자와 관련된 글로벌 예외처리 객체
// 단, 응답 형식은 비동기 -> 데이터 형태 반환
@RestControllerAdvice(annotations = RestController.class)
public class RestAdminGlobalException {

	private Logger logger=LoggerFactory.getLogger(this.getClass().getName());
	

	@ExceptionHandler(value= {AdminException.class})
	public ResponseEntity<Message> handle(RuntimeException e){
		logger.info("관리자의 Rest 글로벌 예외 어드바이스 작동함");
		
		Message message=new Message();
		message.setMsg("Admin Rest 글로벌 예외"+e.getMessage());
		ResponseEntity entity=new ResponseEntity<Message>(message, HttpStatus.INTERNAL_SERVER_ERROR);
		return entity;
	}
	
	
}
