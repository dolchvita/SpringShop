<%@page import="com.jspshop.domain.Member"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.ibatis.session.SqlSession"%>
<%@page import="com.jspshop.exception.MemberException"%>
<%@page import="com.jspshop.util.ResponseMeaasge"%>
<%@page import="com.jspshop.mybatis.MybatisConfig"%>
<%@page import="com.jspshop.repository.MemberDAO"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%!
	MybatisConfig mybatisConfig=MybatisConfig.getInstance();
	MemberDAO memberDAO=new MemberDAO();
	ResponseMeaasge responseMeaasge=new ResponseMeaasge();
%>
<%
	request.setCharacterEncoding("utf-8");

	SqlSession sqlSession=mybatisConfig.getSqlSession();
	memberDAO.setSqlSession(sqlSession);

	
	String id=request.getParameter("id");
	String pass=request.getParameter("pass");
	out.println("아이디"+id);
	out.println("패스"+pass);
	
	
	Member member=new Member();
	
	member.setId(id);
	member.setPass(pass);
	
	
	try{
		Member obj=memberDAO.select(member);			
		session.setAttribute("member", obj);
		
		// 메인 페이지 보여주기
		out.print(responseMeaasge.getMsgURL("로그인 성공", "/"));
		
	}catch(MemberException e){
		out.print(responseMeaasge.getMsgBack(e.getMessage()));
	}finally{
		mybatisConfig.release(sqlSession);		
	}
	

	
	
%>