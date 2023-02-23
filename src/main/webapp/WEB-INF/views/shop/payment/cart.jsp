<%@page import="com.jspshop.domain.Product"%>
<%@page import="org.apache.ibatis.session.SqlSession"%>
<%@page import="com.jspshop.repository.ProductDAO"%>
<%@page import="com.jspshop.mybatis.MybatisConfig"%>
<%@page import="org.apache.ibatis.reflection.SystemMetaObject"%>
<%@page import="com.jspshop.domain.Member"%>
<%@page import="com.jspshop.domain.Cart"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%! 
	MybatisConfig mybatisConfig=MybatisConfig.getInstance();
	ProductDAO productDAO=new ProductDAO();
%>
<%
	// 클라이언트의 장바구니 등록 요청을 처리한다
	
	// 지금 요청이 만일 최초의 요청이라면, 세션 객체 생성 및 ID 할당
	// 최초 요청 판단은? --> 클라이언트의 브라우저에 흔적을 남긴 cookie ID의 존재 여부
	String sid=session.getId();
	
	System.out.println("이 요청에 대해 생성된 세션 ID "+sid);
	
	// 장바구니 목록을 표현하기 위한 순서있는 컬렉션인 List를 준비하자
	List<Cart> cartList=new ArrayList();
	
	/* 2리스트를 세션에 담지 않으면, 서비스 메서드의 지역변수이므로 요청시마다 생성되어 소멸되기를 반복한다.
	 	따라서 생명을 유지할 수 있는 보다 전역적인 영역에 List를 보관해놓자! Session이 가장 적당!
	 	application 객체 담으면 톰켓이 꺼질 때까지 사용사능
	 	session : 세션 끊길 때 (브라우저 닫거나, 일정시간 요청이 없거나)
	*/
	
	if(session.getAttribute("cartList")==null){		// 이미 카트가 세션에 담겨 있으면 덮어쓰지 않는다!
		session.setAttribute("cartList", cartList);
	}

	
	
	// 1-1) 누가!
	// 원래는 로그인한 유저를 대상으로 하므로, session.Attribute() 로 얻어와야 한다 (추후 진행)
	Member member=(Member)session.getAttribute("member");
	
	
	// 1-2) 무엇을?
	String product_idx=request.getParameter("product_idx");
	
	SqlSession sqlSession=mybatisConfig.getSqlSession();
	productDAO.setSqlSession(sqlSession);
	Product product=productDAO.select(Integer.parseInt(product_idx));
	
	
	
	// 몇 개나?
	Cart cart=new Cart();
	
	cart.setMember(member);
	cart.setProduct(product);
	cart.setEa(1);		// 목록을 통해 담을 때는 1개를 디폴트로 담는다!
	
	
	// 한 건의 장바구니 객체를 List에 담자!
	List sessionCartList=(List)session.getAttribute("cartList");
	sessionCartList.add(cart);
	
	
	System.out.println("현재 장바구니 "+sessionCartList.size()+"건 담김");
	out.print("장바구니에 상품을 담았습니다");
	
	
%>