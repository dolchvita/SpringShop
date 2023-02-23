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
	
	String id=request.getParameter("id2");
	String pass=request.getParameter("pass2");
	String name=request.getParameter("name2");
	String email=request.getParameter("email2");
	String addr1=request.getParameter("addr12");
	String addr2=request.getParameter("addr22");
	String member_idx=request.getParameter("member_idx");
	
	Member member=new Member();
	member.setMember_idx(Integer.parseInt(member_idx));
	member.setId(id);
	member.setPass(pass);
	member.setName(name);
	member.setEmail(email);
	member.setAddr1(addr1);
	member.setAddr2(addr2);
	
/* 	int result=memberDAO.update(member);
	if(result>0){
		out.print("수정 성공");
		sqlSession.commit();
	}

	 */
	mybatisConfig.release(sqlSession);
	
	
%>