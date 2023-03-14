package com.edu.springshop.sns;

import lombok.Data;

@Data
public class NaverLogin {
	// 인증 화면과 관련된 변수
	private String endpoint;
	private String client_id;
	private String client_secret;
	private String redirect_uri;
	private String response_type;
	private String state;
	
	// 토큰을 위한 변수
	private String token_request_url;
	private String grant_type;
	
	// 사용자 정보 조회를 위한 변수
	private String userinfo_url;
	
	
	public String getGrantUrl() {
		StringBuffer sb=new StringBuffer();
		
		sb.append(endpoint+"?client_id="+client_id);
		sb.append("&redirect_uri="+redirect_uri);
		sb.append("&response_type="+response_type);
		sb.append("&state="+state);
		
		return sb.toString();
	}
	
}
