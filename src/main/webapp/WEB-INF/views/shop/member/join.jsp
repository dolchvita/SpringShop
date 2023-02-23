<%@page import="com.jspshop.util.ResponseMeaasge"%>
<%@page import="com.jspshop.exception.MemberException"%>
<%@page import="org.apache.ibatis.session.SqlSession"%>
<%@page import="com.jspshop.repository.MemberDAO"%>
<%@page import="com.jspshop.mybatis.MybatisConfig"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%!
	MybatisConfig mybatisConfig=MybatisConfig.getInstance();
	MemberDAO memberDAO=new MemberDAO();
	ResponseMeaasge responseMeaasge=new ResponseMeaasge();
%>
<jsp:useBean id="member" class="com.jspshop.domain.Member"></jsp:useBean>
<%
	request.setCharacterEncoding("utf-8");
%>
<jsp:setProperty property="*" name="member"/>
<%
	SqlSession sqlSession=mybatisConfig.getSqlSession();
	memberDAO.setSqlSession(sqlSession);
	
	try{
		memberDAO.insert(member);
		sqlSession.commit();
		
		out.print(responseMeaasge.getMsgURL("등록 성공", "/"));

	}catch(MemberException e){
		out.print(responseMeaasge.getMsgBack(e.getMessage()));
		
	}finally{
		mybatisConfig.release(sqlSession);
	}
	
	
%>