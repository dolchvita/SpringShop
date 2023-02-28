package com.edu.springshop.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edu.springshop.exception.AdminException;

public class AdminLoginCheckAdvice {
	private Logger logger=LoggerFactory.getLogger(this.getClass().getName());
	
	
	
	public Object sessionCheck(ProceedingJoinPoint joinPoint) throws AdminException, Throwable{
		Class targetClass=joinPoint.getTarget().getClass();
		
		Object[] args=joinPoint.getArgs();		// 매개변수
		
		
		logger.info("호출하려는 타겟 클래스는 "+targetClass.getName());
		logger.info("호출하려는 타겟 메서드의 매개변수의 수는 "+args.length);
		
		// 현재 이 요청에 대해 session에 값이 들어있는지 여부를 조사
		// 원래 호출하려던 메서드의 매개변수 정보를 현재 대리객체가 추출할 수 있으므로,
		// 원래 호출하려던 메서드에는 HttpServletRequest가 명시되어 있어야 한다.

		Object result=null;	// proceed 후 반환되는 객체를 담기 위한 변수
		
		
		HttpServletRequest request=null;
		HttpSession session=null;
		
		// 타겟메서드에서 HttpServletRequest 추출하여 session 에 관리자 객체 있는지 판단하기!
		for(int i=0; i<args.length; i++) {
			
			if(args[i] instanceof HttpServletRequest) {
				request=(HttpServletRequest)args[i];
			}
		}
		

		String uri=request.getRequestURI();
		if(
				uri.equals("/admin/loginform")||
				uri.equals("/admin/rest/login/admin")
		) {
			// 예외 던지기!
			result=joinPoint.proceed();

			
		}else {
			
			// 로그인이 필요한 서비스에서만 아래의 코드들이 실행 (로그인 여부 나누기!)
			session=request.getSession();
			
			if(session.getAttribute("admin")==null) {
				logger.info("aop에 의한 로그인 체크 : 세션 없음");
				
				throw new AdminException("로그인이 필요한 서비스입니다!");
				
			}else{
				// 로그인한 사람은 가던 길 가기
				result=joinPoint.proceed();
			}
		}
		
		return result;
	}
}
