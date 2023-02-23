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
	
	String id=request.getParameter("id");
	String pass=request.getParameter("pass");
	String name=request.getParameter("name");
	String email=request.getParameter("email");
	String addr1=request.getParameter("addr1");
	String addr2=request.getParameter("addr2");
	
	Member member=new Member();
	member.setId(id);
	member.setPass(pass);
	member.setName(name);
	member.setEmail(email);
	member.setAddr1(addr1);
	member.setAddr2(addr2);
	
	memberDAO.insert(member);
	
	mybatisConfig.release(sqlSession);
	
%>