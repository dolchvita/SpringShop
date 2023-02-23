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
	SqlSession sqlSession=mybatisConfig.getSqlSession();
	memberDAO.setSqlSession(sqlSession);
	
	request.setCharacterEncoding("utf-8");
%>
<jsp:useBean id="member" class="com.jspshop.domain.Member"/>
<jsp:setProperty property="*" name="member"/>
<%	// 자동 매핑!

	try{
		memberDAO.update(member);
		messageObject.setCode(1);
		messageObject.setMsg("수정 성공");
		sqlSession.commit();
		
	}catch(MemberException e){
		messageObject.setCode(0);
		messageObject.setMsg(e.getMessage());
	}
	

	mybatisConfig.release(sqlSession);

%>