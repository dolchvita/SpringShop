package com.edu.springshop.sns;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;

import lombok.Data;

@Data
public class GoogleLogin {
	@Autowired
	private GoogleConnectionFactory googleConnectionFactory;
	@Autowired
	private OAuth2Parameters oAuth2Parameters;
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	
	
	public String handle() {
		// 1-1) 인증 화면과 관련된 URL 만들어내기
		OAuth2Operations operation=googleConnectionFactory.getOAuthOperations();
		
		String url=operation.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, oAuth2Parameters);
		logger.info("인증에 사용할 url : "+url);
		
		return url;
	}
	
	
}
