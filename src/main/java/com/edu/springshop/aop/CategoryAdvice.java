package com.edu.springshop.aop;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.edu.springshop.domain.Category;
import com.edu.springshop.model.category.CategoryService;

// 쇼핑몰 공통 코드를 AOP 적용
public class CategoryAdvice {
	private Logger logger=LoggerFactory.getLogger(this.getClass().getName());
	@Autowired
	private CategoryService categoryService;
	
	
	// 서비스의 selectAll()을 호출하여 MaV에 저장
	public Object getCategoryList(ProceedingJoinPoint joinPoint) throws Throwable{
		Object obj=null;
		
		// 원래 호출하려던 메서드들 호출 전, 후에 관여할 수 있는 기능 지원
		String target=joinPoint.getTarget().getClass().getName();
		//logger.info("타겟은?!!"+target);
		Signature sig=joinPoint.getSignature();
		String method=sig.getName(); 		//원래 호출하려던 메서드~?
		logger.info(method);
		
		
				
		Object[] args=joinPoint.getArgs();
		HttpServletRequest request=null;
		
		for(Object arg:args) {
			
			if(arg instanceof HttpServletRequest) {
				request=(HttpServletRequest)arg;
			}
		}
		
		// 제외될 요청 uri (카테고리 처리가 필요없는 요청들!)
		String uri=request.getRequestURI();
		if(
			uri.equals("/rest/member")||
			uri.equals("/member/login")
				) {
			obj=joinPoint.proceed();
			
			
		}else {
			List categoryList=categoryService.selectAll();
			ModelAndView mav=null;
		
			obj=joinPoint.proceed();		// 원래 호출하려던 메서드 호출!
			
			// obj의 자료형이 ModelAndView라면..
			if(obj instanceof ModelAndView) {
				
				mav=(ModelAndView) obj;		// 반환값
				mav.addObject("categoryList", categoryList);
				obj=mav;
			}
			
			
		}

		
		return obj;
	}
	
}
