<%@page import="com.jspshop.util.MessageObject"%>
<%@page import="com.jspshop.exception.MemberException"%>
<%@page import="com.jspshop.domain.Member"%>
<%@page import="org.apache.ibatis.session.SqlSession"%>
<%@page import="com.jspshop.mybatis.MybatisConfig"%>
<%@page import="com.jspshop.repository.MemberDAO"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%!
	MemberDAO memberDAO=new MemberDAO();
	MybatisConfig mybatisConfig=MybatisConfig.getInstance();
	MessageObject messageObject=new MessageObject();
%>
<%
	SqlSession sqlSession=mybatisConfig.getSqlSession();
	memberDAO.setSqlSession(sqlSession);
	
	String member_idx=request.getParameter("member_idx");
	
	
/* 	// memberDAO.delete(Integer.parseInt(member_idx)); */


	mybatisConfig.release(sqlSession);
	
	
%>