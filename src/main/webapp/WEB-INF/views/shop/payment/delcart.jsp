<%@page import="com.jspshop.util.ResponseMeaasge"%>
<%@page import="com.jspshop.domain.Product"%>
<%@page import="com.jspshop.domain.Cart"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%

	List<Cart> sessionCartList=(List)session.getAttribute("cartList");
	String product_idx=request.getParameter("product_idx");
	System.out.println(product_idx);
	
	int sel_index=Integer.parseInt(product_idx);
	for(int i=0; i<sessionCartList.size(); i++){
		Cart cart=sessionCartList.get(i);
		Product product=cart.getProduct();
		
		if(product_idx.equals(Integer.toString(product.getProduct_idx()))){
			System.out.println("ahffkd");
			Cart obj=sessionCartList.get(sel_index);
			sessionCartList.remove(obj);
			
			out.print(ResponseMeaasge.getMsgURL("장바구니 삭제됨", "/payment/cartList.jsp"));
		}
	}
%>

