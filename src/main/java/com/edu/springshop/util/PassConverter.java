package com.edu.springshop.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

import com.edu.springshop.exception.HashException;

// 평문을 해시값으로 변경 (개발자는 알고리즘 선택할 수 있음!)
public class PassConverter {
	
	
	public static String convertHash(String text) throws HashException{
		// 1-4) 쪼갠 값 누적시키기
		StringBuilder sb= new StringBuilder();

		try {
			MessageDigest digest=MessageDigest.getInstance("SHA-256");
			
			//1-2) 일반 스트링 쪼개기
			byte hash[]=digest.digest(text.getBytes("UTF-8"));
			
			for(int i=0; i<hash.length; i++) {
				// 1-3) 16진수 문자열로 변환
				String hex=Integer.toHexString(0xff&hash[i]);
				
				// 1-5) 64자로 만들기
				if(hex.length()==1)sb.append("0");	// 1자리 수는 2자리로
				sb.append(hex);
			}
			//System.out.println("16진수는!! "+sb.toString());
			//System.out.println("반환할 개수 "+sb.toString().length());
			
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			// 예외 전환  - 오류를 알기 위해
			throw new HashException("암호화 실패", e);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new HashException("암호화 실패", e);
		}
		
		return sb.toString();
	}
	
	
	public static void main(String[] args) {
		String hash=convertHash("apple");
		System.out.println(hash);
	}


}
