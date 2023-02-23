<%@page import="com.google.gson.Gson"%>
<%@page import="com.jspshop.exception.MemberException"%>
<%@page import="org.apache.ibatis.session.SqlSession"%>
<%@page import="com.jspshop.util.MessageObject"%>
<%@page import="com.jspshop.mybatis.MybatisConfig"%>
<%@page import="com.jspshop.repository.MemberDAO"%>
<%@ page contentType="application/json; charset=UTF-8"%>
<%!
	MemberDAO memberDAO=new MemberDAO();
	MybatisConfig mybatisConfig=MybatisConfig.getInstance();
	MessageObject messageObject=new MessageObject();
%>
<%
	SqlSession sqlSession=mybatisConfig.getSqlSession();
	memberDAO.setSqlSession(sqlSession);
	
	String member_idx=request.getParameter("member_idx");
	
	
	try{
		memberDAO.delete(Integer.parseInt(member_idx));
		messageObject.setCode(1);
		messageObject.setMsg("삭제성공");
		sqlSession.commit();
		
	}catch(MemberException e){
		messageObject.setCode(0);
		messageObject.setMsg(e.getMessage());
	}


	Gson gson=new Gson();
	out.print(gson.toJson(messageObject));
	
	mybatisConfig.release(sqlSession);
	
%>
