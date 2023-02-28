package com.edu.springshop.aop;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.edu.springshop.exception.AdminException;

@ControllerAdvice
public class AdminGlobalException {

	
	@ExceptionHandler(AdminException.class)
	public ModelAndView handle(AdminException e){
		// 에러를 일으키면 이 객체를 만나기 때문에 여기서 페이지 이동시키자!
		ModelAndView mav=new ModelAndView("admin/error/result");
		
		mav.addObject("e", e);
		return mav;
	}
	
}
