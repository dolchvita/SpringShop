
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html lang="zxx">

<head>
<%@ include file="/inc/header.jsp"%>
</head>

<body>
    <!-- Page Preloder -->
	<%@ include file="/inc/preloder.jsp"%>


    <!-- Offcanvas Menu Begin -->
    
    <!-- jsp자체에서 지원하는 태그!! 
    	  왜 써야 하나? 사실 jsp는 사실 디자인 영역이므로, 개발자만 사용하는 것이 아니라
    	  퍼블리셔, 웹디자이너와 공유한다. 이때 java에 대한 비전문가들은 java 코드를 이해할 수 없기 때문에
    	  그들이 쉽게 지원해준다 (협업 때문에)-->
	<%@ include file="/inc/header.jsp"%>
    <!-- Offcanvas Menu End -->


    <!-- Header Section Begin -->
    <%@ include file="/inc/header_section.jsp"%>
    <!-- Header Section End -->



<!-- Instagram Begin -->
<%@ include file="/inc/insta.jsp" %>
<!-- Instagram End -->

<!-- Footer Section Begin -->
<%@ include file="/inc/footer.jsp" %>
<!-- Footer Section End -->

<!-- Search Begin -->
<%@ include file="/inc/search.jsp" %>
<!-- Search End -->

<!-- Js Plugins -->
<%@ include file="/inc/footer_link.jsp" %>

</body>

</html>