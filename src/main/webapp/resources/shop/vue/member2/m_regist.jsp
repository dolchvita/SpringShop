<%@page import="com.google.gson.Gson"%>
<%@page import="com.jspshop.exception.MemberException"%>
<%@page import="org.apache.ibatis.session.SqlSession"%>
<%@page import="com.jspshop.util.MessageObject"%>
<%@page import="com.jspshop.mybatis.MybatisConfig"%>
<%@page import="com.jspshop.repository.MemberDAO"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%!
	MemberDAO memberDAO=new MemberDAO();
	MybatisConfig mybatisConfig=MybatisConfig.getInstance();
	MessageObject messageObject=new MessageObject();
%>
<%
	// DAO에게 주입 injection
	SqlSession sqlSession=mybatisConfig.getSqlSession();
	memberDAO.setSqlSession(sqlSession);

	// 파라미터 받기
	request.setCharacterEncoding("utf-8");		// 받기 전에 설정해야 한다!
	
%>
<jsp:useBean id="member" class="com.jspshop.domain.Member"/>
<jsp:setProperty property="*" name="member"/>
<%
	out.println(member.getId());
	out.println(member.getPass());
	out.println(member.getEmail());
	out.println(member.getName());
	
	try{
		memberDAO.insert(member);
		messageObject.setCode(1);
		messageObject.setMsg("등록 성공");
		sqlSession.commit();
		
	}catch(MemberException e){
		messageObject.setCode(0);
		messageObject.setMsg(e.getMessage());
	}
	
	Gson gson=new Gson();
	out.print(gson.toJson(messageObject));
	
	mybatisConfig.release(sqlSession);
%>






