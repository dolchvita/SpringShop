package com.edu.springshop.shop.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.springshop.domain.Member;
import com.edu.springshop.exception.EmailException;
import com.edu.springshop.exception.HashException;
import com.edu.springshop.exception.MemberException;
import com.edu.springshop.model.member.MemberService;
import com.edu.springshop.util.Message;

@RestController
@RequestMapping("/rest")
public class RestMemberController {
	@Autowired
	private MemberService memberService;
	Logger logger=LoggerFactory.getLogger(this.getClass());
	
	
	// 회원가입 요청 처리
	@PostMapping("/member")
	public ResponseEntity<Message> regist(Member member) {
		logger.info("멤버 상태 ~ "+member);
		memberService.regist(member);
		Message message=new Message();
		message.setMsg("회원가입 성공");
		ResponseEntity entity=new ResponseEntity<Message>(message, HttpStatus.OK);
		return entity;
	}
	
	
	
	
	/*---------------------------
		예외처리
	 ---------------------------*/
	@ExceptionHandler(HashException.class)
	public ResponseEntity<Message> handle(HashException e){
		Message message=new Message();
		message.setMsg(e.getMessage());
		ResponseEntity entity=new ResponseEntity<Message>(message, HttpStatus.INTERNAL_SERVER_ERROR);
		return entity;
	}
	@ExceptionHandler(EmailException.class)
	public ResponseEntity<Message> handle(EmailException e){
		Message message=new Message();
		message.setMsg(e.getMessage());
		ResponseEntity entity=new ResponseEntity<Message>(message, HttpStatus.INTERNAL_SERVER_ERROR);
		return entity;
	}
	@ExceptionHandler(value= {MemberException.class})
	public ResponseEntity<Message> handle(MemberException e){
		Message message=new Message();
		message.setMsg(e.getMessage());
		ResponseEntity entity=new ResponseEntity<Message>(message, HttpStatus.INTERNAL_SERVER_ERROR);
		return entity;
	}

}
