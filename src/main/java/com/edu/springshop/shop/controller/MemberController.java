package com.edu.springshop.shop.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.edu.springshop.domain.Member;
import com.edu.springshop.model.member.MemberService;
import com.edu.springshop.sns.GoogleLogin;
import com.edu.springshop.sns.GoogleOAuthToken;
import com.edu.springshop.sns.KakaoLogin;
import com.edu.springshop.sns.KakaoOAuthToken;
import com.edu.springshop.sns.NaverLogin;
import com.edu.springshop.sns.NaverOAuthToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class MemberController {
	Logger logger=LoggerFactory.getLogger(this.getClass());
	@Autowired
	private MemberService memberService;
	@Autowired
	private GoogleLogin googleLogin;
	@Autowired
	private KakaoLogin kakaoLogin;
	@Autowired
	private	NaverLogin naverLogin;
	
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
	
	// 이 주소는 ? 사용자 인증 정보 - 승인된 리다이렉션 URI
	@GetMapping("/sns/google/callback")
	public ModelAndView GoogleCallback(HttpServletRequest request, HttpSession session) {
		String code=request.getParameter("code");
		logger.info("사용자가 동의하고 날라오는 코드 : "+code);
		
		// 3) 우리 스프링 서버는 상대적으로 클라리언트가 외어 head+body로 POST보내기!!!
		
		// 3-1) 토큰 취득을 위한 POST 헤더와 바디 구성하기
		String url=googleLogin.getToken_request_url();
		
		// body의 parameter 구성하기 <파라미터명, 파라미터값>
		MultiValueMap<String, String> params=new LinkedMultiValueMap<String, String>();
		params.add("code", code);
		params.add("client_id", googleLogin.getClient_id());
		params.add("client_secret", googleLogin.getClient_secrete());
		params.add("redirect_uri", googleLogin.getRedirect_uri());
		params.add("grant_type", googleLogin.getGrant_type());
		
		// post 방식의 헤더(application/x-www-form-urlencoded)
		HttpHeaders headers=new HttpHeaders();
		headers.add("Content-Type", "application/x-www-form-urlencoded");
		
		HttpEntity httpEntity=new HttpEntity(params, headers);
		
		// 요청 시도를 위한 객체 생성 --> 비동기 방식의 요청을 위한 객체
		RestTemplate restTemplate=new RestTemplate();
		ResponseEntity<String> responseEntity=restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
		
		
		
		// 3-2) 토큰 요청 후 responseEntity 로부터 access_token 꺼내기!
		String body=responseEntity.getBody();
		
		logger.info("구글에서 넘겨받은 응답 정보 "+responseEntity);
		// ==> access_token 으로 날라오는 토큰이 핵심이다!
		// JSON 파싱 --> 자바의 객체 옮겨 담자!
		
		ObjectMapper objectMapper=new ObjectMapper();
		GoogleOAuthToken oAuthToken=null;
		try {
			
			// 이 객체가 토큰을 가지고 있다!!
			oAuthToken=objectMapper.readValue(body, GoogleOAuthToken.class);
			
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		
		
		// 3-3) oAuthToken 안에 있는 토큰을 이용하여 회원정보 접근
		String userinfo_url=googleLogin.getUserinfo_url();
		
		// GET 방식 요청 구성
		HttpHeaders header2=new HttpHeaders();
		header2.add("Authorization", "Bearer "+oAuthToken.getAccess_token());	// 표준 변수
		
		HttpEntity entity=new HttpEntity(header2);
		
		RestTemplate restTemplate2=new RestTemplate();
		
		// 최종 데이터 가진 객체																							// 결과 받는 객체(문자)
		ResponseEntity<String> userEntity=restTemplate2.exchange(userinfo_url, HttpMethod.GET, entity, String.class);
		
		String userBody=userEntity.getBody();
		logger.info("회원 정보 "+userBody);
		
		HashMap<String, Object> userMap=new HashMap<String, Object>();
		
		
		// 4 사용자 정보 추출하기
		ObjectMapper objectMapper2=new ObjectMapper();
		
		try {
			userMap=objectMapper2.readValue(userBody, HashMap.class);
			
			
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		String id=(String)userMap.get("id");
		String email=(String)userMap.get("email");
		String nickname=(String)userMap.get("nickname");
		
		
		// 언박싱) 자료값=자료형
		boolean varified_email=(Boolean)userMap.get("verified_email");
		logger.info("id"+id);
		logger.info("email"+email);
		logger.info("nickname"+nickname);
		
		
		/* 5 이미 DB에 이 회원의 식별 고유의 id가 존재할 경우와 그렇지 않은 경우 나누어 확인
		// 모두 세션에 담는다!
		if(false) {		// 회원아니면 회원가입
			
		}else {		//로그인만
			
		}
		*/
		return null;
	}
	
	
	
	/*----------------------------
		카카오 로그인 콜백!
	-----------------------------*/
	@GetMapping("/sns/kakao/callback")
	public ModelAndView KakaoCallback(HttpServletRequest request, HttpSession session) {
		String code=request.getParameter("code");
		logger.info("사용자가 동의하고 날라오는 코드 : "+code);
		
		// 3) 우리 스프링 서버는 상대적으로 클라리언트가 외어 head+body로 POST보내기!!!
		
		// 3-1) 토큰 취득을 위한 POST 헤더와 바디 구성하기
		String url=kakaoLogin.getToken_request_url();
		
		MultiValueMap<String, String> params=new LinkedMultiValueMap<String, String>();
		params.add("code", code);
		params.add("client_id", kakaoLogin.getClient_id());
		params.add("redirect_uri", kakaoLogin.getRedirect_uri());
		params.add("grant_type", kakaoLogin.getGrant_type());
		
		HttpHeaders headers=new HttpHeaders();
		headers.add("Content-Type", "application/x-www-form-urlencoded");
		
		HttpEntity httpEntity=new HttpEntity(params, headers);
		
		// 요청 시도를 위한 객체 생성 --> 비동기 방식의 요청을 위한 객체
		RestTemplate restTemplate=new RestTemplate();
		ResponseEntity<String> responseEntity=restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
		
		
		String body=responseEntity.getBody();
		
		logger.info("카카오에서 넘겨받은 응답 정보 "+body);
		
		
		// ==> access_token 으로 날라오는 토큰이 핵심이다!
		// JSON 파싱 --> 자바의 객체 옮겨 담자!
		
		ObjectMapper objectMapper=new ObjectMapper();
		KakaoOAuthToken oAuthToken=null;
		try {
			
			// 이 객체가 토큰을 가지고 있다!!
			oAuthToken=objectMapper.readValue(body, KakaoOAuthToken.class);
			
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		
		
		// 3-3) oAuthToken 안에 있는 토큰을 이용하여 회원정보 접근
		String userinfo_url=kakaoLogin.getUserinfo_url();
		
		// GET 방식 요청 구성
		HttpHeaders header2=new HttpHeaders();
		header2.add("Authorization", "Bearer "+oAuthToken.getAccess_token());	// 표준 변수
		
		HttpEntity entity=new HttpEntity(header2);
		RestTemplate restTemplate2=new RestTemplate();
		
		// 최종 데이터 가진 객체																							// 결과 받는 객체(문자)
		ResponseEntity<String> userEntity=restTemplate2.exchange(userinfo_url, HttpMethod.GET, entity, String.class);
		
		String userBody=userEntity.getBody();
		logger.info("회원 정보 "+userBody);
		
		HashMap<String, Object> userMap=new HashMap<String, Object>();
		
		
		// 4 사용자 정보 추출하기
		// 잭슨컨버터! jackson
		ObjectMapper objectMapper2=new ObjectMapper();
		
		try {
			userMap=objectMapper2.readValue(userBody, HashMap.class);
			
			
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		//String id=(String)userMap.get("id");
		String connected_at=(String)userMap.get("connected_at");
		
		// 내부의 json은 Map으로 처리
		Map properties=(Map)userMap.get("properties");
		String nickname=(String)properties.get("nickname");
		
		logger.info("아이디 "+userMap.get("id"));
		logger.info("닉네임 "+nickname);
		
		/*
		// 5 이미 DB에 이 회원의 식별 고유의 id가 존재할 경우와 그렇지 않은 경우 나누어 확인
		// 모두 세션에 담는다!
		if(false) {		// 회원아니면 회원가입
			
		}else {		//로그인만
			
		}
		*/
		 
		return null;
	}

	
	
	/*----------------------------
		네이버 로그인 콜백!
	-----------------------------*/
	@GetMapping("/sns/naver/callback")
	public ModelAndView NaverCallback(HttpServletRequest request, HttpSession session) {
		String code=request.getParameter("code");
		logger.info("사용자가 동의하고 날라오는 코드 : "+code);
		
		// 3) 우리 스프링 서버는 상대적으로 클라리언트가 외어 head+body로 POST보내기!!!
		
		// 3-1) 토큰 취득을 위한 POST 헤더와 바디 구성하기
		/*--------
			서버에게 요청을 보내는 코드
		---------*/
		String url=naverLogin.getToken_request_url();
		
		
		MultiValueMap<String, String> params=new LinkedMultiValueMap<String, String>();
		params.add("code", code);
		params.add("client_id", naverLogin.getClient_id());
		params.add("client_secret", naverLogin.getClient_secret());
		params.add("redirect_uri", naverLogin.getRedirect_uri());
		params.add("grant_type", naverLogin.getGrant_type());
		params.add("state", naverLogin.getState());
		
		HttpHeaders headers=new HttpHeaders();
		headers.add("Content-Type", "application/x-www-form-urlencoded");
		
		HttpEntity httpEntity=new HttpEntity(params, headers);
		
		// 요청 시도를 위한 객체 생성 --> 비동기 방식의 요청을 위한 객체
		RestTemplate restTemplate=new RestTemplate();
		ResponseEntity<String> responseEntity=restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
		
		
		String body=responseEntity.getBody();
		
		logger.info("카카오에서 넘겨받은 응답 정보 "+body);
		
		
		// ==> access_token 으로 날라오는 토큰이 핵심이다!
		// JSON 파싱 --> 자바의 객체 옮겨 담자!
		ObjectMapper objectMapper=new ObjectMapper();
		NaverOAuthToken oAuthToken=null;
		try {
			
			// 이 객체가 토큰을 가지고 있다!!
			oAuthToken=objectMapper.readValue(body, NaverOAuthToken.class);
			
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		
		// 2단계
		String userinfo_url=naverLogin.getUserinfo_url();
		
		// GET 방식 요청 구성
		HttpHeaders header2=new HttpHeaders();
		header2.add("Authorization", "Bearer "+oAuthToken.getAccess_token());	// 표준 변수
		
		HttpEntity entity=new HttpEntity(header2);
		RestTemplate restTemplate2=new RestTemplate();
		
		// 최종 데이터 가진 객체																							// 결과 받는 객체(문자)
		ResponseEntity<String> userEntity=restTemplate2.exchange(userinfo_url, HttpMethod.GET, entity, String.class);
		
		String userBody=userEntity.getBody();
		logger.info("회원 정보 "+userBody);
		

		
		HashMap<String, Object> userMap=new HashMap<String, Object>();
		
		// 4 사용자 정보 추출하기
		// 잭슨컨버터! jackson
		ObjectMapper objectMapper2=new ObjectMapper();
		
		try {
			userMap=objectMapper2.readValue(userBody, HashMap.class);
			
			
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		// 내부의 json은 Map으로 처리
		Map response=(Map)userMap.get("response");
		String id=(String)response.get("id");
		String nickname=(String)response.get("nickname");
		String gender=(String)response.get("gender");
		String email=(String)response.get("email");
		
		logger.info("아이디 "+id);
		logger.info("닉네임 "+nickname);
		logger.info("성별 "+gender);
		logger.info("이메일 "+email);

		
		
		/*
		if(false) {		// 회원아니면 회원가입
			
		}else {		//로그인만
			
		}
		*/
		 
		return null;
	}

	
	
}
