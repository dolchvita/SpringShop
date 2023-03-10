package com.edu.springshop.shop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.edu.springshop.domain.Member;
import com.edu.springshop.model.member.MemberService;
import com.edu.springshop.sns.GoogleLogin;

@Controller
public class MemberController {
	Logger logger=LoggerFactory.getLogger(this.getClass());
	@Autowired
	private MemberService memberService;
	@Autowired
	private GoogleLogin googleLogin;
	
	//회원가입 폼 요청
	@GetMapping("/member/joinform")
	public ModelAndView getJoinform(HttpServletRequest request) {
		return new ModelAndView("shop/member/joinform");
	}
	
	
	// 로그인 폼 요청
	@GetMapping("/member/loginform")
	public ModelAndView getLoginform(HttpServletRequest request) {
		return new ModelAndView("shop/member/loginform");
	}
	
	
	// 멤버 가입 요청
	@PostMapping("/member/regist")
	public ModelAndView regsit(Member member, HttpServletRequest request) {
		memberService.regist(member);
		ModelAndView mav=new ModelAndView("redirect:/member/loginform");
		return mav;
	}
	// 여기서 발생할 수 있는 예외를 글로벌 Exception이 대신 처리할 것
	// 이 메서드는 카테고리 메뉴가 필요없으므로, 제외 uri 추가하자!
	
	
	// 상담 게시판 페이지 요청 처리
	@GetMapping("/member/chatform")
	public ModelAndView getChatform(HttpServletRequest request) {
		return new ModelAndView("shop/member/chat");
	}
	
	
	
	
	/*----------------------------
		구글 로그인 콜백!
    -----------------------------*/
	
	// 상담 게시판 페이지 요청 처리
	@GetMapping("/member/authform/google")
	public ModelAndView getAuthForm(HttpServletRequest request) {
		String url=googleLogin.handle();
		
		ModelAndView mv=new ModelAndView("shop/member/loginform");
		mv.addObject("url", url);
		return mv;
	}

	
	
	// 이 주소는 ? 사용자 인증 정보 - 승인된 리다이렉션 URI
	@GetMapping("/member/auth/google")
	public ModelAndView callback(HttpServletRequest request, HttpSession session) {
		String code=request.getParameter("code");
		logger.info("사용자가 동의하고 날라오는 코드 : "+code);
		
		// 2) 코드 + cilentID + clientSecrt = 토큰
		OAuth2Operations operation=googleLogin.getGoogleConnectionFactory().getOAuthOperations();
		
		// 넘겨 받은 코드 이용하여 권한 객체 발급
		AccessGrant accessGrant=operation.exchangeForAccess(code, googleLogin.getOAuth2Parameters().getRedirectUri(), null);
		
		String token=accessGrant.getAccessToken();
		logger.info("발급받은 토큰은? : "+token);
				
		Connection con=googleLogin.getGoogleConnectionFactory().createConnection(accessGrant);
		Google google=(Google)con.getApi();
		String accToken=google.getAccessToken();
		
		logger.info("이건 뭐지? : "+accToken);
		
		/*  1)토큰을 이용하여 사용자의 정보를 접근한다 
		 * 		Google 객체 얻으면 됨 (Connection 객체로 얻어온다)
		 * 		
		 *  2) 사용자 정보를 MemberDTO에 담기
		 *  3) session에 DTO 담아서 로그인 처리를 해준다
		 *	 4) 로그인 후 보게 될 페이지 보여주기
		 */
		return null;
	}
	
	
	
	
}
