package com.edu.springshop.shop.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MemberController {
	Logger logger=LoggerFactory.getLogger(this.getClass());
	
	//회원가입 폼 요청
	@GetMapping("/member/joinform")
	public ModelAndView getJoinform() {
		logger.info("동작하니");
		
		return new ModelAndView("shop/member/joinform");
	}
	
}
