<%@page import="com.edu.springshop.domain.Category"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	List<Category> categoryList=(List)request.getAttribute("categoryList");
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>상품 등록</title>
<%@ include file="../inc/header_link.jsp"%>
<style type="text/css">
	.box-style{
		width:90px;
		height:95px;
		border:1px solid #ccc;
		display:inline-block;
		margin:5px;
	}
	.box-style img{
		width:65px;
		height:55px;
	}
	.box-style div{
		text-align:right;
		margin-right:5px;
		font-weight:bold;
	}
</style>
</head>
<body class="hold-transition sidebar-mini layout-fixed">
	<div class="wrapper">

		<!-- Preloader -->
		<%@ include file="../inc/preloader.jsp" %>
		
		<!-- Navbar -->
		<%@ include file="../inc/navbar.jsp" %>
		<!-- /.navbar -->

		<!-- Main Sidebar Container -->
		<%@ include file="../inc/sidebar_left.jsp" %>
		
		
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<div class="content-header">
				<div class="container-fluid">
					<div class="row mb-2">
						<div class="col-sm-6">
							<h1 class="m-0">상품등록</h1>
						</div>
						<!-- /.col -->
						<div class="col-sm-6">
							<ol class="breadcrumb float-sm-right">
								<li class="breadcrumb-item"><a href="#">Home</a></li>
								<li class="breadcrumb-item active">상품관리> 상품등록</li>
							</ol>
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.container-fluid -->
			</div>
			<!-- /.content-header -->

			<!-- Main content -->
			<section class="content" id="app1">
				<div class="container-fluid">
				
					<!-- Main row -->
					<div class="row">
						<div class="col-6">


							<div class="form-group row">
								<div class="col-9">
	
								<form id="form1">
								
									<input type="text" name="category_name" class="form-control" placeholder="상품명">
									<div class="col-3">
										<button type="button" class="btn btn-danger btn-md"
											id="bt_regist">등록</button>
									</div>
								</form>

								</div>
							</div>



							<div class="card">
								<div class="card-header">
									<h3 class="card-title">Responsive Hover Table</h3>

									<div class="card-tools">
										<div class="input-group input-group-sm" style="width: 150px;">
											<input type="text" name="table_search"
												class="form-control float-right" placeholder="Search">

											<div class="input-group-append">
												<button type="submit" class="btn btn-default">
													<i class="fas fa-search"></i>
												</button>
											</div>
										</div>
									</div>
								</div>
								<!-- /.card-header -->
								<div class="card-body table-responsive p-0">
									<table class="table table-hover text-nowrap">
										<thead>
											<tr>
												<th>No</th>
												<th>카테고리명</th>
											</tr>
										</thead>
										<tbody>
										
											<template v-for="category in categoryList">
												<row :json="category" :key="category.category_idx"/>
											</template>
							
										</tbody>
									</table>
								</div>
								<!-- /.card-body -->
							</div>


						</div>
						<div class="col-6">
							<div class="form-group row">
								<div class="col-9">
									<form id="form2">
										<input type="hidden" name="_method">
										<input type="hidden" name="category_idx">
										<input type="text" name="category_name" class="form-control" placeholder="상품명" value="">
										<div class="col-3">
											<button type="button" class="btn btn-danger btn-md" id="bt_edit">수정</button>
											<button type="button" class="btn btn-danger btn-md" id="bt_del">삭제</button>
										</div>
									</form>

								</div>
							</div>	
						</div><!-- 수정 삭제 폼 -->
						
					</div>
					<!-- /.row (main row) -->
				</div>
				<!-- /.container-fluid -->
			
			</section>
			<!-- /.content -->
		</div>
		<!-- /.content-wrapper -->
		
		<%@ include file="../inc/footer.jsp" %>		

		<!-- Control Sidebar -->
		<%@ include file="../inc/sidebar_right.jsp" %>
		<!-- /.control-sidebar -->
	</div>
	<!-- ./wrapper -->
	<%@ include file="../inc/footer_link.jsp" %>
	<script type="text/javascript">
	let app1;
	let selectedRow;		// 선택된 row (tr세트)
	
	
	const row={
			template:`
				<tr>
					<td>{{category.category_idx}}</td>
					<td @click="getDetail(category)"><a href="#">{{category.category_name}}</a></td>
				</tr>
			
			`,
			props:["json"],
			data(){
				return{
					category:this.json
				}
			},
			
			methods:{
				getDetail:function(category){
					$("#form2 input[name='category_idx']").val(category.category_idx);
					$("#form2 input[name='category_name']").val(category.category_name);
					console.log(this);
					selectedRow=this;
				},
				
				updateData:function(){
					this.$nextTick(function(){
						getList();
					});
				}
			}
	}
	
	
	function init(){
		app1=new Vue({
			el:"#app1",
			data:{
				categoryList:[]
			},
			components:{
				row
			}
		});
		console.log("과연~~~~", app1.categoryList);
	}
	
	
	
	// 카테고리 가져오기
	function getList(){
		$.ajax({
			url:"/admin/rest/category",
			type:"get",
			success:function(result, status, xhr){
				app1.categoryList=result;
			}
		});
		
	}
	
	
	function regist(){
		// 비동기로 전송할 예정
		
		$.ajax({
			url:"/admin/rest/category",
			type:"post",
			data:{
				category_name:$("input[name='category_name']").val()
			},
			
			// 서버로부터 전송된 HTTp 응답 헤더 정보가 성공일때
			success:function(result, status, xhr){
				alert(result.msg);
				getList();
			},
			
			// 에러도 잡아보자
			error:function(xhr, status, err){
				alert("에러에요~~"+err);		// 여기서 err은 500으로 찍힌다!
			}
		});
	}
	
	
	function editAsync(){
		// 전송 데이터를 ajax 형식으로 전송
		let json={};
		json['category_idx']=$("#form2 input[name='category_idx']").val();
		json['category_name']=$("#form2 input[name='category_name']").val();
		console.log(json);
		// 웹상에 데이터 교환시 데이터 형식은 무조건 문자열이 되어야 한다!
		
 		$.ajax({
			url:"/admin/rest/category",
			type:"put",
			contentType:"application/json;charset=utf-8",		// 헤더 정보
			processData:false,		// 쿼리스트링화 여부
			data:JSON.stringify(json),		// body 정보 구성
			success:function(result, status, xhr){
				alert("수정 성공");
				
				// 수정된 내용만 컴포넌트에 반영하기
				selectedRow.category=json;
			}
		});
	}
	
	
	
	
	function delAsync(){
		$.ajax({
			url:"/admin/rest/category/"+$("#form2 input[name='category_idx']").val(),
			type:"delete",
			success:function(result, status, xhr){
				alert("삭제 성공");
				getList();
			}
			
		});
		
	}
	
	
	$(function(){
		
		//비동기로 카테고리 목록 가져오기		// 비동기로 리스트를 가져오는 것이 먼저 와야 한다!
		getList();
		init();
		
		//등록 이벤트 연결 
		$("#bt_regist").click(function(){
			regist();
		});
		
		$("#bt_edit").click(function(){
			if(confirm("수정하시겠습니까?")){
				editAsync();
			}
		});
		
		$("#bt_del").click(function(){
			if(confirm("삭제하시겠습니까?")){
				delAsync();
			}
		});
		
		
	})
	
	</script>
</body>

</html>

