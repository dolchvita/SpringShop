package com.edu.springshop.sns;


import lombok.Data;

@Data
public class NaverOAuthToken {
	
	//제이슨으로 날라온 목록들을 담아놓은 것!
	private String access_token;
	private String token_type;
	// 재발급시 필요
	private String refresh_token;
	private int expires_in;

}
