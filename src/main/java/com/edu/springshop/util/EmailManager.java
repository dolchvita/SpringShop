package com.edu.springshop.util;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.edu.springshop.domain.Member;
import com.edu.springshop.exception.EmailException;

public class EmailManager {
	@Autowired
	private JavaMailSender javaMailSender;
	
	
	public void send(Member member) throws EmailException{
		// 메시지 객체를 생성 (이메일 정보를 담을 것)
		MimeMessage msg=javaMailSender.createMimeMessage();
		
		// 받을 사람 정보 설정
		try {
			msg.addRecipient(RecipientType.TO, new InternetAddress(member.getEmail()));		// 누구에게
			
			
			// 보내는 사람 정보 설정
			msg.addFrom(new InternetAddress[] {
					new InternetAddress("yesoe0502@gmail.com", "dolchcvita")
			});
			
			
			// 메일 보내기
			msg.setSubject("쇼핑몰 회원가입 완료");
			msg.setText("회원가입 완료되었습니다", "UTF-8");
			
			// 메일 전송
			javaMailSender.send(msg);
			
			
		} catch (AddressException e) {
			e.printStackTrace();
			throw new EmailException("받는 사람 정보 설정 실패", e);
		} catch (MessagingException e) {
			e.printStackTrace();
			throw new EmailException("받는 사람 정보 설정 실패", e);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new EmailException("보내는 사람 정보 설정 실패", e);
		}
	}

	
}
