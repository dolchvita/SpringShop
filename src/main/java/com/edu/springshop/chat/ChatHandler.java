package com.edu.springshop.chat;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

// 웹 소켓 요청을 처리할 핸들러
// TextWebSocketHandler : 이미지, 동영상, 텍스트 다 가능하지만 채팅을 위함
public class ChatHandler extends TextWebSocketHandler{
	Logger logger=LoggerFactory.getLogger(this.getClass());
	
	//접속자 보관 리스트
	// 접속자를 WebSocketSession 이라고 한다!
	List<WebSocketSession> sessionList=new ArrayList<WebSocketSession>();
	
	
	//클라이언트가 접속
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		super.afterConnectionEstablished(session);
		logger.info("afterConnectionEstablished 호출");
		
		sessionList.add(session);		// 접속자 추가
		logger.info("접속자 수 : "+sessionList.size());
		
	}
	
	
	// 메시지가 도착하면
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		super.handleTextMessage(session, message);
		String msg=message.getPayload();
		logger.info("클라이언트 메시지 "+msg);
		
		TextMessage data=new TextMessage("서버가 보낸 메시지"+message.getPayload());
		
		// 접속한 모든 사용자에게 메시지 보내기
		for(WebSocketSession ss :sessionList) {
			
			ss.sendMessage(data);
		}
		
	}
	
	
	
	// 접속 끊기면
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		super.afterConnectionClosed(session, status);
		logger.info("afterConnectionClosed 호출");
		sessionList.remove(session);
	}
	
	
	
}
