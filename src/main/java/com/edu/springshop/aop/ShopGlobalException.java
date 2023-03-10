package com.edu.springshop.aop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.edu.springshop.exception.EmailException;
import com.edu.springshop.exception.HashException;
import com.edu.springshop.exception.MemberException;

// 쇼핑몰과 관련된 글로벌(전역적) 예외 객체
@ControllerAdvice(annotations = Controller.class)
public class ShopGlobalException {

	
	@ExceptionHandler(HashException.class)
	public ModelAndView handle(HashException e) {
		ModelAndView mav=new ModelAndView("shop/error/result");
		mav.addObject("e", e);
		return mav;
	}
	
	@ExceptionHandler(MemberException.class)
	public ModelAndView handle(MemberException e) {
		ModelAndView mav=new ModelAndView("shop/error/result");
		mav.addObject("e", e);
		return mav;
	}
	@ExceptionHandler(EmailException.class)
	public ModelAndView handle(EmailException e) {
		ModelAndView mav=new ModelAndView("shop/error/result");
		mav.addObject("e", e);
		return mav;
	}
	
}
