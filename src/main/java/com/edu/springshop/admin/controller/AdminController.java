package com.edu.springshop.admin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.edu.springshop.domain.Admin;
import com.edu.springshop.exception.AdminException;
import com.edu.springshop.model.admin.AdminService;
import com.edu.springshop.util.Message;

@Controller
public class AdminController {
	private Logger logger=LoggerFactory.getLogger(this.getClass().getName());
	@Autowired
	private AdminService adminService;
	
	
	@GetMapping("/main")
	public ModelAndView getMain(HttpServletRequest request) {
		
		// 로그인 인증 여부 따져보기!
		HttpSession session=request.getSession();
		Admin admin=(Admin)session.getAttribute("admin");
		
		ModelAndView mav=new ModelAndView();

		if(admin==null) {
			// 아래 예외 핸들러에게 던지기
			throw new AdminException("로그인이 필요한 서비스입니다");
			
		}else {
			mav.setViewName("admin/index");
		}
		return mav;
	}
	
	
	@GetMapping("/loginform")
	public ModelAndView getLoginForm(HttpServletRequest request) {		// aop의 대상 조건이 되려면 매개변수에 request가 있어야 한다!
		return new ModelAndView("admin/login/loginform");
	}
	
	
	@PostMapping("/login")
	public ModelAndView login(HttpServletRequest request, Admin admin) {
		Admin obj=adminService.select(admin);
		
		HttpSession session=request.getSession();
		session.setAttribute("admin", obj);
		
		ModelAndView mav=new ModelAndView("redirect:/admin/main");
		return mav;
	}
	
	
	/*
	 * @ExceptionHandler(AdminException.class) public ModelAndView
	 * handle(AdminException e){ // 에러를 일으키면 이 객체를 만나기 때문에 여기서 페이지 이동시키자!
	 * ModelAndView mav=new ModelAndView("error/result");
	 * 
	 * mav.addObject("e", e); return mav; }
	 */
	
}
