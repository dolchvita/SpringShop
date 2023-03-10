<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@ include file="../inc/header_link.jsp"%>
</head>
<body>
	<div class="wrapper">

		<div class="card card-info m-3">
			<div class="card-header">
				<h3 class="card-title">관리자 로그인</h3>
			</div>
			<!-- /.card-header -->
			<!-- form start -->
			<form class="form-horizontal" id="form1">
				<div class="card-body">

					<div class="form-group row" id="loding-bar">
						<div class="overlay">
							<i class="fas fa-3x fa-sync-alt fa-spin"></i>
							<div class="text-bold pt-2">잠시 대기중</div>
						</div>
					</div>

					<div class="form-group row">
						<label for="inputEmail3" class="col-sm-2 col-form-label">ID</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" name="admin_id" placeholder="Your Id">
						</div>
					</div>
					<div class="form-group row">
						<label for="inputPassword3" class="col-sm-2 col-form-label">Password</label>
						<div class="col-sm-10">
							<input type="password" class="form-control" name="admin_pass" placeholder="Yore Password">
						</div>
					</div>

				</div>
				</form>
				<!-- /.card-body -->
				
				<div class="card-footer">
					<button type="button" class="btn btn-info" id="bt_loginAsync">비동기 로그인</button>
					<button type="button" class="btn btn-info" id="bt_login">동기 로그인</button>
					<button type="submit" class="btn btn-default float-right">Cancel</button>
				</div>
				
				<!-- /.card-footer -->
		</div>
		<%@ include file="../inc/footer.jsp"%>
		<!-- Control Sidebar -->
		<%@ include file="../inc/sidebar_right.jsp"%>
		<!-- /.control-sidebar -->
	</div>
	<!-- ./wrapper -->
	
<script type="text/javascript">
	function loginAsync(){
		let formData=$("#form1").serialize();
		
		$.ajax({
			// 회원가입 양식과 똑같으므로 로그인이라는 디렉토리 표시 해주기
			url:"/admin/rest/login/admin",
			
			type:"post",
			data:formData,
			success:function(result,status,xhr){
				alert(result.msg);
				location.href="/admin/main";
			},
			error:function(xhr, status, err){
				let json=JSON.parse(xhr.responseText);
				alert(json.msg);
			}
		});
	}
	
	function login(){
		
		$("#form1").attr({
			action:"/admin/login",
			method:"post"
		});
		
		$("#form1").submit();
	}
	

	$(function(){
		
		// 비동기 로그인
		$("#bt_loginAsync").click(function(){
			loginAsync();
		});
		
		// 동기 로그인
		$("#bt_login").click(function(){
			login();
		});
		
	});

</script>
</body>
</html>