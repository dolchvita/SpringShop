package com.edu.springshop.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.edu.springshop.exception.EmailException;
import com.edu.springshop.exception.HashException;
import com.edu.springshop.exception.MemberException;
import com.edu.springshop.util.Message;

/*
	하위 컨트롤러에서 예외 발생시 해당 하위 컨트롤러에 지정된 핸들러가 먼저 적용되고, 
 	만일 해당 @ExceptionHandlet가 지정되지 않으면 이 객체가 예외를 처리함!
*/

// ControllerAdvice + @ResponseBody
// RestController에서 발생하는 예외만 처리한다는 보정을 하지 않는다!
@RestControllerAdvice(annotations = RestController.class)
public class RestGlobalException {
	private Logger logger=LoggerFactory.getLogger(this.getClass().getName());
	

	@ExceptionHandler(value= {MemberException.class, EmailException.class, HashException.class})
	public ResponseEntity<Message> handle(RuntimeException e){
		logger.info("글로벌 예외 어드바이스 작동함");
		
		Message message=new Message();
		message.setMsg(e.getMessage());
		ResponseEntity entity=new ResponseEntity<Message>(message, HttpStatus.INTERNAL_SERVER_ERROR);
		return entity;
	}
	
	
}
