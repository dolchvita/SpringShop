<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html lang="zxx">

<head>
	<%@ include file="../inc/header.jsp"%>
</head>

<body>
    <!-- Page Preloder -->
	<%@ include file="../inc/preloder.jsp"%>


    <!-- Offcanvas Menu Begin -->

	<%@ include file="../inc/main_navi.jsp"%>
    <!-- Offcanvas Menu End -->


    <!-- Header Section Begin -->
    <%@ include file="../inc/header_section.jsp"%>
    <!-- Header Section End -->

	
	<!-- 회원가입 컨텐츠 시작 -->
	<section>
		<div calss="container">
			<div class="row m-5">
				<div class="col">
					<form id="form1">
						<div class="form-group">
							<input type="text" class="form-control" placeholder="Enter email" name="id">
						</div>
						<div class="form-group">
							<input type="text" class="form-control" placeholder="Enter password" name="pass">
						</div>
						
						<button type="button" class="btn btn-secondary" id="bt_login">로그인</button>
						<button type="button" class="btn btn-secondary" id="bt_regist">신규가입</button>
						
						<button type="button" class="btn btn-primary" id="bt_google">구글 로그인</button>
						
						<button type="button" class="btn btn-success" id="bt_naver">네이버 로그인</button>
						<button type="button" class="btn btn-warning" id="bt_kakao">카카오 로그인</button>
					</form>
				</div>
			</div>
		</div>	
	</section>
	<!-- 회원가입 컨텐츠 끝 -->
	
	
	<!-- Instagram Begin -->
	<%@ include file="../inc/insta.jsp" %>
	<!-- Instagram End -->
	
	<!-- Footer Section Begin -->
	<%@ include file="../inc/footer.jsp" %>
	<!-- Footer Section End -->
	
	<!-- Search Begin -->
	<%@ include file="../inc/search.jsp" %>
	<!-- Search End -->
	
	<!-- Js Plugins -->
	<%@ include file="../inc/footer_link.jsp" %>

<script>

	function goToAuthForm(sns){
		$.ajax({
			url:"/rest/member/authform/"+sns,
			type:"GET",
			success:function(result, status, xhr){
				console.log("인증주소는 ", result.msg);
				location.href= result.msg;
				
			}
			
		});
		
	}

	$(function(){
		
		// 구글 로그인
		$("#bt_google").click(function(){
			// sns 사업자가 미리 만들어놓은 인증화면 주소 요청
			goToAuthForm("google");
			// 따라서 주소 및 파라미터명이 이미 정해져 있다
		
		});
		
		// 카카오 로그인
		$("#bt_kakao").click(function(){
			goToAuthForm("kakao");
		
		});
		
		// 네이버 로그인
		$("#bt_naver").click(function(){
			goToAuthForm("naver");
		
		});


		
	});
</script>
</body>

</html>