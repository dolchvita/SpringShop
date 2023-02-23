<%@page import="com.jspshop.util.ResponseMeaasge"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	// 기존 회원이 사용 중이던 세션을 사용하지 못하게 비활성화 하면 됨
	session.invalidate();
	out.print(ResponseMeaasge.getMsgURL("로그아웃처리 됨", "/"));
%>